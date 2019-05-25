package es.formulastudent.app.mvp.data.business.endurance.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.endurance.EnduranceBO;
import es.formulastudent.app.mvp.data.model.EnduranceRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.User;

public class EnduranceBOFirebaseImpl implements EnduranceBO {

    private FirebaseFirestore firebaseFirestore;

    public EnduranceBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveEnduranceRegisters(Date from, Date to, String teamID, final BusinessCallback callback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_ENDURANCE);

        //Competition day filter
        if(from != null && to != null){
            query = query.whereLessThanOrEqualTo(EventRegister.DATE, to);
            query = query.whereGreaterThan(EventRegister.DATE, from);
        }

        //Teams filter
        if(teamID != null && !teamID.equals("-1")){
            query = query.whereEqualTo(EventRegister.TEAM_ID, teamID);
        }

        query.orderBy(EventRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //Response object
                        ResponseDTO responseDTO = new ResponseDTO();

                        if (task.isSuccessful()) {
                            List<EnduranceRegister> result = new ArrayList<>();

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EnduranceRegister enduranceRegister = new EnduranceRegister(document);
                                result.add(enduranceRegister);
                            }

                            responseDTO.setData(result);

                        } else {
                            //TODO añadir mensaje de error
                            //responseDTO.getErrors().add(R.string.mensajedeerror);
                        }

                        callback.onSuccess(responseDTO);
                    }
                });
    }


    @Override
    public void createEnduranceRegistry(User user, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Date registerDate = Calendar.getInstance().getTime();
        EnduranceRegister enduranceRegister = new EnduranceRegister(user, registerDate, null, null, null);

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_ENDURANCE)
                .document(enduranceRegister.getID())
                .set(enduranceRegister.toObjectData())
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO añadir mensaje de error
                        responseDTO.getErrors().add("");
                        callback.onFailure(responseDTO);
                    }
                });
    }
}

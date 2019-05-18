package es.formulastudent.app.mvp.data.business.acceleration.impl;

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
import es.formulastudent.app.mvp.data.business.acceleration.AccelerationBO;
import es.formulastudent.app.mvp.data.model.AccelerationRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.User;

public class AccelerationBOFirebaseImpl implements AccelerationBO {

    private FirebaseFirestore firebaseFirestore;

    public AccelerationBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveAccelerationRegisters(Date from, Date to, String teamID, final BusinessCallback callback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_ACCELERATION);

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
                            List<AccelerationRegister> result = new ArrayList<>();

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AccelerationRegister accelerationRegister = new AccelerationRegister(document);
                                result.add(accelerationRegister);
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
    public void createAccelerationRegistry(User user, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Date registerDate = Calendar.getInstance().getTime();
        AccelerationRegister accelerationRegister = new AccelerationRegister(user, registerDate);

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_ACCELERATION)
                .document(accelerationRegister.getID())
                .set(accelerationRegister.toObjectData())
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

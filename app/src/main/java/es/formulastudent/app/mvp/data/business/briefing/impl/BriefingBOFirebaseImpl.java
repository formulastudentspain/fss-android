package es.formulastudent.app.mvp.data.business.briefing.impl;

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
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.User;

public class BriefingBOFirebaseImpl implements BriefingBO {

    private FirebaseFirestore firebaseFirestore;

    public BriefingBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveBriefingRegisters(Date from, Date to, String teamID, final BusinessCallback callback) {

        Query query = firebaseFirestore.collection(BriefingRegister.COLLECTION_ID);

        //Competition day filter
        if(from != null && to != null){
            query = query.whereLessThanOrEqualTo(BriefingRegister.DATE, to);
            query = query.whereGreaterThan(BriefingRegister.DATE, from);
        }

        //Teams filter
        if(teamID != null && !teamID.equals("-1")){
            query = query.whereEqualTo(BriefingRegister.TEAM_ID, teamID);
        }

        query.orderBy(BriefingRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //Response object
                        ResponseDTO responseDTO = new ResponseDTO();

                        if (task.isSuccessful()) {
                            List<BriefingRegister> result = new ArrayList<>();

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BriefingRegister briefingRegister = new BriefingRegister(document);
                                result.add(briefingRegister);
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
    public void createBriefingRegistry(User user, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Date registerDate = Calendar.getInstance().getTime();
        BriefingRegister briefingRegister = new BriefingRegister(user, registerDate);

        firebaseFirestore.collection(BriefingRegister.COLLECTION_ID)
                .document(briefingRegister.getID())
                .set(briefingRegister.toObjectData())
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

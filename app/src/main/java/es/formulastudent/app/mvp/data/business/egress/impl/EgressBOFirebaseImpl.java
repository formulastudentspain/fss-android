package es.formulastudent.app.mvp.data.business.egress.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.model.EgressRegister;

public class EgressBOFirebaseImpl implements EgressBO {


    private FirebaseFirestore firebaseFirestore;

    public EgressBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveEgressByPreScrutineeringId(String preScrutineeringID, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_EVENT_CONTROL_EGRESS)
                .whereEqualTo(EgressRegister.PRESCRUTINEERING_ID, preScrutineeringID)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){
                    EgressRegister egressRegister = new EgressRegister(queryDocumentSnapshots.getDocuments().get(0));
                    responseDTO.setData(egressRegister);
                    callback.onSuccess(responseDTO);
                }else{
                    callback.onFailure(responseDTO);
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO añadir mensaje de error
                responseDTO.getErrors().add("");
                callback.onFailure(responseDTO);
            }
        });

    }

    @Override
    public void createRegister(String preScrutineeringID, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        EgressRegister register = new EgressRegister(preScrutineeringID);

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_EVENT_CONTROL_EGRESS)
                .document(register.getId())
                .set(register.toObjectData())
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



    @Override
    public void saveTime(String ID, final Long time, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_EVENT_CONTROL_EGRESS).document(ID);

        registerReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        EgressRegister recordToUpdate = new EgressRegister(documentSnapshot);

                        String varToUpdate;
                        if(recordToUpdate.getFirstAttempt() == null || recordToUpdate.getFirstAttempt() == 0L){
                            varToUpdate = EgressRegister.FIRST_ATTEMPT;
                        }else if(recordToUpdate.getSecondAttempt() == null || recordToUpdate.getSecondAttempt() == 0L){
                            varToUpdate = EgressRegister.SECOND_ATTEMPT;
                        }else{
                            varToUpdate = EgressRegister.THIRD_ATTEMPT;
                        }

                        registerReference.update(varToUpdate, time)

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        responseDTO.getInfo().add("Chrono time updated successfully!!");
                                        callback.onSuccess(responseDTO);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //TODO
                                        responseDTO.getErrors().add("");
                                        callback.onSuccess(responseDTO);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO
                        responseDTO.getErrors().add("");
                        callback.onSuccess(responseDTO);
                    }
                });

    }
}

package code.formulastudentspain.app.mvp.data.business.egress.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.BusinessCallback;
import code.formulastudentspain.app.mvp.data.business.ConfigConstants;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.ResponseDTO;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.data.model.EgressRegister;

public class EgressBOFirebaseImpl extends DataLoader implements EgressBO {


    private FirebaseFirestore firebaseFirestore;

    public EgressBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveEgressByPreScrutineeringId(String preScrutineeringID, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_EVENT_CONTROL_EGRESS)
                .whereEqualTo(EgressRegister.PRESCRUTINEERING_ID, preScrutineeringID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        EgressRegister egressRegister = new EgressRegister(queryDocumentSnapshots.getDocuments().get(0));
                        responseDTO.setData(egressRegister);
                        responseDTO.setInfo(R.string.event_egress_message_info_retrieving);
                        callback.onSuccess(responseDTO);
                        loadingData(false);
                    } else {
                        responseDTO.setError(R.string.event_egress_message_error_retrieving);
                        callback.onFailure(responseDTO);
                        loadingData(false);
                    }
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.event_egress_message_error_retrieving);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }

    @Override
    public void createRegister(String preScrutineeringID, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        EgressRegister register = new EgressRegister(preScrutineeringID);

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_EVENT_CONTROL_EGRESS)
                .document(register.getId())
                .set(register.toObjectData())
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.dynamic_event_message_info_create_egress);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_message_error_create_egress);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }


    @Override
    public void saveTime(String ID, final Long time, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_EVENT_CONTROL_EGRESS).document(ID);

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> {

                    EgressRegister recordToUpdate = new EgressRegister(documentSnapshot);

                    String varToUpdate;
                    if (recordToUpdate.getFirstAttempt() == null || recordToUpdate.getFirstAttempt() == 0L) {
                        varToUpdate = EgressRegister.FIRST_ATTEMPT;
                    } else if (recordToUpdate.getSecondAttempt() == null || recordToUpdate.getSecondAttempt() == 0L) {
                        varToUpdate = EgressRegister.SECOND_ATTEMPT;
                    } else {
                        varToUpdate = EgressRegister.THIRD_ATTEMPT;
                    }

                    registerReference.update(varToUpdate, time)
                            .addOnSuccessListener(aVoid -> {
                                responseDTO.setInfo(R.string.dynamic_event_message_info_save_egress_time);
                                callback.onSuccess(responseDTO);
                                loadingData(false);
                            })
                            .addOnFailureListener(e -> {
                                responseDTO.setError(R.string.dynamic_event_message_error_save_egress_time);
                                callback.onSuccess(responseDTO);
                                loadingData(false);
                            });
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_message_error_save_egress_time);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                });
    }
}

package es.formulastudent.app.mvp.data.business.conecontrol.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;

public class ConeControlBOFirebaseImpl implements ConeControlBO {

    private FirebaseFirestore firebaseFirestore;

    public ConeControlBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public ListenerRegistration getConeControlRegistersRealTime(final Map<String, Object> filters, final BusinessCallback callback) {


        //Event type
        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_CONE_CONTROL);

        //Race Type filter (electric, combustion and final)
        query = query.whereEqualTo(ConeControlRegister.RACE_ROUND, filters.get("round"));

        //Sector
        query = query.whereEqualTo(ConeControlRegister.SECTOR_NUMBER, filters.get("sector"));

        //Only registers that are racing
        query = query.whereEqualTo(ConeControlRegister.IS_RACING, true);


        ListenerRegistration registration = query.orderBy(ConeControlRegister.CAR_NUMBER, Query.Direction.ASCENDING)
                .addSnapshotListener((value, e) -> {

                    //Response object
                    ResponseDTO responseDTO = new ResponseDTO();

                    if (e != null) {
                        responseDTO.setError(R.string.cc_realtime_error_retrieving_message);
                        callback.onFailure(responseDTO);
                        return;
                    }

                    List<ConeControlRegister> result = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        result.add(new ConeControlRegister(doc));
                    }

                    responseDTO.setData(result);
                    callback.onSuccess(responseDTO);
                });

        return registration;
    }

    @Override
    public void createConeControlForAllSectors(Long carNumer, String flagURL, String raceRound, int numberOfSectors, BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        WriteBatch batch = firebaseFirestore.batch();

        for(int i=1; i<=numberOfSectors; i++){
            ConeControlRegister register = new ConeControlRegister();
            register.setCarNumber(carNumer);
            register.setFlagURL(flagURL);
            register.setIsRacing(false);
            register.setOffCourses(0L);
            register.setSectorNumber((long)i);
            register.setTrafficCones(0L);
            register.setRaceRound(raceRound);

            DocumentReference docRef = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_CONE_CONTROL).document(register.getId());
            batch.set(docRef, register.toObjectData());
        }


        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.cone_control_create_success);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.cone_control_create_error);
                    callback.onFailure(responseDTO);
                });

    }

    @Override
    public void updateConeControlRegister(ConeControlRegister register, BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_CONE_CONTROL).document(register.getId());

        registerReference.update(register.toObjectData())

                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.teams_info_update_message);//FIXME
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.teams_error_update_message);//FIXME
                    callback.onFailure(responseDTO);
                });
    }


    @Override
    public void enableOrDisableConeControlRegistersByTeam(Long carNumber, boolean enable, BusinessCallback callback){
        final ResponseDTO responseDTO = new ResponseDTO();

        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_CONE_CONTROL)
                .whereEqualTo(ConeControlRegister.CAR_NUMBER, carNumber)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                       DocumentReference ref = document.getReference();
                       batch.update(ref, ConeControlRegister.IS_RACING, enable);
                    }

                    // Commit the batch
                    batch.commit().addOnSuccessListener(task -> {
                        callback.onSuccess(responseDTO);

                    }).addOnFailureListener(task -> {
                        callback.onFailure(responseDTO);
                    });

                }).addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_message_error_retrieving_registers);
                    callback.onFailure(responseDTO);
                });

    }

}

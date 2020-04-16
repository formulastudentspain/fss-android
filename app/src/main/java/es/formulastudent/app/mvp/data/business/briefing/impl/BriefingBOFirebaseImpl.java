package es.formulastudent.app.mvp.data.business.briefing.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.TeamMember;

public class BriefingBOFirebaseImpl extends DataLoader implements BriefingBO {

    private FirebaseFirestore firebaseFirestore;

    public BriefingBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveBriefingRegisters(Date from, Date to, String teamID, final BusinessCallback callback) {
        ResponseDTO responseDTO = new ResponseDTO();
        loadingData(true);
        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS);

        //Competition day filter
        if (from != null && to != null) {
            query = query.whereLessThanOrEqualTo(BriefingRegister.DATE, to);
            query = query.whereGreaterThan(BriefingRegister.DATE, from);
        }

        //Teams filter
        if (teamID != null && !teamID.equals("-1")) {
            query = query.whereEqualTo(BriefingRegister.TEAM_ID, teamID);
        }

        //Type Filter
        query = query.whereEqualTo(BriefingRegister.EVENT_TYPE, EventType.BRIEFING);
        query.orderBy(BriefingRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<BriefingRegister> result = new ArrayList<>();
                    
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        BriefingRegister briefingRegister = new BriefingRegister(document);
                        result.add(briefingRegister);
                    }

                    responseDTO.setData(result);
                    responseDTO.setInfo(R.string.briefing_messages_retrieve_registers_info);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.briefing_messages_retrieve_registers_error);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }


    @Override
    public void createBriefingRegistry(TeamMember teamMember, String registerUserMail, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        Date registerDate = Calendar.getInstance().getTime();
        BriefingRegister briefingRegister = new BriefingRegister(teamMember, registerDate, registerUserMail);

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .document(briefingRegister.getID())
                .set(briefingRegister.toObjectData())
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.briefing_messages_create_registers_info);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.briefing_messages_create_registers_error);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }

    @Override
    public void checkBriefingByUser(String userID, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .whereEqualTo(BriefingRegister.USER_ID, userID)
                .whereEqualTo(BriefingRegister.EVENT_TYPE, EventType.BRIEFING)
                .orderBy(BriefingRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        responseDTO.setData(Boolean.FALSE);

                    } else {
                        BriefingRegister briefingRegister = new BriefingRegister(queryDocumentSnapshots.getDocuments().get(0));
                        Long briefingDateMillis = briefingRegister.getDate().getTime();
                        Long nowMillis = Calendar.getInstance().getTime().getTime();
                        long secs = (nowMillis - briefingDateMillis) / 1000;
                        long hours = secs / 3600;

                        if (hours > 24) {
                            responseDTO.setData(Boolean.FALSE);
                        } else {
                            responseDTO.setData(Boolean.TRUE);
                        }
                    }

                    callback.onSuccess(responseDTO);
                    responseDTO.setInfo(R.string.briefing_messages_retrieve_registers_info);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    loadingData(false);
                    responseDTO.setError(R.string.briefing_messages_retrieve_registers_error);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });

    }

    @Override
    public void deleteBriefingRegister(String registerID, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore
                .collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .document(registerID);

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.delete()
                        .addOnSuccessListener(aVoid -> {
                            responseDTO.setInfo(R.string.briefing_messages_delete_register_info);
                            callback.onSuccess(responseDTO);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            responseDTO.setError(R.string.briefing_messages_delete_register_error);
                            callback.onSuccess(responseDTO);
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.briefing_messages_delete_register_error);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                });
    }
}

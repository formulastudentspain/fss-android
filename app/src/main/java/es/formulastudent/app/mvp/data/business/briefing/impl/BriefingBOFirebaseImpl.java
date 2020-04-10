package es.formulastudent.app.mvp.data.business.briefing.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

public class BriefingBOFirebaseImpl implements BriefingBO {

    private FirebaseFirestore firebaseFirestore;
    private LoadingDialog loadingDialog;
    private Messages messages;

    public BriefingBOFirebaseImpl(FirebaseFirestore firebaseFirestore,
                                  LoadingDialog loadingDialog, Messages messages) {
        this.firebaseFirestore = firebaseFirestore;
        this.loadingDialog = loadingDialog;
        this.messages = messages;
    }

    @Override
    public void retrieveBriefingRegisters(Date from, Date to, String teamID, final BusinessCallback callback) {
        loadingDialog.show();

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
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    //Response object
                    ResponseDTO responseDTO = new ResponseDTO();

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<BriefingRegister> result = new ArrayList<>();

                        //Add results to list
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            BriefingRegister briefingRegister = new BriefingRegister(document);
                            result.add(briefingRegister);
                        }

                        responseDTO.setData(result);
                        messages.showInfo(R.string.briefing_messages_retrieve_registers_info);
                        callback.onSuccess(responseDTO);
                        loadingDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    //Response object
                    ResponseDTO responseDTO = new ResponseDTO();

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        messages.showError(R.string.briefing_messages_retrieve_registers_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }


    @Override
    public void createBriefingRegistry(TeamMember teamMember, String registerUserMail, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Date registerDate = Calendar.getInstance().getTime();
        BriefingRegister briefingRegister = new BriefingRegister(teamMember, registerDate, registerUserMail);

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .document(briefingRegister.getID())
                .set(briefingRegister.toObjectData())
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.briefing_messages_create_registers_info);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.briefing_messages_create_registers_error);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void checkBriefingByUser(String userID, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

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
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.briefing_messages_retrieve_registers_error);
                    callback.onFailure(responseDTO);
                });

    }

    @Override
    public void deleteBriefingRegister(String registerID, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore
                .collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .document(registerID);

        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.delete()
                        .addOnSuccessListener(aVoid -> {
                            responseDTO.setInfo(R.string.briefing_messages_delete_register_info);
                            callback.onSuccess(responseDTO);
                        })
                        .addOnFailureListener(e -> {
                            responseDTO.setError(R.string.briefing_messages_delete_register_error);
                            callback.onSuccess(responseDTO);
                        }))
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.briefing_messages_delete_register_error);
                    callback.onSuccess(responseDTO);
                });
    }
}

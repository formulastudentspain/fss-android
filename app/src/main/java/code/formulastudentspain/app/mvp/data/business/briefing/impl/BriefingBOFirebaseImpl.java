package code.formulastudentspain.app.mvp.data.business.briefing.impl;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.ConfigConstants;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.briefing.BriefingBO;
import code.formulastudentspain.app.mvp.data.model.BriefingRegister;
import code.formulastudentspain.app.mvp.data.model.EventType;
import code.formulastudentspain.app.mvp.data.model.TeamMember;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

public class BriefingBOFirebaseImpl extends DataLoader implements BriefingBO {

    private FirebaseFirestore firebaseFirestore;

    public BriefingBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveBriefingRegisters(Date from, Date to, String teamID,
                                          @NonNull OnSuccessCallback<List<BriefingRegister>> onSuccessCallback,
                                          @NonNull OnFailureCallback onFailureCallback) {

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
        loadingData(true);
        query = query.whereEqualTo(BriefingRegister.EVENT_TYPE, EventType.BRIEFING);
        query.orderBy(BriefingRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<BriefingRegister> result = new ArrayList<>();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        BriefingRegister briefingRegister = new BriefingRegister(document);
                        result.add(briefingRegister);
                    }

                    loadingData(false);
                    onSuccessCallback.onSuccess(result);

                })
                .addOnFailureListener(e -> {
                    loadingData(false);
                    onFailureCallback.onFailure(new Message(R.string.briefing_messages_retrieve_registers_error));
                });
    }


    @Override
    public void createBriefingRegistry(TeamMember teamMember, String registerUserMail,
                                       @NonNull OnSuccessCallback<?> onSuccessCallback,
                                       @NonNull OnFailureCallback onFailureCallback) {
        Date registerDate = Calendar.getInstance().getTime();
        BriefingRegister briefingRegister = new BriefingRegister(teamMember, registerDate, registerUserMail);

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .document(briefingRegister.getID())
                .set(briefingRegister.toObjectData())
                .addOnSuccessListener(aVoid -> {
                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.briefing_messages_create_registers_error));
                    loadingData(false);
                });
    }

    @Override
    public void checkBriefingByUser(String userID,
                                    @NonNull OnSuccessCallback<Boolean> onSuccessCallback,
                                    @NonNull OnFailureCallback onFailureCallback) {

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .whereEqualTo(BriefingRegister.USER_ID, userID)
                .whereEqualTo(BriefingRegister.EVENT_TYPE, EventType.BRIEFING)
                .orderBy(BriefingRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    Boolean result;
                    if (queryDocumentSnapshots.isEmpty()) {
                        result = Boolean.FALSE;

                    } else {
                        BriefingRegister briefingRegister = new BriefingRegister(queryDocumentSnapshots.getDocuments().get(0));
                        Long briefingDateMillis = briefingRegister.getDate().getTime();
                        Long nowMillis = Calendar.getInstance().getTime().getTime();
                        long secs = (nowMillis - briefingDateMillis) / 1000;
                        long hours = secs / 3600;

                        if (hours > 24) {
                            result = Boolean.FALSE;
                        } else {
                            result = Boolean.TRUE;
                        }
                    }

                    onSuccessCallback.onSuccess(result);
                    loadingData(false);

                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.briefing_messages_retrieve_registers_error));
                    loadingData(false);
                });

    }

    @Override
    public void deleteBriefingRegister(String registerID,
                                       @NonNull OnSuccessCallback<?> onSuccessCallback,
                                       @NonNull OnFailureCallback onFailureCallback) {
        final DocumentReference registerReference = firebaseFirestore
                .collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS)
                .document(registerID);

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.delete()
                        .addOnSuccessListener(aVoid -> {
                            onSuccessCallback.onSuccess(null);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            onFailureCallback.onFailure(new Message(R.string.briefing_messages_delete_register_error));
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.briefing_messages_delete_register_error));
                    loadingData(false);
                });
    }
}

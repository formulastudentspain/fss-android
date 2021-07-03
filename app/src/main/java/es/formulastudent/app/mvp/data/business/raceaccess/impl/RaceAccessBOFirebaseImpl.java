package es.formulastudent.app.mvp.data.business.raceaccess.impl;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;
import es.formulastudent.app.mvp.data.business.raceaccess.RaceAccessBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.utils.messages.Message;

//@SuppressWarnings("ALL")
public class RaceAccessBOFirebaseImpl extends DataLoader implements RaceAccessBO {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public RaceAccessBOFirebaseImpl(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void retrieveRegisters(Date from, Date to, String teamID, Long carNumber, final EventType type,
                                  @NotNull OnSuccessCallback<List<EventRegister> > onSuccessCallback,
                                  @NonNull OnFailureCallback onFailureCallback) {
        Query query = firebaseFirestore.collection(type.getFirebaseTable());

        //Competition day filter
        if (from != null && to != null) {
            query = query.whereLessThanOrEqualTo(EventRegister.DATE, to);
            query = query.whereGreaterThan(EventRegister.DATE, from);
        }

        //Teams filter
        if (teamID != null && !teamID.equals("-1")) {
            query = query.whereEqualTo(EventRegister.TEAM_ID, teamID);
        }

        //Car number filter
        if (carNumber != null) {
            query = query.whereEqualTo(EventRegister.CAR_NUMBER, carNumber);
        }

        //Event type
        if (type.name() != null) {
            query = query.whereEqualTo(EventRegister.EVENT_TYPE, type.name());
        }

        loadingData(true);
        query.orderBy(EventRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    //Add results to list
                    List<EventRegister> result = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (type.equals(EventType.PRE_SCRUTINEERING)) {
                            PreScrutineeringRegister register = new PreScrutineeringRegister(document, type);
                            result.add(register);
                        } else {
                            EventRegister register = new EventRegister(document, type);
                            result.add(register);
                        }
                    }
                    onSuccessCallback.onSuccess(result);
                    loadingData(false);

                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.dynamic_event_message_error_retrieving_registers));
                    loadingData(false);
                });
    }


    @Override
    public void createRegister(TeamMember teamMember, Long carNumber, Boolean briefingDone, EventType type,
                               @NonNull OnSuccessCallback<EventRegister> onSuccessCallback,
                               @NonNull OnFailureCallback onFailureCallback) {

        Date registerDate = Calendar.getInstance().getTime();
        final EventRegister register;
        if (type.equals(EventType.PRE_SCRUTINEERING)) {
            register = new PreScrutineeringRegister(teamMember.getTeamID(), teamMember.getTeam(), teamMember.getID(),
                    teamMember.getName(), teamMember.getPhotoUrl(), registerDate, carNumber, briefingDone, type, firebaseAuth.getCurrentUser().getEmail(), 0L);
        } else {
            register = new EventRegister(teamMember.getTeamID(), teamMember.getTeam(), teamMember.getID(),
                    teamMember.getName(), teamMember.getPhotoUrl(), registerDate, carNumber, briefingDone, type, firebaseAuth.getCurrentUser().getEmail());
        }

        firebaseFirestore.collection(type.getFirebaseTable())
                .document(register.getID())
                .set(register.toObjectData())
                .addOnSuccessListener(aVoid -> {
                    onSuccessCallback.onSuccess(register);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.dynamic_event_messages_create_registers_error));
                });
    }

    @Override
    public void updatePreScrutineeringRegister(String id, final long milliseconds,
                                               @NonNull OnSuccessCallback<?> onSuccessCallback,
                                               @NonNull OnFailureCallback onFailureCallback) {

        final DocumentReference registerReference = firebaseFirestore.collection(EventType.PRE_SCRUTINEERING.getFirebaseTable()).document(id);

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(
                        documentSnapshot -> registerReference.update(PreScrutineeringRegister.TIME, milliseconds)
                                .addOnSuccessListener(aVoid -> {
                                    onSuccessCallback.onSuccess(null);
                                    loadingData(false);
                                })
                                .addOnFailureListener(e -> {
                                    onFailureCallback.onFailure(new Message(R.string.dynamic_event_messages_prescrutineering_update_error));
                                    loadingData(false);
                                }))
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.dynamic_event_messages_prescrutineering_update_error));
                    loadingData(false);
                });
    }

    @Override
    public void deleteRegister(EventType type, String registerID,
                               @NonNull OnSuccessCallback<?> onSuccessCallback,
                               @NonNull OnFailureCallback onFailureCallback) {
        final DocumentReference registerReference = firebaseFirestore
                .collection(type.getFirebaseTable())
                .document(registerID);

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.delete()
                        .addOnSuccessListener(aVoid -> {
                            onSuccessCallback.onSuccess(null);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            onFailureCallback.onFailure(new Message(R.string.dynamic_event_message_error_deleting_registers));
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.dynamic_event_message_error_deleting_registers));
                    loadingData(false);
                });
    }

    @Override
    public void getDifferentEventRegistersByDriver(String userId,
                                                   @NonNull OnSuccessCallback<Map<String, List<EventRegister>>> onSuccessCallback,
                                                   @NonNull OnFailureCallback onFailureCallback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS);

        if (userId != null) {
            query = query.whereEqualTo(BriefingRegister.USER_ID, userId);
        }

        loadingData(true);
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    //Add results to list Map<EventType, register>
                    Map<String, List<EventRegister>> result = new HashMap<>();
                    List<EventRegister> eventRegisterList;
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        EventRegister register = new EventRegister(document);

                        //Get all dynamic event from user, with 2 as maximum, except PracticeTrack, PreScrutineering and Briefing
                        if (register.getType() != EventType.PRACTICE_TRACK &&
                                register.getType() != EventType.PRE_SCRUTINEERING &&
                                register.getType() != EventType.BRIEFING) {

                            if (result.containsKey(register.getType().name())) {
                                eventRegisterList = result.get(register.getType().name());
                                eventRegisterList.add(register);
                                result.put(register.getType().name(), eventRegisterList);

                            } else if (!result.containsKey(register.getType().name()) && result.size() < 2) {
                                eventRegisterList = new ArrayList<>();
                                eventRegisterList.add(register);
                                result.put(register.getType().name(), eventRegisterList);

                            } else {
                                onFailureCallback.onFailure(new Message(R.string.dynamic_event_message_error_runs));
                            }
                        }
                    }
                    onSuccessCallback.onSuccess(result);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.dynamic_event_message_error_runs));
                    loadingData(false);
                });
    }
}

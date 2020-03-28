package es.formulastudent.app.mvp.data.business.dynamicevent.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.data.model.TeamMember;

public class DynamicEventBOFirebaseImpl implements DynamicEventBO {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public DynamicEventBOFirebaseImpl(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void retrieveRegisters(Date from, Date to, String teamID, Long carNumber, final EventType type, final BusinessCallback callback) {

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


        final ResponseDTO responseDTO = new ResponseDTO();
        query.orderBy(EventRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    //Response object
                    ResponseDTO responseDTO1 = new ResponseDTO();

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

                    responseDTO1.setData(result);
                    responseDTO1.setInfo(R.string.dynamic_event_message_info_retrieving_registers);
                    callback.onSuccess(responseDTO1);

                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_message_error_retrieving_registers);
                    callback.onFailure(responseDTO);
                });
    }


    @Override
    public void createRegister(TeamMember teamMember, Long carNumber, Boolean briefingDone, EventType type, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
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
                    responseDTO.setData(register);
                    responseDTO.setInfo(R.string.dynamic_event_messages_create_registers_info);
                    callback.onSuccess(responseDTO);

                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_messages_create_registers_error);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void updatePreScrutineeringRegister(String id, final long milliseconds, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(EventType.PRE_SCRUTINEERING.getFirebaseTable()).document(id);

        registerReference.get()
                .addOnSuccessListener(
                        documentSnapshot -> registerReference.update(PreScrutineeringRegister.TIME, milliseconds)
                                .addOnSuccessListener(aVoid -> {
                                    responseDTO.setInfo(R.string.dynamic_event_messages_prescrutineering_update_info);
                                    callback.onSuccess(responseDTO);
                                })
                                .addOnFailureListener(e -> {
                                    responseDTO.setError(R.string.dynamic_event_messages_prescrutineering_update_error);
                                    callback.onFailure(responseDTO);
                                }))
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_messages_prescrutineering_update_error);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void deleteRegister(EventType type, String registerID, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore
                .collection(type.getFirebaseTable())
                .document(registerID);

        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.delete()
                        .addOnSuccessListener(aVoid -> {
                            responseDTO.setInfo(R.string.dynamic_event_message_info_deleting_registers);
                            callback.onSuccess(responseDTO);
                        })
                        .addOnFailureListener(e -> {
                            responseDTO.setError(R.string.dynamic_event_message_error_deleting_registers);
                            callback.onFailure(responseDTO);
                        }))
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_message_error_deleting_registers);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void getDifferentEventRegistersByDriver(String userId, final BusinessCallback callback) {

        //Response object
        final ResponseDTO responseDTO = new ResponseDTO();

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS);

        if (userId != null) {
            query = query.whereEqualTo(BriefingRegister.USER_ID, userId);
        }

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
                                responseDTO.setError(R.string.dynamic_event_message_error_runs);
                            }
                        }
                    }
                    responseDTO.setData(result);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.dynamic_event_message_error_runs);
                    callback.onFailure(responseDTO);
                });
    }
}

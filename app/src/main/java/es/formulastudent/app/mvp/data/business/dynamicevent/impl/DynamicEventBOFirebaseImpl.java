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

import org.apache.poi.ss.formula.functions.Even;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.data.model.User;

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
        if(type.name() != null){
            query = query.whereEqualTo(EventRegister.EVENT_TYPE, type.name());
        }


        final ResponseDTO responseDTO = new ResponseDTO();
        query.orderBy(EventRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //Response object
                        ResponseDTO responseDTO = new ResponseDTO();

                        //Add results to list
                        List<EventRegister> result = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            if(type.equals(EventType.PRE_SCRUTINEERING)){
                                PreScrutineeringRegister register = new PreScrutineeringRegister(document, type);
                                result.add(register);
                            }else{
                                EventRegister register = new EventRegister(document, type);
                                result.add(register);
                            }

                        }

                        responseDTO.setData(result);
                        callback.onSuccess(responseDTO);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO add messages
                callback.onFailure(responseDTO);
            }
        });
    }


    @Override
    public void createRegister(User user, String carType, Long carNumber, Boolean briefingDone, EventType type, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Date registerDate = Calendar.getInstance().getTime();

        final EventRegister register;
        if(type.equals(EventType.PRE_SCRUTINEERING)){
            register = new PreScrutineeringRegister(user.getTeamID(), user.getTeam(), user.getID(),
                    user.getName(), user.getPhotoUrl(), registerDate, carType, carNumber, briefingDone, type, firebaseAuth.getCurrentUser().getEmail(), 0L);
        }else{
            register = new EventRegister(user.getTeamID(), user.getTeam(), user.getID(),
                    user.getName(), user.getPhotoUrl(), registerDate, carType, carNumber, briefingDone, type, firebaseAuth.getCurrentUser().getEmail());
        }


        firebaseFirestore.collection(type.getFirebaseTable())
                .document(register.getID())
                .set(register.toObjectData())
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        responseDTO.setData(register);
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
    public void updatePreScrutineeringRegister(String id, final long milliseconds, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(EventType.PRE_SCRUTINEERING.getFirebaseTable()).document(id);

        registerReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        registerReference.update(PreScrutineeringRegister.TIME, milliseconds)

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

    @Override
    public void deleteRegister(EventType type, String registerID, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore
                .collection(type.getFirebaseTable())
                .document(registerID);

        registerReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        registerReference.delete()

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        responseDTO.getInfo().add("Register deleted successfully!!");
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

    @Override
    public void getDifferentEventRegistersByDriver(String userId, final BusinessCallback callback){
        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_DYNAMIC_EVENTS);

        if(userId != null){
            query = query.whereEqualTo(BriefingRegister.USER_ID, userId);
        }

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //Response object
                ResponseDTO responseDTO = new ResponseDTO();

                //Add results to list Map<EventType, register>
                Map<String, List<EventRegister>> result = new HashMap<>();
                List<EventRegister> eventRegisterList;
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    EventRegister register = new EventRegister(document);
                    //Get all dynamic event from user, with 2 as maximum, except PracticeTrack and PreScrutineering
                    if(register.getType() != EventType.PRACTICE_TRACK && register.getType() != EventType.PRE_SCRUTINEERING) {
                        if (result.containsKey(register.getType().name()) && result.size() < 2) {
                            eventRegisterList = result.get(register.getType().name());
                            eventRegisterList.add(register);
                            result.put(register.getType().name(), eventRegisterList);
                        } else if (!result.containsKey(register.getType().name()) && result.size() < 2) {
                            eventRegisterList = new ArrayList<>();
                            eventRegisterList.add(register);
                            result.put(register.getType().name(), eventRegisterList);
                        } else {
                            responseDTO.getErrors().add("User is registered in more than one dynamic event");
                        }
                    }
                }

                responseDTO.setData(result);
                callback.onSuccess(responseDTO);
            }
        });
    }

}

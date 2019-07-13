package es.formulastudent.app.mvp.data.business.dynamicevent.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.data.model.User;

public class DynamicEventBOFirebaseImpl implements DynamicEventBO {

    private FirebaseFirestore firebaseFirestore;

    public DynamicEventBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
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

        EventRegister register = null;
        if(type.equals(EventType.PRE_SCRUTINEERING)){
            register = new PreScrutineeringRegister(user.getTeamID(), user.getTeam(), user.getID(),
                    user.getName(), user.getPhotoUrl(), registerDate, carType, carNumber, briefingDone, type, 0L);
        }else{
            register = new EventRegister(user.getTeamID(), user.getTeam(), user.getID(),
                    user.getName(), user.getPhotoUrl(), registerDate, carType, carNumber, briefingDone, type);
        }


        firebaseFirestore.collection(type.getFirebaseTable())
                .document(register.getID())
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

}

package es.formulastudent.app.mvp.data.business.racecontrol.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlRegisterEndurance;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.RaceControlTeamDTO;

public class RaceControlBOFirebaseImpl implements RaceControlBO {

    private FirebaseFirestore firebaseFirestore;
    private TeamBO teamBO;

    public RaceControlBOFirebaseImpl(FirebaseFirestore firebaseFirestore, TeamBO teamBO) {
        this.firebaseFirestore = firebaseFirestore;
        this.teamBO = teamBO;
    }

    @Override
    public ListenerRegistration getRaceControlRegistersRealTime(final Map<String, Object> filters, final BusinessCallback callback) {

        Query query = firebaseFirestore.collection(RaceControlEvent.ENDURANCE.getFirebaseTable());

        //Race Type filter (electric, combustion and final)
        if(filters.get("raceType").equals(RaceControlRegister.RACE_TYPE_FINAL)){
            query = query.whereEqualTo(RaceControlRegister.RUN_FINAL, true);

        }else{
            query = query.whereEqualTo(RaceControlRegister.RUN_FINAL, false);

            if(filters.get("raceType").equals(RaceControlRegister.RACE_TYPE_ELECTRIC)){
                query = query.whereEqualTo(RaceControlRegister.CAR_TYPE, Car.CAR_TYPE_ELECTRIC);

            }else{
                query = query.whereEqualTo(RaceControlRegister.CAR_TYPE, Car.CAR_TYPE_COMBUSTION);

            }
        }


        ListenerRegistration registration = query.orderBy(RaceControlRegisterEndurance.ORDER, Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {

                        //Response object
                        ResponseDTO responseDTO = new ResponseDTO();

                        if (e != null) {
                            responseDTO.getErrors().add("Error retrieving Race Control registers");
                            return;
                        }

                        List<RaceControlRegisterEndurance> result = new ArrayList<>();
                        List<String> states = (List<String>)filters.get("states");

                        for (QueryDocumentSnapshot doc : value) {
                            RaceControlRegisterEndurance register = new RaceControlRegisterEndurance(doc);
                            if(states != null && states.contains(register.getCurrentState().getAcronym())){
                                result.add(register);
                            }

                        }

                        responseDTO.setData(result);
                        callback.onSuccess(responseDTO);
                    }
                });

        return registration;
    }



    @Override
    public void getRaceControlTeams(final Map<String, Object> filters, final BusinessCallback callback) {

        final String carType = filters.get("raceType").equals(RaceControlRegister.RACE_TYPE_FINAL) ? null : (String)filters.get("raceType");


        //First, retrieve all the teams depending on the race type
        teamBO.retrieveAllTeams(carType, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                final List<Team> teams = (List<Team>)responseDTO.getData();

                //Second, retrieve the existing Endurance registers
                getRaceControlRegisters(filters, new BusinessCallback() {

                    ResponseDTO response = new ResponseDTO();

                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {

                        List<RaceControlTeamDTO> resultList = new ArrayList<>();
                        List<RaceControlRegisterEndurance> list = (List<RaceControlRegisterEndurance>)responseDTO.getData();

                        //Check if a team has been already added
                        for(Team team: teams){

                            boolean isTeamAlreadyAdded = false;
                            for(RaceControlRegisterEndurance raceControlTeam: list){

                                if(team.getCar().getNumber().equals(raceControlTeam.getCarNumber())){
                                    isTeamAlreadyAdded = true;
                                    break;
                                }
                            }

                            RaceControlTeamDTO result = new RaceControlTeamDTO(team, isTeamAlreadyAdded);
                            resultList.add(result);
                        }

                        response.setData(resultList);
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        //TODO
                    }
                });

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO
            }
        });
    }

    @Override
    public void createRaceControlRegister(List<RaceControlTeamDTO> raceControlTeamDTOList, RaceControlEvent eventType, String raceType, Long currentMaxIndex, final BusinessCallback callback) {


        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        for(RaceControlTeamDTO item: raceControlTeamDTOList){

            currentMaxIndex++;

            // Set the value of 'NYC'
            DocumentReference nycRef = firebaseFirestore
                    .collection(eventType.getFirebaseTable())
                    .document(item.getCarNumber().toString());

            RaceControlRegisterEndurance register = new RaceControlRegisterEndurance();
            register.setCarNumber(item.getCarNumber());
            register.setCarType(raceType);
            register.setRunFinal(RaceControlRegister.RACE_TYPE_FINAL.equals(raceType));
            register.setCurrentStateDate(Calendar.getInstance().getTime());
            register.setOrder(currentMaxIndex);
            register.setCurrentState(RaceControlState.NOT_AVAILABLE);
            batch.set(nycRef, register.toObjectData());
        }


        // Commit the batch
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //callback.onSuccess(responseDTO);
                int a = 0;
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO añadir mensaje de error
                        //responseDTO.getErrors().add("");
                   //     callback.onFailure(responseDTO);
                        int a = 0;
                    }
                });


    }


    @Override
    public void getRaceControlRegisters(Map<String, Object> filters, final BusinessCallback callback) {

        //Response object
        final ResponseDTO responseDTO = new ResponseDTO();

        //Get Race type value
        String carType = (String)filters.get("raceType");

        //Get Event type
        RaceControlEvent event = (RaceControlEvent)filters.get("eventType");

        firebaseFirestore.collection(event.getFirebaseTable())
                .whereEqualTo(RaceControlRegisterEndurance.CAR_TYPE, carType)
                .orderBy(RaceControlRegisterEndurance.ORDER, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //Add results to list
                        List<RaceControlRegisterEndurance> result = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            RaceControlRegisterEndurance register = new RaceControlRegisterEndurance(document);
                            result.add(register);
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
    public void updateRaceControlState(RaceControlRegister register, RaceControlEvent event, RaceControlState newState, final BusinessCallback callback) {

        //Update currentState and currentStateDate with the new value
        register.setCurrentState(newState);
        register.setCurrentStateDate(Calendar.getInstance().getTime());

        //Cast the data depending on the Race Type
        Map<String, Object> data = null;
        if(register instanceof RaceControlRegisterEndurance){
            data = ((RaceControlRegisterEndurance) register).toObjectData();
        }

        //Call Firebase to update
        firebaseFirestore.collection(event.getFirebaseTable()).document(register.getID())
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //There is nothing to be returned
                        callback.onSuccess(new ResponseDTO());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        callback.onFailure(new ResponseDTO());

                    }
                });
    }
}

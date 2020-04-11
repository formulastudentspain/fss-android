package es.formulastudent.app.mvp.data.business.racecontrol.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.RaceControlAutocrossState;
import es.formulastudent.app.mvp.data.model.RaceControlEnduranceState;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlRegisterAutocross;
import es.formulastudent.app.mvp.data.model.RaceControlRegisterEndurance;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.racecontrol.dialog.RaceControlTeamDTO;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

public class RaceControlBOFirebaseImpl implements RaceControlBO {

    private FirebaseFirestore firebaseFirestore;
    private TeamBO teamBO;
    private ConeControlBO coneControlBO;
    private LoadingDialog loadingDialog;
    private Messages messages;

    public RaceControlBOFirebaseImpl(FirebaseFirestore firebaseFirestore, TeamBO teamBO, 
                                     ConeControlBO coneControlBO, LoadingDialog loadingDialog, 
                                     Messages messages) {
        this.firebaseFirestore = firebaseFirestore;
        this.teamBO = teamBO;
        this.coneControlBO = coneControlBO;
        this.loadingDialog = loadingDialog;
        this.messages = messages;
    }

    @Override
    public ListenerRegistration getRaceControlRegistersRealTime(final Map<String, Object> filters, final BusinessCallback callback) {

        //Event type
        RaceControlEvent event = (RaceControlEvent) filters.get("eventType");

        Query query = firebaseFirestore.collection(event.getFirebaseTable());

        //ENDURANCE only
        if (RaceControlEvent.ENDURANCE.equals(event)) {
            query = query.whereEqualTo(RaceControlRegister.ROUND, filters.get("raceRound"));
        }

        //Car number
        Long carNumber = (Long) filters.get("carNumber");
        if (carNumber != null) {
            query = query.whereEqualTo(RaceControlRegister.CAR_NUMBER, carNumber);
        }

        //Order by
        if (RaceControlEvent.ENDURANCE.equals(event)) {
            query = query.orderBy(RaceControlRegister.ORDER, Query.Direction.ASCENDING);

        } else {
            //carNumber cannot be filtered and sorted at the same time
            if (carNumber == null) {
                query = query.orderBy(RaceControlRegister.CAR_NUMBER, Query.Direction.ASCENDING);
            }
        }


        return query.addSnapshotListener((value, e) -> {

            //Response object
            ResponseDTO responseDTO = new ResponseDTO();

            if (e != null) {
                messages.showError(R.string.rc_realtime_error_retrieving_message);
                callback.onFailure(responseDTO);
                e.printStackTrace();
            }

            List<RaceControlRegister> result = new ArrayList<>();
            List<String> states = (List<String>) filters.get("states");

            for (QueryDocumentSnapshot doc : value) {

                RaceControlRegister register = null;
                if (RaceControlEvent.ENDURANCE.equals(event)) {
                    register = new RaceControlRegisterEndurance(doc);
                } else if (RaceControlEvent.AUTOCROSS.equals(event)
                        || RaceControlEvent.SKIDPAD.equals(event)) {
                    register = new RaceControlRegisterAutocross(doc);
                }

                if (states != null && states.contains(register.getCurrentState().getAcronym())) {
                    result.add(register);
                }
            }

            responseDTO.setData(result);
            callback.onSuccess(responseDTO);
        });
    }


    @Override
    public void getRaceControlTeams(final Map<String, Object> filters, final BusinessCallback callback) {
        ResponseDTO response = new ResponseDTO();

        //First, retrieve all the teams depending on the race type
        loadingDialog.show();
        teamBO.retrieveTeams(null, null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                final List<Team> teams = (List<Team>) responseDTO.getData();

                //Second, retrieve the existing Endurance registers
                getRaceControlRegisters(filters, new BusinessCallback() {

                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {

                        List<RaceControlTeamDTO> resultList = new ArrayList<>();
                        List<RaceControlRegisterEndurance> list = (List<RaceControlRegisterEndurance>) responseDTO.getData();

                        //Check if a team has been already added
                        for (Team team : teams) {

                            boolean isTeamAlreadyAdded = false;
                            for (RaceControlRegisterEndurance raceControlTeam : list) {

                                if (team.getCar().getNumber().equals(raceControlTeam.getCarNumber())) {
                                    isTeamAlreadyAdded = true;
                                    break;
                                }
                            }

                            RaceControlTeamDTO result = new RaceControlTeamDTO(team, isTeamAlreadyAdded);
                            resultList.add(result);
                        }

                        response.setData(resultList);
                        callback.onSuccess(response);
                        loadingDialog.hide();
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        messages.showError(R.string.rc_teams_error_message);
                        callback.onFailure(responseDTO);
                        loadingDialog.hide();
                    }
                });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(R.string.rc_teams_error_message);
                callback.onFailure(responseDTO);
                loadingDialog.hide();
            }
        });
    }

    @Override
    public void createRaceControlRegister(List<RaceControlTeamDTO> raceControlTeamDTOList,
                                          RaceControlEvent eventType, String raceRound, Long currentMaxIndex,
                                          final BusinessCallback callback) {

        loadingDialog.show();
        final ResponseDTO responseDTO = new ResponseDTO();
        Date now = Calendar.getInstance().getTime();

        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        for (RaceControlTeamDTO item : raceControlTeamDTOList) {
            currentMaxIndex++;

            DocumentReference ref = firebaseFirestore
                    .collection(eventType.getFirebaseTable())
                    .document(item.getCarNumber().toString());

            RaceControlRegister register = null;
            if (RaceControlEvent.ENDURANCE.equals(eventType)) {
                register = new RaceControlRegisterEndurance();
                register.setCurrentState(RaceControlEnduranceState.NOT_AVAILABLE);

            } else if (RaceControlEvent.AUTOCROSS.equals(eventType)
                    || RaceControlEvent.SKIDPAD.equals(eventType)) {
                register = new RaceControlRegisterAutocross();
                register.setCurrentState(RaceControlAutocrossState.NOT_AVAILABLE);
            }

            //Common fields
            register.setCarNumber(item.getCarNumber());
            register.setRound(raceRound);
            register.setCurrentStateDate(now);
            register.setFlagURL(item.getFlagURL());
            register.setOrder(currentMaxIndex);
            register.setStateNA(now);

            if (RaceControlEvent.ENDURANCE.equals(eventType)) {
                batch.set(ref, ((RaceControlRegisterEndurance) register).toObjectData());

            } else if (RaceControlEvent.AUTOCROSS.equals(eventType)
                    || RaceControlEvent.SKIDPAD.equals(eventType)) {
                batch.set(ref, ((RaceControlRegisterAutocross) register).toObjectData());
            }
        }

        // Commit the batch
        batch.commit()
                .addOnSuccessListener(aVoid -> {

                    //Now create cones
                    for (RaceControlTeamDTO item : raceControlTeamDTOList) {
                        coneControlBO.createConeControlForAllSectors(
                                eventType.getConeControlEvent(),
                                item.getCarNumber(),
                                item.getFlagURL(),
                                raceRound,
                                7,
                                new BusinessCallback() {
                                    @Override
                                    public void onSuccess(ResponseDTO responseDTO) {
                                        //DO NOTHING
                                    }

                                    @Override
                                    public void onFailure(ResponseDTO responseDTO) {
                                        //DO NOTHING
                                    }
                                });
                    }

                    messages.showInfo(R.string.rc_create_info_message);
                    callback.onSuccess(responseDTO);
                    loadingDialog.hide();
                })
                .addOnFailureListener(e -> {
                    messages.showError(R.string.rc_create_error_message);
                    callback.onFailure(responseDTO);
                    loadingDialog.hide();
                });
    }


    @Override
    public void getRaceControlRegisters(Map<String, Object> filters, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        //Get Event type
        RaceControlEvent event = (RaceControlEvent) filters.get("eventType");

        loadingDialog.show();
        firebaseFirestore.collection(event.getFirebaseTable())
                .orderBy(RaceControlRegisterEndurance.ORDER, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    //Add results to list
                    List<RaceControlRegisterEndurance> result = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        RaceControlRegisterEndurance register = new RaceControlRegisterEndurance(document);
                        result.add(register);
                    }

                    responseDTO.setData(result);
                    messages.showInfo(R.string.rc_info_retrieving_message);
                    callback.onSuccess(responseDTO);
                    loadingDialog.hide();

                })
                .addOnFailureListener(e -> {
                    messages.showError(R.string.rc_error_retrieving_message);
                    callback.onFailure(responseDTO);
                    loadingDialog.hide();
                });

    }

    @Override
    public void updateRaceControlState(RaceControlRegister register, RaceControlEvent event,
                                       RaceControlState newState, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        Date now = Calendar.getInstance().getTime();

        //Update currentState and currentStateDate with the new value
        register.setCurrentState(newState);
        register.setCurrentStateDate(now);

        //Cast the data depending on the Race Type
        Map<String, Object> data = null;
        if (register instanceof RaceControlRegisterEndurance) {
            data = ((RaceControlRegisterEndurance) register).toObjectData();

        } else if (register instanceof RaceControlRegisterAutocross) {
            data = ((RaceControlRegisterAutocross) register).toObjectData();
        }

        //Call Firebase to update
        loadingDialog.show();
        firebaseFirestore.collection(event.getFirebaseTable()).document(register.getID())
                .update(data)
                .addOnSuccessListener(aVoid -> {
                    messages.showInfo(R.string.rc_info_update_message);

                    //Enable/disable cones for the selected car
                    coneControlBO.enableOrDisableConeControlRegistersByTeam(event.getConeControlEvent(), register.getCarNumber(), newState, new BusinessCallback() {
                        @Override
                        public void onSuccess(ResponseDTO responseDTO) {
                            //TODO add things
                            loadingDialog.hide();
                        }

                        @Override
                        public void onFailure(ResponseDTO responseDTO) {
                            //TODO add things
                            loadingDialog.hide();
                        }
                    });
                    callback.onSuccess(responseDTO);

                })
                .addOnFailureListener(e -> {
                    messages.showInfo(R.string.rc_error_update_message);
                    callback.onFailure(responseDTO);
                    loadingDialog.hide();
                });
    }
}
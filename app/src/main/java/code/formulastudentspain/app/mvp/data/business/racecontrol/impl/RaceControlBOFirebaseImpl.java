package code.formulastudentspain.app.mvp.data.business.racecontrol.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.conecontrol.ConeControlBO;
import code.formulastudentspain.app.mvp.data.business.racecontrol.RaceControlBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.model.RaceControlAutocrossState;
import code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState;
import code.formulastudentspain.app.mvp.data.model.RaceControlEvent;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegister;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegisterAutocross;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegisterEndurance;
import code.formulastudentspain.app.mvp.data.model.RaceControlState;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.dialog.RaceControlTeamDTO;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

public class RaceControlBOFirebaseImpl extends DataLoader implements RaceControlBO {

    private FirebaseFirestore firebaseFirestore;
    private TeamBO teamBO;
    private ConeControlBO coneControlBO;


    public RaceControlBOFirebaseImpl(FirebaseFirestore firebaseFirestore, TeamBO teamBO, 
                                     ConeControlBO coneControlBO) {
        this.firebaseFirestore = firebaseFirestore;
        this.teamBO = teamBO;
        this.coneControlBO = coneControlBO;
    }

    @Override
    public ListenerRegistration getRaceControlRegistersRealTime(final Map<String, Object> filters,
                                                                @NotNull OnSuccessCallback<List<RaceControlRegister>> onSuccessCallback,
                                                                @NotNull OnFailureCallback onFailureCallback) {

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

            if (e != null) {
                onFailureCallback.onFailure(new Message(R.string.rc_realtime_error_retrieving_message));
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
            onSuccessCallback.onSuccess(result);
        });
    }


    @Override
    public void getRaceControlTeams(final Map<String, Object> filters,
                                    @NotNull OnSuccessCallback<List<RaceControlTeamDTO>> onSuccessCallback,
                                    @NotNull OnFailureCallback onFailureCallback) {

        //First, retrieve all the teams depending on the race type
        loadingData(true);
        teamBO.retrieveTeams(null, null,
                teams -> {
                    //Second, retrieve the existing Endurance registers
                    getRaceControlRegisters(filters,
                            list -> {
                                List<RaceControlTeamDTO> resultList = new ArrayList<>();

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
                                onSuccessCallback.onSuccess(resultList);
                                loadingData(false);
                            },
                            errorMessage -> {
                                onFailureCallback.onFailure(new Message(R.string.rc_teams_error_message));
                                loadingData(false);
                            });
                },
                error -> {
                    onFailureCallback.onFailure(new Message(R.string.rc_teams_error_message));
                    loadingData(false);
                });
    }

    @Override
    public void createRaceControlRegister(List<RaceControlTeamDTO> raceControlTeamDTOList,
                                          RaceControlEvent eventType, String raceRound, Long currentMaxIndex,
                                          @NotNull OnSuccessCallback<?> onSuccessCallback,
                                          @NotNull OnFailureCallback onFailureCallback) {

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
        loadingData(true);
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
                                new OnSuccessCallback<Object>() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        //TODO
                                    }
                                }, new OnFailureCallback() {
                                    @Override
                                    public void onFailure(Message errorMessage) {
                                        //TODO
                                    }
                                });
                    }

                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.rc_create_error_message));
                    loadingData(false);
                });
    }


    @Override
    public void getRaceControlRegisters(Map<String, Object> filters,
                                        @NotNull OnSuccessCallback<List<RaceControlRegisterEndurance>> onSuccessCallback,
                                        @NotNull OnFailureCallback onFailureCallback) {

        //Get Event type
        RaceControlEvent event = (RaceControlEvent) filters.get("eventType");

        loadingData(true);
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

                    onSuccessCallback.onSuccess(result);
                    loadingData(false);

                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.rc_error_retrieving_message));
                    loadingData(false);
                });

    }

    @Override
    public void updateRaceControlState(RaceControlRegister register, RaceControlEvent event,
                                       RaceControlState newState,
                                       @NotNull OnSuccessCallback<?> onSuccessCallback,
                                       @NotNull OnFailureCallback onFailureCallback) {

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
        loadingData(true);
        firebaseFirestore.collection(event.getFirebaseTable()).document(register.getID())
                .update(data)
                .addOnSuccessListener(aVoid -> {

                    //Enable/disable cones for the selected car
                    coneControlBO.enableOrDisableConeControlRegistersByTeam(event.getConeControlEvent(),
                            register.getCarNumber(), newState,
                            success -> {
                                onSuccessCallback.onSuccess(null);
                                loadingData(false);
                            },
                            errorMessage -> {
                                onFailureCallback.onFailure(errorMessage);
                                loadingData(false);
                            });

                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.rc_error_update_message));
                    loadingData(false);
                });
    }
}
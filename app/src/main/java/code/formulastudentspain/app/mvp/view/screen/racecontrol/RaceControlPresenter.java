package code.formulastudentspain.app.mvp.view.screen.racecontrol;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.racecontrol.RaceControlBO;
import code.formulastudentspain.app.mvp.data.model.RaceControlAutocrossState;
import code.formulastudentspain.app.mvp.data.model.RaceControlEnduranceState;
import code.formulastudentspain.app.mvp.data.model.RaceControlEvent;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegister;
import code.formulastudentspain.app.mvp.data.model.RaceControlState;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.data.model.UserRole;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewLongClickListener;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.dialog.CreateRegisterDialog;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.dialog.FilteringRegistersDialog;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.dialog.RaceControlTeamDTO;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.dialog.UpdatingRegistersDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


public class RaceControlPresenter extends DataConsumer implements RecyclerViewClickListener, RecyclerViewLongClickListener {

    //Race Control Event Type
    private RaceControlEvent rcEventType;
    private String raceType;
    private String raceArea;

    //Dependencies
    private View view;
    private Context context;
    private RaceControlBO raceControlBO;
    private Messages messages;
    private User loggedUser;

    //Data
    private List<RaceControlRegister> raceControlRegisterList = new ArrayList<>();
    private ListenerRegistration registration = null;
    private RaceControlState newState = null;

    //Filtering values
    private Long selectedCarNumber;
    private boolean showFinishedCars = false;


    public RaceControlPresenter(RaceControlPresenter.View view, Context context, RaceControlEvent rcEventType,
                                String raceType, String raceArea, RaceControlBO raceControlBO, User loggedUser,
                                Messages messages) {
        super(raceControlBO);
        this.view = view;
        this.context = context;
        this.rcEventType = rcEventType;
        this.raceControlBO = raceControlBO;
        this.raceType = raceType;
        this.raceArea = raceArea;
        this.loggedUser = loggedUser;
        this.messages = messages;
    }


    /**
     * Retrieve Event registers
     */
    public ListenerRegistration retrieveRegisterList() {

        //We need to prevent multiple listeners if user filters multiple times
        if (registration != null) {
            registration.remove();
        }

        Map<String, Object> filters = new HashMap<>();
        List<String> states = new ArrayList<>();
        if (RaceControlEvent.ENDURANCE.equals(rcEventType)) {
            states.addAll(this.getEnduranceStates());

            if (showFinishedCars) {
                states.add(RaceControlEnduranceState.FINISHED.getAcronym());
            }

        } else if (RaceControlEvent.AUTOCROSS.equals(rcEventType)
                || RaceControlEvent.SKIDPAD.equals(rcEventType)) {
            states.addAll(this.getAutocrossStates());

            if (showFinishedCars) {
                states.add(RaceControlAutocrossState.FINISHED_ROUND_4.getAcronym());
                states.add(RaceControlAutocrossState.DNF_ROUND_4.getAcronym());
            }
        }

        filters.put("states", states);
        filters.put("raceRound", raceType);
        filters.put("eventType", rcEventType);
        filters.put("carNumber", selectedCarNumber);
        filters.put("showFinishedCars", showFinishedCars);

        //Retrieve race control registers in real-time
        ListenerRegistration registration = raceControlBO.getRaceControlRegistersRealTime(filters,
                this::updateEventRegisters,
                this::setErrorToDisplay);

        this.registration = registration;
        return registration;
    }


    private List<String> getAutocrossStates() {
        return new ArrayList<>(Arrays.asList(
                RaceControlAutocrossState.NOT_AVAILABLE.getAcronym(),
                RaceControlAutocrossState.RACING_ROUND_1.getAcronym(),
                RaceControlAutocrossState.FINISHED_ROUND_1.getAcronym(),
                RaceControlAutocrossState.DNF_ROUND_1.getAcronym(),
                RaceControlAutocrossState.RACING_ROUND_2.getAcronym(),
                RaceControlAutocrossState.FINISHED_ROUND_2.getAcronym(),
                RaceControlAutocrossState.DNF_ROUND_2.getAcronym(),
                RaceControlAutocrossState.RACING_ROUND_3.getAcronym(),
                RaceControlAutocrossState.FINISHED_ROUND_3.getAcronym(),
                RaceControlAutocrossState.DNF_ROUND_3.getAcronym(),
                RaceControlAutocrossState.RACING_ROUND_4.getAcronym()
                //RaceControlAutocrossState.FINISHED_ROUND_4.getAcronym(),
                //RaceControlAutocrossState.DNF_ROUND_4.getAcronym()
        ));
    }


    private List<String> getEnduranceStates() {

        //Select states for the selected area
        List<String> states = new ArrayList<>();
        if (context.getString(R.string.rc_area_waiting_area).equals(raceArea)) {
            states.addAll(Collections.singletonList(
                    RaceControlEnduranceState.NOT_AVAILABLE.getAcronym()));

        } else if (context.getString(R.string.rc_area_scrutineering).equals(raceArea)) {
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.WAITING_AREA.getAcronym(),
                    RaceControlEnduranceState.FIXING.getAcronym(),
                    RaceControlEnduranceState.SCRUTINEERING.getAcronym()));

        } else if (context.getString(R.string.rc_area_racing1).equals(raceArea)) {
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.SCRUTINEERING.getAcronym(),
                    RaceControlEnduranceState.READY_TO_RACE_1D.getAcronym()));

        } else if (context.getString(R.string.rc_area_racing2).equals(raceArea)) {
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.RACING_1D.getAcronym(),
                    RaceControlEnduranceState.READY_TO_RACE_2D.getAcronym(),
                    RaceControlEnduranceState.RACING_2D.getAcronym()));

        } else if (raceArea == null || context.getString(R.string.rc_area_all).equals(raceArea)) {
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.NOT_AVAILABLE.getAcronym(),
                    RaceControlEnduranceState.WAITING_AREA.getAcronym(),
                    RaceControlEnduranceState.FIXING.getAcronym(),
                    RaceControlEnduranceState.SCRUTINEERING.getAcronym(),
                    RaceControlEnduranceState.READY_TO_RACE_1D.getAcronym(),
                    RaceControlEnduranceState.RACING_1D.getAcronym(),
                    RaceControlEnduranceState.READY_TO_RACE_2D.getAcronym(),
                    RaceControlEnduranceState.RACING_2D.getAcronym(),
                    //RaceControlEnduranceState.FINISHED.getAcronym(),
                    RaceControlEnduranceState.RUN_LATER.getAcronym(),
                    RaceControlEnduranceState.DNF.getAcronym()));
        }

        return states;
    }


    void openCreateRegisterDialog() {

        Map<String, Object> filters = new HashMap<>();
        filters.put("raceRound", raceType);
        filters.put("eventType", rcEventType);

        //Call business to retrieve teams
        raceControlBO.getRaceControlTeams(filters,
                raceControlTeamsDTO -> {
                    FragmentManager fm = view.getActivity().getSupportFragmentManager();
                    CreateRegisterDialog
                            .newInstance(RaceControlPresenter.this, raceControlTeamsDTO, context)
                            .show(fm, "rc_endurance_create_dialog");
                },
                this::setErrorToDisplay);
    }


    private void updateEventRegisters(List<RaceControlRegister> items) {
        this.raceControlRegisterList.clear();
        this.raceControlRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        RaceControlRegister register = raceControlRegisterList.get(position);

        //State 1 clicked
        if (v.getId() == R.id.state1) {
            newState = register.getNextStateAtIndex(0);

            //State 2 clicked
        } else if (v.getId() == R.id.state2) {
            newState = register.getNextStateAtIndex(1);
        }

        if (RaceControlAutocrossState.DNF_ROUND_1.equals(newState)
                || RaceControlAutocrossState.DNF_ROUND_2.equals(newState)
                || RaceControlAutocrossState.DNF_ROUND_3.equals(newState)
                || RaceControlAutocrossState.DNF_ROUND_4.equals(newState)) {

            if (!loggedUser.getRole().equals(UserRole.ADMINISTRATOR)
                    && !loggedUser.getRole().equals(UserRole.OFFICIAL_MARSHALL)) {
                messages.showError(R.string.rc_info_only_officials);
                return;
            }
        }
        updateRegister(register, rcEventType, newState);
    }


    public void updateRegister(final RaceControlRegister register, final RaceControlEvent event,
                               final RaceControlState newState) {
        raceControlBO.updateRaceControlState(register, event, newState,
                response -> view.closeUpdatedRow(register.getID()),
                this::setErrorToDisplay);
    }


    void filterIconClicked() {
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        FilteringRegistersDialog createFilteringDialog = FilteringRegistersDialog
                .newInstance(RaceControlPresenter.this, selectedCarNumber, showFinishedCars);
        createFilteringDialog.show(fm, "rc_filtering_dialog");
    }

    public void setFilteringValues(Long selectedCarNumber, boolean showFinishedCars) {
        this.selectedCarNumber = selectedCarNumber;
        this.showFinishedCars = showFinishedCars;
        view.filtersActivated(selectedCarNumber != null || showFinishedCars);
    }


    public void createRaceControlRegisters(List<RaceControlTeamDTO> raceControlTeamDTOList, Long currentMaxIndex) {
        raceControlBO.createRaceControlRegister(raceControlTeamDTOList, rcEventType, raceType, currentMaxIndex,
                response -> { /* Nothing to do, real-time baby */ },
                this::setErrorToDisplay);
    }


    @Override
    public void recyclerViewLongListClicked(android.view.View v, int position) {

        if (loggedUser.isAdministrator() || loggedUser.isRole(UserRole.OFFICIAL_MARSHALL)) {

            RaceControlRegister register = raceControlRegisterList.get(position);

            //Opening officials raceControl dialog
            FragmentManager fm = view.getActivity().getSupportFragmentManager();
            UpdatingRegistersDialog createUpdatingDialog = UpdatingRegistersDialog
                    .newInstance(RaceControlPresenter.this, register, rcEventType);
            createUpdatingDialog.show(fm, "rc_updating_dialog");
        }
    }

    public boolean isUserOfficial(){
        return  this.loggedUser.isRole(UserRole.OFFICIAL_MARSHALL);
    }

    public boolean isUserAdministrator(){
        return  this.loggedUser.isAdministrator();
    }


    List<RaceControlRegister> getEventRegisterList() {
        return raceControlRegisterList;
    }

    public interface View {

        FragmentActivity getActivity();

        /**
         * Refresh items in list
         */
        void refreshEventRegisterItems();

        /**
         * Method to know if the filters are activated
         *
         * @param activated
         */
        void filtersActivated(Boolean activated);

        /**
         * The row must be closed if it is updated (register state)
         *
         * @param id
         */
        void closeUpdatedRow(String id);
    }

}

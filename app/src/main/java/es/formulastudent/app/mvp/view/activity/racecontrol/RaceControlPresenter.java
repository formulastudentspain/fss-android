package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.model.RaceControlAutocrossState;
import es.formulastudent.app.mvp.data.model.RaceControlEnduranceState;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewLongClickListener;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.CreateRegisterDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.FilteringRegistersDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.RaceControlTeamDTO;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.UpdatingRegistersDialog;


public class RaceControlPresenter implements RecyclerViewClickListener, RecyclerViewLongClickListener {

    //Race Control Event Type
    RaceControlEvent rcEventType;
    String raceType;
    String raceArea;

    //Dependencies
    private View view;
    private Context context;
    private RaceControlBO raceControlBO;

    //Data
    List<RaceControlRegister> allRaceControlRegisterList = new ArrayList<>();
    List<RaceControlRegister> filteredRaceControlRegisterList = new ArrayList<>();
    ListenerRegistration registration = null;
    RaceControlState newState = null;
    RaceControlRegister register = null;
    User loggedUser;


    //Filtering values
    private Long selectedCarNumber;
    private boolean showFinishedCars = false;


    public RaceControlPresenter(RaceControlPresenter.View view, Context context, RaceControlEvent rcEventType,
                                String raceType, String raceArea, RaceControlBO raceControlBO, User loggedUser) {
        this.view = view;
        this.context = context;
        this.rcEventType = rcEventType;
        this.raceControlBO = raceControlBO;
        this.raceType = raceType;
        this.raceArea = raceArea;
        this.loggedUser = loggedUser;
    }


    /**
     * Retrieve Event registers
     */
     public ListenerRegistration retrieveRegisterList() {

         //We need to prevent multiple listeners if user filters multiple times
         if(registration != null){
             registration.remove();
         }

        //Show loading
        view.showLoading();

        //Filters
        Map<String, Object> filters = new HashMap<>();

         List<String> states = new ArrayList<>();
         if(RaceControlEvent.ENDURANCE.equals(rcEventType)){
             states.addAll(this.getEnduranceStates());

             if(showFinishedCars){
                 states.add(RaceControlEnduranceState.FINISHED.getAcronym());
             }

         }else if(RaceControlEvent.AUTOCROSS.equals(rcEventType)
                 || RaceControlEvent.SKIDPAD.equals(rcEventType)){
             states.addAll(this.getAutocrossStates());

             if(showFinishedCars){
                 states.add(RaceControlAutocrossState.FINISHED_ROUND_4.getAcronym());
                 states.add(RaceControlAutocrossState.DNF_ROUND_4.getAcronym());
             }
         }

         filters.put("states",states);
         filters.put("raceType", raceType);
         filters.put("eventType", rcEventType);
         filters.put("carNumber", selectedCarNumber);
         filters.put("showFinishedCars", showFinishedCars);


        //Retrieve race control registers in real-time
         ListenerRegistration registration = raceControlBO.getRaceControlRegistersRealTime(filters,  new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<RaceControlRegister> results = (List<RaceControlRegister>) responseDTO.getData();
                     updateEventRegisters(results==null ? new ArrayList<>() : results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 //Show error message
                 view.createMessage(responseDTO.getError());
             }
         });

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
        if(context.getString(R.string.rc_area_waiting_area).equals(raceArea)){
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.NOT_AVAILABLE.getAcronym()));

        }else if(context.getString(R.string.rc_area_scrutineering).equals(raceArea)){
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.WAITING_AREA.getAcronym(),
                    RaceControlEnduranceState.FIXING.getAcronym(),
                    RaceControlEnduranceState.SCRUTINEERING.getAcronym()));

        }else if(context.getString(R.string.rc_area_racing1).equals(raceArea)){
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.SCRUTINEERING.getAcronym(),
                    RaceControlEnduranceState.READY_TO_RACE_1D.getAcronym()));

        }else if(context.getString(R.string.rc_area_racing2).equals(raceArea)){
            states.addAll(Arrays.asList(
                    RaceControlEnduranceState.RACING_1D.getAcronym(),
                    RaceControlEnduranceState.READY_TO_RACE_2D.getAcronym(),
                    RaceControlEnduranceState.RACING_2D.getAcronym()));

        }else if(raceArea == null || context.getString(R.string.rc_area_all).equals(raceArea)) {
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


    protected void openCreateRegisterDialog(){

        //Show loading
        view.showLoading();

        Map<String, Object> filters = new HashMap<>();
        filters.put("raceType", raceType);
        filters.put("eventType", rcEventType);

        //Call business to retrieve teams
        raceControlBO.getRaceControlTeams(filters, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<RaceControlTeamDTO> raceControlTeamsDTO = (List<RaceControlTeamDTO>)responseDTO.getData();

                //With all the information, we open the dialog
                FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
                CreateRegisterDialog createUserDialog = CreateRegisterDialog
                        .newInstance(RaceControlPresenter.this, raceControlTeamsDTO, context);
                createUserDialog.show(fm, "rc_endurance_create_dialog");

                //Hide loading
                view.hideLoading();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                view.createMessage(responseDTO.getError());
            }

        });

    }


    public void updateEventRegisters(List<RaceControlRegister> items){
        //Update all-register-list
        this.allRaceControlRegisterList.clear();
        this.allRaceControlRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredRaceControlRegisterList.clear();
        this.filteredRaceControlRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
    }




    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

         register = filteredRaceControlRegisterList.get(position);

        //State 1 clicked
        if(v.getId() == R.id.state1){
            newState = register.getNextStateAtIndex(0);

        //State 2 clicked
        }else if(v.getId() == R.id.state2){
            newState = register.getNextStateAtIndex(1);
        }

        if(RaceControlAutocrossState.DNF_ROUND_1.equals(newState)
            || RaceControlAutocrossState.DNF_ROUND_2.equals(newState)
            || RaceControlAutocrossState.DNF_ROUND_3.equals(newState)
            || RaceControlAutocrossState.DNF_ROUND_4.equals(newState)){

            if(!loggedUser.getRole().equals(UserRole.ADMINISTRATOR) &&
                !loggedUser.getRole().equals(UserRole.OFFICIAL_MARSHALL)){
                view.createMessage(R.string.rc_info_only_officials);
                return;
            }
        }

        //Update register state
        updateRegister(register, rcEventType, newState);
    }

    /**
     * Change the register state
     * @param register
     * @param event
     * @param newState
     */
    public void updateRegister(final RaceControlRegister register, final RaceControlEvent event, final RaceControlState newState){

         final String oldState = register.getCurrentState().getAcronym();

         raceControlBO.updateRaceControlState(register, event, newState, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Close the swiped row after updating
                view.closeUpdatedRow(register.getID());

                //Show success message
                view.createMessage(R.string.rc_update_state_success_message, oldState, newState.getAcronym());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                view.createMessage(responseDTO.getError());
            }
        });
    }


    void filterIconClicked(){

        //Opening filtering dialog
        FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
        FilteringRegistersDialog createFilteringDialog = FilteringRegistersDialog
                .newInstance(RaceControlPresenter.this, selectedCarNumber, showFinishedCars);
        createFilteringDialog.show(fm, "rc_filtering_dialog");

    }

    public void setFilteringValues(Long selectedCarNumber, boolean showFinishedCars){
        this.selectedCarNumber = selectedCarNumber;
        this.showFinishedCars = showFinishedCars;

        view.filtersActivated(selectedCarNumber != null || showFinishedCars);
    }


    /**
     * Create race control registers (as a transaction)
     * @param raceControlTeamDTOList
     * @param currentMaxIndex
     */
    public void createRaceControlRegisters(List<RaceControlTeamDTO> raceControlTeamDTOList, Long currentMaxIndex){

         raceControlBO.createRaceControlRegister(raceControlTeamDTOList, rcEventType, raceType, currentMaxIndex, new BusinessCallback() {
             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                 //Show info message
                 view.createMessage(responseDTO.getInfo());
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 //Show error message
                 view.createMessage(responseDTO.getError());
             }
         });

    }


    public List<RaceControlRegister> getEventRegisterList() {
        return filteredRaceControlRegisterList;
    }

    @Override
    public void recyclerViewLongListClicked(android.view.View v, int position) {

        if(loggedUser.getRole().equals(UserRole.ADMINISTRATOR) ||
            loggedUser.getRole().equals(UserRole.OFFICIAL_MARSHALL)){

            RaceControlRegister register = filteredRaceControlRegisterList.get(position);

            //Opening officials raceControl dialog
            FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
            UpdatingRegistersDialog createUpdatingDialog = UpdatingRegistersDialog
                .newInstance(RaceControlPresenter.this, register, rcEventType);
            createUpdatingDialog.show(fm, "rc_updating_dialog");
        }
    }



    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object...args);

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoading();

        /**
         * Refresh items in list
         */
        void refreshEventRegisterItems();

        /**
         * Method to know if the filters are activated
         * @param activated
         */
        void filtersActivated(Boolean activated);

        /**
         * The row must be closed if it is updated (register state)
         * @param id
         */
        void closeUpdatedRow(String id);
    }

}

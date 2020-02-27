package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.RaceControlAutocrossState;
import es.formulastudent.app.mvp.data.model.RaceControlEnduranceState;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventGeneralPresenter;
import es.formulastudent.app.mvp.view.activity.dynamicevent.dialog.ConfirmEventRegisterDialog;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewLongClickListener;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.CreateRegisterDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.FilteringRegistersDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.RaceControlTeamDTO;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.UpdatingRegistersDialog;


public class RaceControlPresenter implements RecyclerViewClickListener, RecyclerViewLongClickListener, DynamicEventGeneralPresenter {

    //Race Control Event Type
    RaceControlEvent rcEventType;
    String raceType;
    String raceArea;

    //Dependencies
    private View view;
    private Context context;
    private RaceControlBO raceControlBO;
    private TeamMemberBO teamMemberBO;
    private BriefingBO briefingBO;
    private DynamicEventBO dynamicEventBO;

    //Data
    List<RaceControlRegister> allRaceControlRegisterList = new ArrayList<>();
    List<RaceControlRegister> filteredRaceControlRegisterList = new ArrayList<>();
    ListenerRegistration registration = null;
    RaceControlState newState = null;
    RaceControlRegister register = null;


    //Filtering values
    private Long selectedCarNumber;


    public RaceControlPresenter(RaceControlPresenter.View view, Context context, RaceControlEvent rcEventType,
                                String raceType, String raceArea, RaceControlBO raceControlBO, TeamMemberBO teamMemberBO, BriefingBO briefingBO, DynamicEventBO dynamicEventBO) {
        this.view = view;
        this.context = context;
        this.rcEventType = rcEventType;
        this.raceControlBO = raceControlBO;
        this.teamMemberBO = teamMemberBO;
        this.briefingBO = briefingBO;
        this.dynamicEventBO = dynamicEventBO;
        this.raceType = raceType;
        this.raceArea = raceArea;
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

         }else if(RaceControlEvent.AUTOCROSS.equals(rcEventType)
                 || RaceControlEvent.SKIDPAD.equals(rcEventType)){
             states.addAll(this.getAutocrossStates());
         }

         filters.put("states",states);
         filters.put("raceType", raceType);
         filters.put("eventType", rcEventType);
         filters.put("carNumber", selectedCarNumber);


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
                RaceControlAutocrossState.RACING_ROUND_2.getAcronym(),
                RaceControlAutocrossState.FINISHED_ROUND_2.getAcronym(),
                RaceControlAutocrossState.RACING_ROUND_3.getAcronym(),
                RaceControlAutocrossState.FINISHED_ROUND_3.getAcronym(),
                RaceControlAutocrossState.RACING_ROUND_4.getAcronym(),
                RaceControlAutocrossState.FINISHED_ROUND_4.getAcronym(),
                RaceControlAutocrossState.DNF.getAcronym()));
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
                    RaceControlEnduranceState.FINISHED.getAcronym(),
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

            if(newState.equals(RaceControlEnduranceState.READY_TO_RACE_1D) || newState.equals(RaceControlEnduranceState.READY_TO_RACE_2D)){
                //Open NFC reader to check driver
                view.openNFCReader();

            }else{
                //Update register state
                updateRegister(register, rcEventType, newState);
            }

        //State 2 clicked
        }else if(v.getId() == R.id.state2){

            newState = register.getNextStateAtIndex(1);

            if(newState.equals(RaceControlEnduranceState.READY_TO_RACE_1D) || newState.equals(RaceControlEnduranceState.READY_TO_RACE_2D)){
                //Open NFC reader to check driver
                view.openNFCReader();

            }else{
                //Update register state
                updateRegister(register, rcEventType, newState);
            }
        }


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
                .newInstance(RaceControlPresenter.this, selectedCarNumber);
        createFilteringDialog.show(fm, "rc_filtering_dialog");

    }

    public void setFilteringValues(Long selectedCarNumber){
        this.selectedCarNumber = selectedCarNumber;

        view.filtersActivated(selectedCarNumber != null);
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

    /**
     * Retrieve user by NFC tag after read
     * @param tag
     */
    void onNFCTagDetected(String tag){

        //Show loading
        view.showLoading();

        //Retrieve user by the NFC tag
        teamMemberBO.retrieveTeamMemberByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                TeamMember teamMember = (TeamMember)responseDTO.getData();

                //Now check if the teamMember did the briefing today
                getUserBriefingRegister(teamMember);
            }
            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoading();

                //Show error message
                view.createMessage(R.string.team_member_get_by_nfc_error);
            }
        });
    }

    void getUserBriefingRegister(final TeamMember teamMember){

        Calendar cal = Calendar.getInstance();
        Date to = cal.getTime();

        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.SECOND, 0);

        Date from = cal.getTime(); //current day at 05:00am

        if(teamMember != null && teamMember.getID() != null) {

            //If the teamMember exists, retrieve its briefing registers
            briefingBO.retrieveBriefingRegistersByUserAndDates(from, to, teamMember.getID(), new BusinessCallback() {

                @Override
                public void onSuccess(ResponseDTO responseDTO) {

                    List<BriefingRegister> briefingRegisters = (List<BriefingRegister>) responseDTO.getData();

                    //Hide loading
                    view.hideLoading();

                    //With all the information, we create the dialog
                    FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
                    ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                            .newInstance(RaceControlPresenter.this, teamMember, !briefingRegisters.isEmpty());

                    //Show the dialog
                    createUserDialog.show(fm, "fragment_event_confirm");

                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    //Show error message
                    view.createMessage(R.string.briefing_messages_retrieve_registers_error);
                }
            });

        } else {

            //Hide loading
            view.hideLoading();

            //Show error message
            view.createMessage(R.string.team_member_get_by_nfc_not_existing);
        }
    }


    public List<RaceControlRegister> getEventRegisterList() {
        return filteredRaceControlRegisterList;
    }

    @Override
    public void recyclerViewLongListClicked(android.view.View v, int position) {
        RaceControlRegister register = filteredRaceControlRegisterList.get(position);

        //Opening officials raceControl dialog
        FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
        UpdatingRegistersDialog createUpdatingDialog = UpdatingRegistersDialog
                .newInstance(RaceControlPresenter.this, register, rcEventType);
        createUpdatingDialog.show(fm, "rc_updating_dialog");

    }

    @Override
    public void createRegistry(final TeamMember teamMember, final Long carNumber, final Boolean briefingDone){

        //Show loading
        view.showLoading();

        //Check that the driver is able to run, that means he/she has not run already in two different events
        dynamicEventBO.getDifferentEventRegistersByDriver(teamMember.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                if(responseDTO.getError() == null){

                    Map<String, EventRegister> eventRegisterMap = (Map<String, EventRegister>) responseDTO.getData();

                    //The driver can't run
                    if(eventRegisterMap.size() >= 2
                            && !eventRegisterMap.containsKey(rcEventType.getEventType().name())
                            && !rcEventType.getEventType().equals(EventType.PRE_SCRUTINEERING)
                            && !rcEventType.getEventType().equals(EventType.BRIEFING)
                            && !rcEventType.getEventType().equals(EventType.PRACTICE_TRACK)){

                        //Hide loading
                        view.hideLoading();

                        //Show error message
                        view.createMessage(R.string.dynamic_event_message_error_runs);

                    } else {

                        //The driver can run, create the register
                        dynamicEventBO.createRegister(teamMember, carNumber, briefingDone, rcEventType.getEventType(), new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {

                                //Refresh the records
                                retrieveRegisterList();

                                //Hide loading
                                view.hideLoading();

                                //Update new state
                                updateRegister(register, rcEventType, newState);

                            }
                            @Override
                            public void onFailure(ResponseDTO responseDTO) {

                                //Hide loading
                                view.hideLoading();

                                //Show error message
                                view.createMessage(R.string.dynamic_event_message_error_create);
                            }
                        });
                    }

                } else {

                    //Hide loading
                    view.hideLoading();

                    //Show error message
                    view.createMessage(R.string.dynamic_event_message_error_runs);
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                view.createMessage(R.string.dynamic_event_message_error_retrieving_by_driver);
            }
        });

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
         * Open NFC reader
         */
        void openNFCReader();

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

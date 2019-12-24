package es.formulastudent.app.mvp.view.activity.dynamicevent;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.activity.dynamicevent.dialog.ConfirmEventRegisterDialog;
import es.formulastudent.app.mvp.view.activity.dynamicevent.dialog.DeleteEventRegisterDialog;
import es.formulastudent.app.mvp.view.activity.dynamicevent.dialog.FilteringRegistersDialog;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class DynamicEventPresenter implements RecyclerViewClickListener, DynamicEventGeneralPresenter {

    //DYNAMIC EVENT TYPE
    EventType eventType;

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private DynamicEventBO dynamicEventBO;
    private TeamMemberBO teamMemberBO;
    private BriefingBO briefingBO;
    private EgressBO egressBO;

    //Data
    List<EventRegister> allEventRegisterList = new ArrayList<>();
    List<EventRegister> filteredEventRegisterList = new ArrayList<>();

    //Filtering values
    List<Team> teams;
    String selectedTeamID;
    String selectedDay;
    Long selectedCarNumber;

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;


    public DynamicEventPresenter(DynamicEventPresenter.View view, TeamBO teamBO,
                                 DynamicEventBO dynamicEventBO, TeamMemberBO teamMemberBO, BriefingBO briefingBO, EventType eventType, EgressBO egressBO) {
        this.view = view;
        this.teamBO = teamBO;
        this.dynamicEventBO = dynamicEventBO;
        this.teamMemberBO = teamMemberBO;
        this.briefingBO = briefingBO;
        this.egressBO = egressBO;
        this.eventType = eventType;
    }



    /**
     * Create register
     * @param teamMember
     * @param carNumber
     * @param briefingDone
     */
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
                            && !eventRegisterMap.containsKey(eventType.name())
                            && !eventType.equals(EventType.PRE_SCRUTINEERING)
                            && !eventType.equals(EventType.BRIEFING)
                            && !eventType.equals(EventType.PRACTICE_TRACK)){

                        //Hide loading
                        view.hideLoading();

                        //Show error message
                        view.createMessage(R.string.dynamic_event_message_error_runs);

                    } else {

                        //The driver can run, create the register
                        dynamicEventBO.createRegister(teamMember, carNumber, briefingDone, eventType, new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {

                                //Refresh the records
                                retrieveRegisterList();

                                //Hide loading
                                view.hideLoading();
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


    /**
     * Retrieve Event registers
     */
     public void retrieveRegisterList() {

        //Show loading
        view.showLoading();

        //Call Event business
        dynamicEventBO.retrieveRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, selectedCarNumber, eventType, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                 //Refresh the records
                 List<EventRegister> results = (List<EventRegister>) responseDTO.getData();
                 updateEventRegisters(results==null ? new ArrayList<EventRegister>() : results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 //Show error message
                 view.createMessage(R.string.dynamic_event_message_error_retrieving_registers);
             }
         });
    }


    public void updateEventRegisters(List<EventRegister> items){
        //Update all-register-list
        this.allEventRegisterList.clear();
        this.allEventRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredEventRegisterList.clear();
        this.filteredEventRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
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




    /**
     * Method to delete a dynamic event register
     * @param registerID
     */
    public void deleteDynamicEventRegister(String registerID) {

        //Show loading
        view.showLoading();

        //Call business to delete the dynamic event register
        dynamicEventBO.deleteRegister(eventType, registerID, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Show info message
                view.createMessage(responseDTO.getInfo());

                //Retrieve and refresh the list
                retrieveRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error
                view.createMessage(responseDTO.getError());
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
                    FragmentManager fm = ((DynamicEventActivity)view.getActivity()).getSupportFragmentManager();
                    ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                            .newInstance(DynamicEventPresenter.this, teamMember, !briefingRegisters.isEmpty());

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



    /**
     * Retrieve teams from database
     */
    void retrieveTeams(){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveAllTeams(null, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<Team> teams = (List<Team>)responseDTO.getData();

                //Add "All" option
                Team teamAll = new Team("-1", "All");
                teams.add(0, teamAll);

                //Show filtering dialog
                openFilteringDialog(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.dynamic_event_message_error_retrieving_teams);
            }

        });
    }


    /**
     * Open the dialog to repeat the run
     * @param selectedRegister
     */
    void openRepeatRunDialog(EventRegister selectedRegister) {

        //With all the information, we open the dialog
        FragmentManager fm = ((DynamicEventActivity)view.getActivity()).getSupportFragmentManager();
        ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                .newInstance(DynamicEventPresenter.this, selectedRegister);
        createUserDialog.show(fm, "fragment_event_confirm");
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        //Delete run
        if(v.getId() == R.id.delete_run_button){
            EventRegister selectedRegister = filteredEventRegisterList.get(position);
            openConfirmDeleteRegister(selectedRegister);

        //Repeat run
        }else if(v.getId() == R.id.repeat_run_button){
            EventRegister selectedRegister = filteredEventRegisterList.get(position);
            openRepeatRunDialog(selectedRegister);
        }

    }


    /**
     * Open the dialog to filter results
     * @param teams
     */
    void openFilteringDialog(List<Team> teams){
        this.teams = teams;

        //With all the information, we create the dialog
        FragmentManager fm = ((DynamicEventActivity)view.getActivity()).getSupportFragmentManager();
        FilteringRegistersDialog createUserDialog = FilteringRegistersDialog
                .newInstance(this, teams, selectedTeamID, selectedCarNumber, selectedDay);

        //Show the dialog
        createUserDialog.show(fm, "fragment_event_confirm");

        //Hide loading right after showing the filtering dialog
        view.hideLoading();
    }


    /**
     * Open the dialog to confirm the register to be deleted
     * @param register
     */
    void openConfirmDeleteRegister(EventRegister register){
        //With all the information, we open the dialog
        FragmentManager fm = ((DynamicEventActivity)view.getActivity()).getSupportFragmentManager();
        DeleteEventRegisterDialog deleteEventRegisterDialog = DeleteEventRegisterDialog.newInstance(this, register);
        deleteEventRegisterDialog.show(fm, "delete_event_confirm");
    }


    /**
     * Open the filtering dialog if we already have the teams, retrieve them if not.
     */
    void filterIconClicked(){

        //Go retrieve teams if we have not yet
        if(teams == null){
            retrieveTeams();
        }else{
            openFilteringDialog(teams);
        }
    }


    /**
     * Activate/deactivate filtering indicator (red circle in filtering icon)
     * @param selectedDateFrom
     * @param selectedDateTo
     * @param selectedDay
     * @param selectedTeamID
     * @param selectedCarNumber
     */
    public void setFilteringValues(Date selectedDateFrom, Date selectedDateTo, String selectedDay, String selectedTeamID, Long selectedCarNumber){
        this.selectedDateFrom = selectedDateFrom;
        this.selectedDateTo = selectedDateTo;
        this.selectedTeamID = selectedTeamID;
        this.selectedDay = selectedDay;
        this.selectedCarNumber = selectedCarNumber;

        view.filtersActivated(
                selectedDay!=null
                        || selectedCarNumber != null
                        || (selectedTeamID != null && !selectedTeamID.equals("-1"))
        );
    }

    /**
     * Create message
     * @param message
     */
    public void createMessage(Integer message){
        view.createMessage(message);
    }


    public List<EventRegister> getEventRegisterList() {
        return filteredEventRegisterList;
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
    }

}

package es.formulastudent.app.mvp.view.screen.raceaccess;

import androidx.fragment.app.FragmentActivity;
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
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.screen.raceaccess.dialog.ConfirmEventRegisterDialog;
import es.formulastudent.app.mvp.view.screen.raceaccess.dialog.DeleteEventRegisterDialog;
import es.formulastudent.app.mvp.view.screen.raceaccess.dialog.FilteringRegistersDialog;


public class RaceAccessPresenter implements RecyclerViewClickListener, RaceAccessGeneralPresenter {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private DynamicEventBO dynamicEventBO;
    private TeamMemberBO teamMemberBO;
    private BriefingBO briefingBO;
    private EgressBO egressBO;

    //Data
    private EventType eventType;
    private List<EventRegister> eventRegisterList = new ArrayList<>();

    //Filtering values
    private List<Team> teams;
    private String selectedTeamID;
    private String selectedDay;
    private Long selectedCarNumber;

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;


    public RaceAccessPresenter(RaceAccessPresenter.View view, TeamBO teamBO,
                               DynamicEventBO dynamicEventBO, TeamMemberBO teamMemberBO,
                               BriefingBO briefingBO, EventType eventType, EgressBO egressBO) {
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

                        //TODO Show error message
                        //view.createMessage(R.string.dynamic_event_message_error_runs);

                    } else {
                        dynamicEventBO.createRegister(teamMember, carNumber, briefingDone, eventType, new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {
                                retrieveRegisterList();
                            }
                            @Override
                            public void onFailure(ResponseDTO responseDTO) { }
                        });
                   }
                } else {

                    //TODO: Show error message
                    //view.createMessage(R.string.dynamic_event_message_error_runs);
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) { }
        });
    }


    /**
     * Retrieve Event registers
     */
     public void retrieveRegisterList() {
        dynamicEventBO.retrieveRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, selectedCarNumber, eventType, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                 List<EventRegister> results = (List<EventRegister>) responseDTO.getData();
                 updateEventRegisters(results==null ? new ArrayList<EventRegister>() : results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) { }
         });
    }


    private void updateEventRegisters(List<EventRegister> items){
         this.eventRegisterList.clear();
        this.eventRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
    }


    /**
     * Retrieve user by NFC tag after read
     * @param tag
     */
    void onNFCTagDetected(String tag){
        teamMemberBO.retrieveTeamMemberByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                TeamMember teamMember = (TeamMember)responseDTO.getData();
                getUserBriefingRegister(teamMember);
            }
            @Override
            public void onFailure(ResponseDTO responseDTO) {}
        });
    }

    /**
     * Method to delete a dynamic event register
     * @param registerID
     */
    public void deleteDynamicEventRegister(String registerID) {
        dynamicEventBO.deleteRegister(eventType, registerID, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {}
        });
    }

    private void getUserBriefingRegister(final TeamMember teamMember){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.SECOND, 0);

        if(teamMember != null && teamMember.getID() != null) {
            briefingBO.checkBriefingByUser(teamMember.getID(), new BusinessCallback() {

                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    Boolean briefingAvailable = (Boolean) responseDTO.getData();

                    //With all the information, we create the dialog
                    FragmentManager fm = view.getActivity().getSupportFragmentManager();
                    ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                            .newInstance(RaceAccessPresenter.this, teamMember, briefingAvailable);
                    createUserDialog.show(fm, "fragment_event_confirm");
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) { }
            });

        } else {
            //Show error message
            //view.createMessage(R.string.team_member_get_by_nfc_not_existing);
        }
    }



    /**
     * Retrieve teams from database
     */
    private void retrieveTeams(){
        teamBO.retrieveTeams(null, null, new BusinessCallback() {

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
            public void onFailure(ResponseDTO responseDTO) {}
        });
    }


    /**
     * Open the dialog to repeat the run
     * @param selectedRegister
     */
    void openRepeatRunDialog(EventRegister selectedRegister) {

        //With all the information, we open the dialog
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                .newInstance(RaceAccessPresenter.this, selectedRegister);
        createUserDialog.show(fm, "fragment_event_confirm");
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        //Delete run
        if(v.getId() == R.id.delete_run_button){
            EventRegister selectedRegister = eventRegisterList.get(position);
            openConfirmDeleteRegister(selectedRegister);

        //Repeat run
        }else if(v.getId() == R.id.repeat_run_button){
            EventRegister selectedRegister = eventRegisterList.get(position);
            openRepeatRunDialog(selectedRegister);
        }
    }


    /**
     * Open the dialog to filter results
     * @param teams
     */
    private void openFilteringDialog(List<Team> teams){
        this.teams = teams;

        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        FilteringRegistersDialog createUserDialog = FilteringRegistersDialog
                .newInstance(this, teams, selectedTeamID, selectedCarNumber, selectedDay);
        createUserDialog.show(fm, "fragment_event_confirm");
    }


    /**
     * Open the dialog to confirm the register to be deleted
     * @param register
     */
    private void openConfirmDeleteRegister(EventRegister register){
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        DeleteEventRegisterDialog deleteEventRegisterDialog = DeleteEventRegisterDialog.newInstance(this, register);
        deleteEventRegisterDialog.show(fm, "delete_event_confirm");
    }


    /**
     * Open the filtering dialog if we already have the teams, retrieve them if not.
     */
    void filterIconClicked(){
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

    List<EventRegister> getEventRegisterList() {
        return eventRegisterList;
    }

    public interface View {

        FragmentActivity getActivity();

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

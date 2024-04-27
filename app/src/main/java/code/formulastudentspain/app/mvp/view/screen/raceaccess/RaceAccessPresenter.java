package code.formulastudentspain.app.mvp.view.screen.raceaccess;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.briefing.BriefingBO;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.data.business.raceaccess.RaceAccessBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.EventRegister;
import code.formulastudentspain.app.mvp.data.model.EventType;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.TeamMember;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import code.formulastudentspain.app.mvp.view.screen.raceaccess.dialog.ConfirmEventRegisterDialog;
import code.formulastudentspain.app.mvp.view.screen.raceaccess.dialog.DeleteEventRegisterDialog;
import code.formulastudentspain.app.mvp.view.screen.raceaccess.dialog.FilteringRegistersDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;


public class RaceAccessPresenter extends DataConsumer implements RecyclerViewClickListener, RaceAccessGeneralPresenter {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private RaceAccessBO raceAccessBO;
    private TeamMemberBO teamMemberBO;
    private BriefingBO briefingBO;
    private EgressBO egressBO; //TODO use it to take egress done

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
                               RaceAccessBO raceAccessBO, TeamMemberBO teamMemberBO,
                               BriefingBO briefingBO, EventType eventType, EgressBO egressBO) {
        super(teamBO, raceAccessBO, teamMemberBO, briefingBO, egressBO);
        this.view = view;
        this.teamBO = teamBO;
        this.raceAccessBO = raceAccessBO;
        this.teamMemberBO = teamMemberBO;
        this.briefingBO = briefingBO;
        this.egressBO = egressBO;
        this.eventType = eventType;
    }


    @Override
    public void createRegistry(final TeamMember teamMember, final Long carNumber, final Boolean briefingDone) {
        raceAccessBO.getDifferentEventRegistersByDriver(teamMember.getID(),
                eventRegisterMap -> {

                    //The driver can't run
                    if (eventRegisterMap.size() >= 2
                            && !eventRegisterMap.containsKey(eventType.name())
                            && !eventType.equals(EventType.PRE_SCRUTINEERING)
                            && !eventType.equals(EventType.BRIEFING)
                            && !eventType.equals(EventType.PRACTICE_TRACK)) {

                        setErrorToDisplay(new Message(R.string.dynamic_event_message_error_runs));
                    } else {
                        raceAccessBO.createRegister(teamMember, carNumber, briefingDone, eventType,
                                success -> retrieveRegisterList(),
                                this::setErrorToDisplay);
                    }
                }, this::setErrorToDisplay);
    }


    /**
     * Retrieve Event registers
     */
    public void retrieveRegisterList() {
        raceAccessBO.retrieveRegisters(selectedDateFrom, selectedDateTo, selectedTeamID,
                selectedCarNumber, eventType,
                this::updateEventRegisters,
                this::setErrorToDisplay);
    }


    private void updateEventRegisters(List<EventRegister> items) {
        this.eventRegisterList.clear();
        this.eventRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
    }


    /**
     * Retrieve user by NFC tag after read
     *
     * @param tag
     */
    void onNFCTagDetected(String tag) {
        teamMemberBO.retrieveTeamMemberByNFCTag(tag,
                this::getUserBriefingRegister,
                this::setErrorToDisplay);
    }

    /**
     * Method to delete a dynamic event register
     *
     * @param registerID
     */
    public void deleteDynamicEventRegister(String registerID) {
        raceAccessBO.deleteRegister(eventType, registerID,
                response -> retrieveRegisterList(),
                this::setErrorToDisplay);
    }

    private void getUserBriefingRegister(final TeamMember teamMember) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.SECOND, 0);

        if (teamMember != null && teamMember.getID() != null) {
            briefingBO.checkBriefingByUser(teamMember.getID(),
                    briefingAvailable -> {
                        if (briefingAvailable != null) {
                            FragmentManager fm = view.getActivity().getSupportFragmentManager();
                            ConfirmEventRegisterDialog
                                    .newInstance(RaceAccessPresenter.this, teamMember, briefingAvailable)
                                    .show(fm, "fragment_event_confirm");
                        }
                    },
                    this::setErrorToDisplay);
        } else {
            setErrorToDisplay(new Message(R.string.team_member_get_by_nfc_not_existing));
        }
    }


    /**
     * Retrieve teams from database
     */
    private void retrieveTeams() {
        teamBO.retrieveTeams(null, null,
                teams -> {
                    Team teamAll = new Team("-1", "All");
                    teams.add(0, teamAll);
                    openFilteringDialog(teams);
                },
                this::setErrorToDisplay);
    }


    /**
     * Open the dialog to repeat the run
     *
     * @param selectedRegister
     */
    private void openRepeatRunDialog(EventRegister selectedRegister) {

        //With all the information, we open the dialog
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                .newInstance(RaceAccessPresenter.this, selectedRegister);
        createUserDialog.show(fm, "fragment_event_confirm");
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        //Delete run
        if (v.getId() == R.id.delete_run_button) {
            EventRegister selectedRegister = eventRegisterList.get(position);
            openConfirmDeleteRegister(selectedRegister);

            //Repeat run
        } else if (v.getId() == R.id.repeat_run_button) {
            EventRegister selectedRegister = eventRegisterList.get(position);
            openRepeatRunDialog(selectedRegister);
        }
    }


    /**
     * Open the dialog to filter results
     *
     * @param teams
     */
    private void openFilteringDialog(List<Team> teams) {
        this.teams = teams;

        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        FilteringRegistersDialog createUserDialog = FilteringRegistersDialog
                .newInstance(this, teams, selectedTeamID, selectedCarNumber, selectedDay);
        createUserDialog.show(fm, "fragment_event_confirm");
    }


    /**
     * Open the dialog to confirm the register to be deleted
     *
     * @param register
     */
    private void openConfirmDeleteRegister(EventRegister register) {
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        DeleteEventRegisterDialog deleteEventRegisterDialog = DeleteEventRegisterDialog.newInstance(this, register);
        deleteEventRegisterDialog.show(fm, "delete_event_confirm");
    }


    /**
     * Open the filtering dialog if we already have the teams, retrieve them if not.
     */
    void filterIconClicked() {
        if (teams == null) {
            retrieveTeams();
        } else {
            openFilteringDialog(teams);
        }
    }


    /**
     * Activate/deactivate filtering indicator (red circle in filtering icon)
     *
     * @param selectedDateFrom
     * @param selectedDateTo
     * @param selectedDay
     * @param selectedTeamID
     * @param selectedCarNumber
     */
    public void setFilteringValues(Date selectedDateFrom, Date selectedDateTo, String selectedDay, String selectedTeamID, Long selectedCarNumber) {
        this.selectedDateFrom = selectedDateFrom;
        this.selectedDateTo = selectedDateTo;
        this.selectedTeamID = selectedTeamID;
        this.selectedDay = selectedDay;
        this.selectedCarNumber = selectedCarNumber;

        view.filtersActivated(
                selectedDay != null
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
         *
         * @param activated
         */
        void filtersActivated(Boolean activated);
    }

}

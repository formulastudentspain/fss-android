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
import es.formulastudent.app.mvp.data.business.raceaccess.RaceAccessBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.DataConsumer;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.screen.raceaccess.dialog.ConfirmEventRegisterDialog;
import es.formulastudent.app.mvp.view.screen.raceaccess.dialog.DeleteEventRegisterDialog;
import es.formulastudent.app.mvp.view.screen.raceaccess.dialog.FilteringRegistersDialog;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;


public class RaceAccessPresenter extends DataConsumer implements RecyclerViewClickListener, RaceAccessGeneralPresenter {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private RaceAccessBO raceAccessBO;
    private TeamMemberBO teamMemberBO;
    private BriefingBO briefingBO;
    private EgressBO egressBO; //TODO use it to take egress done
    private LoadingDialog loadingDialog;
    private Messages messages;

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
                               BriefingBO briefingBO, EventType eventType, EgressBO egressBO,
                               LoadingDialog loadingDialog, Messages messages) {
        super(teamBO, raceAccessBO, teamMemberBO, briefingBO, egressBO);
        this.view = view;
        this.teamBO = teamBO;
        this.raceAccessBO = raceAccessBO;
        this.teamMemberBO = teamMemberBO;
        this.briefingBO = briefingBO;
        this.egressBO = egressBO;
        this.eventType = eventType;
        this.loadingDialog = loadingDialog;
        this.messages = messages;
    }


    /**
     * Create register
     *
     * @param teamMember
     * @param carNumber
     * @param briefingDone
     */
    @Override
    public void createRegistry(final TeamMember teamMember, final Long carNumber, final Boolean briefingDone) {
        loadingDialog.show();
        raceAccessBO.getDifferentEventRegistersByDriver(teamMember.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                if (responseDTO.getError() == null) {

                    Map<String, EventRegister> eventRegisterMap = (Map<String, EventRegister>) responseDTO.getData();

                    //The driver can't run
                    if (eventRegisterMap.size() >= 2
                            && !eventRegisterMap.containsKey(eventType.name())
                            && !eventType.equals(EventType.PRE_SCRUTINEERING)
                            && !eventType.equals(EventType.BRIEFING)
                            && !eventType.equals(EventType.PRACTICE_TRACK)) {

                        messages.showError(R.string.dynamic_event_message_error_runs);

                    } else {
                        loadingDialog.show();
                        raceAccessBO.createRegister(teamMember, carNumber, briefingDone, eventType, new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {
                                loadingDialog.hide();
                                retrieveRegisterList();
                            }

                            @Override
                            public void onFailure(ResponseDTO responseDTO) {
                                loadingDialog.hide();
                                messages.showError(responseDTO.getError());
                            }
                        });
                    }
                } else {
                    messages.showError(R.string.dynamic_event_message_error_runs);
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }


    /**
     * Retrieve Event registers
     */
    public void retrieveRegisterList() {
        loadingDialog.show();
        raceAccessBO.retrieveRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, selectedCarNumber, eventType, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                List<EventRegister> results = (List<EventRegister>) responseDTO.getData();
                updateEventRegisters(results == null ? new ArrayList<EventRegister>() : results);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
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
        loadingDialog.show();
        teamMemberBO.retrieveTeamMemberByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                TeamMember teamMember = (TeamMember) responseDTO.getData();
                getUserBriefingRegister(teamMember);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }

    /**
     * Method to delete a dynamic event register
     *
     * @param registerID
     */
    public void deleteDynamicEventRegister(String registerID) {
        loadingDialog.show();
        raceAccessBO.deleteRegister(eventType, registerID, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                retrieveRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }

    private void getUserBriefingRegister(final TeamMember teamMember) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.SECOND, 0);

        if (teamMember != null && teamMember.getID() != null) {
            loadingDialog.show();
            briefingBO.checkBriefingByUser(teamMember.getID(), new BusinessCallback() {

                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    loadingDialog.hide();
                    Boolean briefingAvailable = (Boolean) responseDTO.getData();

                    //With all the information, we create the dialog
                    FragmentManager fm = view.getActivity().getSupportFragmentManager();
                    ConfirmEventRegisterDialog createUserDialog = ConfirmEventRegisterDialog
                            .newInstance(RaceAccessPresenter.this, teamMember, briefingAvailable);
                    createUserDialog.show(fm, "fragment_event_confirm");
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    loadingDialog.hide();
                    messages.showError(responseDTO.getError());
                }
            });

        } else {
            messages.showError(R.string.team_member_get_by_nfc_not_existing);
        }
    }


    /**
     * Retrieve teams from database
     */
    private void retrieveTeams() {
        loadingDialog.show();
        teamBO.retrieveTeams(null, null, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                List<Team> teams = (List<Team>) responseDTO.getData();

                //Add "All" option
                Team teamAll = new Team("-1", "All");
                teams.add(0, teamAll);

                //Show filtering dialog
                openFilteringDialog(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
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

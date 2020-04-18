package es.formulastudent.app.mvp.view.screen.briefing;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.view.screen.briefing.dialog.ConfirmBriefingRegisterDialog;
import es.formulastudent.app.mvp.view.screen.briefing.dialog.DeleteEventRegisterDialog;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;


public class BriefingPresenter extends DataConsumer implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private BriefingBO briefingBO;
    private TeamMemberBO teamMemberBO;
    private User loggedUser;

    //Data
    private List<BriefingRegister> briefingRegisterList = new ArrayList<>();

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String selectedTeamID;

    public BriefingPresenter(BriefingPresenter.View view, TeamBO teamBO,
                             BriefingBO briefingBO, TeamMemberBO teamMemberBO,
                             User loggedUser) {
        super(teamBO, briefingBO, teamMemberBO);
        this.view = view;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
        this.teamMemberBO = teamMemberBO;
        this.loggedUser = loggedUser;
    }


    public void createRegistry(TeamMember teamMember) {
        briefingBO.createBriefingRegistry(teamMember, loggedUser.getMail(),
                success -> retrieveBriefingRegisterList(),
                this::setErrorToDisplay);
    }


    void retrieveBriefingRegisterList() {
        briefingBO.retrieveBriefingRegisters(selectedDateFrom, selectedDateTo, selectedTeamID,
                this::updateBriefingRegisters,
                this::setErrorToDisplay);
    }


    protected void updateBriefingRegisters(List<BriefingRegister> items) {
        this.briefingRegisterList.clear();
        this.briefingRegisterList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }


    public void deleteBriefingRegister(String id) {
        briefingBO.deleteBriefingRegister(id,
                success -> retrieveBriefingRegisterList(),
                this::setErrorToDisplay);
    }


    void onNFCTagDetected(String tag) {
        teamMemberBO.retrieveTeamMemberByNFCTag(tag,
                teamMember -> {
                    FragmentManager fm = view.getActivity().getSupportFragmentManager();
                    ConfirmBriefingRegisterDialog
                            .newInstance(BriefingPresenter.this, teamMember)
                            .show(fm, "fragment_briefing_confirm");
                }, this::setErrorToDisplay);
    }


    void retrieveTeams() {
        teamBO.retrieveTeams(null, null,
                teams -> {
                    Team teamAll = new Team("-1", "All");
                    teams.add(0, teamAll);
                    view.initializeTeamsSpinner(teams);
                }, this::setErrorToDisplay);
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {
        if (v.getId() == R.id.delete_run_button) {
            BriefingRegister selectedRegister = briefingRegisterList.get(position);
            openConfirmDeleteRegister(selectedRegister);
        }
    }


    private void openConfirmDeleteRegister(EventRegister register) {
        //With all the information, we open the dialog
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        DeleteEventRegisterDialog deleteEventRegisterDialog = DeleteEventRegisterDialog.newInstance(this, register);
        deleteEventRegisterDialog.show(fm, "delete_event_confirm");
    }


    List<BriefingRegister> getBriefingRegisterList() {
        return briefingRegisterList;
    }

    void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }

    void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }

    void setSelectedTeamID(String selectedTeamID) {
        this.selectedTeamID = selectedTeamID;
    }

    public interface View {

        /**
         * @return current activity
         */
        FragmentActivity getActivity();

        /**
         * Refresh items in list
         */
        void refreshBriefingRegisterItems();

        /**
         * Initialize teams spinner
         */
        void initializeTeamsSpinner(List<Team> teams);
    }
}

package es.formulastudent.app.mvp.view.screen.briefing;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.briefing.dialog.ConfirmBriefingRegisterDialog;
import es.formulastudent.app.mvp.view.screen.briefing.dialog.DeleteEventRegisterDialog;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;


public class BriefingPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private BriefingBO briefingBO;
    private TeamMemberBO teamMemberBO;
    private User loggedUser;
    private LoadingDialog loadingDialog;
    private Messages messages;

    //Data
    private List<BriefingRegister> filteredBriefingRegisterList = new ArrayList<>();

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String selectedTeamID;

    public BriefingPresenter(BriefingPresenter.View view, TeamBO teamBO,
                             BriefingBO briefingBO, TeamMemberBO teamMemberBO,
                             User loggedUser, LoadingDialog loadingDialog, Messages messages) {
        this.view = view;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
        this.teamMemberBO = teamMemberBO;
        this.loggedUser = loggedUser;
        this.loadingDialog = loadingDialog;
        this.messages = messages;
    }


    public void createRegistry(TeamMember teamMember) {
        loadingDialog.show();
        briefingBO.createBriefingRegistry(teamMember, loggedUser.getMail(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                retrieveBriefingRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
                loadingDialog.hide();
            }
        });
    }


    void retrieveBriefingRegisterList() {
        loadingDialog.show();
        briefingBO.retrieveBriefingRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                List<BriefingRegister> results = (List<BriefingRegister>) responseDTO.getData();
                if (results == null) {
                    results = new ArrayList<>();
                }
                updateBriefingRegisters(results);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }


    private void updateBriefingRegisters(List<BriefingRegister> items) {
        this.filteredBriefingRegisterList.clear();
        this.filteredBriefingRegisterList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }


    public void deleteBriefingRegister(String id) {
        loadingDialog.show();
        briefingBO.deleteBriefingRegister(id, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                retrieveBriefingRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }


    void onNFCTagDetected(String tag) {
        loadingDialog.show();
        teamMemberBO.retrieveTeamMemberByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                TeamMember teamMember = (TeamMember) responseDTO.getData();
                FragmentManager fm = view.getActivity().getSupportFragmentManager();
                ConfirmBriefingRegisterDialog createUserDialog = ConfirmBriefingRegisterDialog
                        .newInstance(BriefingPresenter.this, teamMember);
                createUserDialog.show(fm, "fragment_briefing_confirm");
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }


    @SuppressWarnings("unchecked")
    void retrieveTeams() {
        loadingDialog.show();
        teamBO.retrieveTeams(null, null, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                List<Team> teams = (List<Team>) responseDTO.getData();

                //Add "All" option
                Team teamAll = new Team("-1", "All");
                teams.add(0, teamAll);
                view.initializeTeamsSpinner(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {
        if (v.getId() == R.id.delete_run_button) {
            BriefingRegister selectedRegister = filteredBriefingRegisterList.get(position);
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
        return filteredBriefingRegisterList;
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

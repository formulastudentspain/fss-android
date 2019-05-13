package es.formulastudent.app.mvp.view.activity.briefing;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.api.BusinessCallback;
import es.formulastudent.app.mvp.data.api.ResponseDTO;
import es.formulastudent.app.mvp.data.api.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.api.team.TeamBO;
import es.formulastudent.app.mvp.data.api.user.UserBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.briefing.dialog.ConfirmBriefingRegisterDialog;


public class BriefingPresenter {

    //Dependencies
    private View view;
    private Context context;
    private TeamBO teamBO;
    private BriefingBO briefingBO;
    private UserBO userBO;


    //Data
    List<BriefingRegister> allBriefingRegisterList = new ArrayList<>();
    List<BriefingRegister> filteredBriefingRegisterList = new ArrayList<>();

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;

    //Team
    private String selectedTeamID;


    public BriefingPresenter(BriefingPresenter.View view, Context context, TeamBO teamBO, BriefingBO briefingBO, UserBO userBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
        this.briefingBO = briefingBO;
        this.userBO = userBO;
    }

    /**
     * Create Briefing registry
     * @param user
     */
     public void createRegistry(User user){

        //Show loading
        view.showLoading();

        briefingBO.createBriefingRegistry(user, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveBriefingRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar errores
            }
        });
    }


    /**
     * Retrieve Briefing registers
     */
     void retrieveBriefingRegisterList() {

        //Show loading
        view.showLoading();

        //Call Briefing business
         briefingBO.retrieveBriefingRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<BriefingRegister> results = (List<BriefingRegister>) responseDTO.getData();
                     updateBriefingRegisters(results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes
             }
         });
    }


    public void updateBriefingRegisters(List<BriefingRegister> items){
        //Update all-register-list
        this.allBriefingRegisterList.clear();
        this.allBriefingRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredBriefingRegisterList.clear();
        this.filteredBriefingRegisterList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }


    /**
     * Retrieve user by NFC tag after read
     * @param tag
     */
    void onNFCTagDetected(String tag){

        userBO.retrieveUserByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                User user = (User)responseDTO.getData();

                FragmentManager fm = ((BriefingActivity)view.getActivity()).getSupportFragmentManager();
                ConfirmBriefingRegisterDialog createUserDialog = ConfirmBriefingRegisterDialog.newInstance(BriefingPresenter.this, user);
                createUserDialog.show(fm, "fragment_briefing_confirm");
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes
            }
        });
    }


    /**
     * Retrieve teams from database
     */
    void retrieveTeams(){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveAllTeams(new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<Team> teams = (List<Team>)responseDTO.getData();

                //Add All option
                Team teamAll = new Team("-1", "All");
                teams.add(0, teamAll);

                view.initializeTeamsSpinner(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes de error
            }

        });
    }


    public List<BriefingRegister> getBriefingRegisterList() {
        return filteredBriefingRegisterList;
    }

    public Date getSelectedDateFrom() {
        return selectedDateFrom;
    }

    public void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }

    public Date getSelectedDateTo() {
        return selectedDateTo;
    }

    public void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }

    public String getSelectedTeamID() {
        return selectedTeamID;
    }

    public void setSelectedTeamID(String selectedTeamID) {
        this.selectedTeamID = selectedTeamID;
    }

    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

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
        void refreshBriefingRegisterItems();

        /**
         * Initialize teams spinner
         */
        void initializeTeamsSpinner(List<Team> teams);
    }

}

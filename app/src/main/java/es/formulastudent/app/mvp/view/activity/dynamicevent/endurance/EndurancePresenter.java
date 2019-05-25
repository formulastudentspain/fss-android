package es.formulastudent.app.mvp.view.activity.dynamicevent.endurance;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.endurance.EnduranceBO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.EnduranceRegister;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.dialog.ConfirmEnduranceRegisterDialog;


public class EndurancePresenter {

    //Dependencies
    private View view;
    private Context context;
    private TeamBO teamBO;
    private EnduranceBO enduranceBO;
    private UserBO userBO;
    private BriefingBO briefingBO;


    //Data
    List<EnduranceRegister> allEnduranceRegisterList = new ArrayList<>();
    List<EnduranceRegister> filteredEnduranceRegisterList = new ArrayList<>();

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;

    //Team
    private String selectedTeamID;


    public EndurancePresenter(EndurancePresenter.View view, Context context, TeamBO teamBO,
                              EnduranceBO enduranceBO, UserBO userBO, BriefingBO briefingBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
        this.enduranceBO = enduranceBO;
        this.userBO = userBO;
        this.briefingBO = briefingBO;
    }

    /**
     * Create Endurance registry
     * @param user
     */
     public void createRegistry(User user){

        //Show loading
        view.showLoading();

        enduranceBO.createEnduranceRegistry(user, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveEnduranceRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar errores
            }
        });
    }


    /**
     * Retrieve Endurance registers
     */
     void retrieveEnduranceRegisterList() {

        //Show loading
        view.showLoading();

        //Call Endurance business
         enduranceBO.retrieveEnduranceRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<EnduranceRegister> results = (List<EnduranceRegister>) responseDTO.getData();
                     updateEnduranceRegisters(results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes
             }
         });
    }


    public void updateEnduranceRegisters(List<EnduranceRegister> items){
        //Update all-register-list
        this.allEnduranceRegisterList.clear();
        this.allEnduranceRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredEnduranceRegisterList.clear();
        this.filteredEnduranceRegisterList.addAll(items);
        this.view.refreshEnduranceRegisterItems();
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

                //Now check if the user did the briefing today
                getUserBriefingRegister(user);

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO mostrar mensajes
            }
        });
    }

    void getUserBriefingRegister(final User user){

        Calendar cal = Calendar.getInstance();
        Date to = cal.getTime();

        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.SECOND, 0);

        Date from = cal.getTime(); //current day at 05:00am
        if(user != null && user.getID() != null) {
            briefingBO.retrieveBriefingRegistersByUserAndDates(from, to, user.getID(), new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {

                    List<BriefingRegister> briefingRegisters = (List<BriefingRegister>) responseDTO.getData();

                    FragmentManager fm = ((EnduranceActivity) view.getActivity()).getSupportFragmentManager();
                    ConfirmEnduranceRegisterDialog createUserDialog = ConfirmEnduranceRegisterDialog
                            .newInstance(EndurancePresenter.this, user, !briefingRegisters.isEmpty());
                    createUserDialog.show(fm, "fragment_endurance_confirm");
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    //TODO mostrar mensajes
                }
            });
        } else {
            view.hideLoading();
            view.createMessage("User does not exist");
        }
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


    public List<EnduranceRegister> getEnduranceRegisterList() {
        return filteredEnduranceRegisterList;
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
        void createMessage(String message);

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
        void refreshEnduranceRegisterItems();

        /**
         * Initialize teams spinner
         */
        void initializeTeamsSpinner(List<Team> teams);
    }

}

package es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.acceleration.AccelerationBO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.AccelerationRegister;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.dialog.ConfirmAccelerationRegisterDialog;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.recyclerview.RecyclerViewLongClickedListener;


public class AccelerationPresenter implements RecyclerViewLongClickedListener {

    //Dependencies
    private View view;
    private Context context;
    private TeamBO teamBO;
    private AccelerationBO accelerationBO;
    private UserBO userBO;
    private BriefingBO briefingBO;


    //Data
    List<AccelerationRegister> allAccelerationRegisterList = new ArrayList<>();
    List<AccelerationRegister> filteredAccelerationRegisterList = new ArrayList<>();

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;

    //Team
    private String selectedTeamID;


    public AccelerationPresenter(AccelerationPresenter.View view, Context context, TeamBO teamBO,
                                 AccelerationBO accelerationBO, UserBO userBO, BriefingBO briefingBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
        this.accelerationBO = accelerationBO;
        this.userBO = userBO;
        this.briefingBO = briefingBO;
    }

    /**
     * Create Acceleration registry
     * @param user
     */
     public void createRegistry(User user, Long carNumber, String carType, Boolean briefingDone){

        //Show loading
        view.showLoading();

        accelerationBO.createAccelerationRegistry(user, carType, carNumber, briefingDone, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveAccelerationRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage("Couldn't not create the acceleration registry");
            }
        });
    }


    /**
     * Retrieve Acceleration registers
     */
     void retrieveAccelerationRegisterList() {

        //Show loading
        view.showLoading();

        //Call Acceleration business
         accelerationBO.retrieveAccelerationRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<AccelerationRegister> results = (List<AccelerationRegister>) responseDTO.getData();
                     updateAccelerationRegisters(results==null ? new ArrayList<AccelerationRegister>() : results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                view.createMessage("Couldn't get the acceleration register");
             }
         });
    }


    public void updateAccelerationRegisters(List<AccelerationRegister> items){
        //Update all-register-list
        this.allAccelerationRegisterList.clear();
        this.allAccelerationRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredAccelerationRegisterList.clear();
        this.filteredAccelerationRegisterList.addAll(items);
        this.view.refreshAccelerationRegisterItems();
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
                view.createMessage("Couldn't get the user by this Tag");
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

                    //Get now cars
                    getCarsByUserId(user, !briefingRegisters.isEmpty());
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

    void getCarsByUserId(final User user, final boolean briefingExists){

        teamBO.retrieveTeamById(user.getTeamID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                Team team = (Team) responseDTO.getData();
                Car car = team.getCar();

                //With all the information, we open the dialog
                FragmentManager fm = ((AccelerationActivity)view.getActivity()).getSupportFragmentManager();
                ConfirmAccelerationRegisterDialog createUserDialog = ConfirmAccelerationRegisterDialog
                        .newInstance(AccelerationPresenter.this, user, briefingExists, car);
                createUserDialog.show(fm, "fragment_acceleration_confirm");
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage("Couldn't get the team from this user");
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



    @Override
    public void recyclerViewListLongClicked(android.view.View v, int position) {
        AccelerationRegister selectedRegister = filteredAccelerationRegisterList.get(position);

        //With all the information, we open the dialog
        FragmentManager fm = ((AccelerationActivity)view.getActivity()).getSupportFragmentManager();
        ConfirmAccelerationRegisterDialog createUserDialog = ConfirmAccelerationRegisterDialog
                .newInstance(AccelerationPresenter.this, selectedRegister);
        createUserDialog.show(fm, "fragment_acceleration_confirm");

    }

    public void createMessage(String message){
        view.createMessage(message);
    }


    public List<AccelerationRegister> getAccelerationRegisterList() {
        return filteredAccelerationRegisterList;
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
        void refreshAccelerationRegisterItems();

        /**
         * Initialize teams spinner
         */
        void initializeTeamsSpinner(List<Team> teams);
    }

}

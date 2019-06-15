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
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.endurance.EnduranceBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.EnduranceRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventPresenter;
import es.formulastudent.app.mvp.view.activity.dynamicevent.FilteringRegistersDialog;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.recyclerview.RecyclerViewLongClickedListener;
import es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.dialog.ConfirmEnduranceRegisterDialog;


public class EndurancePresenter implements DynamicEventPresenter, RecyclerViewLongClickedListener {

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

    //Filtering values
    List<Team> teams;
    String selectedTeamID;
    String selectedDay;
    Long selectedCarNumber;

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;


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
     public void createRegistry(User user, Long carNumber, String carType, Boolean briefingDone){

        //Show loading
        view.showLoading();

        enduranceBO.createEnduranceRegistry(user, carType, carNumber, briefingDone, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage("Couldn't not create the endurance registry");
            }
        });
    }


    /**
     * Retrieve Endurance registers
     */
    @Override
     public void retrieveRegisterList() {

        //Show loading
        view.showLoading();

        //Call Endurance business
         enduranceBO.retrieveEnduranceRegisters(selectedDateFrom, selectedDateTo, selectedTeamID, selectedCarNumber, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<EnduranceRegister> results = (List<EnduranceRegister>) responseDTO.getData();
                     updateEnduranceRegisters(results==null ? new ArrayList<EnduranceRegister>() : results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 view.createMessage("Couldn't get the endurance register");
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

        //Show loading
        view.showLoading();

        userBO.retrieveUserByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                User user = (User)responseDTO.getData();

                //Now check if the user did the briefing today
                getUserBriefingRegister(user);

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoading();

                view.createMessage("Couldn't get the user by this Tag");
            }
        });
    }

    void getUserBriefingRegister(final User user){

        //Show loading
        view.showLoading();

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
                    //Hide loading
                    view.hideLoading();

                    view.createMessage("Couldn't get the user briefing registers");
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

                //Hide loading
                view.hideLoading();

                Team team = (Team) responseDTO.getData();
                Car car = team.getCar();

                //With all the information, we open the dialog
                FragmentManager fm = ((EnduranceActivity)view.getActivity()).getSupportFragmentManager();
                ConfirmEnduranceRegisterDialog createUserDialog = ConfirmEnduranceRegisterDialog
                        .newInstance(EndurancePresenter.this, user, briefingExists, car);
                createUserDialog.show(fm, "fragment_endurance_confirm");
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoading();

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

                openFilteringDialog(teams);

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoading();

                view.createMessage("Couldn't get the teams");
            }

        });
    }


    @Override
    public void recyclerViewListLongClicked(android.view.View v, int position) {
        EnduranceRegister selectedRegister = filteredEnduranceRegisterList.get(position);

        //With all the information, we open the dialog
        FragmentManager fm = ((EnduranceActivity)view.getActivity()).getSupportFragmentManager();
        ConfirmEnduranceRegisterDialog createUserDialog = ConfirmEnduranceRegisterDialog
                .newInstance(EndurancePresenter.this, selectedRegister);
        createUserDialog.show(fm, "fragment_acceleration_confirm");

    }

    public void openFilteringDialog(List<Team> teams){
        this.teams = teams;

        //With all the information, we open the dialog
        FragmentManager fm = ((EnduranceActivity)view.getActivity()).getSupportFragmentManager();
        FilteringRegistersDialog createUserDialog = FilteringRegistersDialog
                .newInstance(this, teams, selectedTeamID, selectedCarNumber, selectedDay);
        createUserDialog.show(fm, "fragment_acceleration_confirm");

        //Hide loading right after showing the filtering dialog
        view.hideLoading();
    }


    void filterIconClicked(){

        //Go retrieve teams if we have not yet
        if(teams == null){
            retrieveTeams();
        }else{
            openFilteringDialog(teams);
        }
    }


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


    @Override
    public void createMessage(String message) {
        view.createMessage(message);
    }


    public List<EnduranceRegister> getEnduranceRegisterList() {
        return filteredEnduranceRegisterList;
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

        void filtersActivated(Boolean activated);
    }

}

package es.formulastudent.app.mvp.view.activity.teams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.TeamsDetailScrutineeringActivity;


public class TeamsPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;
    private TeamBO teamBO;

    //Data
    List<Team> allTeamsList = new ArrayList<>();
    List<Team> filteredTeamsList = new ArrayList<>();


    public TeamsPresenter(TeamsPresenter.View view, Context context, TeamBO teamBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
    }


    /**
     * Retrieve Briefing registers
     */
     void retrieveBriefingRegisterList() {

        //Show loading
        view.showLoading();

        //Call Briefing business
         teamBO.retrieveAllTeams(null, new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<Team> results = (List<Team>) responseDTO.getData();
                     if(results == null){
                         results = new ArrayList<>();
                     }
                     updateTeams(results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 //Show error message
                 view.createMessage(responseDTO.getError());
             }
         });
    }


    public void updateTeams(List<Team> items){
        //Update all-register-list
        this.allTeamsList.clear();
        this.allTeamsList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredTeamsList.clear();
        this.filteredTeamsList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        Team selectedTeam = filteredTeamsList.get(position);

        //Open Scrutineering
         if(v.getId() == R.id.scrutineering_button){
             Intent intent = new Intent(context, TeamsDetailScrutineeringActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             intent.putExtra("selectedTeam", selectedTeam);
             context.startActivity(intent);
         }
    }


    public List<Team> getTeamsList() {
        return filteredTeamsList;
    }


    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object... args);

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

    }

}

package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering;

import android.app.Activity;
import android.content.Context;

import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Team;


public class TeamsDetailScrutineeringPresenter {

    //Dependencies
    private View view;
    private Context context;
    private TeamBO teamBO;


    public TeamsDetailScrutineeringPresenter(TeamsDetailScrutineeringPresenter.View view, Context context, TeamBO teamBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
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
         * Get selected Team
         * @return
         */
        Team getCurrentTeam();

    }

}

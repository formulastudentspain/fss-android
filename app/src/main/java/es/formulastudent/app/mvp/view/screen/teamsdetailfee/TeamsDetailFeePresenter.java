package es.formulastudent.app.mvp.view.screen.teamsdetailfee;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.FeeItem;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.dialog.ConfirmNextStepDialog;


public class TeamsDetailFeePresenter {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private DynamicEventBO dynamicEventBO;
    private TeamMemberBO teamMemberBO;
    private EgressBO egressBO;


    public TeamsDetailFeePresenter(TeamsDetailFeePresenter.View view, TeamBO teamBO,
                                   DynamicEventBO dynamicEventBO, TeamMemberBO teamMemberBO) {
        this.view = view;
        this.teamBO = teamBO;
        this.dynamicEventBO = dynamicEventBO;
        this.teamMemberBO = teamMemberBO;
    }


    public void showConfirmNextStepDialog(FeeItem feeItem){

        //Open confirm dialog
        ConfirmNextStepDialog confirmNextStepDialog = ConfirmNextStepDialog
                .newInstance(this, view.getCurrentTeam(), feeItem);
        confirmNextStepDialog.show(view.getViewFragmentManager(), "confirmPassTestDialog");

    }


    public void updateTeam(FeeItem feeItem, final Team team) {

        //Show loading
        view.showLoading();


        switch (feeItem){
            case TRANSPONDER_FEE_GIVEN:
                team.setTransponderFeeGiven(true);
                break;
            case TRANSPONDER_GIVEN:
                team.setTransponderItemGiven(true);
                break;
            case TRANSPONDER_RETURNED:
                team.setTransponderItemReturned(true);
                break;
            case TRANSPONDER_FEE_RETURNED:
                team.setTransponderFeeReturned(true);
                break;
            case ENERGY_METER_FEE_GIVEN:
                team.setEnergyMeterFeeGiven(true);
                break;
            case ENERGY_METER_GIVEN:
                team.setEnergyMeterItemGiven(true);
                break;
            case ENERGY_METER_RETURNED:
                team.setEnergyMeterItemReturned(true);
                break;
            case ENERGY_METER_FEE_RETURNED:
                team.setEnergyMeterFeeReturned(true);
                break;
        }

        //Update
        teamBO.updateTeam(team, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Get fragments and update fields with the new values
                List<Fragment> fragmentList = view.getViewFragmentManager().getFragments();
                for(Fragment fragment: fragmentList){
                    if(fragment instanceof TeamsDetailFeeFragment){
                        ((TeamsDetailFeeFragment)fragment).updateView(team);
                    }
                }

                //Show info message
                view.createMessage(responseDTO.getInfo());

                //Hide loading
                view.hideLoading();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

                //Show error message
                view.createMessage(responseDTO.getError());

                //Hide loading
                view.hideLoading();
            }
        });
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
         * Get current team
         * @return
         */
        Team getCurrentTeam();

        /**
         * Get fragment manager
         * @return
         */
        FragmentManager getViewFragmentManager();
    }
}

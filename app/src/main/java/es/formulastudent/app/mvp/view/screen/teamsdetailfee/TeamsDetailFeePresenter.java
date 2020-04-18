package es.formulastudent.app.mvp.view.screen.teamsdetailfee;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.FeeItem;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.dialog.ConfirmNextStepDialog;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.tabs.TeamsDetailFeeTabFragment;


public class TeamsDetailFeePresenter extends DataConsumer {

    //Dependencies
    private View view;
    private TeamBO teamBO;

    public TeamsDetailFeePresenter(TeamsDetailFeePresenter.View view, TeamBO teamBO) {
        super(teamBO);
        this.view = view;
        this.teamBO = teamBO;
    }


    public void showConfirmNextStepDialog(FeeItem feeItem){
        //Open confirm dialog
        ConfirmNextStepDialog confirmNextStepDialog = ConfirmNextStepDialog
                .newInstance(this, view.getCurrentTeam(), feeItem);
        confirmNextStepDialog.show(view.getViewFragmentManager(), "confirmPassTestDialog");
    }


    public void updateTeam(FeeItem feeItem, final Team team) {

        switch (feeItem) {
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
        teamBO.updateTeam(team,
                response -> {
                    List<Fragment> fragmentList = view.getViewFragmentManager().getFragments();
                    for (Fragment fragment : fragmentList) {
                        if (fragment instanceof TeamsDetailFeeTabFragment) {
                            ((TeamsDetailFeeTabFragment) fragment).updateView(team);
                        }
                    }
                },
                this::setErrorToDisplay);
    }


    public interface View {

        FragmentActivity getActivity();

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

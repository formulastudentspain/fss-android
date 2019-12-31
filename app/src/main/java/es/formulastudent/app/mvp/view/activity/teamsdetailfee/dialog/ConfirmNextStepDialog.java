package es.formulastudent.app.mvp.view.activity.teamsdetailfee.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.FeeItem;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.teamsdetailfee.TeamsDetailFeePresenter;

public class ConfirmNextStepDialog extends DialogFragment {

    //Presenter
    private TeamsDetailFeePresenter presenter;

    //Selected original team
    private Team team;

    //Fee item to be updated
    private FeeItem feeItem;

    public ConfirmNextStepDialog() {}

    public static ConfirmNextStepDialog newInstance(TeamsDetailFeePresenter presenter, Team team, FeeItem feeItem) {
        ConfirmNextStepDialog frag = new ConfirmNextStepDialog();
        frag.setPresenter(presenter);
        frag.setTeam(team);
        frag.setFeeItem(feeItem);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Buttons
        builder.setTitle(R.string.teams_dialog_fee_next_step_title)
                .setMessage(R.string.teams_dialog_fee_next_step_description)

                //Action buttons
                .setPositiveButton(R.string.teams_dialog_fee_next_button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        presenter.updateTeam(feeItem, team);
                    }
                })
                .setNegativeButton(R.string.teams_dialog_fee_next_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmNextStepDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void setPresenter(TeamsDetailFeePresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setFeeItem(FeeItem feeItem) {
        this.feeItem = feeItem;
    }
}


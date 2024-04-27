package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;

public class ConfirmPassTestDialog extends DialogFragment {

    //Presenter
    private TeamsDetailScrutineeringPresenter presenter;

    //Selected original team
    private Team team;

    //Selected modified team
    private Team modifiedTeam;

    public ConfirmPassTestDialog() {}

    public static ConfirmPassTestDialog newInstance(TeamsDetailScrutineeringPresenter presenter, Team team, Team modifiedTeam) {
        ConfirmPassTestDialog frag = new ConfirmPassTestDialog();
        frag.setPresenter(presenter);
        frag.setTeam(team);
        frag.setModifiedTeam(modifiedTeam);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Buttons
        builder.setTitle(R.string.teams_dialog_pass_title)
                .setMessage(R.string.teams_dialog_pass_message)

                //Action buttons
                .setPositiveButton(R.string.teams_dialog_pass_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        team = modifiedTeam;
                        presenter.updateTeam(modifiedTeam, team);
                    }
                })
                .setNegativeButton(R.string.teams_dialog_pass_cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmPassTestDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void setPresenter(TeamsDetailScrutineeringPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setModifiedTeam(Team modifiedTeam){
        this.modifiedTeam = modifiedTeam;
    }
}


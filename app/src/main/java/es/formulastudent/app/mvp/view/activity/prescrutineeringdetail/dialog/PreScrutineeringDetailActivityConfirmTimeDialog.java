package es.formulastudent.app.mvp.view.activity.prescrutineeringdetail.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.Utils;
import es.formulastudent.app.mvp.view.activity.prescrutineeringdetail.PreScrutineeringDetailActivity;

public class PreScrutineeringDetailActivityConfirmTimeDialog extends DialogFragment {

    private PreScrutineeringDetailActivity activity;
    private Long milliseconds;

    public PreScrutineeringDetailActivityConfirmTimeDialog() {}

    public static PreScrutineeringDetailActivityConfirmTimeDialog newInstance(PreScrutineeringDetailActivity activity, Long milliseconds) {
        PreScrutineeringDetailActivityConfirmTimeDialog frag = new PreScrutineeringDetailActivityConfirmTimeDialog();
        frag.setActivity(activity);
        frag.setMilliseconds(milliseconds);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String message = getString(R.string.prescruti_confirm_time_dialog_message);

        this.setCancelable(false);

        builder.setTitle(Utils.chronoFormatter(milliseconds))
                .setMessage(message)
                .setPositiveButton(R.string.prescruti_confirm_time_dialog_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        activity.handleSuccessfulTime(milliseconds);
                    }
                })
                .setNegativeButton(R.string.prescruti_confirm_time_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        PreScrutineeringDetailActivityConfirmTimeDialog.this.dismiss();
                        activity.resetTime();
                    }
                });

        return builder.create();
    }


    public void setActivity(PreScrutineeringDetailActivity activity) {
        this.activity = activity;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }
}


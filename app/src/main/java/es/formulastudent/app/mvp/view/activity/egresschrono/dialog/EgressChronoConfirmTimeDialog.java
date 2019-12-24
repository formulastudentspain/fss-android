package es.formulastudent.app.mvp.view.activity.egresschrono.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.Utils;
import es.formulastudent.app.mvp.view.activity.egresschrono.EgressChronoActivity;

public class EgressChronoConfirmTimeDialog extends DialogFragment {

    private EgressChronoActivity activity;
    private Long milliseconds;

    public EgressChronoConfirmTimeDialog() {}

    public static EgressChronoConfirmTimeDialog newInstance(EgressChronoActivity activity, Long milliseconds) {
        EgressChronoConfirmTimeDialog frag = new EgressChronoConfirmTimeDialog();
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
                        EgressChronoConfirmTimeDialog.this.dismiss();
                        activity.resetTime();
                    }
                });

        return builder.create();
    }


    public void setActivity(EgressChronoActivity activity) {
        this.activity = activity;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }
}


package code.formulastudentspain.app.mvp.view.screen.general.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import code.formulastudentspain.app.R;

public class GeneralActivityExitDialog extends DialogFragment {

    private Activity currentActivity;

    public GeneralActivityExitDialog() {}

    public static GeneralActivityExitDialog newInstance(Activity activity) {
        GeneralActivityExitDialog frag = new GeneralActivityExitDialog();
        frag.setCurrentActivity(activity);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.exit_dialog_title)
                .setMessage(R.string.exit_dialog_message)
                .setPositiveButton(R.string.exit_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        currentActivity.finish();
                    }
                })
                .setNegativeButton(R.string.exit_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        GeneralActivityExitDialog.this.dismiss();
                    }
                });

        return builder.create();
    }


    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }
}


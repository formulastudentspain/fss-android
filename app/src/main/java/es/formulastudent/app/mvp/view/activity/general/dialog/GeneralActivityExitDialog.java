package es.formulastudent.app.mvp.view.activity.general.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;

public class GeneralActivityExitDialog extends DialogFragment {

    public GeneralActivityExitDialog() {}

    public static GeneralActivityExitDialog newInstance() {
        GeneralActivityExitDialog frag = new GeneralActivityExitDialog();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_loading, null);
        builder.setView(view);
        builder.setTitle(R.string.exit_dialog_title);

        //Buttons
        builder.setPositiveButton(R.string.exit_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}


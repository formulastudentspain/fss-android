package es.formulastudent.app.mvp.view.screen.general.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;

public class GeneralActivityLoadingDialog extends DialogFragment {

    public GeneralActivityLoadingDialog() {}

    public static GeneralActivityLoadingDialog newInstance() {
        GeneralActivityLoadingDialog frag = new GeneralActivityLoadingDialog();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_loading, null);
        builder.setView(view);
        builder.setTitle(R.string.general_activity_dialog_loading_title);

        return builder.create();
    }
}


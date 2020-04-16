package es.formulastudent.app.mvp.view.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import es.formulastudent.app.R;

public class LoadingDialog extends DialogFragment {

    private static final String KEY = "loading_dialog";
    private FragmentActivity activity;
    private boolean displaying;

    private LoadingDialog() {
    }

    public static LoadingDialog newInstance(FragmentActivity activity) {
        LoadingDialog frag = new LoadingDialog();
        frag.setActivity(activity);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_loading, null);
        builder.setView(view);
        builder.setTitle(R.string.general_activity_dialog_loading_title);

        return builder.create();
    }

    public void show() {
        if (displaying) return;

        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(this, KEY);
        ft.commitAllowingStateLoss();
        displaying = true;
    }

    public void hide() {
        if (!displaying) return;

        this.dismiss();
        displaying = false;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }
}

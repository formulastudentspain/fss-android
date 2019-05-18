package es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.AccelerationPresenter;

public class ConfirmAccelerationRegisterDialog extends DialogFragment {

    private ImageView userPhoto;
    private TextView userName;
    private TextView userTeam;
    private TextView registerDate;

    //Presenter
    private AccelerationPresenter presenter;

    //Detected user
    private User user;

    public ConfirmAccelerationRegisterDialog() {}

    public static ConfirmAccelerationRegisterDialog newInstance(AccelerationPresenter presenter, User user) {
        ConfirmAccelerationRegisterDialog frag = new ConfirmAccelerationRegisterDialog();
        frag.setPresenter(presenter);
        frag.setUser(user);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_briefing_confirmation, null);

        // Get view components
        userName = rootView.findViewById(R.id.user_name);
        userTeam = rootView.findViewById(R.id.user_team);
        registerDate = rootView.findViewById(R.id.registration_time);
        userPhoto = rootView.findViewById(R.id.user_profile_image);

        //Set values
        userName.setText(user.getName());
        userTeam.setText(user.getTeam());
        registerDate.setText(sdf.format(Calendar.getInstance().getTime()));
        Picasso.get().load(user.getPhotoUrl()).into(userPhoto);


        //Buttons
        builder.setView(rootView)
                .setTitle(R.string.acceleration_activity_dialog_confirm_register_title)

                //Action buttons
                .setPositiveButton(R.string.acceleration_activity_dialog_confirm_button_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.createRegistry(user);
                    }
                })
                .setNegativeButton(R.string.acceleration_activity_dialog_confirm_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmAccelerationRegisterDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void setPresenter(AccelerationPresenter presenter) {
        this.presenter = presenter;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


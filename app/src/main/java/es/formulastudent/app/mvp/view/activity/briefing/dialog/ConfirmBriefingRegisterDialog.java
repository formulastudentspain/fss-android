package es.formulastudent.app.mvp.view.activity.briefing.dialog;

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

import java.util.Calendar;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.dto.UserDTO;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingPresenter;

public class ConfirmBriefingRegisterDialog extends DialogFragment {

    private ImageView userPhoto;
    private TextView userName;
    private TextView userTeam;
    private TextView registerDate;

    //Presenter
    private BriefingPresenter presenter;

    //Detected user
    private UserDTO user;

    public ConfirmBriefingRegisterDialog() {}

    public static ConfirmBriefingRegisterDialog newInstance(BriefingPresenter presenter, UserDTO user) {
        ConfirmBriefingRegisterDialog frag = new ConfirmBriefingRegisterDialog();
        frag.setPresenter(presenter);
        frag.setUser(user);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_briefing_confirmation, null);

        // Get view components
        userName = rootView.findViewById(R.id.user_name);
        userTeam = rootView.findViewById(R.id.user_team);
        registerDate = rootView.findViewById(R.id.registration_time);
        userPhoto = rootView.findViewById(R.id.user_profile_image);

        //Set values
        userName.setText(user.getName());
        userTeam.setText("Applus+ IDIADA");
        registerDate.setText(Calendar.getInstance().getTime().toString());
        Picasso.get().load(user.getPhotoUrl()).into(userPhoto);


        //Buttons
        builder.setView(rootView)
                .setTitle(R.string.briefing_activity_dialog_confirm_register_title)

                //Action buttons
                .setPositiveButton(R.string.dialog_create_user_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.createRegistry(user);
                    }
                })
                .setNegativeButton(R.string.dialog_create_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmBriefingRegisterDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void setPresenter(BriefingPresenter presenter) {
        this.presenter = presenter;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}


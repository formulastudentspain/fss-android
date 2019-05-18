package es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.dialog;

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
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.EndurancePresenter;

public class ConfirmEnduranceRegisterDialog extends DialogFragment {

    private ImageView userPhoto;
    private TextView userName;
    private TextView userTeam;
    private ImageView briefingDoneIcon;

    //Presenter
    private EndurancePresenter presenter;

    //Detected user
    private User user;
    private boolean briefingDone;

    public ConfirmEnduranceRegisterDialog() {}

    public static ConfirmEnduranceRegisterDialog newInstance(EndurancePresenter presenter, User user, boolean briefingDone) {
        ConfirmEnduranceRegisterDialog frag = new ConfirmEnduranceRegisterDialog();
        frag.setPresenter(presenter);
        frag.setUser(user);
        frag.setBriefingDone(briefingDone);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_endurance_confirmation, null);

        // Get view components
        userName = rootView.findViewById(R.id.user_name);
        userTeam = rootView.findViewById(R.id.user_team);
        userPhoto = rootView.findViewById(R.id.user_profile_image);
        briefingDoneIcon = rootView.findViewById(R.id.briefing_done_icon);

        //Set values
        userName.setText(user.getName());
        userTeam.setText(user.getTeam());
        Picasso.get().load(user.getPhotoUrl()).into(userPhoto);

        if(briefingDone){
            briefingDoneIcon.setImageResource(R.drawable.ic_user_registered);
        }else{
            briefingDoneIcon.setImageResource(R.drawable.ic_red_cross);
        }



        if(briefingDone){
            builder.setView(rootView)
                    .setTitle(R.string.endurance_activity_dialog_confirm_register_title)
                    .setPositiveButton(R.string.endurance_activity_dialog_confirm_button_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            presenter.createRegistry(user);
                        }
                    })
                    .setNegativeButton(R.string.endurance_activity_dialog_confirm_button_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ConfirmEnduranceRegisterDialog.this.getDialog().cancel();
                        }
                    });
        }else{
            builder.setView(rootView)
                    .setTitle(R.string.endurance_activity_dialog_confirm_register_title)
                    .setNegativeButton(R.string.endurance_activity_dialog_confirm_button_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ConfirmEnduranceRegisterDialog.this.getDialog().cancel();
                        }
                    });
        }


        return builder.create();
    }

    public void setPresenter(EndurancePresenter presenter) {
        this.presenter = presenter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBriefingDone(boolean briefingDone) {
        this.briefingDone = briefingDone;
    }
}


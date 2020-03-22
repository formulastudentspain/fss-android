package es.formulastudent.app.mvp.view.activity.briefing.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingPresenter;

public class ConfirmBriefingRegisterDialog extends DialogFragment {

    private BriefingPresenter presenter;
    private TeamMember teamMember;


    public static ConfirmBriefingRegisterDialog newInstance(BriefingPresenter presenter, TeamMember teamMember) {
        ConfirmBriefingRegisterDialog frag = new ConfirmBriefingRegisterDialog();
        frag.setPresenter(presenter);
        frag.setTeamMember(teamMember);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_briefing_confirmation, null);

        // Get view components
        TextView userName = rootView.findViewById(R.id.user_name);
        TextView userTeam = rootView.findViewById(R.id.user_team);
        TextView registerDate = rootView.findViewById(R.id.registration_time);
        ImageView userPhoto = rootView.findViewById(R.id.user_profile_image);

        //Set values
        userName.setText(teamMember.getName());
        userTeam.setText(teamMember.getTeam());
        registerDate.setText(sdf.format(Calendar.getInstance().getTime()));
        Picasso.get().load(teamMember.getPhotoUrl()).into(userPhoto);


        builder.setView(rootView)
            .setTitle(R.string.briefing_activity_dialog_confirm_register_title)
            .setPositiveButton(R.string.briefing_activity_dialog_confirm_button_confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    presenter.createRegistry(teamMember);
                }
            })
            .setNegativeButton(R.string.briefing_activity_dialog_confirm_button_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ConfirmBriefingRegisterDialog.this.getDialog().cancel();
                }
            });

        return builder.create();
    }

    public void setPresenter(BriefingPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }
}


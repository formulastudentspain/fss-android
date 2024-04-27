package code.formulastudentspain.app.mvp.view.screen.teammember.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import java.util.List;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.general.spinneradapters.TeamsSpinnerAdapter;
import code.formulastudentspain.app.mvp.view.screen.teammember.TeamMemberPresenter;

public class FilterTeamMembersDialog extends DialogFragment {

    private TeamMemberPresenter presenter;
    private Context context;
    private AlertDialog dialog;

    //View components
    private Spinner availableTeams;
    private TeamsSpinnerAdapter teamsAdapter;

    //Spinner values
    private List<Team> teams;

    //Selected values
    private Team selectedTeam;


    public FilterTeamMembersDialog() {
    }

    public static FilterTeamMembersDialog newInstance(TeamMemberPresenter presenter, Context context,
                                                      List<Team> teams, Team selectedTeam) {
        FilterTeamMembersDialog frag = new FilterTeamMembersDialog();
        frag.setPresenter(presenter);
        frag.setContext(context);
        frag.setTeams(teams);
        frag.setSelectedTeam(selectedTeam);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filter_team_member, null);

        //Add fake team
        Team team = new Team();
        team.setID("");
        team.setName("All teams");
        teams.add(0, team);

        availableTeams = rootView.findViewById(R.id.create_user_team);
        teamsAdapter = new TeamsSpinnerAdapter(context, android.R.layout.simple_spinner_item, teams);
        availableTeams.setAdapter(teamsAdapter);
        availableTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Team team = teamsAdapter.getItem(position);
                selectedTeam = team;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


        //Set selected Team
        if (selectedTeam != null) {
            for (int i = 0; i < teams.size(); i++) {
                if (selectedTeam.getID().equals(teams.get(i).getID())) {
                    availableTeams.setSelection(i);
                    break;
                }
            }
        }


        builder.setView(rootView)
            .setTitle(R.string.team_member_dialog_filter_title)

            //Action buttons
            .setPositiveButton(R.string.team_member_dialog_filter_button, null)
            .setNeutralButton("Clear", null)
            .setNegativeButton(R.string.dialog_create_user_button_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FilterTeamMembersDialog.this.getDialog().cancel();
                }
            });

        dialog = builder.create();

        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setSelectedTeamToFilter(selectedTeam);
                presenter.retrieveTeamMembers();
                FilterTeamMembersDialog.this.getDialog().dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setSelectedTeamToFilter(null);
                presenter.retrieveTeamMembers();
                FilterTeamMembersDialog.this.getDialog().dismiss();
            }
        });
    }

    public void setPresenter(TeamMemberPresenter presenter) {
        this.presenter = presenter;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }
}


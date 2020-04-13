package es.formulastudent.app.mvp.view.screen.teammember.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.general.spinneradapters.TeamsSpinnerAdapter;
import es.formulastudent.app.mvp.view.screen.teammember.TeamMemberGeneralPresenter;

public class CreateEditTeamMemberDialog extends DialogFragment {

    private TeamMemberGeneralPresenter presenter;
    private Context context;
    private AlertDialog dialog;

    //View components
    private EditText userName;
    private EditText userMail;
    private AppCompatCheckBox driverCheckbox;
    private AppCompatCheckBox esoCheckbox;
    private AppCompatCheckBox asrCheckbox;

    private Spinner availableTeams;
    private TeamsSpinnerAdapter teamsAdapter;

    //Spinner values
    List<Team> teams;
    TeamMember teamMember;

    //Selected values
    private Team selectedTeam;


    public CreateEditTeamMemberDialog() {}

    public static CreateEditTeamMemberDialog newInstance(TeamMemberGeneralPresenter presenter, Context context,
                                                         List<Team> teams, TeamMember teamMember) {
        CreateEditTeamMemberDialog frag = new CreateEditTeamMemberDialog();
        frag.setPresenter(presenter);
        frag.setContext(context);
        frag.setTeams(teams);
        frag.setTeamMember(teamMember);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_create_team_member, null);

        // Get view components
        userName = rootView.findViewById(R.id.create_user_name);
        userMail = rootView.findViewById(R.id.create_user_mail);

       //Teams
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
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        //Roles
        driverCheckbox = rootView.findViewById(R.id.checkboxRolDriver);
        esoCheckbox = rootView.findViewById(R.id.checkboxRolESO);
        asrCheckbox = rootView.findViewById(R.id.checkboxRolASR);


        if(teamMember != null){
            setFieldsToEdit();
        }


        builder.setView(rootView)
                .setTitle(teamMember==null ? R.string.dialog_create_user_title : R.string.dialog_edit_user_title)

                //Action buttons
                .setPositiveButton(teamMember==null ? R.string.dialog_create_user_button_create : R.string.dialog_edit_user_button_edit, null)
                .setNegativeButton(R.string.dialog_create_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateEditTeamMemberDialog.this.getDialog().cancel();
                    }
                });

         dialog = builder.create();

         return dialog;
    }

    private void setFieldsToEdit() {

        //Name and mail
        userName.setText(teamMember.getName());
        userMail.setText(teamMember.getMail());
        userMail.setEnabled(false);

        //Team
        for(int i = 0; i<teams.size(); i++){
            if(teamMember.getTeamID().equals(teams.get(i).getID())){
                availableTeams.setSelection(i);
                break;
            }
        }
        availableTeams.setEnabled(false);

        //Roles
        driverCheckbox.setChecked(teamMember.getDriver());
        esoCheckbox.setChecked(teamMember.getESO());
        asrCheckbox.setChecked(teamMember.getASR());

        //If the user role has validated the documents, cannot be changed
        if(teamMember.getCertifiedDriver() || teamMember.getCertifiedESO() || teamMember.getCertifiedASR()){
            driverCheckbox.setEnabled(false);
            esoCheckbox.setEnabled(false);
            asrCheckbox.setEnabled(false);
        }
    }

    private boolean validateFields(){

        //TeamMember name
        String userNameValue = userName.getText().toString();
        if(userNameValue.trim().isEmpty()){
            userName.setError(getString(R.string.dialog_create_user_error_field));
            return false;
        }

        //TeamMember mail
        String userMailValue = userMail.getText().toString();
        if(userMailValue.trim().isEmpty()){
            userMail.setError(getString(R.string.dialog_create_user_error_field));
            return false;
        }

        return true;
    }

    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(teamMember == null){ //Creating
                    teamMember = new TeamMember();
                    teamMember.setPhotoUrl(getString(R.string.default_image_url));
                }


                if(validateFields()){
                    String userNameValue = userName.getText().toString();
                    String userMailValue = userMail.getText().toString();

                    teamMember.setName(userNameValue);
                    teamMember.setTeam(selectedTeam.getCar().getNumber() + " - " + selectedTeam.getName());
                    teamMember.setTeamID(selectedTeam.getID());
                    teamMember.setMail(userMailValue);

                    teamMember.setDriver(driverCheckbox.isChecked());
                    teamMember.setESO(esoCheckbox.isChecked());
                    teamMember.setASR(asrCheckbox.isChecked());

                    //Call business
                    presenter.updateOrCreateTeamMember(teamMember);

                    //Close dialog
                    dialog.dismiss();
                }
            }
        });
    }


    public void setPresenter(TeamMemberGeneralPresenter presenter) {
        this.presenter = presenter;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    public void setTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }
}


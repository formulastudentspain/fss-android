package es.formulastudent.app.mvp.view.activity.userlist.dialog;

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

import androidx.fragment.app.DialogFragment;

import java.util.List;
import java.util.UUID;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.general.spinneradapters.RolesSpinnerAdapter;
import es.formulastudent.app.mvp.view.activity.general.spinneradapters.TeamsSpinnerAdapter;
import es.formulastudent.app.mvp.view.activity.userlist.UserListPresenter;

public class CreateUserDialog extends DialogFragment {

    private UserListPresenter presenter;
    private Context context;
    private AlertDialog dialog;

    //View components
    private EditText userName;
    private EditText userMail;

    private Spinner availableRoles;
    private RolesSpinnerAdapter rolesAdapter;

    private Spinner availableTeams;
    private TeamsSpinnerAdapter teamsAdapter;

    //Spinner values
    List<Team> teams;
    List<UserRole> roles;

    //Selected values
    private Team selectedTeam;
    private UserRole selectedRole;


    public CreateUserDialog() {}

    public static CreateUserDialog newInstance(UserListPresenter presenter, Context context,
                                               List<Team> teams, List<UserRole> roles) {
        CreateUserDialog frag = new CreateUserDialog();
        frag.setPresenter(presenter);
        frag.setContext(context);
        frag.setRoles(roles);
        frag.setTeams(teams);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_create_user, null);

        // Get view components
        userName = rootView.findViewById(R.id.create_user_name);
        userMail = rootView.findViewById(R.id.create_user_mail);
        availableRoles = rootView.findViewById(R.id.create_user_role);

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
        availableRoles = rootView.findViewById(R.id.create_user_role);
        rolesAdapter = new RolesSpinnerAdapter(context, android.R.layout.simple_spinner_item, roles);
        availableRoles.setAdapter(rolesAdapter);
        availableRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                UserRole role = rolesAdapter.getItem(position);
                selectedRole = role;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        builder.setView(rootView)
                .setTitle(R.string.dialog_create_user_title)

                //Action buttons
                .setPositiveButton(R.string.dialog_create_user_button_create, null)
                .setNegativeButton(R.string.dialog_create_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateUserDialog.this.getDialog().cancel();
                    }
                });

         dialog = builder.create();

         return dialog;
    }

    private boolean validateFields(){

        //User name
        String userNameValue = userName.getText().toString();
        if(userNameValue.trim().isEmpty()){
            userName.setError(getString(R.string.dialog_create_user_error_field));
            return false;
        }

        //User mail
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

                User user = new User();

                if(validateFields()){
                    String userNameValue = userName.getText().toString();
                    String userMailValue = userMail.getText().toString();

                    user.setID(UUID.randomUUID().toString());
                    user.setName(userNameValue);
                    user.setTeam(selectedTeam.getName());
                    user.setTeamID(selectedTeam.getID());
                    user.setMail(userMailValue);
                    user.setRole(selectedRole.getName());
                    user.setRoleID(selectedRole.getID());
                    user.setPhotoUrl(getString(R.string.default_image_url));

                    //Call business
                    presenter.createUser(user);

                    //Close dialog
                    dialog.dismiss();
                }
            }
        });
    }


    public void setPresenter(UserListPresenter presenter) {
        this.presenter = presenter;
    }
    public void setContext(Context context){
        this.context = context;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}


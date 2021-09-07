package es.formulastudent.app.mvp.view.screen.userdetail.dialog;

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

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.screen.general.spinneradapters.RolesSpinnerAdapter;
import es.formulastudent.app.mvp.view.screen.userdetail.UserDetailPresenter;

public class EditUserDialog extends DialogFragment {

    private UserDetailPresenter presenter;
    private Context context;
    private AlertDialog dialog;
    private User loggedUser;
    private User selectedUser;

    //View components
    private EditText userName;
    private EditText userMail;

    private Spinner availableRoles;
    private RolesSpinnerAdapter rolesAdapter;

    //Selected values
    private UserRole selectedRole;


    public EditUserDialog() {}

    public static EditUserDialog newInstance(UserDetailPresenter presenter, Context context, User loggedUser, User selectedUser) {
        EditUserDialog frag = new EditUserDialog();
        frag.setPresenter(presenter);
        frag.setContext(context);
        frag.setLoggedUser(loggedUser);
        frag.setSelectedUser(selectedUser);

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

        //Roles
        availableRoles = rootView.findViewById(R.id.create_user_role);

        List<UserRole> roles = new ArrayList<>();
        int selectedRoleIndex = 0;
        int roleIndex = 0;

        for(String roleString: loggedUser.getRole().getManagedRoles()){
            roles.add(UserRole.getRoleByName(roleString));
            if(selectedUser.getRole()!=null) {
                if (roleString.equals(selectedUser.getRole().getName())) {
                    selectedRoleIndex = roleIndex;
                }
            }
            roleIndex++;
        }

        rolesAdapter = new RolesSpinnerAdapter(context, android.R.layout.simple_spinner_item, roles);
        availableRoles.setAdapter(rolesAdapter);
        availableRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                UserRole role = (UserRole) rolesAdapter.getItem(position);
                selectedRole = role;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        //Set data
        userName.setText(selectedUser.getName());
        userMail.setText(selectedUser.getMail());
        userMail.setEnabled(false);
        availableRoles.setSelection(selectedRoleIndex);

        builder.setView(rootView)
                .setTitle(R.string.dialog_edit_user_title)

                //Action buttons
                .setPositiveButton(R.string.dialog_edit_user_button_edit, null)
                .setNegativeButton(R.string.dialog_edit_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditUserDialog.this.getDialog().cancel();
                    }
                });

         dialog = builder.create();

         return dialog;
    }

    private boolean validateFields(){

        //TeamMember name
        String userNameValue = userName.getText().toString();
        if(userNameValue.trim().isEmpty()){
            userName.setError(getString(R.string.dialog_create_user_error_field));
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

                if(validateFields()){
                    String userNameValue = userName.getText().toString();

                    selectedUser.setName(userNameValue);
                    selectedUser.setRole(selectedRole);

                    //Call business
                    presenter.updateUser(selectedUser);

                    //Close dialog
                    dialog.dismiss();
                }
            }
        });
    }


    public void setPresenter(UserDetailPresenter presenter) {
        this.presenter = presenter;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}


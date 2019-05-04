package es.formulastudent.app.mvp.view.activity.userlist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.userlist.UserListPresenter;

public class CreateUserDialog extends DialogFragment {

    private EditText userName;
    private EditText userSurname;
    private Spinner userRoles;
    private Spinner availableTeams;
    private UserListPresenter presenter;

    public CreateUserDialog() {}

    public static CreateUserDialog newInstance(UserListPresenter presenter) {
        CreateUserDialog frag = new CreateUserDialog();
        frag.setPresenter(presenter);
        return frag;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_create_user, null);

        // Get view components
        userName = rootView.findViewById(R.id.create_user_name);
        userSurname = rootView.findViewById(R.id.create_user_surname);
        userRoles = rootView.findViewById(R.id.create_user_role);
        availableTeams = rootView.findViewById(R.id.create_user_team);

        userRoles.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, UserRole.values()));


        builder.setView(rootView)
                .setTitle(R.string.dialog_create_user_title)

                //Action buttons
                .setPositiveButton(R.string.dialog_create_user_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String userNameValue = userName.getText().toString();
                        String userSurnameValue = userSurname.getText().toString();

                        //TODO añadir adapter para los teams
                        //TODO hacer validación de que los datos son decentes
                        //TODO crear un objeto userDTO con la info rellenada
                        //TODO llamar al presenter para crear el usuario
                    }
                })
                .setNegativeButton(R.string.dialog_create_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateUserDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void setPresenter(UserListPresenter presenter) {
        this.presenter = presenter;
    }
}


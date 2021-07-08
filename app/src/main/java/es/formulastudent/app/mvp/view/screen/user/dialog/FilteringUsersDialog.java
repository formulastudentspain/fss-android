package es.formulastudent.app.mvp.view.screen.user.dialog;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.screen.general.spinneradapters.RolesSpinnerAdapter;
import es.formulastudent.app.mvp.view.screen.user.UserPresenter;

public class FilteringUsersDialog extends DialogFragment {

    private AlertDialog dialog;
    private RolesSpinnerAdapter rolesAdapter;
    private Context context;

    //Data
    private Role selectedRole;

    //Presenter
    private UserPresenter presenter;

    public FilteringUsersDialog() {
    }

    public static FilteringUsersDialog newInstance(Context context, UserPresenter presenter, Role selectedRole) {
        FilteringUsersDialog frag = new FilteringUsersDialog();
        frag.setPresenter(presenter);
        frag.setSelectedRole(selectedRole);
        frag.setContext(context);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filter_user, null);
        initializeElements(rootView);

        builder.setView(rootView)
            .setTitle(R.string.dynamic_event_filtering_dialog_title)
            .setPositiveButton(R.string.dynamic_event_filtering_dialog_filter_button, null)
            .setNeutralButton("Clear", null)
            .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FilteringUsersDialog.this.getDialog().cancel();
                }
            });

        dialog = builder.create();
        return dialog;
    }


    private void initializeElements(View rootView) {

        Spinner viewRoles = rootView.findViewById(R.id.roles_spinner);

        List<UserRole> roles = new ArrayList<>(Arrays.asList(UserRole.values()));
        roles.add(0, null);

        int selectedRoleIndex = 0;
        int roleIndex = 0;

        for (Role role : roles) {
            if (selectedRole != null) {
                if (selectedRole.equals(role)) {
                    selectedRoleIndex = roleIndex;
                }
            }
            roleIndex++;
        }

        rolesAdapter = new RolesSpinnerAdapter(context, android.R.layout.simple_spinner_item, roles);
        viewRoles.setAdapter(rolesAdapter);
        viewRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedRole = rolesAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        viewRoles.setSelection(selectedRoleIndex);
    }


    @Override
    public void onStart() {
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setFilteringValues((UserRole) selectedRole);
                presenter.retrieveUsers();
                dialog.dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setFilteringValues(null);
                presenter.retrieveUsers();
                dialog.dismiss();
            }
        });
    }


    public void setPresenter(UserPresenter presenter) {
        this.presenter = presenter;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
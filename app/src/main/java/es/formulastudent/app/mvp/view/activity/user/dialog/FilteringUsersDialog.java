package es.formulastudent.app.mvp.view.activity.user.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

public class FilteringUsersDialog extends DialogFragment implements ChipGroup.OnCheckedChangeListener{

    private AlertDialog dialog;

    //View elements
    private ChipGroup rolesGroup;
    private ChipGroup itemsGroup;

    //Data
    private String selectedRole;
    private Boolean cellPhoneSelected;
    private Boolean walkieSelected;


    //Presenter
    private RaceControlPresenter presenter;

    public FilteringUsersDialog() {}

    public static FilteringUsersDialog newInstance(RaceControlPresenter presenter, String selectedRole, Boolean cellPhoneSelected, Boolean walkieSelected) {
        FilteringUsersDialog frag = new FilteringUsersDialog();
        frag.setPresenter(presenter);
        frag.setSelectedRole(selectedRole);
        frag.setWalkieSelected(walkieSelected);
        frag.setCellPhoneSelected(cellPhoneSelected);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filter_user, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                    .setTitle(R.string.dynamic_event_filtering_dialog_title)
                    .setPositiveButton(R.string.dynamic_event_filtering_dialog_filter_button,null)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FilteringUsersDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {


        //Chips role
        if(selectedRole != null){
            if(selectedRole.equalsIgnoreCase(getString(R.string.user_role_staff))){ //waiting area
                ((Chip) rolesGroup.getChildAt(0)).setChecked(true);
            }else if(selectedRole.equalsIgnoreCase(getString(R.string.user_role_scrutineer))){ //scrutineering
                ((Chip) rolesGroup.getChildAt(1)).setChecked(true);
            }else if(selectedRole.equalsIgnoreCase(getString(R.string.user_role_marshall))){ //racing 1
                ((Chip) rolesGroup.getChildAt(2)).setChecked(true);
            }else if(selectedRole.equalsIgnoreCase(getString(R.string.user_role_official))){ //racing 2
                ((Chip) rolesGroup.getChildAt(3)).setChecked(true);
            }
        }else{ //all
            ((Chip) rolesGroup.getChildAt(4)).setChecked(true);
        }


        //Chips items
        if(cellPhoneSelected != null && cellPhoneSelected){
            ((Chip) itemsGroup.getChildAt(0)).setChecked(true);
        }else{
            ((Chip) itemsGroup.getChildAt(0)).setChecked(false);
        }
        if(walkieSelected != null && walkieSelected){
            ((Chip) itemsGroup.getChildAt(1)).setChecked(true);
        }else{
            ((Chip) itemsGroup.getChildAt(1)).setChecked(false);
        }
    }

    private void initializeElements(View rootView){

        //Roles group
        rolesGroup = rootView.findViewById(R.id.user_roles_chip_group);
        rolesGroup.setOnCheckedChangeListener(this);

        //Items group
        itemsGroup = rootView.findViewById(R.id.user_list_with_items);
        itemsGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set values for filtering
              //  presenter.setFilteringValues(selectedArea, selectedCarNumber);

                //Do filter
                presenter.retrieveRegisterList();

                //Close dialog
                dialog.dismiss();

            }
        });
    }

    @Override
    public void onCheckedChanged(ChipGroup chipGroup, int selectedChipId) {

        if(selectedChipId == R.id.user_list_role_staff){
            selectedRole = getString(R.string.user_role_staff);

        }else if(selectedChipId == R.id.user_list_role_scrutineer){
            selectedRole = getString(R.string.user_role_scrutineer);

        }else if(selectedChipId == R.id.user_list_role_marshall){
            selectedRole = getString(R.string.user_role_marshall);

        }else if(selectedChipId == R.id.user_list_role_official){
            selectedRole = getString(R.string.user_role_official);

        }else{ //all
            selectedRole = null;
        }

    }

    public void setPresenter(RaceControlPresenter presenter) {
        this.presenter = presenter;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public void setCellPhoneSelected(Boolean cellPhoneSelected) {
        this.cellPhoneSelected = cellPhoneSelected;
    }

    public void setWalkieSelected(Boolean walkieSelected) {
        this.walkieSelected = walkieSelected;
    }
}
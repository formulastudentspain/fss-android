package es.formulastudent.app.mvp.view.activity.racecontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

public class FilteringRegistersDialog extends DialogFragment implements ChipGroup.OnCheckedChangeListener{

    private AlertDialog dialog;

    //View elements
    private ChipGroup rcAreaGroup;
    private EditText carNumberField;

    //Data
    private Long selectedCarNumber;
    private String selectedArea;


    //Presenter
    private RaceControlPresenter presenter;

    public FilteringRegistersDialog() {}

    public static FilteringRegistersDialog newInstance(RaceControlPresenter presenter, Long selectedCarNumber, String selectedArea) {
        FilteringRegistersDialog frag = new FilteringRegistersDialog();
        frag.setPresenter(presenter);
        frag.setSelectedCarNumber(selectedCarNumber);
        frag.setSelectedArea(selectedArea);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filter_race_control, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                    .setTitle(R.string.dynamic_event_filtering_dialog_title)
                    .setPositiveButton(R.string.dynamic_event_filtering_dialog_filter_button,null)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FilteringRegistersDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {

        //Car number
        if(selectedCarNumber != null){
            carNumberField.setText(selectedCarNumber.toString());
        }else{
            carNumberField.setText("");
        }

        //Chips
        if(selectedArea != null){
            if(selectedArea.equalsIgnoreCase(getString(R.string.rc_area_waiting_area))){ //waiting area
                ((Chip) rcAreaGroup.getChildAt(0)).setChecked(true);
            }else if(selectedArea.equalsIgnoreCase(getString(R.string.rc_area_scrutineering))){ //scrutineering
                ((Chip) rcAreaGroup.getChildAt(1)).setChecked(true);
            }else if(selectedArea.equalsIgnoreCase(getString(R.string.rc_area_racing1))){ //racing 1
                ((Chip) rcAreaGroup.getChildAt(2)).setChecked(true);
            }else if(selectedArea.equalsIgnoreCase(getString(R.string.rc_area_racing2))){ //racing 2
                ((Chip) rcAreaGroup.getChildAt(3)).setChecked(true);
            }
        }else{ //all
            ((Chip) rcAreaGroup.getChildAt(4)).setChecked(true);
        }
    }

    private void initializeElements(View rootView){

        //RC area group
        rcAreaGroup = rootView.findViewById(R.id.dynamic_event_chip_group);
        rcAreaGroup.setOnCheckedChangeListener(this);

        //Car number
        carNumberField = rootView.findViewById(R.id.filtering_car_number);
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    //Get car number value
                    String carNumString = carNumberField.getText().toString();
                    if(carNumString.isEmpty()){
                        selectedCarNumber = null;
                    }else{
                        selectedCarNumber = Long.parseLong(carNumberField.getText().toString());
                    }

                    //Set values for filtering
                    presenter.setFilteringValues(selectedArea, selectedCarNumber);

                    //Do filter
                    presenter.retrieveRegisterList();

                    //Close dialog
                    dialog.dismiss();

                }catch(Exception e){
                    carNumberField.setError("Invalid car number");
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(ChipGroup chipGroup, int selectedChipId) {

        if(selectedChipId == R.id.rc_area_waiting_area){
            selectedArea = getString(R.string.rc_area_waiting_area);

        }else if(selectedChipId == R.id.rc_area_scrutineering){
            selectedArea = getString(R.string.rc_area_scrutineering);

        }else if(selectedChipId == R.id.rc_area_racing1){
            selectedArea = getString(R.string.rc_area_racing1);

        }else if(selectedChipId == R.id.rc_area_racing2){
            selectedArea = getString(R.string.rc_area_racing2);

        }else{ //all
            selectedArea = null;
        }

    }

    public void setPresenter(RaceControlPresenter presenter) {
        this.presenter = presenter;
    }

    public void setSelectedArea(String selectedArea) {
        this.selectedArea = selectedArea;
    }

    public void setSelectedCarNumber(Long selectedCarNumber) {
        this.selectedCarNumber = selectedCarNumber;
    }

}
package es.formulastudent.app.mvp.view.activity.racecontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

public class FilteringRegistersDialog extends DialogFragment{

    private AlertDialog dialog;

    //View elements
    private EditText carNumberField;

    //Data
    private Long selectedCarNumber;

    //Presenter
    private RaceControlPresenter presenter;

    public FilteringRegistersDialog() {}

    public static FilteringRegistersDialog newInstance(RaceControlPresenter presenter, Long selectedCarNumber){
        FilteringRegistersDialog frag = new FilteringRegistersDialog();
        frag.setPresenter(presenter);
        frag.setSelectedCarNumber(selectedCarNumber);

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


    }

    private void initializeElements(View rootView){

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
                    presenter.setFilteringValues(selectedCarNumber);

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

    public void setPresenter(RaceControlPresenter presenter) {
        this.presenter = presenter;
    }

    public void setSelectedCarNumber(Long selectedCarNumber) {
        this.selectedCarNumber = selectedCarNumber;
    }
}
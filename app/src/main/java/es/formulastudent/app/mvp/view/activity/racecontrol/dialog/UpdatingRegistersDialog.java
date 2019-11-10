package es.formulastudent.app.mvp.view.activity.racecontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

public class UpdatingRegistersDialog extends DialogFragment implements View.OnClickListener {

    private AlertDialog dialog;

    //View elements
    private LinearLayout rcStateButton_NA, rcStateButton_WA, rcStateButton_SCR, rcStateButton_FIX, rcStateButton_RR1D,
        rcStateButton_R1D, rcStateButton_RR2D, rcStateButton_R2D, rcStateButton_FIN, rcStateButton_RL, rcStateButton_DNF;
    private TextView carNumber;
    private TextView currentState;

    //Data
    private RaceControlRegister register;
    private RaceControlEvent event;

    //Presenter
    private RaceControlPresenter presenter;

    public UpdatingRegistersDialog() {}

    public static UpdatingRegistersDialog newInstance(RaceControlPresenter presenter, RaceControlRegister register, RaceControlEvent event) {
        UpdatingRegistersDialog frag = new UpdatingRegistersDialog();
        frag.setPresenter(presenter);
        frag.setRegister(register);
        frag.setEvent(event);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_update_rc_register, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                    .setTitle(R.string.dynamic_event_filtering_dialog_title)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UpdatingRegistersDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {


    }

    private void initializeElements(View rootView){


        rcStateButton_NA = rootView.findViewById(R.id.rc_state_NA);
        rcStateButton_NA.setOnClickListener(this);

        rcStateButton_WA = rootView.findViewById(R.id.rc_state_WA);
        rcStateButton_WA.setOnClickListener(this);

        rcStateButton_SCR = rootView.findViewById(R.id.rc_state_SCR);
        rcStateButton_SCR.setOnClickListener(this);

        rcStateButton_FIX = rootView.findViewById(R.id.rc_state_FIX);
        rcStateButton_FIX.setOnClickListener(this);

        rcStateButton_RR1D = rootView.findViewById(R.id.rc_state_RR1D);
        rcStateButton_RR1D.setOnClickListener(this);

        rcStateButton_R1D = rootView.findViewById(R.id.rc_state_R1D);
        rcStateButton_R1D.setOnClickListener(this);

        rcStateButton_RR2D = rootView.findViewById(R.id.rc_state_RR2D);
        rcStateButton_RR2D.setOnClickListener(this);

        rcStateButton_R2D = rootView.findViewById(R.id.rc_state_R2D);
        rcStateButton_R2D.setOnClickListener(this);

        rcStateButton_FIN = rootView.findViewById(R.id.rc_state_FIN);
        rcStateButton_FIN.setOnClickListener(this);

        rcStateButton_RL = rootView.findViewById(R.id.rc_state_RL);
        rcStateButton_RL.setOnClickListener(this);

        rcStateButton_DNF = rootView.findViewById(R.id.rc_state_DNF);
        rcStateButton_DNF.setOnClickListener(this);


        //Car number
        carNumber = rootView.findViewById(R.id.rc_car_number);
        carNumber.setText(register.getCarNumber().toString());

        //Current state
        currentState = rootView.findViewById(R.id.rc_current_state);
        String currentStateText = register.getCurrentState().getAcronym() + " - " + register.getCurrentState().getName();
        currentState.setText(currentStateText);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rc_state_NA:
                presenter.updateRegister(register, event, RaceControlState.NOT_AVAILABLE);
                break;
            case R.id.rc_state_WA:
                presenter.updateRegister(register, event, RaceControlState.WAITING_AREA);
                break;
            case R.id.rc_state_SCR:
                presenter.updateRegister(register, event, RaceControlState.SCRUTINEERING);
                break;
            case R.id.rc_state_FIX:
                presenter.updateRegister(register, event, RaceControlState.FIXING);
                break;
            case R.id.rc_state_RR1D:
                presenter.updateRegister(register, event, RaceControlState.READY_TO_RACE_1D);
                break;
            case R.id.rc_state_R1D:
                presenter.updateRegister(register, event, RaceControlState.RACING_1D);
                break;
            case R.id.rc_state_RR2D:
                presenter.updateRegister(register, event, RaceControlState.READY_TO_RACE_2D);
                break;
            case R.id.rc_state_R2D:
                presenter.updateRegister(register, event, RaceControlState.RACING_2D);
                break;
            case R.id.rc_state_FIN:
                presenter.updateRegister(register, event, RaceControlState.FINISHED);
                break;
            case R.id.rc_state_RL:
                presenter.updateRegister(register, event, RaceControlState.RUN_LATER);
                break;
            case R.id.rc_state_DNF:
                presenter.updateRegister(register, event, RaceControlState.DNF);
                break;
        }

        //Dismiss the dialog
        UpdatingRegistersDialog.this.getDialog().cancel();
    }

    public void setPresenter(RaceControlPresenter presenter) {
        this.presenter = presenter;
    }

    public void setRegister(RaceControlRegister register) {
        this.register = register;
    }

    public void setEvent(RaceControlEvent event) {
        this.event = event;
    }
}
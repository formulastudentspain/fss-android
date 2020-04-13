package es.formulastudent.app.mvp.view.screen.racecontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.RaceControlAutocrossState;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlEnduranceState;
import es.formulastudent.app.mvp.view.screen.racecontrol.RaceControlPresenter;

public class UpdatingRegistersDialog extends DialogFragment implements View.OnClickListener {

    private AlertDialog dialog;

    //Endurance states
    private LinearLayout rcStateButton_NA, rcStateButton_WA, rcStateButton_SCR, rcStateButton_FIX, rcStateButton_RR1D,
        rcStateButton_R1D, rcStateButton_RR2D, rcStateButton_R2D, rcStateButton_FIN, rcStateButton_RL, rcStateButton_DNF;

    //Autocross states
    private LinearLayout rcStateAutocrossNA, rcStateAutocrossRR1, rcStateAutocrossFR1, rcStateAutocrossDNF1, rcStateAutocrossRR2,
        rcStateAutocrossFR2, rcStateAutocrossDNF2, rcStateAutocrossRR3, rcStateAutocrossFR3,
        rcStateAutocrossDNF3, rcStateAutocrossRR4, rcStateAutocrossFR4, rcStateAutocrossDNF4;

    private ScrollView autocrossContainer;
    private ScrollView enduranceContainer;

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



    private void initializeElements(View rootView){

        if(RaceControlEvent.ENDURANCE.equals(event)){
            initializeEnduranceValues(rootView);
            enduranceContainer.setVisibility(View.VISIBLE);

        }else if(RaceControlEvent.AUTOCROSS.equals(event)
            || RaceControlEvent.SKIDPAD.equals(event)){
            initializeAutocrossValues(rootView);
            autocrossContainer.setVisibility(View.VISIBLE);
        }


        //Car number
        carNumber = rootView.findViewById(R.id.rc_car_number);
        carNumber.setText(register.getCarNumber().toString());

        //Current state
        currentState = rootView.findViewById(R.id.rc_current_state);
        String currentStateText = register.getCurrentState().getAcronym() + " - " + register.getCurrentState().getName();
        currentState.setText(currentStateText);
    }

    private void initializeAutocrossValues(View rootView) {
        autocrossContainer = rootView.findViewById(R.id.autocross_states_container);

        rcStateAutocrossNA = rootView.findViewById(R.id.rc_autocross_state_NA);
        rcStateAutocrossNA.setOnClickListener(this);

        rcStateAutocrossRR1 = rootView.findViewById(R.id.rc_autocross_state_RR1);
        rcStateAutocrossRR1.setOnClickListener(this);

        rcStateAutocrossFR1 = rootView.findViewById(R.id.rc_autocross_state_FR1);
        rcStateAutocrossFR1.setOnClickListener(this);

        rcStateAutocrossDNF1 = rootView.findViewById(R.id.rc_autocross_state_DNF1);
        rcStateAutocrossDNF1.setOnClickListener(this);

        rcStateAutocrossRR2 = rootView.findViewById(R.id.rc_autocross_state_RR2);
        rcStateAutocrossRR2.setOnClickListener(this);

        rcStateAutocrossFR2 = rootView.findViewById(R.id.rc_autocross_state_FR2);
        rcStateAutocrossFR2.setOnClickListener(this);

        rcStateAutocrossDNF2 = rootView.findViewById(R.id.rc_autocross_state_DNF2);
        rcStateAutocrossDNF2.setOnClickListener(this);

        rcStateAutocrossRR3 = rootView.findViewById(R.id.rc_autocross_state_RR3);
        rcStateAutocrossRR3.setOnClickListener(this);

        rcStateAutocrossFR3 = rootView.findViewById(R.id.rc_autocross_state_FR3);
        rcStateAutocrossFR3.setOnClickListener(this);

        rcStateAutocrossDNF3 = rootView.findViewById(R.id.rc_autocross_state_DNF3);
        rcStateAutocrossDNF3.setOnClickListener(this);

        rcStateAutocrossRR4 = rootView.findViewById(R.id.rc_autocross_state_RR4);
        rcStateAutocrossRR4.setOnClickListener(this);

        rcStateAutocrossFR4 = rootView.findViewById(R.id.rc_autocross_state_FR4);
        rcStateAutocrossFR4.setOnClickListener(this);

        rcStateAutocrossDNF4 = rootView.findViewById(R.id.rc_autocross_state_DNF4);
        rcStateAutocrossDNF4.setOnClickListener(this);
    }


    private void initializeEnduranceValues(View rootView) {
        enduranceContainer = rootView.findViewById(R.id.endurance_states_container);

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
    }


    @Override
    public void onClick(View view) {

        if(RaceControlEvent.ENDURANCE.equals(event)){
            onEnduranceStateClick(view);

        }else if(RaceControlEvent.AUTOCROSS.equals(event)
            || RaceControlEvent.SKIDPAD.equals(event)){
            onAutocrossStateClick(view);
        }

        //Dismiss the dialog
        UpdatingRegistersDialog.this.getDialog().cancel();
    }

    private void onEnduranceStateClick(View view){
        switch (view.getId()){
            case R.id.rc_state_NA:
                presenter.updateRegister(register, event, RaceControlEnduranceState.NOT_AVAILABLE);
                break;
            case R.id.rc_state_WA:
                presenter.updateRegister(register, event, RaceControlEnduranceState.WAITING_AREA);
                break;
            case R.id.rc_state_SCR:
                presenter.updateRegister(register, event, RaceControlEnduranceState.SCRUTINEERING);
                break;
            case R.id.rc_state_FIX:
                presenter.updateRegister(register, event, RaceControlEnduranceState.FIXING);
                break;
            case R.id.rc_state_RR1D:
                presenter.updateRegister(register, event, RaceControlEnduranceState.READY_TO_RACE_1D);
                break;
            case R.id.rc_state_R1D:
                presenter.updateRegister(register, event, RaceControlEnduranceState.RACING_1D);
                break;
            case R.id.rc_state_RR2D:
                presenter.updateRegister(register, event, RaceControlEnduranceState.READY_TO_RACE_2D);
                break;
            case R.id.rc_state_R2D:
                presenter.updateRegister(register, event, RaceControlEnduranceState.RACING_2D);
                break;
            case R.id.rc_state_FIN:
                presenter.updateRegister(register, event, RaceControlEnduranceState.FINISHED);
                break;
            case R.id.rc_state_RL:
                presenter.updateRegister(register, event, RaceControlEnduranceState.RUN_LATER);
                break;
            case R.id.rc_state_DNF:
                presenter.updateRegister(register, event, RaceControlEnduranceState.DNF);
                break;
        }

    }

    private void onAutocrossStateClick(View view){
        switch (view.getId()){
            case R.id.rc_autocross_state_NA:
                presenter.updateRegister(register, event, RaceControlAutocrossState.NOT_AVAILABLE);
                break;
            case R.id.rc_autocross_state_RR1:
                presenter.updateRegister(register, event, RaceControlAutocrossState.RACING_ROUND_1);
                break;
            case R.id.rc_autocross_state_FR1:
                presenter.updateRegister(register, event, RaceControlAutocrossState.FINISHED_ROUND_1);
                break;
            case R.id.rc_autocross_state_DNF1:
                presenter.updateRegister(register, event, RaceControlAutocrossState.DNF_ROUND_1);
                break;
            case R.id.rc_autocross_state_RR2:
                presenter.updateRegister(register, event, RaceControlAutocrossState.RACING_ROUND_2);
                break;
            case R.id.rc_autocross_state_FR2:
                presenter.updateRegister(register, event, RaceControlAutocrossState.FINISHED_ROUND_2);
                break;
            case R.id.rc_autocross_state_DNF2:
                presenter.updateRegister(register, event, RaceControlAutocrossState.DNF_ROUND_2);
                break;
            case R.id.rc_autocross_state_RR3:
                presenter.updateRegister(register, event, RaceControlAutocrossState.RACING_ROUND_3);
                break;
            case R.id.rc_autocross_state_FR3:
                presenter.updateRegister(register, event, RaceControlAutocrossState.FINISHED_ROUND_3);
                break;
            case R.id.rc_autocross_state_DNF3:
                presenter.updateRegister(register, event, RaceControlAutocrossState.DNF_ROUND_3);
                break;
            case R.id.rc_autocross_state_RR4:
                presenter.updateRegister(register, event, RaceControlAutocrossState.RACING_ROUND_4);
                break;
            case R.id.rc_autocross_state_FR4:
                presenter.updateRegister(register, event, RaceControlAutocrossState.FINISHED_ROUND_4);
                break;
            case R.id.rc_autocross_state_DNF4:
                presenter.updateRegister(register, event, RaceControlAutocrossState.DNF_ROUND_4);
                break;
        }
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
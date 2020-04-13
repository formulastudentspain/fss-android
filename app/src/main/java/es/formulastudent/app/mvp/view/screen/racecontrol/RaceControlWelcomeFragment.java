package es.formulastudent.app.mvp.view.screen.racecontrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerRaceControlWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;


public class RaceControlWelcomeFragment extends Fragment implements View.OnClickListener{

    private String selectedRound;
    private String selectedArea;
    private RaceControlEvent rcEvent;

    private LinearLayout round1;
    private LinearLayout round2;
    private LinearLayout roundFinal;

    private LinearLayout areaWA;
    private LinearLayout areaSCR;
    private LinearLayout areaR1;
    private LinearLayout areaR2;
    private LinearLayout areaALL;
    private Button buttonSTART;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_control_welcome, container, false);

        //Get the event type (Endurance, AutoX, Skidpad, Acceleration)
        assert getArguments() != null;
        RaceControlWelcomeFragmentArgs args = RaceControlWelcomeFragmentArgs.fromBundle(getArguments());
        this.rcEvent = args.getRaceControlEvent();

        initViews(view);
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerRaceControlWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .build()
                .inject(this);
    }

    private void initViews(View view){

        //Buttons container
        LinearLayout buttonsContainer = view.findViewById(R.id.endurance_buttons_container);

        //Event Type text
        TextView eventType = view.findViewById(R.id.event_type);
        if(RaceControlEvent.ENDURANCE.equals(rcEvent)){
            eventType.setText("ENDURANCE"); //TODO crear un enum con los tipos de evento, los 4
        }else if(RaceControlEvent.AUTOCROSS.equals(rcEvent)){
            eventType.setText("AUTOCROSS"); //TODO crear un enum con los tipos de evento, los 4
        }else if(RaceControlEvent.SKIDPAD.equals(rcEvent)){
            eventType.setText("SKIDPAD"); //TODO crear un enum con los tipos de evento, los 4
        }

        //Button for round 1
        round1 = view.findViewById(R.id.round_1_container);
        round1.setOnClickListener(this);

        //Button for round 2
        round2 = view.findViewById(R.id.round_2_container);
        round2.setOnClickListener(this);

        //Button for round final
        roundFinal = view.findViewById(R.id.round_3_container);
        roundFinal.setOnClickListener(this);

        //Button for area WA
        areaWA = view.findViewById(R.id.area_wa_container);
        areaWA.setOnClickListener(this);

        //Button for area SCR
        areaSCR = view.findViewById(R.id.area_scr_container);
        areaSCR.setOnClickListener(this);

        //Button for area R1
        areaR1 = view.findViewById(R.id.area_r1_container);
        areaR1.setOnClickListener(this);

        //Button for area R2
        areaR2 = view.findViewById(R.id.area_r2_container);
        areaR2.setOnClickListener(this);

        //Button for area ALL
        areaALL = view.findViewById(R.id.area_all_container);
        areaALL.setOnClickListener(this);

        //Button START
        buttonSTART = view.findViewById(R.id.go_button);
        buttonSTART.setOnClickListener(this);

        if(RaceControlEvent.ENDURANCE.equals(rcEvent)) {
            buttonSTART.setVisibility(View.GONE);
            buttonsContainer.setVisibility(View.VISIBLE);
        }else{
            buttonSTART.setVisibility(View.VISIBLE);
            buttonsContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        //Round 1
        if(view.getId() == R.id.round_1_container){
            selectedRound = RaceControlRegister.RACE_ROUND_1;
            round1.setSelected(true);
            round2.setSelected(false);
            roundFinal.setSelected(false);

            //Round 2
        }else if(view.getId() == R.id.round_2_container){
            selectedRound = RaceControlRegister.RACE_ROUND_2;
            round1.setSelected(false);
            round2.setSelected(true);
            roundFinal.setSelected(false);

            //Final round
        }else if(view.getId() == R.id.round_3_container){
            selectedRound = RaceControlRegister.RACE_ROUND_FINAL;
            round1.setSelected(false);
            round2.setSelected(false);
            roundFinal.setSelected(true);

            //WA area
        }else if(view.getId() == R.id.area_wa_container){
            selectedArea = getString(R.string.rc_area_waiting_area);
            areaWA.setSelected(true);
            areaSCR.setSelected(false);
            areaR1.setSelected(false);
            areaR2.setSelected(false);
            areaALL.setSelected(false);

            //SCR area
        }else if(view.getId() == R.id.area_scr_container){
            selectedArea = getString(R.string.rc_area_scrutineering);
            areaWA.setSelected(false);
            areaSCR.setSelected(true);
            areaR1.setSelected(false);
            areaR2.setSelected(false);
            areaALL.setSelected(false);

            //R1 area
        }else if(view.getId() == R.id.area_r1_container){
            selectedArea = getString(R.string.rc_area_racing1);
            areaWA.setSelected(false);
            areaSCR.setSelected(false);
            areaR1.setSelected(true);
            areaR2.setSelected(false);
            areaALL.setSelected(false);

            //R2 area
        }else if(view.getId() == R.id.area_r2_container){
            selectedArea = getString(R.string.rc_area_racing2);
            areaWA.setSelected(false);
            areaSCR.setSelected(false);
            areaR1.setSelected(false);
            areaR2.setSelected(true);
            areaALL.setSelected(false);

            //ALL area
        }else if(view.getId() == R.id.area_all_container){
            selectedArea = getString(R.string.rc_area_all);
            areaWA.setSelected(false);
            areaSCR.setSelected(false);
            areaR1.setSelected(false);
            areaR2.setSelected(false);
            areaALL.setSelected(true);

        }else if(view.getId() == R.id.go_button){
            assert getActivity() != null;
            NavController navController = Navigation.findNavController(getActivity(), R.id.myNavHostFragment);
            navController.navigate(RaceControlWelcomeFragmentDirections
                    .actionRaceControlWelcomeFragmentToRaceControlFragment(
                            rcEvent,
                            selectedRound,
                            selectedArea,
                            getString(rcEvent.getActivityTitle())));
        }

        if((RaceControlEvent.ENDURANCE.equals(rcEvent) && selectedRound != null && selectedArea!=null)
                || RaceControlEvent.AUTOCROSS.equals(rcEvent)
                || RaceControlEvent.SKIDPAD.equals(rcEvent)) {
            buttonSTART.setVisibility(View.VISIBLE);
        }
    }
}

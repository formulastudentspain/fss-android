package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerRaceControlWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;


public class RaceControlWelcomeActivity extends GeneralActivity implements View.OnClickListener {

    String selectedRound;
    String selectedArea;
    RaceControlEvent rcEvent;

    LinearLayout buttonsContainer;
    TextView eventType;

    LinearLayout round1;
    LinearLayout round2;
    LinearLayout roundFinal;

    LinearLayout areaWA;
    LinearLayout areaSCR;
    LinearLayout areaR1;
    LinearLayout areaR2;
    LinearLayout areaALL;
    Button buttonSTART;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_race_control_welcome);
        super.onCreate(savedInstanceState);
        setupComponent(FSSApp.getApp().component());

        //Get the event type (Endurance, AutoX, Skidpad, Acceleration)
        RaceControlEvent rcEvent = (RaceControlEvent) getIntent().getSerializableExtra("eventType");
        this.rcEvent = rcEvent;

        initViews();
        setSupportActionBar(toolbar);
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerRaceControlWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }


    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = rcEvent.getDrawerItemID();

        //Buttons container
        buttonsContainer = findViewById(R.id.endurance_buttons_container);

        //Event Type text
        eventType = findViewById(R.id.event_type);
        if(RaceControlEvent.ENDURANCE.equals(rcEvent)){
            eventType.setText("ENDURANCE"); //TODO crear un enum con los tipos de evento, los 4
        }else if(RaceControlEvent.AUTOCROSS.equals(rcEvent)){
            eventType.setText("AUTOCROSS"); //TODO crear un enum con los tipos de evento, los 4
        }

        //Button for round 1
        round1 = findViewById(R.id.round_1_container);
        round1.setOnClickListener(this);

        //Button for round 2
        round2 = findViewById(R.id.round_2_container);
        round2.setOnClickListener(this);

        //Button for round final
        roundFinal = findViewById(R.id.round_3_container);
        roundFinal.setOnClickListener(this);

        //Button for area WA
        areaWA = findViewById(R.id.area_wa_container);
        areaWA.setOnClickListener(this);

        //Button for area SCR
        areaSCR = findViewById(R.id.area_scr_container);
        areaSCR.setOnClickListener(this);

        //Button for area R1
        areaR1 = findViewById(R.id.area_r1_container);
        areaR1.setOnClickListener(this);

        //Button for area R2
        areaR2 = findViewById(R.id.area_r2_container);
        areaR2.setOnClickListener(this);

        //Button for area ALL
        areaALL = findViewById(R.id.area_all_container);
        areaALL.setOnClickListener(this);

        //Button START
        buttonSTART = findViewById(R.id.go_button);
        buttonSTART.setOnClickListener(this);

        if(RaceControlEvent.ENDURANCE.equals(rcEvent)) {
            buttonSTART.setVisibility(View.GONE);
            buttonsContainer.setVisibility(View.VISIBLE);
        }else{
            buttonSTART.setVisibility(View.VISIBLE);
            buttonsContainer.setVisibility(View.GONE);
        }

        //Add toolbar title
        setToolbarTitle(getString(rcEvent.getActivityTitle()));
    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
    }


    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    public void onClick(View view) {

        //Round 1
        if(view.getId() == R.id.round_1_container){
            selectedRound = RaceControlRegister.RACE_TYPE_ELECTRIC;
            round1.setSelected(true);
            round2.setSelected(false);
            roundFinal.setSelected(false);

        //Round 2
        }else if(view.getId() == R.id.round_2_container){
            selectedRound = RaceControlRegister.RACE_TYPE_COMBUSTION;
            round1.setSelected(false);
            round2.setSelected(true);
            roundFinal.setSelected(false);

        //Final round
        }else if(view.getId() == R.id.round_3_container){
            selectedRound = RaceControlRegister.RACE_TYPE_FINAL;
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
            Intent intent = new Intent(this, RaceControlActivity.class);
            intent.putExtra("eventType", rcEvent);
            intent.putExtra("rc_type", selectedRound);
            intent.putExtra("rc_area", selectedArea);
            this.startActivity(intent);
        }

        if((RaceControlEvent.ENDURANCE.equals(rcEvent) && selectedRound != null && selectedArea!=null)
            || RaceControlEvent.AUTOCROSS.equals(rcEvent)) {
            buttonSTART.setVisibility(View.VISIBLE);
        }
    }
}
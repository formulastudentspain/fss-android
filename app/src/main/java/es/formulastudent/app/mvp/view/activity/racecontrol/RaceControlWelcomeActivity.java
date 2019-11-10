package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerRaceControlWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;


public class RaceControlWelcomeActivity extends GeneralActivity implements View.OnClickListener {

    RaceControlEvent rcEvent;

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

        //Button for combustion
        LinearLayout type_combustion = findViewById(R.id.rc_welcome_combustion);
        type_combustion.setOnClickListener(this);

        //Button for electric
        LinearLayout type_electric = findViewById(R.id.rc_welcome_electric);
        type_electric.setOnClickListener(this);

        //Button for final
        LinearLayout type_final = findViewById(R.id.rc_welcome_final);
        type_final.setOnClickListener(this);

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

        Intent intent = new Intent(this, RaceControlActivity.class);

        //Electric race
        if(view.getId() == R.id.rc_welcome_electric){
            intent.putExtra("rc_type", RaceControlRegister.RACE_TYPE_ELECTRIC);

        //Combustion race
        }else if(view.getId() == R.id.rc_welcome_combustion){
            intent.putExtra("rc_type", RaceControlRegister.RACE_TYPE_COMBUSTION);

        //Final race
        }else if(view.getId() == R.id.rc_welcome_final){
            intent.putExtra("rc_type", RaceControlRegister.RACE_TYPE_FINAL);
        }

        intent.putExtra("eventType", rcEvent);
        this.startActivity(intent);
    }

}



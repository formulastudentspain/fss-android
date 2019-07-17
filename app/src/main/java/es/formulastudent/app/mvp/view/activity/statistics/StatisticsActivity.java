package es.formulastudent.app.mvp.view.activity.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerStatisticsComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.StatisticsModule;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;

public class StatisticsActivity extends GeneralActivity implements View.OnClickListener, StatisticsPresenter.View {

    @Inject
    StatisticsPresenter presenter;

    //View components
    private Button exportBriefing;
    private Button exportPreSrutineering;
    private Button exportPracticeTrack;
    private Button exportSkidpad;
    private Button exportAcceleration;
    private Button exportAutocross;
    private Button exportEndurance;
    private Button exportUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_statistics);
        super.onCreate(savedInstanceState);

        initViews();
    }



    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerStatisticsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .statisticsModule(new StatisticsModule(this))
                .build()
                .inject(this);
    }




    private void initViews() {

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 10016L;

        //Bind components
        exportBriefing = findViewById(R.id.exportBriefing);
        exportBriefing.setOnClickListener(this);

        exportPreSrutineering = findViewById(R.id.exportPreScrutineering);
        exportPreSrutineering.setOnClickListener(this);

        exportPracticeTrack = findViewById(R.id.exportPracticeTrack);
        exportPracticeTrack.setOnClickListener(this);

        exportSkidpad = findViewById(R.id.exportSkidPad);
        exportSkidpad.setOnClickListener(this);

        exportAcceleration = findViewById(R.id.exportAcceleration);
        exportAcceleration.setOnClickListener(this);

        exportAutocross = findViewById(R.id.exportAutocross);
        exportAutocross.setOnClickListener(this);

        exportEndurance = findViewById(R.id.exportEndurance);
        exportEndurance.setOnClickListener(this);

        exportUsers = findViewById(R.id.exportUsers);
        exportUsers.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.exportBriefing){
            //TODO

        }else if(view.getId() == R.id.exportPreScrutineering){
            presenter.exportDynamicEvent(EventType.PRE_SCRUTINEERING);

        }else if(view.getId() == R.id.exportPracticeTrack){
            presenter.exportDynamicEvent(EventType.PRACTICE_TRACK);

        }else if(view.getId() == R.id.exportSkidPad){
            presenter.exportDynamicEvent(EventType.SKIDPAD);

        }else if(view.getId() == R.id.exportAcceleration){
            presenter.exportDynamicEvent(EventType.ACCELERATION);

        }else if(view.getId() == R.id.exportAutocross){
            presenter.exportDynamicEvent(EventType.AUTOCROSS);

        }else if(view.getId() == R.id.exportEndurance){
            presenter.exportDynamicEvent(EventType.ENDURANCE_EFFICIENCY);

        }else if(view.getId() == R.id.exportUsers){
            //TODO

        }

    }



    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoadingIcon() {
        super.hideLoadingDialog();
    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
    }
}

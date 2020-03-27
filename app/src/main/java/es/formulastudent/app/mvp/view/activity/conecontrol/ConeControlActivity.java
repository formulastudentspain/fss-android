package es.formulastudent.app.mvp.view.activity.conecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.ListenerRegistration;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.ConeControlModule;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.activity.conecontrol.recyclerview.ConeControlAdapter;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;


public class ConeControlActivity extends GeneralActivity implements ConeControlPresenter.View{


    ConeControlEvent ccEvent; //Endurance, AutoX, Acceleration, Skidpad
    Long selectedSector;
    String selectedRound;

    //Real-time register listener
    ListenerRegistration registerListener;

    @Inject
    ConeControlPresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private ConeControlAdapter ccAdapter;
    private TextView roundNumber;
    private TextView sectorNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cone_control);
        super.onCreate(savedInstanceState);

        //Get the event type (Endurance, AutoX, Skidpad, Acceleration)
        ConeControlEvent ccEvent = (ConeControlEvent) getIntent().getSerializableExtra("eventType");
        this.ccEvent = ccEvent;
        setupComponent(FSSApp.getApp().component(), ccEvent);

        //Get selected values
        selectedRound = getIntent().getStringExtra("selectedRound");
        selectedSector = (long)getIntent().getIntExtra("selectedSector", -1);

        initViews();
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent, ConeControlEvent ccEvent) {

        DaggerConeControlComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .coneControlModule(new ConeControlModule(this, ccEvent))
                .build()
                .inject(this);
    }

    private void initViews(){


        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        ccAdapter = new ConeControlAdapter(presenter.getEventRegisterList(), this, presenter);
        recyclerView.setAdapter(ccAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Titles
        sectorNumber = findViewById(R.id.sector_number);
        sectorNumber.setText(selectedSector==-1 ? "-" : String.valueOf(selectedSector));
        roundNumber = findViewById(R.id.round_number);
        roundNumber.setText(selectedRound==null ? "-" : selectedRound);


        //Add toolbar
        setToolbarTitle(getString(ccEvent.getActivityTitle()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void refreshEventRegisterItems() {
        ccAdapter.notifyDataSetChanged();
        this.hideLoading();
    }


    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        super.hideLoadingDialog();
    }

    @Override
    public String getSelectedRound() {
        return this.selectedRound;
    }

    @Override
    public Long getSelectedSector() {
        return this.selectedSector;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ccAdapter != null) {
            ccAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (ccAdapter != null) {
            ccAdapter.restoreStates(savedInstanceState);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();

        //Remove realTime listener
        registerListener.remove();
    }

    @Override
    public void onResume(){
        super.onResume();

        //Remove realTime listener
        if(registerListener != null){
            registerListener.remove();
        }

        //Refresh again the list after resuming activity
        registerListener = presenter.retrieveRegisterList();

    }

}

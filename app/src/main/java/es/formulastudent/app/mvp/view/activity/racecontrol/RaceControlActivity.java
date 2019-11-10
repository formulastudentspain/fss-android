package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.ListenerRegistration;

import org.greenrobot.greendao.annotation.NotNull;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerRaceControlComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.RaceControlModule;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.racecontrol.recyclerview.RaceControlAdapter;


public class RaceControlActivity extends GeneralActivity implements
        RaceControlPresenter.View, View.OnClickListener{

    RaceControlEvent rcEvent; //Endurance, AutoX, Acceleration, Skidpad
    String raceType; //Electric, Combustion or Final

    //Real-time register listener
    ListenerRegistration registerListener;


    @Inject
    RaceControlPresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private RaceControlAdapter rcAdapter;
    private FloatingActionButton buttonAddVehicle;
    private MenuItem filterItem;
    private LinearLayout typeColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_race_control);
        super.onCreate(savedInstanceState);

        //Get race type (Electric, Combustion, Final)
        String raceType = getIntent().getStringExtra("rc_type");
        this.raceType = raceType;

        //Get the event type (Endurance, AutoX...)
        RaceControlEvent rcEvent = (RaceControlEvent) getIntent().getSerializableExtra("eventType");
        setupComponent(FSSApp.getApp().component(), rcEvent, raceType);
        this.rcEvent = rcEvent;

        initViews();
        registerListener = presenter.retrieveRegisterList();
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent, @NotNull RaceControlEvent rcEvent, String raceType) {

        DaggerRaceControlComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .raceControlModule(new RaceControlModule(this, rcEvent, raceType))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        rcAdapter = new RaceControlAdapter(presenter.getEventRegisterList(), this, presenter, presenter);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Add vehicle button
        buttonAddVehicle = findViewById(R.id.button_add_vehicle);
        buttonAddVehicle.setOnClickListener(this);

        //Type color (that small line below the toolbar)
        typeColor = findViewById(R.id.typeColor);
        switch (raceType) {
            case RaceControlRegister.RACE_TYPE_ELECTRIC:
                typeColor.setBackgroundColor(getResources().getColor(R.color.md_blue_300));
                break;
            case RaceControlRegister.RACE_TYPE_COMBUSTION:
                typeColor.setBackgroundColor(getResources().getColor(R.color.md_deep_orange_600));
                break;
            case RaceControlRegister.RACE_TYPE_FINAL:
                typeColor.setBackgroundColor(getResources().getColor(R.color.md_yellow_500));
                break;
            default:
                typeColor.setBackgroundColor(getResources().getColor(R.color.md_grey_900));
                break;
        }

        //Add toolbar
        setToolbarTitle(getString(rcEvent.getRaceTypes().get(raceType)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    @Override
    protected void onStart(){
        super.onStart();
        rcAdapter.notifyDataSetChanged();
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
    public void refreshEventRegisterItems() {
        rcAdapter.notifyDataSetChanged();
        this.hideLoading();
    }

    @Override
    public void filtersActivated(Boolean activated) {
        if(activated){
            filterItem.setIcon(R.drawable.ic_filter_active);
        }else{
            filterItem.setIcon(R.drawable.ic_filter_inactive);
        }
    }

    @Override
    public void closeUpdatedRow(String id) {
        rcAdapter.closeSwipeRow(id);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_vehicle){
            presenter.openCreateRegisterDialog();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dynamic_event, menu);

        //Search menu item
        filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                presenter.filterIconClicked();
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (rcAdapter != null) {
            rcAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (rcAdapter != null) {
            rcAdapter.restoreStates(savedInstanceState);
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
}

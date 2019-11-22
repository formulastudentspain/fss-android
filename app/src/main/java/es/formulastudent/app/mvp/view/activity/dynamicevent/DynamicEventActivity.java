package es.formulastudent.app.mvp.view.activity.dynamicevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerDynamicEventComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.DynamicEventModule;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.view.activity.NFCReaderActivity;
import es.formulastudent.app.mvp.view.activity.dynamicevent.recyclerview.EventRegistersAdapter;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.prescrutineeringdetail.PreScrutineeringDetailActivity;


public class DynamicEventActivity extends GeneralActivity implements
        DynamicEventPresenter.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int NFC_REQUEST_CODE = 101;
    private static final int CHRONO_CODE = 102;

    EventType eventType;

    @Inject
    DynamicEventPresenter presenter;

    //View components
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private EventRegistersAdapter registersAdapter;
    private FloatingActionButton buttonAddRegister;
    private MenuItem filterItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_event);
        super.onCreate(savedInstanceState);

        //Retrieve the event type
        EventType eventType = (EventType) getIntent().getSerializableExtra("eventType");
        setupComponent(FSSApp.getApp().component(), eventType);
        this.eventType = eventType;

        initViews();
        setSupportActionBar(toolbar);
        presenter.retrieveRegisterList();
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent, @NotNull EventType eventType) {

        DaggerDynamicEventComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .dynamicEventModule(new DynamicEventModule(this, eventType))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = eventType.getDrawerItemID();

        //Recycler view
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        registersAdapter = new EventRegistersAdapter(presenter.getEventRegisterList(), this, presenter);
        recyclerView.setAdapter(registersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Add acceleration register button
        buttonAddRegister = findViewById(R.id.button_add_acceleration_register);
        buttonAddRegister.setOnClickListener(this);

        //Add toolbar title
        setToolbarTitle(eventType.getActivityTitle());

    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
        registersAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        super.hideLoadingDialog();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshEventRegisterItems() {
        registersAdapter.notifyDataSetChanged();
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
    public void openChronoActivity(PreScrutineeringRegister register){
        Intent intent = new Intent(this, PreScrutineeringDetailActivity.class);
        intent.putExtra("prescrutineering_register", register);
        startActivityForResult(intent, CHRONO_CODE);
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_add_acceleration_register){

            //Endurance control is done from Race Control
            if(eventType.equals(EventType.ENDURANCE_EFFICIENCY)){
                createMessage(R.string.dynamic_event_message_info_controlled_from_race_control);

            }else{
                Intent i = new Intent(this, NFCReaderActivity.class);
                startActivityForResult(i, NFC_REQUEST_CODE);
            }


        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //NFC reader
        if (requestCode == NFC_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                presenter.onNFCTagDetected(result);
            }

        //Chronometer result for Egress
        }else if(requestCode == CHRONO_CODE){
            if(resultCode == Activity.RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra("result");
                Long miliseconds = Long.parseLong(result.get(0));
                String registerID = result.get(1);
                presenter.onChronoTimeRegistered(miliseconds, registerID);
            }
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
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (registersAdapter != null) {
            registersAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (registersAdapter != null) {
            registersAdapter.restoreStates(savedInstanceState);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    public void onRefresh() {
        presenter.retrieveRegisterList();
    }
}



package es.formulastudent.app.mvp.view.activity.conecontrol;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.ConeControlModule;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.activity.conecontrol.recyclerview.SectorAdapter;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class ConeControlWelcomeActivity extends GeneralActivity implements ConeControlPresenter.View, View.OnClickListener, RecyclerViewClickListener {

    @Inject
    ConeControlPresenter presenter;

    private static final int NUM_SECTORS = 7;

    private MenuItem filterItem;
    private ConeControlEvent ccEvent;
    private Integer selectedSector;
    private String selectedRound;

    //View elements
    private Button goButton;
    private LinearLayout round1;
    private LinearLayout round2;
    private LinearLayout roundFinal;
    private SectorAdapter sectorAdapter;
    private RecyclerView sectorRecyclerView;
    private LinearLayout roundContainer;
    private LinearLayout sectorContainer;
    private TextView eventTypeText;

    Map<Integer, Boolean> sectors = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cone_control_welcome);
        super.onCreate(savedInstanceState);
        setupComponent(FSSApp.getApp().component());

        //Get the event type (Endurance, AutoX, Skidpad, Acceleration)
        ConeControlEvent ccEvent = (ConeControlEvent) getIntent().getSerializableExtra("eventType");
        this.ccEvent = ccEvent;

        initViews();
        setSupportActionBar(toolbar);

        checkWritePermissions();
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerConeControlWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .coneControlModule(new ConeControlModule(this, ccEvent))
                .build()
                .inject(this);
    }


    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = ccEvent.getDrawerItemID();

        //Hide/show rounds and sectors depending on the event type
        roundContainer = findViewById(R.id.roundContainer);
        sectorContainer = findViewById(R.id.sectorContainer);
        if(ConeControlEvent.AUTOCROSS.equals(ccEvent)){
            roundContainer.setVisibility(View.GONE);

        }else if(ConeControlEvent.ENDURANCE.equals(ccEvent)){
            roundContainer.setVisibility(View.VISIBLE);

        }else if(ConeControlEvent.SKIDPAD.equals(ccEvent)){
            sectorContainer.setVisibility(View.GONE);
            roundContainer.setVisibility(View.GONE);
        }

        eventTypeText = findViewById(R.id.eventType);
        eventTypeText.setText(ccEvent.getName());

        goButton = findViewById(R.id.go_button);
        goButton.setOnClickListener(this);

        round1 = findViewById(R.id.button_round1);
        round1.setOnClickListener(this);

        round2 = findViewById(R.id.button_round2);
        round2.setOnClickListener(this);

        roundFinal = findViewById(R.id.button_roundFinal);
        roundFinal.setOnClickListener(this);


        for(int i=1; i<=NUM_SECTORS; i++){
            sectors.put(i,false);
        }

        sectorRecyclerView = findViewById(R.id.sectorRecyclerView);
        sectorAdapter = new SectorAdapter(sectors, this, this);
        sectorRecyclerView.setAdapter(sectorAdapter);
        sectorRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        checkEnableButton();

        //Add toolbar title
        setToolbarTitle(getString(ccEvent.getActivityTitle()));
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


        if(view.getId()==R.id.button_round1){
            round1.setSelected(true);
            round2.setSelected(false);
            roundFinal.setSelected(false);
            selectedRound = "Electric";
            this.checkEnableButton();

        }else if(view.getId()==R.id.button_round2){
            round1.setSelected(false);
            round2.setSelected(true);
            roundFinal.setSelected(false);
            selectedRound = "Combustion";
            this.checkEnableButton();

        }else if(view.getId()==R.id.button_roundFinal){
            round1.setSelected(false);
            round2.setSelected(false);
            roundFinal.setSelected(true);
            selectedRound = "Final";
            this.checkEnableButton();

        }else if(view.getId()==R.id.go_button){
            Intent intent = new Intent(this, ConeControlActivity.class);
            intent.putExtra("selectedSector", selectedSector);
            intent.putExtra("selectedRound", selectedRound);
            intent.putExtra("eventType", ccEvent);
            this.startActivity(intent);
        }

        checkEnableButton();
    }

    private void checkEnableButton() {

        if(ConeControlEvent.ENDURANCE.equals(ccEvent)){
            if(selectedSector==null || selectedRound==null){
                goButton.setVisibility(View.GONE);
            }else{
                goButton.setVisibility(View.VISIBLE);
            }
        }else if(ConeControlEvent.AUTOCROSS.equals(ccEvent)){
            if(selectedSector==null){
                goButton.setVisibility(View.GONE);
            }else{
                goButton.setVisibility(View.VISIBLE);
            }
        }else if(ConeControlEvent.SKIDPAD.equals(ccEvent)){
            goButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        for(Integer i: sectors.keySet()){
            sectors.put(i, false);
        }

        sectors.put(position+1, !sectors.get(position+1));
        sectorAdapter.notifyDataSetChanged();

        this.selectedSector = position+1;
        this.checkEnableButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cone_control_stats, menu);

        //Search menu item
        filterItem = menu.findItem(R.id.cone_control_stats);
        filterItem.setOnMenuItemClickListener( menuItem -> {

            if(checkWritePermissions()){
                presenter.exportConesToExcel(ccEvent);
            }

            return true;
        });

        return true;
    }


    //TODO
    private boolean checkWritePermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        16);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void refreshEventRegisterItems() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getSelectedRound() {
        return null;
    }

    @Override
    public Long getSelectedSector() {
        return null;
    }
}



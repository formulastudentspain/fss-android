package es.formulastudent.app.mvp.view.activity.conecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.activity.conecontrol.recyclerview.SectorAdapter;
import es.formulastudent.app.mvp.view.activity.conecontrolstats.ConeControlStatsActivity;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class ConeControlWelcomeActivity extends GeneralActivity implements View.OnClickListener, RecyclerViewClickListener {

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
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerConeControlWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }


    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = ccEvent.getDrawerItemID();

        //Hide/show rounds depending on the event type
        roundContainer = findViewById(R.id.roundContainer);
        if(ConeControlEvent.AUTOCROSS.equals(ccEvent)){
            roundContainer.setVisibility(View.GONE);

        }else if(ConeControlEvent.ENDURANCE.equals(ccEvent)){
            roundContainer.setVisibility(View.VISIBLE);
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
            Intent intent = new Intent(this, ConeControlStatsActivity.class);
            startActivity(intent);

            return true;
        });

        return true;
    }
}



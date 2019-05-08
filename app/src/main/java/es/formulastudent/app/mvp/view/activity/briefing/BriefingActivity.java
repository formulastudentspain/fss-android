package es.formulastudent.app.mvp.view.activity.briefing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerBriefingComponent;
import es.formulastudent.app.di.module.BriefingModule;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.NFCReaderActivity;
import es.formulastudent.app.mvp.view.activity.briefing.recyclerview.BriefingRegistersAdapter;


public class BriefingActivity extends GeneralActivity implements BriefingPresenter.View, View.OnClickListener {

    private static final int NFC_REQUEST_CODE = 101;

    @Inject
    BriefingPresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private BriefingRegistersAdapter adapter;
    private FloatingActionButton buttonAddRegister;
    private Spinner teamsSpinner;
    private ChipGroup dayListGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_briefing);
        super.onCreate(savedInstanceState);

        initViews();
        presenter.retrieveBriefingRegisterList();
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerBriefingComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .briefingModule(new BriefingModule(this))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 10001L;

        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BriefingRegistersAdapter(presenter.getBriefingRegisterList(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Add briefing register button
        buttonAddRegister = findViewById(R.id.button_add_briefing_register);
        buttonAddRegister.setOnClickListener(this);

        //Teams Spinner
        teamsSpinner = findViewById(R.id.briefing_team_spinner);

        //Add toolbar title
        setToolbarTitle(getString(R.string.briefing_activity_title));

    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoadingIcon() {

    }

    @Override
    public void refreshBriefingRegisterItems() {
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_briefing_register){
            Intent i = new Intent(this, NFCReaderActivity.class);
            startActivityForResult(i, NFC_REQUEST_CODE);
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
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}

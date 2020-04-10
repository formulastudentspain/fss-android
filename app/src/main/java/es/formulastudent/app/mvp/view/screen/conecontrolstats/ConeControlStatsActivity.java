package es.formulastudent.app.mvp.view.screen.conecontrolstats;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlStatsComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.ConeControlStatsModule;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.screen.conecontrolstats.tabadapter.ConeControlStatsTabAdapter;
import es.formulastudent.app.mvp.view.screen.general.GeneralActivity;


public class ConeControlStatsActivity extends GeneralActivity implements ConeControlStatsPresenter.View {

    @Inject
    ConeControlStatsPresenter presenter;

    ConeControlStatsTabAdapter coneControlStatsTabAdapter;
    ViewPager viewPager;
    ConeControlEvent ccEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_teams_tabs);
        super.onCreate(savedInstanceState);

        //Get the event type (Endurance, AutoX, Skidpad, Acceleration)
        this.ccEvent = (ConeControlEvent) getIntent().getSerializableExtra("eventType");

        if(ConeControlEvent.ENDURANCE.equals(ccEvent)){

        }
        coneControlStatsTabAdapter = new ConeControlStatsTabAdapter(getSupportFragmentManager(), presenter, loggedUser);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(coneControlStatsTabAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        initViews();

        checkWritePermissions();
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

        DaggerConeControlStatsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .coneControlStatsModule(new ConeControlStatsModule(this, ccEvent))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add toolbar title
        setToolbarTitle("Cone Control: Stats");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    //TODO
    private void checkWritePermissions(){
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
        } else {
            // Permission has already been granted
        }

    }
}

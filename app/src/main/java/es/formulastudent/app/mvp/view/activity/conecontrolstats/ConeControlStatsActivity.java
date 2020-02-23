package es.formulastudent.app.mvp.view.activity.conecontrolstats;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlStatsComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.ConeControlStatsModule;
import es.formulastudent.app.mvp.view.activity.conecontrolstats.tabadapter.ConeControlStatsTabAdapter;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;


public class ConeControlStatsActivity extends GeneralActivity implements ConeControlStatsPresenter.View {

    @Inject
    ConeControlStatsPresenter presenter;

    ConeControlStatsTabAdapter coneControlStatsTabAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_teams_tabs);
        super.onCreate(savedInstanceState);

        //Get selected team
        coneControlStatsTabAdapter = new ConeControlStatsTabAdapter(getSupportFragmentManager(), presenter, loggedUser);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(coneControlStatsTabAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

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
    protected void setupComponent(AppComponent appComponent) {

        DaggerConeControlStatsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .coneControlStatsModule(new ConeControlStatsModule(this))
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
}

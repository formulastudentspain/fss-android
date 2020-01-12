package es.formulastudent.app.mvp.view.activity.teamsdetailfee;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamsDetailFeeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamsDetailFeeModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.teamsdetailfee.adapter.TabAdapter;


public class TeamsDetailFeeActivity extends GeneralActivity implements TeamsDetailFeePresenter.View {


    @Inject
    TeamsDetailFeePresenter presenter;

    Team team;

    TabAdapter tabAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_teams_fee);
        super.onCreate(savedInstanceState);

        //Get selected team
        team = (Team) getIntent().getSerializableExtra("selectedTeam");

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

        DaggerTeamsDetailFeeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .teamsDetailFeeModule(new TeamsDetailFeeModule(this))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add toolbar title
        setToolbarTitle(team.getCar().getNumber() + " - " + team.getName());

        tabAdapter = new TabAdapter(getSupportFragmentManager(), team, presenter);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void finishView() {

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
    public Team getCurrentTeam() {
        return team;
    }

    @Override
    public FragmentManager getViewFragmentManager() {
        return getSupportFragmentManager();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}

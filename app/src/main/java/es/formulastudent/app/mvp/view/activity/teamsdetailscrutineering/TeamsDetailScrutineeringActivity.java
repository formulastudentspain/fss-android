package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamsDetailScrutineeringComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamsDetailScrutineeringModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.tabsadapter.TabAdapter;


public class TeamsDetailScrutineeringActivity extends GeneralActivity implements TeamsDetailScrutineeringPresenter.View {

    @Inject
    TeamsDetailScrutineeringPresenter presenter;

    TabAdapter tabAdapter;
    ViewPager viewPager;

    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_teams_tabs);
        super.onCreate(savedInstanceState);

        //Get selected team
        team = (Team) getIntent().getSerializableExtra("selectedTeam");

        tabAdapter = new TabAdapter(getSupportFragmentManager(), team);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(tabAdapter);

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

        DaggerTeamsDetailScrutineeringComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .teamsDetailScrutineeringModule(new TeamsDetailScrutineeringModule(this))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add toolbar title
        setToolbarTitle(getString(R.string.drawer_menu_teams));

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
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        tabAdapter.updateTeamFields();
        Toast.makeText(this, "Save comments before leaving this activity", Toast.LENGTH_LONG).show();
        super.onBackPressed();  // optional depending on your needs
    }


}

package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamsDetailScrutineeringComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamsDetailScrutineeringModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.prescrutineering.TeamsDetailPreScrutineeringFragment;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.tabadapter.TabAdapter;


public class TeamsDetailScrutineeringActivity extends GeneralActivity implements TeamsDetailScrutineeringPresenter.View {

    private static final int NFC_REQUEST_CODE = 101;
    private static final int CHRONO_CODE = 102;

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

        tabAdapter = new TabAdapter(getSupportFragmentManager(), team, presenter, loggedUser);
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
        setToolbarTitle(team.getCar().getNumber() + " - " + team.getName());

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
    public FragmentManager getViewFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public Team getCurrentTeam() {
        return team;
    }

    @Override
    public void refreshRegisterItems() {
        //Refresh the egress items
        List<Fragment> fragmentLsit = getSupportFragmentManager().getFragments();
        for(Fragment fragment: fragmentLsit){
            if(fragment instanceof TeamsDetailPreScrutineeringFragment){
                ((TeamsDetailPreScrutineeringFragment)fragment).refreshListItems();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
}

package es.formulastudent.app.mvp.view.screen.teams;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamsComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamsModule;
import es.formulastudent.app.mvp.view.screen.general.GeneralActivity;
import es.formulastudent.app.mvp.view.screen.teams.recyclerview.TeamsAdapter;


public class TeamsActivity extends GeneralActivity implements TeamsPresenter.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    TeamsPresenter presenter;

    //View components
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private TeamsAdapter adapter;
    private MenuItem filterItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_teams);
        super.onCreate(savedInstanceState);


        initViews();
        setSupportActionBar(toolbar);

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

        DaggerTeamsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .teamsModule(new TeamsModule(this))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 70001L;

        //Recycler view
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TeamsAdapter(presenter.getTeamsList(), this, presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Add toolbar title
        setToolbarTitle(getString(R.string.drawer_menu_teams));
    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshBriefingRegisterItems() {
        adapter.notifyDataSetChanged();
        this.hideLoading();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            adapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (adapter != null) {
            adapter.restoreStates(savedInstanceState);
        }
    }

    @Override
    public void filtersActivated(Boolean activated) {
        if(filterItem != null){
            if(activated){
                filterItem.setIcon(R.drawable.ic_filter_active);
            }else{
                filterItem.setIcon(R.drawable.ic_filter_inactive);
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
    public void onRefresh() {
        presenter.retrieveTeamsList();
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.retrieveTeamsList();
    }

}

package es.formulastudent.app.mvp.view.screen.teammember;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamMemberListComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamMemberListModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.general.GeneralActivity;
import es.formulastudent.app.mvp.view.screen.teammember.dialog.CreateEditTeamMemberDialog;
import es.formulastudent.app.mvp.view.screen.teammember.dialog.FilterTeamMembersDialog;
import es.formulastudent.app.mvp.view.screen.teammember.recyclerview.TeamMemberListAdapter;


public class TeamMemberActivity extends GeneralActivity implements TeamMemberPresenter.View, View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {


    @Inject
    TeamMemberPresenter presenter;

    //View components
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private TeamMemberListAdapter teamMemberListAdapter;
    private FloatingActionButton buttonAddUser;
    private EditText searchUser;
    private ImageView searchByNFC;

    private MenuItem filterItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_team_member_list);
        super.onCreate(savedInstanceState);

        initViews();
        setSupportActionBar(toolbar);
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerTeamMemberListComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .teamMemberListModule(new TeamMemberListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        searchUser.setText("");
        presenter.retrieveTeamMembers();
    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
    }


    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 10020L;

        //Recycler view
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        teamMemberListAdapter = new TeamMemberListAdapter(presenter.getUserItemList(), this, presenter);
        recyclerView.setAdapter(teamMemberListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && buttonAddUser.isShown())
                    buttonAddUser.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    buttonAddUser.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //Add user button
        buttonAddUser = findViewById(R.id.button_add_user);
        buttonAddUser.setOnClickListener(this);

        //Search user edit text
        searchUser = findViewById(R.id.search_user_field);
        searchUser.addTextChangedListener(this);
        searchByNFC = findViewById(R.id.teamMemberSearchNFC);
        searchByNFC.setOnClickListener(this);

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_team_members_title));
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
    public void refreshUserItems() {
        teamMemberListAdapter.notifyDataSetChanged();
        this.hideLoading();
    }

    @Override
    public void finishView() {
        this.finish();
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
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_user){
            presenter.openCreateTeamMemberDialog();
        }
    }


    @Override
    public void showCreateTeamMemberDialog(List<Team> teams) {
        FragmentManager fm = getSupportFragmentManager();
        CreateEditTeamMemberDialog createEditTeamMemberDialog = CreateEditTeamMemberDialog.newInstance(presenter, this, teams, null);
        createEditTeamMemberDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void showFilteringDialog(List<Team> teams) {
        FilterTeamMembersDialog filterTeamMembersDialog = FilterTeamMembersDialog.newInstance(presenter,this, teams, presenter.getSelectedTeamToFilter());
        filterTeamMembersDialog.show(getSupportFragmentManager(), "addCommentDialog");

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        presenter.filterUsers(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onRefresh() {
        presenter.retrieveTeamMembers();
    }
}

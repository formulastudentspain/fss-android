package es.formulastudent.app.mvp.view.activity.teammember;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.teammember.dialog.CreateTeamMemberDialog;
import es.formulastudent.app.mvp.view.activity.teammember.recyclerview.TeamMemberListAdapter;


public class TeamMemberActivity extends GeneralActivity implements TeamMemberPresenter.View, View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {


    @Inject
    TeamMemberPresenter presenter;

    //View components
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private TeamMemberListAdapter teamMemberListAdapter;
    private FloatingActionButton buttonAddUser;
    private EditText searchUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_team_member_list);
        super.onCreate(savedInstanceState);

        initViews();
        presenter.retrieveUsers();
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
        presenter.retrieveUsers();

    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
    }


    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 10003L;

        //Recycler view
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        teamMemberListAdapter = new TeamMemberListAdapter(presenter.getUserItemList(), this, presenter);
        recyclerView.setAdapter(teamMemberListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Add user button
        buttonAddUser = findViewById(R.id.button_add_user);
        buttonAddUser.setOnClickListener(this);

        //Search user edit text
        searchUser = findViewById(R.id.search_user_field);
        searchUser.addTextChangedListener(this);

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_user_list_label));
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
            presenter.retrieveCreateUserDialogData();
        }
    }


    @Override
    public void showCreateUserDialog(List<Team> teams, List<Role> roles) {
        FragmentManager fm = getSupportFragmentManager();
        CreateTeamMemberDialog createTeamMemberDialog = CreateTeamMemberDialog.newInstance(presenter, this, teams, roles);
        createTeamMemberDialog.show(fm, "fragment_edit_name");
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
        presenter.retrieveUsers();
    }
}

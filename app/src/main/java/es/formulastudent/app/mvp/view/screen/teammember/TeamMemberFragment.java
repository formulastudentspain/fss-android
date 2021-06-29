package es.formulastudent.app.mvp.view.screen.teammember;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.teammember.dialog.CreateEditTeamMemberDialog;
import es.formulastudent.app.mvp.view.screen.teammember.dialog.FilterTeamMembersDialog;
import es.formulastudent.app.mvp.view.screen.teammember.recyclerview.TeamMemberListAdapter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.messages.Messages;

public class TeamMemberFragment extends Fragment implements TeamMemberPresenter.View, View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    TeamMemberPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    Messages messages;

    private TeamMemberListAdapter teamMemberListAdapter;
    private FloatingActionButton buttonAddUser;
    private EditText searchUser;
    private MenuItem filterItem;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        //Observer to display loading dialog
        presenter.getLoadingData().observe(this, loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        //Observer to display errors
        presenter.getErrorToDisplay().observe(this, message ->
                messages.showError(message.getStringID(), message.getArgs()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_team_member, container, false);
        initViews(view);
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerTeamMemberListComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .teamMemberListModule(new TeamMemberListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        searchUser.setText("");
        presenter.retrieveTeamMembers();
    }

    private void initViews(View view){

        //Recycler view
        //View components
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        teamMemberListAdapter = new TeamMemberListAdapter(presenter.getUserItemList(), getContext(), presenter);
        recyclerView.setAdapter(teamMemberListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        buttonAddUser = view.findViewById(R.id.button_add_user);
        buttonAddUser.setOnClickListener(this);

        //Search user edit text
        searchUser = view.findViewById(R.id.search_user_field);
        searchUser.addTextChangedListener(this);
        ImageView searchByNFC = view.findViewById(R.id.teamMemberSearchNFC);
        searchByNFC.setOnClickListener(this);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dynamic_event, menu);
        filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(menuItem -> {
            presenter.filterIconClicked();
            return false;
        });
    }

    @Override
    public void refreshUserItems() {
        mSwipeRefreshLayout.setRefreshing(false);
        teamMemberListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_user){
            presenter.openCreateTeamMemberDialog();
        }
    }

    @Override
    public void showCreateTeamMemberDialog(List<Team> teams) {
        FragmentManager fm = getParentFragmentManager();
        CreateEditTeamMemberDialog createEditTeamMemberDialog = CreateEditTeamMemberDialog
                .newInstance(presenter, getContext(), teams, null);
        createEditTeamMemberDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void showFilteringDialog(List<Team> teams) {
        FilterTeamMembersDialog filterTeamMembersDialog = FilterTeamMembersDialog
                .newInstance(presenter,getContext(), teams, presenter.getSelectedTeamToFilter());
        filterTeamMembersDialog.show(getParentFragmentManager(), "addCommentDialog");
    }

    @Override
    public void openTeamMemberDetailFragment(TeamMember selectedTeamMember, Boolean lastBriefing) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.myNavHostFragment);
        navController.navigate(TeamMemberFragmentDirections
                .actionTeamMemberFragmentToTeamMemberDetailFragment(selectedTeamMember, lastBriefing, selectedTeamMember.getName()));
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

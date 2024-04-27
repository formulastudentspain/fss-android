package code.formulastudentspain.app.mvp.view.screen.teams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerTeamsComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.TeamsModule;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.data.model.UserRole;
import code.formulastudentspain.app.mvp.view.screen.teams.recyclerview.TeamsAdapter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


public class TeamsFragment extends Fragment implements TeamsPresenter.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    TeamsPresenter presenter;

    @Inject
    User loggedUser;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    Messages messages;

    private TeamsAdapter adapter;
    private MenuItem filterItem;
    private NavController navController;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        //Observer for loading dialog
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
        FragmentActivity activity = getActivity();
        assert activity != null;
        navController = Navigation.findNavController(activity, R.id.myNavHostFragment);
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        initViews(view);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * Inject dependencies method
     *
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerTeamsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .teamsModule(new TeamsModule(this))
                .build()
                .inject(this);
    }

    private void initViews(View view) {
        //View components
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new TeamsAdapter(presenter.getTeamsList(), getContext(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void refreshBriefingRegisterItems() {
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void filtersActivated(Boolean activated) {
        if (filterItem != null) {
            if (activated) {
                filterItem.setIcon(R.drawable.ic_filter_active);
            } else {
                filterItem.setIcon(R.drawable.ic_filter_inactive);
            }
        }
    }

    @Override
    public void openScrutineeringFragment(Team team) {
        navController.navigate(TeamsFragmentDirections
                .actionTeamsFragmentToTeamsDetailScrutineeringFragment(team, team.getName()));
    }

    @Override
    public void openFeeFragment(Team team) {
        if( !loggedUser.isAdministrator() && !loggedUser.isRole(UserRole.OFFICIAL_STAFF)){
            messages.showError(R.string.forbidden_required_role, UserRole.OFFICIAL_STAFF.getName());
            return;
        }
        navController.navigate(TeamsFragmentDirections
                .actionTeamsFragmentToTeamsDetailFeeFragment(team, team.getName()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dynamic_event, menu);

        //Search menu item
        filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(menuItem -> {
            presenter.filterIconClicked();
            return false;
        });
    }

    @Override
    public void onRefresh() {
        presenter.retrieveTeamsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.retrieveTeamsList();
    }
}
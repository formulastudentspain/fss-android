package es.formulastudent.app.mvp.view.screen.teams;

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

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamsComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamsModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.teams.recyclerview.TeamsAdapter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;


public class TeamsFragment extends Fragment implements TeamsPresenter.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    TeamsPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    private TeamsAdapter adapter;
    private MenuItem filterItem;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        presenter.getLoadingData().observe(this, loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });
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
        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new TeamsAdapter(presenter.getTeamsList(), getContext(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void refreshBriefingRegisterItems() {
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
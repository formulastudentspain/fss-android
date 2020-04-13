package es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamsDetailScrutineeringComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamsDetailScrutineeringModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.tabadapter.TabAdapter;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.tabs.prescrutineering.TeamsDetailPreScrutineeringFragment;

public class TeamsDetailScrutineeringFragment extends Fragment implements TeamsDetailScrutineeringPresenter.View{

    private static final int NFC_REQUEST_CODE = 101;
    private static final int CHRONO_CODE = 102;

    @Inject
    TeamsDetailScrutineeringPresenter presenter;

    @Inject
    User loggedUser;

    private Team team;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams_detail_scrutineering, container, false);

        //Get selected team
        assert getArguments() != null;
        TeamsDetailScrutineeringFragmentArgs args = TeamsDetailScrutineeringFragmentArgs.fromBundle(getArguments());
        team = args.getSelectedTeam();

        TabAdapter tabAdapter = new TabAdapter(getParentFragmentManager(), team, presenter, loggedUser);
        ViewPager viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        //Remove elevation from Action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);

        return view;
    }


    @Override
    public FragmentManager getViewFragmentManager() {
        return getParentFragmentManager();
    }

    @Override
    public Team getCurrentTeam() {
        return team;
    }

    @Override
    public void refreshRegisterItems() {
        //Refresh the egress items
        List<Fragment> fragmentList = getParentFragmentManager().getFragments();
        for(Fragment fragment: fragmentList){
            if(fragment instanceof TeamsDetailPreScrutineeringFragment){
                ((TeamsDetailPreScrutineeringFragment)fragment).refreshListItems();
            }
        }
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerTeamsDetailScrutineeringComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .teamsDetailScrutineeringModule(new TeamsDetailScrutineeringModule(this))
                .build()
                .inject(this);
    }
}

package es.formulastudent.app.mvp.view.screen.teamsdetailfee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.adapter.TabAdapter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;


public class TeamsDetailFeeFragment extends Fragment implements TeamsDetailFeePresenter.View{

    @Inject
    TeamsDetailFeePresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    private Team team;

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
        View view = inflater.inflate(R.layout.fragment_teams_detail_fee, container, false);

        //Get selected team
        assert getArguments() != null;
        TeamsDetailFeeFragmentArgs args = TeamsDetailFeeFragmentArgs.fromBundle(getArguments());
        team = args.getSelectedTeam();

        //Remove elevation from Action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);

        initViews(view);
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerTeamsDetailFeeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .teamsDetailFeeModule(new TeamsDetailFeeModule(this))
                .build()
                .inject(this);
    }

    private void initViews(View view){
        TabAdapter tabAdapter = new TabAdapter(getParentFragmentManager(), team, presenter);
        ViewPager viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public Team getCurrentTeam() {
        return team;
    }

    @Override
    public FragmentManager getViewFragmentManager() {
        return getParentFragmentManager();
    }
}

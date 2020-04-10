package es.formulastudent.app.mvp.view.screen.conecontrolstats.tabadapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.conecontrolstats.ConeControlStatsFragment;
import es.formulastudent.app.mvp.view.screen.conecontrolstats.ConeControlStatsPresenter;

public class ConeControlStatsTabAdapter extends FragmentStatePagerAdapter {

    private ConeControlStatsPresenter presenter;
    private User loggedUser;


    public ConeControlStatsTabAdapter(FragmentManager fm, ConeControlStatsPresenter presenter, User loggedUser) {
        super(fm);
        this.presenter = presenter;
        this.loggedUser = loggedUser;
    }

    @Override
    public Fragment getItem(int i) {

        if(i == 0){
            return new ConeControlStatsFragment(presenter, RaceControlRegister.RACE_ROUND_1);
        }else if(i==1){
            return new ConeControlStatsFragment(presenter, RaceControlRegister.RACE_ROUND_2);
        }else{
            return new ConeControlStatsFragment(presenter, RaceControlRegister.RACE_ROUND_FINAL);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return "Round 1";
        }else if(position == 1){
            return "Round 2";
        }else{
            return "Final";
        }
    }
}
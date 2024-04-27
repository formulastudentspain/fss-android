package code.formulastudentspain.app.mvp.view.screen.teamsdetailfee.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import code.formulastudentspain.app.mvp.data.model.Car;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailfee.tabs.TeamsDetailFeeTabFragment;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeePresenter;

public class TabAdapter extends FragmentStatePagerAdapter {

    private Team team;
    private TeamsDetailFeePresenter presenter;

    //Fragments
    private TeamsDetailFeeTabFragment teamsDetailFeeTabFragment;


    public TabAdapter(FragmentManager fm, Team team, TeamsDetailFeePresenter presenter) {
        super(fm);
        this.team = team;
        this.presenter = presenter;
    }

    @Override
    public Fragment getItem(int i) {

        if(i == 0){
            teamsDetailFeeTabFragment = new TeamsDetailFeeTabFragment(team, presenter, 0);
            return teamsDetailFeeTabFragment;

        }else{
            teamsDetailFeeTabFragment = new TeamsDetailFeeTabFragment(team, presenter, 1);
            return teamsDetailFeeTabFragment;

        }
    }

    @Override
    public int getCount() {

        if(team.getCar().getType().equals(Car.CAR_TYPE_ELECTRIC)
                || team.getCar().getType().equals(Car.CAR_TYPE_AUTONOMOUS_ELECTRIC )){
            return 2;

        }else{
            return 1;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return "TRANSPONDER";
        }else{
            return "ENERGY METER";
        }
    }
}
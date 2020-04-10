package es.formulastudent.app.mvp.view.screen.teamsdetailfee.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.tabs.TeamsDetailFeeFragment;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeePresenter;

public class TabAdapter extends FragmentStatePagerAdapter {

    private Team team;
    private TeamsDetailFeePresenter presenter;

    //Fragments
    private TeamsDetailFeeFragment teamsDetailFeeFragment;


    public TabAdapter(FragmentManager fm, Team team, TeamsDetailFeePresenter presenter) {
        super(fm);
        this.team = team;
        this.presenter = presenter;
    }

    @Override
    public Fragment getItem(int i) {

        if(i == 0){
            teamsDetailFeeFragment = new TeamsDetailFeeFragment(team, presenter, 0);
            return teamsDetailFeeFragment;

        }else{
            teamsDetailFeeFragment = new TeamsDetailFeeFragment(team, presenter, 1);
            return teamsDetailFeeFragment;

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
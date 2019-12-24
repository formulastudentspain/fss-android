package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.tabadapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.scrutineering.TeamsDetailScrutineeringFragment;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.prescrutineering.TeamsDetailPreScrutineeringFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private Team team;
    private TeamsDetailScrutineeringPresenter presenter;

    //Fragments
    private TeamsDetailScrutineeringFragment teamsDetailScrutineeringFragment;
    private TeamsDetailPreScrutineeringFragment teamsDetailPreScrutineeringFragment;


    public TabAdapter(FragmentManager fm, Team team, TeamsDetailScrutineeringPresenter presenter) {
        super(fm);
        this.team = team;
        this.presenter = presenter;
    }

    @Override
    public Fragment getItem(int i) {

        if(i == 0){
            teamsDetailPreScrutineeringFragment = new TeamsDetailPreScrutineeringFragment(team, presenter);
            return teamsDetailPreScrutineeringFragment;

        }else{
            teamsDetailScrutineeringFragment = new TeamsDetailScrutineeringFragment(team, presenter);
            return teamsDetailScrutineeringFragment;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return "PRE SCRUTINEERING";
        }else{
            return "SCRUTINEERING";
        }
    }
}
package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.tabsadapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragments.TeamsDetailScrutineeringFragment;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragments.TeamsDetailPreScrutineeringFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private Team team;

    //Fragments
    private TeamsDetailScrutineeringFragment teamsDetailScrutineeringFragment;
    private TeamsDetailPreScrutineeringFragment teamsDetailPreScrutineeringFragment;


    public TabAdapter(FragmentManager fm, Team team) {
        super(fm);
        this.team = team;
    }

    @Override
    public Fragment getItem(int i) {

        if(i == 0){
            teamsDetailPreScrutineeringFragment = new TeamsDetailPreScrutineeringFragment(team);
            return teamsDetailPreScrutineeringFragment;

        }else{
            teamsDetailScrutineeringFragment = new TeamsDetailScrutineeringFragment(team);
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

    public void updateTeamFields(){
        teamsDetailScrutineeringFragment.updateTeamFields();
        teamsDetailPreScrutineeringFragment.updateTeamFields();
    }
}
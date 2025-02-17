package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabadapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.prescrutineering.TeamsDetailPreScrutineeringFragment;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.scrutineering.TeamsDetailScrutineeringFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private Team team;
    private TeamsDetailScrutineeringPresenter presenter;
    private User loggedUser;

    //Fragments
    private TeamsDetailScrutineeringFragment teamsDetailScrutineeringFragment;
    private TeamsDetailPreScrutineeringFragment teamsDetailPreScrutineeringFragment;


    public TabAdapter(FragmentManager fm, Team team, TeamsDetailScrutineeringPresenter presenter, User loggedUser) {
        super(fm);
        this.team = team;
        this.presenter = presenter;
        this.loggedUser = loggedUser;

    }

    @Override
    public Fragment getItem(int i) {

        if(i == 0){
            teamsDetailPreScrutineeringFragment = new TeamsDetailPreScrutineeringFragment(team, presenter, loggedUser);
            return teamsDetailPreScrutineeringFragment;

        }else{
            teamsDetailScrutineeringFragment = new TeamsDetailScrutineeringFragment(team, presenter, loggedUser);
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
package es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;

public class TeamsDetailScrutineeringFragment extends Fragment {

    private Team selectedTeam;

    public static TeamsDetailScrutineeringFragment newInstance(String param1, String param2) {
        TeamsDetailScrutineeringFragment fragment = new TeamsDetailScrutineeringFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams_detail_scrutineering, container, false);
    }
}

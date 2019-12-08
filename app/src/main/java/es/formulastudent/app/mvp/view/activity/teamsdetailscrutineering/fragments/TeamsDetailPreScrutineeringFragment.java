package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;
import info.androidhive.fontawesome.FontTextView;


public class TeamsDetailPreScrutineeringFragment extends Fragment {

    private Team team;

    //View components
    private FontTextView preScrutineeringCheckIcon;
    private EditText prescrutineeringComments;
    private Button preScrutineeringButton;

    public TeamsDetailPreScrutineeringFragment(Team team) {
        this.team = team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_scrutineering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        preScrutineeringCheckIcon = view.findViewById(R.id.pre_scrutineering_check);
        prescrutineeringComments = view.findViewById(R.id.pre_scrutineering_comments);
        preScrutineeringButton = view.findViewById(R.id.pre_scrutineering_button);

        loadData();

    }

    private void loadData(){

        //PS: Icon check, Button and Comments
        if(team.getScrutineeringPS() == null){
            preScrutineeringCheckIcon.setVisibility(View.INVISIBLE);
            preScrutineeringButton.setVisibility(View.VISIBLE);

        }else{
            preScrutineeringCheckIcon.setVisibility(View.VISIBLE);
            preScrutineeringButton.setVisibility(View.INVISIBLE);
        }
        prescrutineeringComments.setText(team.getScrutineeringPSComment());
    }

    public void updateTeamFields() {
    }
}

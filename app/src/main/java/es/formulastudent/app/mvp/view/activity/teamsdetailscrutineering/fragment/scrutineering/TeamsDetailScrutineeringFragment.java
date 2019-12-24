package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.scrutineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.dialog.ConfirmPassTestDialog;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.TeamsDetailFragment;
import info.androidhive.fontawesome.FontTextView;


public class TeamsDetailScrutineeringFragment extends Fragment implements View.OnClickListener, TeamsDetailFragment {

    private Team team;

    //Presenter
    TeamsDetailScrutineeringPresenter presenter;

    //View components
    //AI
    private FontTextView aiCheckIcon;
    private EditText aiComments;
    private Button aiButton;

    //EI
    private FontTextView eiCheckIcon;
    private EditText eiComments;
    private Button eiButton;

    //MI
    private FontTextView miCheckIcon;
    private EditText miComments;
    private Button miButton;

    //TTT
    private FontTextView tttCheckIcon;
    private EditText tttComments;
    private Button tttButton;

    //NT
    private FontTextView ntCheckIcon;
    private EditText ntComments;
    private Button ntButton;

    //RT
    private FontTextView rtCheckIcon;
    private EditText rtComments;
    private Button rtButton;

    //BT
    private FontTextView btCheckIcon;
    private EditText btComments;
    private Button btButton;


    public TeamsDetailScrutineeringFragment(Team team, TeamsDetailScrutineeringPresenter presenter) {
        this.team = team;
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scrutineering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //AI
        aiCheckIcon = view.findViewById(R.id.ai_check);
        aiComments = view.findViewById(R.id.ai_comments);
        aiButton = view.findViewById(R.id.ai_button);

        //EI
        eiCheckIcon = view.findViewById(R.id.ei_check);
        eiComments = view.findViewById(R.id.ei_comments);
        eiButton = view.findViewById(R.id.ei_button);

        //MI
        miCheckIcon = view.findViewById(R.id.mi_check);
        miComments = view.findViewById(R.id.mi_comments);
        miButton = view.findViewById(R.id.mi_button);
        miButton.setOnClickListener(this);

        //TTT
        tttCheckIcon = view.findViewById(R.id.ttt_check);
        tttComments = view.findViewById(R.id.ttt_comments);
        tttButton = view.findViewById(R.id.ttt_button);

        //NT
        ntCheckIcon = view.findViewById(R.id.nt_check);
        ntComments = view.findViewById(R.id.nt_comments);
        ntButton = view.findViewById(R.id.nt_button);

        //RT
        rtCheckIcon = view.findViewById(R.id.rt_check);
        rtComments = view.findViewById(R.id.rt_comments);
        rtButton = view.findViewById(R.id.rt_button);

        //BT
        btCheckIcon = view.findViewById(R.id.bt_check);
        btComments = view.findViewById(R.id.bt_comments);
        btButton = view.findViewById(R.id.bt_button);

        loadData();

    }

    private void loadData() {

        //AI: Icon check, Button and Comments
        if(team.getScrutineeringAI() == null){
            aiCheckIcon.setVisibility(View.INVISIBLE);
            aiButton.setVisibility(View.VISIBLE);
            aiButton.setOnClickListener(this);
            aiComments.setEnabled(true);

        }else{
            aiCheckIcon.setVisibility(View.VISIBLE);
            aiButton.setVisibility(View.INVISIBLE);
            aiComments.setEnabled(false);

        }
        aiComments.setText(team.getScrutineeringAIComment());


        //EI: Icon check, Button and Comments
        if(team.getScrutineeringEI() == null){
            eiCheckIcon.setVisibility(View.INVISIBLE);
            eiButton.setVisibility(View.VISIBLE);
            eiButton.setOnClickListener(this);
            eiComments.setEnabled(true);

        }else{
            eiCheckIcon.setVisibility(View.VISIBLE);
            eiButton.setVisibility(View.INVISIBLE);
            eiComments.setEnabled(false);
        }
        eiComments.setText(team.getScrutineeringEIComment());


        //MI: Icon check, Button and Comments
        if(team.getScrutineeringMI() == null){
            miCheckIcon.setVisibility(View.INVISIBLE);
            miButton.setVisibility(View.VISIBLE);
            miButton.setOnClickListener(this);
            miComments.setEnabled(true);

        }else{
            miCheckIcon.setVisibility(View.VISIBLE);
            miButton.setVisibility(View.INVISIBLE);
            miComments.setEnabled(false);
        }
        miComments.setText(team.getScrutineeringMIComment());


        //TTT: Icon check, Button and Comments
        if(team.getScrutineeringTTT() == null){
            tttCheckIcon.setVisibility(View.INVISIBLE);
            tttButton.setVisibility(View.VISIBLE);
            tttButton.setOnClickListener(this);
            tttComments.setEnabled(true);

        }else{
            tttCheckIcon.setVisibility(View.VISIBLE);
            tttButton.setVisibility(View.INVISIBLE);
            tttComments.setEnabled(false);
        }
        tttComments.setText(team.getScrutineeringTTTComment());


        //NT: Icon check, Button and Comments
        if(team.getScrutineeringNT() == null){
            ntCheckIcon.setVisibility(View.INVISIBLE);
            ntButton.setVisibility(View.VISIBLE);
            ntButton.setOnClickListener(this);
            ntComments.setEnabled(true);

        }else{
            ntCheckIcon.setVisibility(View.VISIBLE);
            ntButton.setVisibility(View.INVISIBLE);
            ntComments.setEnabled(false);
        }
        ntComments.setText(team.getScrutineeringNTComment());


        //RT: Icon check, Button and Comments
        if(team.getScrutineeringRT() == null){
            rtCheckIcon.setVisibility(View.INVISIBLE);
            rtButton.setVisibility(View.VISIBLE);
            rtButton.setOnClickListener(this);
            rtComments.setEnabled(true);

        }else{
            rtCheckIcon.setVisibility(View.VISIBLE);
            rtButton.setVisibility(View.INVISIBLE);
            rtComments.setEnabled(false);
        }
        rtComments.setText(team.getScrutineeringRTComment());


        //BT: Icon check, Button and Comments
        if(team.getScrutineeringBT() == null){
            btCheckIcon.setVisibility(View.INVISIBLE);
            btButton.setVisibility(View.VISIBLE);
            btButton.setOnClickListener(this);
            btComments.setEnabled(true);

        }else{
            btCheckIcon.setVisibility(View.VISIBLE);
            btButton.setVisibility(View.INVISIBLE);
            btComments.setEnabled(false);
        }
        btComments.setText(team.getScrutineeringBTComment());

    }

    @Override
    public void onClick(View view) {

        //Clone team and update it
        Team modifiedTeam = team.clone();

        if(view.getId() == R.id.ai_button){
            modifiedTeam.setScrutineeringAI(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringAIComment(aiComments.getText().toString());

        }else if(view.getId() == R.id.ei_button){
            modifiedTeam.setScrutineeringEI(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringEIComment(eiComments.getText().toString());

        }else if(view.getId() == R.id.mi_button){
            modifiedTeam.setScrutineeringMI(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringMIComment(miComments.getText().toString());

        }else if(view.getId() == R.id.ttt_button){
            modifiedTeam.setScrutineeringTTT(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringTTTComment(tttComments.getText().toString());

        }else if(view.getId() == R.id.nt_button){
            modifiedTeam.setScrutineeringNT(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringNTComment(aiComments.getText().toString());

        }else if(view.getId() == R.id.rt_button){
            modifiedTeam.setScrutineeringRT(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringRTComment(aiComments.getText().toString());

        }else if(view.getId() == R.id.bt_button){
            modifiedTeam.setScrutineeringBT(Calendar.getInstance().getTime());
            modifiedTeam.setScrutineeringBTComment(aiComments.getText().toString());
        }

        //Open confirm dialog
        FragmentManager fm = getFragmentManager();
        ConfirmPassTestDialog confirmPassTestDialog = ConfirmPassTestDialog
                .newInstance(presenter, team, modifiedTeam);
        confirmPassTestDialog.show(fm, "confirmPassTestDialog");
    }

    @Override
    public void updateView(Team team) {

        //Update the team object
        this.team = team;

        //Reload data
        loadData();

    }
}

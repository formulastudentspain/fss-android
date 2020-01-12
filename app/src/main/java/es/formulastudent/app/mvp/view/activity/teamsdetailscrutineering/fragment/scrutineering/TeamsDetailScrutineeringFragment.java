package es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.scrutineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.ScrutineeringTest;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.dialog.AddCommentDialog;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.dialog.ConfirmPassTestDialog;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.fragment.TeamsDetailFragment;
import info.androidhive.fontawesome.FontTextView;


public class TeamsDetailScrutineeringFragment extends Fragment implements View.OnClickListener, TeamsDetailFragment {

    private Team team;

    //Presenter
    TeamsDetailScrutineeringPresenter presenter;

    //View components
    private LinearLayout aiContainer;
    private LinearLayout eiContainer;
    private LinearLayout miContainer;
    private LinearLayout tttContainer;
    private LinearLayout ntContainer;
    private LinearLayout rtContainer;
    private LinearLayout btContainer;

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

        //Containers
        aiContainer = view.findViewById(R.id.ai_container);
        eiContainer = view.findViewById(R.id.ei_container);
        miContainer = view.findViewById(R.id.mi_container);
        tttContainer = view.findViewById(R.id.ttt_container);
        ntContainer = view.findViewById(R.id.nt_container);
        rtContainer = view.findViewById(R.id.rt_container);
        btContainer = view.findViewById(R.id.bt_container);

        //AI
        aiCheckIcon = view.findViewById(R.id.ai_check);
        aiComments = view.findViewById(R.id.ai_comments);
        aiComments.setOnClickListener(this);
        aiButton = view.findViewById(R.id.ai_button);

        //EI
        eiCheckIcon = view.findViewById(R.id.ei_check);
        eiComments = view.findViewById(R.id.ei_comments);
        eiComments.setOnClickListener(this);
        eiButton = view.findViewById(R.id.ei_button);

        //MI
        miCheckIcon = view.findViewById(R.id.mi_check);
        miComments = view.findViewById(R.id.mi_comments);
        miComments.setOnClickListener(this);
        miButton = view.findViewById(R.id.mi_button);
        miButton.setOnClickListener(this);

        //TTT
        tttCheckIcon = view.findViewById(R.id.ttt_check);
        tttComments = view.findViewById(R.id.ttt_comments);
        tttComments.setOnClickListener(this);
        tttButton = view.findViewById(R.id.ttt_button);

        //NT
        ntCheckIcon = view.findViewById(R.id.nt_check);
        ntComments = view.findViewById(R.id.nt_comments);
        ntComments.setOnClickListener(this);
        ntButton = view.findViewById(R.id.nt_button);

        //RT
        rtCheckIcon = view.findViewById(R.id.rt_check);
        rtComments = view.findViewById(R.id.rt_comments);
        rtComments.setOnClickListener(this);
        rtButton = view.findViewById(R.id.rt_button);

        //BT
        btCheckIcon = view.findViewById(R.id.bt_check);
        btComments = view.findViewById(R.id.bt_comments);
        btComments.setOnClickListener(this);
        btButton = view.findViewById(R.id.bt_button);

        loadData();

    }

    private void loadData() {

        List<ScrutineeringTest> tests = team.getTests();

        if(tests.contains(ScrutineeringTest.ACCUMULATION_INSPECTION)){
            aiContainer.setVisibility(View.VISIBLE);
        }else{
            aiContainer.setVisibility(View.GONE);
        }

        if(tests.contains(ScrutineeringTest.ELECTRICAL_INSPECTION)){
            eiContainer.setVisibility(View.VISIBLE);
        }else{
            eiContainer.setVisibility(View.GONE);
        }

        if(tests.contains(ScrutineeringTest.MECHANICAL_INSPECTION)){
            miContainer.setVisibility(View.VISIBLE);
        }else{
            miContainer.setVisibility(View.GONE);
        }

        if(tests.contains(ScrutineeringTest.TILT_TABLE_TEST)){
            tttContainer.setVisibility(View.VISIBLE);
        }else{
            tttContainer.setVisibility(View.GONE);
        }

        if(tests.contains(ScrutineeringTest.RAIN_TEST)){
            rtContainer.setVisibility(View.VISIBLE);
        }else{
            rtContainer.setVisibility(View.GONE);
        }

        if(tests.contains(ScrutineeringTest.NOISE_TEST)){
            ntContainer.setVisibility(View.VISIBLE);
        }else{
            ntContainer.setVisibility(View.GONE);
        }

        if(tests.contains(ScrutineeringTest.BRAKE_TEST)){
            btContainer.setVisibility(View.VISIBLE);
        }else{
            btContainer.setVisibility(View.GONE);
        }

        //AI: Icon check, Button and Comments
        if(team.getScrutineeringAI() == null){
            aiCheckIcon.setVisibility(View.INVISIBLE);
            aiButton.setVisibility(View.VISIBLE);
            aiButton.setOnClickListener(this);

        }else{
            aiCheckIcon.setVisibility(View.VISIBLE);
            aiButton.setVisibility(View.INVISIBLE);

        }
        aiComments.setText(team.getScrutineeringAIComment());


        //EI: Icon check, Button and Comments
        if(team.getScrutineeringEI() == null){
            eiCheckIcon.setVisibility(View.INVISIBLE);
            eiButton.setVisibility(View.VISIBLE);
            eiButton.setOnClickListener(this);

        }else{
            eiCheckIcon.setVisibility(View.VISIBLE);
            eiButton.setVisibility(View.INVISIBLE);
        }
        eiComments.setText(team.getScrutineeringEIComment());


        //MI: Icon check, Button and Comments
        if(team.getScrutineeringMI() == null){
            miCheckIcon.setVisibility(View.INVISIBLE);
            miButton.setVisibility(View.VISIBLE);
            miButton.setOnClickListener(this);

        }else{
            miCheckIcon.setVisibility(View.VISIBLE);
            miButton.setVisibility(View.INVISIBLE);
        }
        miComments.setText(team.getScrutineeringMIComment());


        //TTT: Icon check, Button and Comments
        if(team.getScrutineeringTTT() == null){
            tttCheckIcon.setVisibility(View.INVISIBLE);
            tttButton.setVisibility(View.VISIBLE);
            tttButton.setOnClickListener(this);

        }else{
            tttCheckIcon.setVisibility(View.VISIBLE);
            tttButton.setVisibility(View.INVISIBLE);
        }
        tttComments.setText(team.getScrutineeringTTTComment());


        //NT: Icon check, Button and Comments
        if(team.getScrutineeringNT() == null){
            ntCheckIcon.setVisibility(View.INVISIBLE);
            ntButton.setVisibility(View.VISIBLE);
            ntButton.setOnClickListener(this);

        }else{
            ntCheckIcon.setVisibility(View.VISIBLE);
            ntButton.setVisibility(View.INVISIBLE);
        }
        ntComments.setText(team.getScrutineeringNTComment());


        //RT: Icon check, Button and Comments
        if(team.getScrutineeringRT() == null){
            rtCheckIcon.setVisibility(View.INVISIBLE);
            rtButton.setVisibility(View.VISIBLE);
            rtButton.setOnClickListener(this);

        }else{
            rtCheckIcon.setVisibility(View.VISIBLE);
            rtButton.setVisibility(View.INVISIBLE);
        }
        rtComments.setText(team.getScrutineeringRTComment());


        //BT: Icon check, Button and Comments
        if(team.getScrutineeringBT() == null){
            btCheckIcon.setVisibility(View.INVISIBLE);
            btButton.setVisibility(View.VISIBLE);
            btButton.setOnClickListener(this);

        }else{
            btCheckIcon.setVisibility(View.VISIBLE);
            btButton.setVisibility(View.INVISIBLE);
        }
        btComments.setText(team.getScrutineeringBTComment());

    }

    @Override
    public void onClick(View view) {

        //Clone team and update it
        Team modifiedTeam = team.clone();

        switch (view.getId()){
            case R.id.ai_button:
                modifiedTeam.setScrutineeringAI(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringAIComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.ei_button:
                modifiedTeam.setScrutineeringEI(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringEIComment(eiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.mi_button:
                modifiedTeam.setScrutineeringMI(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringMIComment(miComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.ttt_button:
                modifiedTeam.setScrutineeringTTT(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringTTTComment(tttComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.nt_button:
                modifiedTeam.setScrutineeringNT(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringNTComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.rt_button:
                modifiedTeam.setScrutineeringRT(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringRTComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.bt_button:
                modifiedTeam.setScrutineeringBT(Calendar.getInstance().getTime());
                modifiedTeam.setScrutineeringBTComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.ai_comments:
                openAddCommentDialog(R.id.ai_comments);
                break;

            case R.id.bt_comments:
                openAddCommentDialog(R.id.bt_comments);
                break;

            case R.id.ei_comments:
                openAddCommentDialog(R.id.ei_comments);
                break;

            case R.id.mi_comments:
                openAddCommentDialog(R.id.mi_comments);
                break;

            case R.id.rt_comments:
                openAddCommentDialog(R.id.rt_comments);
                break;

            case R.id.ttt_comments:
                openAddCommentDialog(R.id.ttt_comments);
                break;
        }
    }

    private void openConfirmPassTestDialog(Team modifiedTeam){
        FragmentManager fm = getFragmentManager();
        ConfirmPassTestDialog confirmPassTestDialog = ConfirmPassTestDialog
                .newInstance(presenter, team, modifiedTeam);
        confirmPassTestDialog.show(fm, "confirmPassTestDialog");
    }

    private void openAddCommentDialog(int viewClicked){
        FragmentManager fm = getFragmentManager();
        AddCommentDialog addCommentDialog = AddCommentDialog.newInstance(presenter, team, viewClicked);
        addCommentDialog.show(fm, "addCommentDialog");
    }

    @Override
    public void updateView(Team team) {

        //Update the team object
        this.team = team;

        //Reload data
        loadData();

    }
}

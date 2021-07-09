package es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.tabs.scrutineering;

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

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.ScrutineeringTest;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.dialog.AddCommentDialog;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.dialog.ConfirmPassTestDialog;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.tabs.TeamsDetailFragment;
import es.formulastudent.app.mvp.view.utils.messages.Message;
import info.androidhive.fontawesome.FontTextView;


public class TeamsDetailScrutineeringFragment extends Fragment implements View.OnClickListener, TeamsDetailFragment, View.OnLongClickListener {

    private Team team;
    private User loggedUser;

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


    public TeamsDetailScrutineeringFragment(Team team, TeamsDetailScrutineeringPresenter presenter, User loggedUser) {
        this.team = team;
        this.presenter = presenter;
        this.loggedUser = loggedUser;
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
        aiCheckIcon.setOnLongClickListener(this);
        aiComments = view.findViewById(R.id.ai_comments);
        aiComments.setOnClickListener(this);
        aiButton = view.findViewById(R.id.ai_button);

        //EI
        eiCheckIcon = view.findViewById(R.id.ei_check);
        eiCheckIcon.setOnLongClickListener(this);
        eiComments = view.findViewById(R.id.ei_comments);
        eiComments.setOnClickListener(this);
        eiButton = view.findViewById(R.id.ei_button);

        //MI
        miCheckIcon = view.findViewById(R.id.mi_check);
        miCheckIcon.setOnLongClickListener(this);
        miComments = view.findViewById(R.id.mi_comments);
        miComments.setOnClickListener(this);
        miButton = view.findViewById(R.id.mi_button);
        miButton.setOnClickListener(this);

        //TTT
        tttCheckIcon = view.findViewById(R.id.ttt_check);
        tttCheckIcon.setOnLongClickListener(this);
        tttComments = view.findViewById(R.id.ttt_comments);
        tttComments.setOnClickListener(this);
        tttButton = view.findViewById(R.id.ttt_button);

        //NT
        ntCheckIcon = view.findViewById(R.id.nt_check);
        ntCheckIcon.setOnLongClickListener(this);
        ntComments = view.findViewById(R.id.nt_comments);
        ntComments.setOnClickListener(this);
        ntButton = view.findViewById(R.id.nt_button);

        //RT
        rtCheckIcon = view.findViewById(R.id.rt_check);
        rtCheckIcon.setOnLongClickListener(this);
        rtComments = view.findViewById(R.id.rt_comments);
        rtComments.setOnClickListener(this);
        rtButton = view.findViewById(R.id.rt_button);

        //BT
        btCheckIcon = view.findViewById(R.id.bt_check);
        btCheckIcon.setOnLongClickListener(this);
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
        if(!team.getScrutineeringAI()){
            aiCheckIcon.setVisibility(View.INVISIBLE);
            aiButton.setVisibility(View.VISIBLE);
            aiButton.setOnClickListener(this);

        }else{
            aiCheckIcon.setVisibility(View.VISIBLE);
            aiButton.setVisibility(View.INVISIBLE);

        }
        aiComments.setText(team.getScrutineeringAIComment());


        //EI: Icon check, Button and Comments
        if(!team.getScrutineeringEI()){
            eiCheckIcon.setVisibility(View.INVISIBLE);
            eiButton.setVisibility(View.VISIBLE);
            eiButton.setOnClickListener(this);

        }else{
            eiCheckIcon.setVisibility(View.VISIBLE);
            eiButton.setVisibility(View.INVISIBLE);
        }
        eiComments.setText(team.getScrutineeringEIComment());


        //MI: Icon check, Button and Comments
        if(!team.getScrutineeringMI()){
            miCheckIcon.setVisibility(View.INVISIBLE);
            miButton.setVisibility(View.VISIBLE);
            miButton.setOnClickListener(this);

        }else{
            miCheckIcon.setVisibility(View.VISIBLE);
            miButton.setVisibility(View.INVISIBLE);
        }
        miComments.setText(team.getScrutineeringMIComment());


        //TTT: Icon check, Button and Comments
        if(!team.getScrutineeringTTT()){
            tttCheckIcon.setVisibility(View.INVISIBLE);
            tttButton.setVisibility(View.VISIBLE);
            tttButton.setOnClickListener(this);

        }else{
            tttCheckIcon.setVisibility(View.VISIBLE);
            tttButton.setVisibility(View.INVISIBLE);
        }
        tttComments.setText(team.getScrutineeringTTTComment());


        //NT: Icon check, Button and Comments
        if(!team.getScrutineeringNT()){
            ntCheckIcon.setVisibility(View.INVISIBLE);
            ntButton.setVisibility(View.VISIBLE);
            ntButton.setOnClickListener(this);

        }else{
            ntCheckIcon.setVisibility(View.VISIBLE);
            ntButton.setVisibility(View.INVISIBLE);
        }
        ntComments.setText(team.getScrutineeringNTComment());


        //RT: Icon check, Button and Comments
        if(!team.getScrutineeringRT()){
            rtCheckIcon.setVisibility(View.INVISIBLE);
            rtButton.setVisibility(View.VISIBLE);
            rtButton.setOnClickListener(this);

        }else{
            rtCheckIcon.setVisibility(View.VISIBLE);
            rtButton.setVisibility(View.INVISIBLE);
        }
        rtComments.setText(team.getScrutineeringRTComment());


        //BT: Icon check, Button and Comments
        if(!team.getScrutineeringBT()){
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
                modifiedTeam.setScrutineeringAI(true);
                modifiedTeam.setScrutineeringAIComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.ei_button:
                modifiedTeam.setScrutineeringEI(true);
                modifiedTeam.setScrutineeringEIComment(eiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.mi_button:
                modifiedTeam.setScrutineeringMI(true);
                modifiedTeam.setScrutineeringMIComment(miComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.ttt_button:
                modifiedTeam.setScrutineeringTTT(true);
                modifiedTeam.setScrutineeringTTTComment(tttComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.nt_button:
                modifiedTeam.setScrutineeringNT(true);
                modifiedTeam.setScrutineeringNTComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.rt_button:
                modifiedTeam.setScrutineeringRT(true);
                modifiedTeam.setScrutineeringRTComment(aiComments.getText().toString());
                openConfirmPassTestDialog(modifiedTeam);
                break;

            case R.id.bt_button:
                modifiedTeam.setScrutineeringBT(true);
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

    @Override
    public boolean onLongClick(View view) {

        if(this.loggedUser.getRole().equals(UserRole.ADMINISTRATOR)
                || this.loggedUser.isRole(UserRole.OFFICIAL_SCRUTINEER)){

            //Clone team and update it
            Team modifiedTeam = team.clone();

            switch (view.getId()) {
                case R.id.ai_check:
                    modifiedTeam.setScrutineeringAI(false);
                    break;
                case R.id.ei_check:
                    modifiedTeam.setScrutineeringEI(false);
                    break;
                case R.id.mi_check:
                    modifiedTeam.setScrutineeringMI(false);
                    break;
                case R.id.ttt_check:
                    modifiedTeam.setScrutineeringTTT(false);
                    break;
                case R.id.nt_check:
                    modifiedTeam.setScrutineeringNT(false);
                    break;
                case R.id.rt_check:
                    modifiedTeam.setScrutineeringRT(false);
                    break;
                case R.id.bt_check:
                    modifiedTeam.setScrutineeringBT(false);
                    break;

            }

            presenter.updateTeam(modifiedTeam, team);
            return false;

        }

        presenter.setErrorToDisplay(new Message(R.string.forbidden_required_role, UserRole.OFFICIAL_SCRUTINEER.getName()));
        return false;
    }
}

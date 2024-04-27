package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;

public class AddCommentDialog extends DialogFragment{

    private AlertDialog dialog;

    //View elements
    private EditText commentEditText;

    //Data
    private Team team;
    private int viewClicked;

    //Presenter
    private TeamsDetailScrutineeringPresenter presenter;

    public AddCommentDialog() {}

    public static AddCommentDialog newInstance(TeamsDetailScrutineeringPresenter presenter, Team team, int viewClicked) {
        AddCommentDialog frag = new AddCommentDialog();
        frag.setPresenter(presenter);
        frag.setTeam(team);
        frag.setViewClicked(viewClicked);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_scrutineering_add_comment, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                    .setTitle(R.string.teams_dialog_add_comment_title)
                    .setPositiveButton(R.string.teams_dialog_add_comment_add,null)
                    .setNegativeButton(R.string.teams_dialog_add_comment_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AddCommentDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {

        String comment = "";

        if(viewClicked == R.id.ai_comments){
            comment = team.getScrutineeringAIComment();

        }else if(viewClicked == R.id.bt_comments){
            comment = team.getScrutineeringBTComment();

        }else if(viewClicked == R.id.ei_comments){
            comment = team.getScrutineeringEIComment();

        }else if(viewClicked == R.id.mi_comments){
            comment = team.getScrutineeringMIComment();

        }else if(viewClicked == R.id.nt_comments){
            comment = team.getScrutineeringNTComment();

        }else if(viewClicked == R.id.rt_comments){
            comment = team.getScrutineeringRTComment();

        }else if(viewClicked == R.id.ttt_comments){
            comment = team.getScrutineeringTTTComment();

        }else if(viewClicked == R.id.pre_scrutineering_comments){
            comment = team.getScrutineeringPSComment();

        }

        //Car number
        commentEditText.setText(comment);
    }

    private void initializeElements(View rootView){

        //Comment
        commentEditText = rootView.findViewById(R.id.comment);
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Team teamToUpdate = team.clone();

                if(viewClicked == R.id.ai_comments){
                    teamToUpdate.setScrutineeringAIComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.bt_comments){
                    teamToUpdate.setScrutineeringBTComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.ei_comments){
                    teamToUpdate.setScrutineeringEIComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.mi_comments){
                    teamToUpdate.setScrutineeringMIComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.nt_comments){
                    teamToUpdate.setScrutineeringNTComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.rt_comments){
                    teamToUpdate.setScrutineeringRTComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.ttt_comments){
                    teamToUpdate.setScrutineeringTTTComment(commentEditText.getText().toString());

                }else if(viewClicked == R.id.pre_scrutineering_comments){
                    teamToUpdate.setScrutineeringPSComment(commentEditText.getText().toString());

                }

                presenter.updateTeam(teamToUpdate, team);
                dialog.dismiss();
            }
        });
    }

    public void setPresenter(TeamsDetailScrutineeringPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setViewClicked(int viewClicked) {
        this.viewClicked = viewClicked;
    }
}
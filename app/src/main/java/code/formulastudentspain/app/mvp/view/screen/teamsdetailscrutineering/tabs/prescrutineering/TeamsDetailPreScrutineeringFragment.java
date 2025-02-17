package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.prescrutineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.PreScrutineeringRegister;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.data.model.UserRole;
import code.formulastudentspain.app.mvp.view.screen.NFCReaderActivity;
import code.formulastudentspain.app.mvp.view.screen.egresschrono.EgressChronoActivity;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.dialog.AddCommentDialog;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.dialog.ConfirmPassTestDialog;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.TeamsDetailFragment;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.prescrutineering.recyclerview.EgressRegistersAdapter;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;
import info.androidhive.fontawesome.FontTextView;


public class TeamsDetailPreScrutineeringFragment extends Fragment implements View.OnClickListener, TeamsDetailFragment, SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener, View.OnLongClickListener {

    private static final int NFC_REQUEST_CODE = 101;
    private static final int CHRONO_CODE = 102;

    private Team team;
    private User loggedUser;

    //Presenter
    private TeamsDetailScrutineeringPresenter presenter;

    //View components
    private FontTextView preScrutineeringCheckIcon;
    private EditText prescrutineeringComments;
    private Button preScrutineeringButton;
    private FloatingActionButton addEgressRegisterButton;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EgressRegistersAdapter registersAdapter;



    public TeamsDetailPreScrutineeringFragment(Team team, TeamsDetailScrutineeringPresenter presenter, User loggedUser) {
        this.team = team;
        this.presenter = presenter;
        this.loggedUser = loggedUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_scrutineering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        preScrutineeringCheckIcon = view.findViewById(R.id.pre_scrutineering_check);
        preScrutineeringCheckIcon.setOnLongClickListener(this);
        prescrutineeringComments = view.findViewById(R.id.pre_scrutineering_comments);
        prescrutineeringComments.setOnClickListener(this);
        preScrutineeringButton = view.findViewById(R.id.pre_scrutineering_button);
        preScrutineeringButton.setOnClickListener(this);
        addEgressRegisterButton = view.findViewById(R.id.button_add_egress_register);
        addEgressRegisterButton.setOnClickListener(this);

        //Recycler view
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));
        recyclerView = view.findViewById(R.id.recyclerView);
        registersAdapter = new EgressRegistersAdapter(presenter.getEventRegisterList(), getContext(), this);
        recyclerView.setAdapter(registersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        presenter.retrieveEgressRegisterList();
        loadData();

    }

    private void loadData() {

        //PS: Icon check, Button and Comments
        if (!team.getScrutineeringPS()) {
            preScrutineeringCheckIcon.setVisibility(View.INVISIBLE);
            preScrutineeringButton.setVisibility(View.VISIBLE);

        } else {
            preScrutineeringCheckIcon.setVisibility(View.VISIBLE);
            preScrutineeringButton.setVisibility(View.INVISIBLE);
        }
        prescrutineeringComments.setText(team.getScrutineeringPSComment());
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.pre_scrutineering_button) {

            //Clone team and update it
            Team modifiedTeam = team.clone();
            modifiedTeam.setScrutineeringPS(true);
            modifiedTeam.setScrutineeringPSComment(prescrutineeringComments.getText().toString());

            //Open confirm dialog
            ConfirmPassTestDialog confirmPassTestDialog = ConfirmPassTestDialog.newInstance(presenter, team, modifiedTeam);
            confirmPassTestDialog.show(getFragmentManager(), "confirmPassTestDialog");

        }else if(view.getId() == R.id.button_add_egress_register){
            Intent i = new Intent(getActivity(), NFCReaderActivity.class);
            startActivityForResult(i, NFC_REQUEST_CODE);

        }else if(view.getId() == R.id.pre_scrutineering_comments){

            //Open add comment dialog
            AddCommentDialog addCommentDialog = AddCommentDialog.newInstance(presenter, team, R.id.pre_scrutineering_comments);
            addCommentDialog.show(getFragmentManager(), "addCommentDialog");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //NFC reader
        if (requestCode == NFC_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                presenter.onNFCTagDetected(result, team);
            }

            //Chronometer result for Egress
        }else if(requestCode == CHRONO_CODE){
            if(resultCode == Activity.RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra("result");
                Long milliSeconds = Long.parseLong(result.get(0));
                String registerID = result.get(1);
                presenter.onChronoTimeRegistered(milliSeconds, registerID);
            }
        }
    }

    @Override
    public void updateView(Team team) {

        //Update the team object
        this.team = team;

        //Reload data
        loadData();
    }


    @Override
    public void onRefresh() {
        presenter.retrieveEgressRegisterList();
    }

    public void refreshListItems(){
        registersAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        PreScrutineeringRegister selectedRegister = (PreScrutineeringRegister) presenter.getEventRegisterList().get(position);
        Intent intent = new Intent(getContext(), EgressChronoActivity.class);
        intent.putExtra("prescrutineering_register", selectedRegister);
        startActivityForResult(intent, CHRONO_CODE);
    }

    @Override
    public boolean onLongClick(View view) {
        if(this.loggedUser.isAdministrator() || this.loggedUser.isRole(UserRole.OFFICIAL_SCRUTINEER)){
            Team modifiedTeam = team.clone();
            modifiedTeam.setScrutineeringPS(false);
            presenter.updateTeam(modifiedTeam, team);
            return false;
        }

        presenter.setErrorToDisplay(new Message(R.string.forbidden_required_role, UserRole.OFFICIAL_SCRUTINEER.getName()));
        return false;
    }
}

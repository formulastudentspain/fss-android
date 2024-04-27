package code.formulastudentspain.app.mvp.view.screen.briefing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerBriefingComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.BriefingModule;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.NFCReaderActivity;
import code.formulastudentspain.app.mvp.view.screen.briefing.recyclerview.BriefingRegistersAdapter;
import code.formulastudentspain.app.mvp.view.screen.general.spinneradapters.TeamsSpinnerAdapter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


public class BriefingFragment extends Fragment implements ChipGroup.OnCheckedChangeListener,
        BriefingPresenter.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    @Inject
    BriefingPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    Messages messages;

    private BriefingRegistersAdapter registersAdapter;
    private TeamsSpinnerAdapter teamsAdapter;
    private FloatingActionButton buttonAddRegister;
    private Spinner teamsSpinner;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        //Observer for loading dialog
        presenter.getLoadingData().observe(this, loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        //Observer to display errors
        presenter.getErrorToDisplay().observe(this, message ->
                messages.showError(message.getStringID(), message.getArgs()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_briefing, container, false);

        initViews(view);
        return view;
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerBriefingComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .briefingModule(new BriefingModule(this))
                .build()
                .inject(this);
    }

    private void initViews(View view){
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        registersAdapter = new BriefingRegistersAdapter(presenter.getBriefingRegisterList(), getContext(), presenter);
        recyclerView.setAdapter(registersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && buttonAddRegister.isShown())
                    buttonAddRegister.hide();
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    buttonAddRegister.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //Add briefing register button
        buttonAddRegister = view.findViewById(R.id.button_add_briefing_register);
        buttonAddRegister.setOnClickListener(this);

        //Teams Spinner
        teamsSpinner = view.findViewById(R.id.briefing_team_spinner);
        presenter.retrieveTeams();

        //Chip Group
        ChipGroup dayListGroup = view.findViewById(R.id.briefing_chip_group);
        dayListGroup.setOnCheckedChangeListener(this);
    }

    public void initializeTeamsSpinner(List<Team> teams){
        teamsAdapter = new TeamsSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, teams);
        teamsSpinner.setAdapter(teamsAdapter);
        teamsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Team team = teamsAdapter.getItem(position);
                if(team != null){
                    presenter.setSelectedTeamID(team.getID());
                    presenter.retrieveBriefingRegisterList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }


    @Override
    public void refreshBriefingRegisterItems() {
        mSwipeRefreshLayout.setRefreshing(false);
        registersAdapter.notifyDataSetChanged();
    }

    private ActivityResultLauncher<Intent> NFCReaderResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    int a = 0;
                    //presenter.onNFCTagDetected(data);
                }
            });

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_briefing_register){
            Intent intent = new Intent(getContext(), NFCReaderActivity.class);
            NFCReaderResultLauncher.launch(intent);
        }
    }

    @Override
    public void onCheckedChanged(ChipGroup chipGroup, int selectedChipId) {

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String competitionDayStr = null;

        if(selectedChipId == R.id.briefing_chip_wed){
            competitionDayStr = getString(R.string.competition_day_wed);
        }else if(selectedChipId == R.id.briefing_chip_thu){
            competitionDayStr = getString(R.string.competition_day_thu);
        }else if(selectedChipId == R.id.briefing_chip_fri){
            competitionDayStr = getString(R.string.competition_day_fri);
        }else if(selectedChipId == R.id.briefing_chip_sat){
            competitionDayStr = getString(R.string.competition_day_sat);
        }else if(selectedChipId == R.id.briefing_chip_sun){
            competitionDayStr = getString(R.string.competition_day_sun);
        }else{ //all
            presenter.setSelectedDateFrom(null);
            presenter.setSelectedDateTo(null);
        }

        //Get From and To dates
        if(competitionDayStr != null){

            try{

                Date competitionDate = sdf.parse(competitionDayStr);

                //From
                Calendar calFrom = Calendar.getInstance();
                calFrom.setTime(competitionDate);
                calFrom.set(Calendar.HOUR_OF_DAY, 0);
                calFrom.set(Calendar.MINUTE, 0);
                calFrom.set(Calendar.SECOND, 0);

                //To
                Calendar calTo = Calendar.getInstance();
                calTo.setTime(competitionDate);
                calTo.set(Calendar.HOUR_OF_DAY, 23);
                calTo.set(Calendar.MINUTE, 59);
                calTo.set(Calendar.SECOND, 59);

                //Update From and To values
                presenter.setSelectedDateFrom(calFrom.getTime());
                presenter.setSelectedDateTo(calTo.getTime());

            }catch (ParseException pe){
                //TODO
            }
        }

        //Update list
        presenter.retrieveBriefingRegisterList();
    }

    @Override
    public void onRefresh() {
        presenter.retrieveBriefingRegisterList();
    }
}

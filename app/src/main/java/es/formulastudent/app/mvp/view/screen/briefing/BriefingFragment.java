package es.formulastudent.app.mvp.view.screen.briefing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

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

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerBriefingComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.BriefingModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.NFCReaderActivity;
import es.formulastudent.app.mvp.view.screen.briefing.recyclerview.BriefingRegistersAdapter;
import es.formulastudent.app.mvp.view.screen.general.spinneradapters.TeamsSpinnerAdapter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.messages.Messages;


public class BriefingFragment extends Fragment implements ChipGroup.OnCheckedChangeListener,
        BriefingPresenter.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private static final int NFC_REQUEST_CODE = 101;

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
        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    buttonAddRegister.show();
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
        registersAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_briefing_register){
            Intent i = new Intent(getContext(), NFCReaderActivity.class);
            startActivityForResult(i, NFC_REQUEST_CODE);
        }
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //NFC reader
        if (requestCode == NFC_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                presenter.onNFCTagDetected(result);
            }
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

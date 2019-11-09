package es.formulastudent.app.mvp.view.activity.racecontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.spinneradapters.TeamsSpinnerAdapter;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

public class FilteringRegistersDialog extends DialogFragment implements ChipGroup.OnCheckedChangeListener{

    private AlertDialog dialog;

    //View elements
    private Spinner teamsSpinner;
    private ChipGroup dayListGroup;
    private EditText carNumberField;
    private TeamsSpinnerAdapter teamsAdapter;


    //Data
    private List<Team> teams;
    private String selectedTeamID;
    private Long selectedCarNumber;
    private String selectedDay;
    private Date selectedDateFrom;
    private Date selectedDateTo;

    //Presenter
    private RaceControlPresenter presenter;

    public FilteringRegistersDialog() {}

    public static FilteringRegistersDialog newInstance(RaceControlPresenter presenter, List<Team> teams,
                                                       String selectedTeamID, Long selectedCarNumber, String selectedDay) {
        FilteringRegistersDialog frag = new FilteringRegistersDialog();
        frag.setTeams(teams);
        frag.setPresenter(presenter);
        frag.setSelectedTeamID(selectedTeamID);
        frag.setSelectedCarNumber(selectedCarNumber);
        frag.setSelectedDay(selectedDay);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filter_dynamic_event, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                    .setTitle(R.string.dynamic_event_filtering_dialog_title)
                    .setPositiveButton(R.string.dynamic_event_filtering_dialog_filter_button,null)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FilteringRegistersDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {

        //Teams
        if(selectedTeamID != null && selectedTeamID != "-1"){
            for(Team team: teams){
                if(team.getID().equalsIgnoreCase(selectedTeamID)){
                    int teamPosition = teamsAdapter.getPosition(team);
                    teamsSpinner.setSelection(teamPosition);

                    break;
                }
            }
        }else{
            teamsSpinner.setSelection(0);
        }

        //Car number
        if(selectedCarNumber != null){
            carNumberField.setText(selectedCarNumber.toString());
        }else{
            carNumberField.setText("");
        }

        //Chips
        if(selectedDay != null){
            if(selectedDay.equalsIgnoreCase(getString(R.string.competition_day_wed))){ //wednesday
                ((Chip)dayListGroup.getChildAt(0)).setChecked(true);
            }else if(selectedDay.equalsIgnoreCase(getString(R.string.competition_day_thu))){ //thursday
                ((Chip)dayListGroup.getChildAt(1)).setChecked(true);
            }else if(selectedDay.equalsIgnoreCase(getString(R.string.competition_day_fri))){ //friday
                ((Chip)dayListGroup.getChildAt(2)).setChecked(true);
            }else if(selectedDay.equalsIgnoreCase(getString(R.string.competition_day_sat))){ //saturday
                ((Chip)dayListGroup.getChildAt(3)).setChecked(true);
            }else if(selectedDay.equalsIgnoreCase(getString(R.string.competition_day_sun))){ //friday
                ((Chip)dayListGroup.getChildAt(4)).setChecked(true);
            }
        }else{ //all
            ((Chip)dayListGroup.getChildAt(5)).setChecked(true);
        }
    }

    private void initializeElements(View rootView){

        //Teams spinner
        teamsSpinner = rootView.findViewById(R.id.dynamic_event_team_spinner);
        teamsAdapter = new TeamsSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, teams);
        teamsSpinner.setAdapter(teamsAdapter);
        teamsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Team team = teamsAdapter.getItem(position);
                selectedTeamID = team.getID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        //Day list group
        dayListGroup = rootView.findViewById(R.id.dynamic_event_chip_group);
        dayListGroup.setOnCheckedChangeListener(this);

        //Car number
        carNumberField = rootView.findViewById(R.id.filtering_car_number);
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    //Get car number value
                    String carNumString = carNumberField.getText().toString();
                    if(carNumString.isEmpty()){
                        selectedCarNumber = null;
                    }else{
                        selectedCarNumber = Long.parseLong(carNumberField.getText().toString());
                    }

                    //Set values for filtering
                    presenter.setFilteringValues(selectedDateFrom, selectedDateTo, selectedDay, selectedTeamID, selectedCarNumber);

                    //Do filter
                    presenter.retrieveRegisterList();

                    //Close dialog
                    dialog.dismiss();

                }catch(Exception e){
                    carNumberField.setError("Invalid car number");
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(ChipGroup chipGroup, int selectedChipId) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String competitionDayStr = null;

        if(selectedChipId == R.id.dynamic_event_chip_wed){
            competitionDayStr = getString(R.string.competition_day_wed);
            selectedDay = getString(R.string.competition_day_wed);

        }else if(selectedChipId == R.id.dynamic_event_chip_thu){
            competitionDayStr = getString(R.string.competition_day_thu);
            selectedDay = getString(R.string.competition_day_thu);

        }else if(selectedChipId == R.id.dynamic_event_chip_fri){
            competitionDayStr = getString(R.string.competition_day_fri);
            selectedDay = getString(R.string.competition_day_fri);

        }else if(selectedChipId == R.id.dynamic_event_chip_sat){
            competitionDayStr = getString(R.string.competition_day_sat);
            selectedDay = getString(R.string.competition_day_sat);

        }else if(selectedChipId == R.id.dynamic_event_chip_sun){
            competitionDayStr = getString(R.string.competition_day_sun);
            selectedDay = getString(R.string.competition_day_sun);

        }else{ //all
            selectedDateFrom = null;
            selectedDateTo = null;
            selectedDay = null;
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
                selectedDateFrom = calFrom.getTime();
                selectedDateTo = calTo.getTime();

            }catch (ParseException pe){
                presenter.createMessage("Error parsing dates");
            }
        }

    }

    public void setPresenter(RaceControlPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void setSelectedTeamID(String selectedTeamID) {
        this.selectedTeamID = selectedTeamID;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setSelectedCarNumber(Long selectedCarNumber) {
        this.selectedCarNumber = selectedCarNumber;
    }

}
package code.formulastudentspain.app.mvp.view.screen.teams.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;

import java.util.HashMap;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.view.screen.teams.TeamsPresenter;

public class FilterTeamsDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    //Filter values
    public final static String RADIOBUTTON_PASSED_PS = "radiobutton_passed_ps";
    public final static String RADIOBUTTON_PASSED_AI = "radiobutton_passed_ai";
    public final static String RADIOBUTTON_PASSED_EI = "radiobutton_passed_ei";
    public final static String RADIOBUTTON_PASSED_MI = "radiobutton_passed_mi";
    public final static String RADIOBUTTON_PASSED_TTT = "radiobutton_passed_ttt";
    public final static String RADIOBUTTON_PASSED_RT = "radiobutton_passed_rt";
    public final static String RADIOBUTTON_PASSED_NT = "radiobutton_passed_nt";
    public final static String RADIOBUTTON_PASSED_BT = "radiobutton_passed_bt";
    public final static String RADIOBUTTON_NOT_PASSED_PS = "radiobutton_not_passed_ps";
    public final static String RADIOBUTTON_NOT_PASSED_AI = "radiobutton_not_passed_ai";
    public final static String RADIOBUTTON_NOT_PASSED_EI = "radiobutton_not_passed_ei";
    public final static String RADIOBUTTON_NOT_PASSED_MI = "radiobutton_not_passed_mi";
    public final static String RADIOBUTTON_NOT_PASSED_TTT = "radiobutton_not_passed_ttt";
    public final static String RADIOBUTTON_NOT_PASSED_RT = "radiobutton_not_passed_rt";
    public final static String RADIOBUTTON_NOT_PASSED_NT = "radiobutton_not_passed_nt";
    public final static String RADIOBUTTON_NOT_PASSED_BT = "radiobutton_not_passed_bt";
    public final static String TRANSPONDER_STEP = "transponder_step";
    public final static String ENERGY_METER_STEP = "energy_meter_step";


    //View elements
    private AlertDialog dialog;
    private RadioButton radiobutton_passed_ps;
    private RadioButton radiobutton_passed_ai;
    private RadioButton radiobutton_passed_ei;
    private RadioButton radiobutton_passed_mi;
    private RadioButton radiobutton_passed_ttt;
    private RadioButton radiobutton_passed_rt;
    private RadioButton radiobutton_passed_nt;
    private RadioButton radiobutton_passed_bt;
    private RadioButton radiobutton_not_passed_ps;
    private RadioButton radiobutton_not_passed_ai;
    private RadioButton radiobutton_not_passed_ei;
    private RadioButton radiobutton_not_passed_mi;
    private RadioButton radiobutton_not_passed_ttt;
    private RadioButton radiobutton_not_passed_rt;
    private RadioButton radiobutton_not_passed_nt;
    private RadioButton radiobutton_not_passed_bt;
    //private Spinner spinnerTransponder;
    //private Spinner spinnerEnergyMeter;

    //Data
    private Map<String, String> filters = new HashMap<>();
    private Map<String, String> newFilters = new HashMap<>();

    //Presenter
    private TeamsPresenter presenter;
    
    

    public FilterTeamsDialog() {}

    public static FilterTeamsDialog newInstance(TeamsPresenter presenter, Map<String, String> filters) {
        FilterTeamsDialog frag = new FilterTeamsDialog();
        frag.setPresenter(presenter);
        frag.setFilters(filters);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filter_teams, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                .setTitle(R.string.dynamic_event_filtering_dialog_title)
                .setPositiveButton(R.string.dynamic_event_filtering_dialog_filter_button,null)
                .setNeutralButton("Clear", null)
                .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FilterTeamsDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {


        radiobutton_not_passed_ps.setChecked(true);
        radiobutton_not_passed_ai.setChecked(true);
        radiobutton_not_passed_ei.setChecked(true);
        radiobutton_not_passed_mi.setChecked(true);
        radiobutton_not_passed_ttt.setChecked(true);
        radiobutton_not_passed_rt.setChecked(true);
        radiobutton_not_passed_nt.setChecked(true);
        radiobutton_not_passed_bt.setChecked(true);

        newFilters.put(RADIOBUTTON_NOT_PASSED_PS,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_AI,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_EI,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_MI,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_TTT,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_RT,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_NT,"true");
        newFilters.put(RADIOBUTTON_NOT_PASSED_BT,"true");


        for(String filter: filters.keySet()){
            if(filter.equals(RADIOBUTTON_PASSED_PS)){
                radiobutton_passed_ps.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_ps.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_AI)){
                radiobutton_passed_ai.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_ai.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_EI)){
                radiobutton_passed_ei.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_ei.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_MI)){
                radiobutton_passed_mi.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_mi.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_TTT)){
                radiobutton_passed_ttt.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_ttt.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_RT)){
                radiobutton_passed_rt.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_rt.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_NT)){
                radiobutton_passed_nt.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_nt.setChecked(false);

            }
            if(filter.equals(RADIOBUTTON_PASSED_BT)){
                radiobutton_passed_bt.setChecked(Boolean.parseBoolean(filters.get(filter)));
                radiobutton_not_passed_bt.setChecked(false);

            }

            if(filter.equals(RADIOBUTTON_NOT_PASSED_PS)){
                radiobutton_not_passed_ps.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_AI)){
                radiobutton_not_passed_ai.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_EI)){
                radiobutton_not_passed_ei.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_MI)){
                radiobutton_not_passed_mi.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_TTT)){
                radiobutton_not_passed_ttt.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_RT)){
                radiobutton_not_passed_rt.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_NT)){
                radiobutton_not_passed_nt.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }
            if(filter.equals(RADIOBUTTON_NOT_PASSED_BT)){
                radiobutton_not_passed_bt.setChecked(Boolean.parseBoolean(filters.get(filter)));
            }

            /*
            //Fees
            if(filter.equals(TRANSPONDER_STEP)){
                spinnerTransponder.setSelection(Integer.parseInt(filters.get(filter)));
            }
            if(filter.equals(ENERGY_METER_STEP)){
                spinnerEnergyMeter.setSelection(Integer.parseInt(filters.get(filter)));
            }
            */

        }
    }

    private void initializeElements(View rootView){

        radiobutton_passed_ps = rootView.findViewById(R.id.filter_radiobutton_passed_ps);
        radiobutton_passed_ps.setOnCheckedChangeListener(this);
        radiobutton_passed_ai = rootView.findViewById(R.id.filter_radiobutton_passed_ai);
        radiobutton_passed_ai.setOnCheckedChangeListener(this);
        radiobutton_passed_ei = rootView.findViewById(R.id.filter_radiobutton_passed_ei);
        radiobutton_passed_ei.setOnCheckedChangeListener(this);
        radiobutton_passed_mi = rootView.findViewById(R.id.filter_radiobutton_passed_mi);
        radiobutton_passed_mi.setOnCheckedChangeListener(this);
        radiobutton_passed_ttt = rootView.findViewById(R.id.filter_radiobutton_passed_ttt);
        radiobutton_passed_ttt.setOnCheckedChangeListener(this);
        radiobutton_passed_rt = rootView.findViewById(R.id.filter_radiobutton_passed_rt);
        radiobutton_passed_rt.setOnCheckedChangeListener(this);
        radiobutton_passed_nt = rootView.findViewById(R.id.filter_radiobutton_passed_nt);
        radiobutton_passed_nt.setOnCheckedChangeListener(this);
        radiobutton_passed_bt = rootView.findViewById(R.id.filter_radiobutton_passed_bt);
        radiobutton_passed_bt.setOnCheckedChangeListener(this);

        radiobutton_not_passed_ps = rootView.findViewById(R.id.filter_radiobutton_not_passed_ps);
        radiobutton_not_passed_ps.setOnCheckedChangeListener(this);
        radiobutton_not_passed_ai = rootView.findViewById(R.id.filter_radiobutton_not_passed_ai);
        radiobutton_not_passed_ai.setOnCheckedChangeListener(this);
        radiobutton_not_passed_ei = rootView.findViewById(R.id.filter_radiobutton_not_passed_ei);
        radiobutton_not_passed_ei.setOnCheckedChangeListener(this);
        radiobutton_not_passed_mi = rootView.findViewById(R.id.filter_radiobutton_not_passed_mi);
        radiobutton_not_passed_mi.setOnCheckedChangeListener(this);
        radiobutton_not_passed_ttt = rootView.findViewById(R.id.filter_radiobutton_not_passed_ttt);
        radiobutton_not_passed_ttt.setOnCheckedChangeListener(this);
        radiobutton_not_passed_rt = rootView.findViewById(R.id.filter_radiobutton_not_passed_rt);
        radiobutton_not_passed_rt.setOnCheckedChangeListener(this);
        radiobutton_not_passed_nt = rootView.findViewById(R.id.filter_radiobutton_not_passed_nt);
        radiobutton_not_passed_nt.setOnCheckedChangeListener(this);
        radiobutton_not_passed_bt = rootView.findViewById(R.id.filter_radiobutton_not_passed_bt);
        radiobutton_not_passed_bt.setOnCheckedChangeListener(this);

        /*
        spinnerTransponder = rootView.findViewById(R.id.spinner_transponder);
        spinnerTransponder.setOnItemSelectedListener(this);
        spinnerEnergyMeter = rootView.findViewById(R.id.spinner_energy_meter);
        spinnerEnergyMeter.setOnItemSelectedListener(this);
        */


    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setFilters(newFilters);
                presenter.retrieveTeamsList();
                dialog.dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setFilters(new HashMap<>());
                presenter.retrieveTeamsList();
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()){
            case R.id.filter_radiobutton_not_passed_ps:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_PS, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_PS);
                    radiobutton_passed_ps.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_ai:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_AI, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_AI);
                    radiobutton_passed_ai.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_ei:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_EI, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_EI);
                    radiobutton_passed_ei.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_mi:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_MI, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_MI);
                    radiobutton_passed_mi.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_ttt:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_TTT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_TTT);
                    radiobutton_passed_ttt.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_rt:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_RT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_RT);
                    radiobutton_passed_rt.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_nt:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_NT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_NT);
                    radiobutton_passed_nt.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_not_passed_bt:
                if(compoundButton.isChecked()) {
                    newFilters.put(RADIOBUTTON_NOT_PASSED_BT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_PASSED_BT);
                    radiobutton_passed_bt.setChecked(!compoundButton.isChecked());
                }
                break;

            case R.id.filter_radiobutton_passed_ps:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_PS, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_PS);
                    radiobutton_not_passed_ps.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_ai:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_AI, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_AI);
                    radiobutton_not_passed_ai.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_ei:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_EI, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_EI);
                    radiobutton_not_passed_ei.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_mi:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_MI, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_MI);
                    radiobutton_not_passed_mi.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_ttt:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_TTT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_TTT);
                    radiobutton_not_passed_ttt.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_rt:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_RT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_RT);
                    radiobutton_not_passed_rt.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_nt:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_NT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_NT);
                    radiobutton_not_passed_nt.setChecked(!compoundButton.isChecked());
                }
                break;
            case R.id.filter_radiobutton_passed_bt:
                if(compoundButton.isChecked()){
                    newFilters.put(RADIOBUTTON_PASSED_BT, Boolean.toString(compoundButton.isChecked()));
                    newFilters.remove(RADIOBUTTON_NOT_PASSED_BT);
                    radiobutton_not_passed_bt.setChecked(!compoundButton.isChecked());
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        /*
        if(adapterView.getId() == R.id.spinner_transponder){
            newFilters.put(TRANSPONDER_STEP, String.valueOf(i));

        }else if(adapterView.getId() == R.id.spinner_energy_meter){
            newFilters.put(ENERGY_METER_STEP, String.valueOf(i));
        }
        */

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setFilters(Map<String, String> filters) {
        this.newFilters.putAll(filters);
        this.filters = filters;
    }
    public void setPresenter(TeamsPresenter presenter) {
        this.presenter = presenter;
    }


}
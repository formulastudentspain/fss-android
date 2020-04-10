package es.formulastudent.app.mvp.view.screen.teamsdetailfee.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liefery.android.vertical_stepper_view.VerticalStepperView;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.FeeItem;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeePresenter;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.adapter.StepperAdapter;


public class TeamsDetailFeeFragment extends Fragment {

    private Team team;
    private int type; //0 = Transponder, 1 = Energy Meter

    //Presenter
    private TeamsDetailFeePresenter presenter;

    //Data
    private List<FeeItem> transponderStates = new ArrayList<>();
    private List<FeeItem> energyMeterStates = new ArrayList<>();

    //Adapters
    private StepperAdapter transponderAdapter;
    private StepperAdapter energyMeterAdapter;


    public TeamsDetailFeeFragment(Team team, TeamsDetailFeePresenter presenter, int type) {
        this.team = team;
        this.presenter = presenter;
        this.type = type;

        transponderStates.addAll(team.getTransponderStates());
        energyMeterStates.addAll(team.getEnergyMeterStates());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teams_fee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //View items
        VerticalStepperView stepper = view.findViewById(R.id.stepper_list);

        if (type == 0) { //Transponder
            transponderAdapter = new StepperAdapter(getContext(), this.transponderStates, presenter);
            stepper.setStepperAdapter(transponderAdapter);

        } else { //Energy meter
            energyMeterAdapter = new StepperAdapter(getContext(), this.energyMeterStates, presenter);
            stepper.setStepperAdapter(energyMeterAdapter);
        }
    }

    public void updateView(Team team) {

        if (type == 0) {
            this.transponderStates.clear();
            this.transponderStates.addAll(team.getTransponderStates());
            this.transponderAdapter.notifyDataSetChanged();

        } else {
            this.energyMeterStates.clear();
            this.energyMeterStates.addAll(team.getEnergyMeterStates());
            this.energyMeterAdapter.notifyDataSetChanged();
        }
    }
}

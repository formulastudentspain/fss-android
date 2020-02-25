package es.formulastudent.app.mvp.view.activity.teams.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.ScrutineeringTest;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class TeamsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<Team> teamsList;
    private Context context;
    private LayoutInflater mLayoutInflater;

    private RecyclerViewClickListener clickListener;


    public TeamsAdapter(List<Team> teamsList, Context context, RecyclerViewClickListener clickListener) {
        this.teamsList = teamsList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_teams_item, parent, false);
        return new TeamsViewHolder(view, clickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2001);
        Date nullDate = cal.getTime();

        Team register = teamsList.get(position);

        TeamsViewHolder teamsViewHolder = (TeamsViewHolder)holder;
        viewBinderHelper.bind(teamsViewHolder.swipeRevealLayout, register.getID());
        teamsViewHolder.teamName.setText(register.getName());
        teamsViewHolder.carNumber.setText(register.getCar().getNumber().toString());

        List<ScrutineeringTest> tests = register.getTests();

        //AI
        if(tests.contains(ScrutineeringTest.ACCUMULATION_INSPECTION)){
            teamsViewHolder.aiTest.setVisibility(View.VISIBLE);
            teamsViewHolder.aiContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringAI()){
                teamsViewHolder.aiTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.aiTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.aiTest.setVisibility(View.GONE);
            teamsViewHolder.aiContainer.setVisibility(View.GONE);
        }

        //BT
        if(tests.contains(ScrutineeringTest.BRAKE_TEST)){
            teamsViewHolder.btTest.setVisibility(View.VISIBLE);
            teamsViewHolder.btContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringBT()){
                teamsViewHolder.btTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.btTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.btTest.setVisibility(View.GONE);
            teamsViewHolder.btContainer.setVisibility(View.GONE);
        }

        //EI
        if(tests.contains(ScrutineeringTest.ELECTRICAL_INSPECTION)){
            teamsViewHolder.eiTest.setVisibility(View.VISIBLE);
            teamsViewHolder.eiContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringEI()){
                teamsViewHolder.eiTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.eiTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.eiTest.setVisibility(View.GONE);
            teamsViewHolder.eiContainer.setVisibility(View.GONE);
        }

        //MI
        if(tests.contains(ScrutineeringTest.MECHANICAL_INSPECTION)){
            teamsViewHolder.miTest.setVisibility(View.VISIBLE);
            teamsViewHolder.miContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringMI()){
                teamsViewHolder.miTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.miTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.miTest.setVisibility(View.GONE);
            teamsViewHolder.miContainer.setVisibility(View.GONE);
        }

        //NT
        if(tests.contains(ScrutineeringTest.NOISE_TEST)){
            teamsViewHolder.ntTest.setVisibility(View.VISIBLE);
            teamsViewHolder.ntContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringNT()){
                teamsViewHolder.ntTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.ntTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.ntTest.setVisibility(View.GONE);
            teamsViewHolder.ntContainer.setVisibility(View.GONE);
        }

        //PS
        if(tests.contains(ScrutineeringTest.PRE_SCRUTINEERING)){
            teamsViewHolder.psTest.setVisibility(View.VISIBLE);
            teamsViewHolder.psContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringPS()){
                teamsViewHolder.psTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.psTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.psTest.setVisibility(View.GONE);
            teamsViewHolder.psContainer.setVisibility(View.GONE);
        }

        //RT
        if(tests.contains(ScrutineeringTest.RAIN_TEST)){
            teamsViewHolder.rtTest.setVisibility(View.VISIBLE);
            teamsViewHolder.rtContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringRT()){
                teamsViewHolder.rtTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.rtTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.rtTest.setVisibility(View.GONE);
            teamsViewHolder.rtContainer.setVisibility(View.GONE);
        }

        //TTT
        if(tests.contains(ScrutineeringTest.TILT_TABLE_TEST)){
            teamsViewHolder.tttTest.setVisibility(View.VISIBLE);
            teamsViewHolder.tttContainer.setVisibility(View.VISIBLE);
            if(register.getScrutineeringTTT()){
                teamsViewHolder.tttTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.tttTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.tttTest.setVisibility(View.GONE);
            teamsViewHolder.tttContainer.setVisibility(View.GONE);
        }

        //Fee states: Transponder
        teamsViewHolder.transponderState1.setText("1");
        teamsViewHolder.transponderState2.setText("2");
        teamsViewHolder.transponderState3.setText("3");
        teamsViewHolder.transponderState4.setText("4");

        if(register.getTransponderFeeGiven()){
            teamsViewHolder.transponderState1.setText(R.string.fa_check_circle);
        }
        
        if(register.getTransponderItemGiven()){
            teamsViewHolder.transponderState2.setText(R.string.fa_check_circle);
        }
        
        if(register.getTransponderItemReturned()){
            teamsViewHolder.transponderState3.setText(R.string.fa_check_circle);
        }
        
        if(register.getTransponderFeeReturned()){
            teamsViewHolder.transponderState4.setText(R.string.fa_check_circle);
        }

        if(register.getCar().getType().equals(Car.CAR_TYPE_ELECTRIC)
                || register.getCar().getType().equals(Car.CAR_TYPE_AUTONOMOUS_ELECTRIC)){

            //Fee states: EnergyMeter

            teamsViewHolder.energyMeterContainer.setVisibility(View.VISIBLE);

            teamsViewHolder.energyMeterState1.setText("1");
            teamsViewHolder.energyMeterState2.setText("2");
            teamsViewHolder.energyMeterState3.setText("3");
            teamsViewHolder.energyMeterState4.setText("4");

            if(register.getEnergyMeterFeeGiven()){
                teamsViewHolder.energyMeterState1.setText(R.string.fa_check_circle);
            }

            if(register.getEnergyMeterItemGiven()){
                teamsViewHolder.energyMeterState2.setText(R.string.fa_check_circle);
            }

            if(register.getEnergyMeterItemReturned()){
                teamsViewHolder.energyMeterState3.setText(R.string.fa_check_circle);
            }

            if(register.getEnergyMeterFeeReturned()){
                teamsViewHolder.energyMeterState4.setText(R.string.fa_check_circle);
            }

        }else{ //it is not electric, hide Energy Meter elements
            teamsViewHolder.energyMeterContainer.setVisibility(View.INVISIBLE);
        }

        //Comment indicator
        if(register.getScrutineeringPSComment() != null
                && !register.getScrutineeringPSComment().isEmpty()){
            teamsViewHolder.psCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.psCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringAIComment() != null
                && !register.getScrutineeringAIComment().isEmpty()){
            teamsViewHolder.aiCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.aiCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringEIComment() != null
                && !register.getScrutineeringEIComment().isEmpty()){
            teamsViewHolder.eiCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.eiCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringMIComment() != null
                && !register.getScrutineeringMIComment().isEmpty()){
            teamsViewHolder.miCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.miCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringTTTComment() != null
                && !register.getScrutineeringTTTComment().isEmpty()){
            teamsViewHolder.tttCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.tttCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringNTComment() != null
                && !register.getScrutineeringNTComment().isEmpty()){
            teamsViewHolder.ntCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.ntCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringRTComment() != null
                && !register.getScrutineeringRTComment().isEmpty()){
            teamsViewHolder.rtCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.rtCommentIndicator.setVisibility(View.GONE);
        }

        if(register.getScrutineeringBTComment() != null
                && !register.getScrutineeringBTComment().isEmpty()){
            teamsViewHolder.btCommentIndicator.setVisibility(View.VISIBLE);
        }else{
            teamsViewHolder.btCommentIndicator.setVisibility(View.GONE);
        }


        //Country flag
        Picasso.get().load(register.getCountry().getFlagURL()).into(teamsViewHolder.countryFlag);

    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}



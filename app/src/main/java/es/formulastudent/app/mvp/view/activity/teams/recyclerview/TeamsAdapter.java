package es.formulastudent.app.mvp.view.activity.teams.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Country;
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

        Team register = teamsList.get(position);

        TeamsViewHolder teamsViewHolder = (TeamsViewHolder)holder;
        viewBinderHelper.bind(teamsViewHolder.swipeRevealLayout, register.getID());
        teamsViewHolder.teamName.setText(register.getName());
        teamsViewHolder.carNumber.setText(register.getCar().getNumber().toString());

        List<ScrutineeringTest> tests = register.getTests();

        //AI
        if(tests.contains(ScrutineeringTest.ACCUMULATION_INSPECTION)){
            teamsViewHolder.aiTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringAI() != null){
                teamsViewHolder.aiTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.aiTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.aiTest.setVisibility(View.GONE);
        }

        //BT
        if(tests.contains(ScrutineeringTest.BRAKE_TEST)){
            teamsViewHolder.btTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringBT() != null){
                teamsViewHolder.btTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.btTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.btTest.setVisibility(View.GONE);
        }

        //EI
        if(tests.contains(ScrutineeringTest.ELECTRICAL_INSPECTION)){
            teamsViewHolder.eiTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringEI() != null){
                teamsViewHolder.eiTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.eiTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.eiTest.setVisibility(View.GONE);
        }

        //MI
        if(tests.contains(ScrutineeringTest.MECHANICAL_INSPECTION)){
            teamsViewHolder.miTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringMI() != null){
                teamsViewHolder.miTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.miTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.miTest.setVisibility(View.GONE);
        }

        //NT
        if(tests.contains(ScrutineeringTest.NOISE_TEST)){
            teamsViewHolder.ntTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringNT() != null){
                teamsViewHolder.ntTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.ntTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.ntTest.setVisibility(View.GONE);
        }

        //PS
        if(tests.contains(ScrutineeringTest.PRE_SCRUTINEERING)){
            teamsViewHolder.psTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringPS() != null){
                teamsViewHolder.psTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.psTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.psTest.setVisibility(View.GONE);
        }

        //RT
        if(tests.contains(ScrutineeringTest.RAIN_TEST)){
            teamsViewHolder.rtTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringRT() != null){
                teamsViewHolder.rtTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.rtTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.rtTest.setVisibility(View.GONE);
        }

        //TTT
        if(tests.contains(ScrutineeringTest.TILT_TABLE_TEST)){
            teamsViewHolder.tttTest.setVisibility(View.VISIBLE);
            if(register.getScrutineeringTTT() != null){
                teamsViewHolder.tttTest.setTextColor(context.getResources().getColor(R.color.md_grey_600));
            }else{
                teamsViewHolder.tttTest.setTextColor(context.getResources().getColor(R.color.md_grey_200));
            }
        }else{
            teamsViewHolder.tttTest.setVisibility(View.GONE);
        }


        Picasso.get().load(Country.GERMANY.getFlagURL()).into(teamsViewHolder.countryFlag);

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



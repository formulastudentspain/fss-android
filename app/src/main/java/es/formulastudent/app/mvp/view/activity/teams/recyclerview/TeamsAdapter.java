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



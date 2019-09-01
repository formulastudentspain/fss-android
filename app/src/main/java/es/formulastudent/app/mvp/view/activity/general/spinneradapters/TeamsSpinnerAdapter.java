package es.formulastudent.app.mvp.view.activity.general.spinneradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import javax.annotation.Nullable;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;

public class TeamsSpinnerAdapter extends ArrayAdapter<Team> {

    private Context context;
    private List<Team> teams;
    private LayoutInflater inflater;

    public TeamsSpinnerAdapter(Context context, int textViewResourceId, List<Team> teams) {
        super(context, textViewResourceId, teams);
        this.context = context;
        this.teams = teams;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return teams.size();
    }

    @Override
    public Team getItem(int position){
        return teams.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.activity_general_spinner, parent, false);

        Team team = teams.get(position);

        TextView teamName = view.findViewById(R.id.spinner_value);
        if(team.getCar() != null) {
            teamName.setText(team.getCar().getNumber() + " - " + team.getName());
        } else{
            teamName.setText(team.getName());
        }

        return view;
    }

}

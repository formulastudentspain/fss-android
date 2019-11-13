package es.formulastudent.app.mvp.view.activity.teammember.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class TeamMemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<TeamMember> timelineItemList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;



    public TeamMemberListAdapter(List<TeamMember> timelineItemList, Context context, RecyclerViewClickListener clickListener) {
        this.timelineItemList = timelineItemList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_user_list_item, parent, false);
        return new TeamMemberListViewHolder(view, clickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TeamMember teamMember = timelineItemList.get(position);

        TeamMemberListViewHolder teamMemberListViewHolder = (TeamMemberListViewHolder)holder;
        teamMemberListViewHolder.userName.setText(teamMember.getName());
        teamMemberListViewHolder.userTeam.setText(teamMember.getTeam());
        Picasso.get().load(teamMember.getPhotoUrl()).into(teamMemberListViewHolder.profileImage);

        if(true){ //teamMember.getRole()==driver
            teamMemberListViewHolder.roleImage.setImageResource(R.drawable.ic_user_role_driver);
        }

        if(teamMember.getNFCTag()==null || teamMember.getNFCTag().isEmpty()){
            teamMemberListViewHolder.registeredImage.setImageResource(R.drawable.ic_user_not_registered);
        }else{
            teamMemberListViewHolder.registeredImage.setImageResource(R.drawable.ic_user_registered);
        }

    }

    @Override
    public int getItemCount() {
        return timelineItemList.size();
    }

}



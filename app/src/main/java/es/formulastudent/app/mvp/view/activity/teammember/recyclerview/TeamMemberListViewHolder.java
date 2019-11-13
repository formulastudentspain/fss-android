package es.formulastudent.app.mvp.view.activity.teammember.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;

public class TeamMemberListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    ImageView roleImage;
    ImageView registeredImage;
    LinearLayout rowContent;
    RecyclerViewClickListener itemListener;


    public TeamMemberListViewHolder(View itemView, RecyclerViewClickListener itemListener) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.user_name);
        userTeam =  itemView.findViewById(R.id.user_team);
        roleImage =  itemView.findViewById(R.id.user_role_image);
        registeredImage = itemView.findViewById(R.id.user_registered_image);
        rowContent = itemView.findViewById(R.id.user_row_container);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

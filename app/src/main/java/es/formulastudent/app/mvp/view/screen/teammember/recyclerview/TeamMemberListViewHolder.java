package es.formulastudent.app.mvp.view.screen.teammember.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;

public class TeamMemberListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    ImageView registeredImage;
    LinearLayout rowContent;
    RecyclerViewClickListener itemListener;

    Chip driverChip;
    Chip esoChip;
    Chip asrChip;



    public TeamMemberListViewHolder(View itemView, RecyclerViewClickListener itemListener) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.user_name);
        userTeam =  itemView.findViewById(R.id.user_team);
        registeredImage = itemView.findViewById(R.id.user_registered_image);
        rowContent = itemView.findViewById(R.id.user_row_container);
        driverChip = itemView.findViewById(R.id.chipDriver);
        esoChip = itemView.findViewById(R.id.chipEso);
        asrChip = itemView.findViewById(R.id.chipAsr);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

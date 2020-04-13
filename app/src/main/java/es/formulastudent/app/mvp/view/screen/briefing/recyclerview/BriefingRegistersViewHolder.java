package es.formulastudent.app.mvp.view.screen.briefing.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;

public class BriefingRegistersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    TextView registerDate;
    SwipeRevealLayout swipeRevealLayout;

    private RecyclerViewClickListener clickListener;


    BriefingRegistersViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.briefing_item_user);
        userTeam =  itemView.findViewById(R.id.briefing_item_team);
        registerDate = itemView.findViewById(R.id.briefing_item_date);
        this.clickListener = clickListener;

        //Swipe components
        CardView deleteBriefingLayout = itemView.findViewById(R.id.delete_run_button);
        deleteBriefingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

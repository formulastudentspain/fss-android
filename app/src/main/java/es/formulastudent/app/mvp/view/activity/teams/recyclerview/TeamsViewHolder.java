package es.formulastudent.app.mvp.view.activity.teams.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;

public class TeamsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView countryFlag;
    TextView teamName;
    TextView carNumber;

    //Swipe components
    SwipeRevealLayout swipeRevealLayout;
    CardView deleteBriefingLayout;

    RecyclerViewClickListener clickListener;

    public TeamsViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
        countryFlag =  itemView.findViewById(R.id.teamFlag);
        teamName =  itemView.findViewById(R.id.teamName);
        carNumber =  itemView.findViewById(R.id.teamNumber);
        this.clickListener = clickListener;


        //Swipe components
        deleteBriefingLayout = itemView.findViewById(R.id.delete_run_button);
        deleteBriefingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

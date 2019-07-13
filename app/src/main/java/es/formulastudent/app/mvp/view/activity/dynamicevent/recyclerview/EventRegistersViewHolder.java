package es.formulastudent.app.mvp.view.activity.dynamicevent.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewLongClickedListener;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;

public class EventRegistersViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    private final static int EVENT_TYPE_PRESCRUTINEERING = 1;


    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    TextView registerDate;
    ImageView carTypeIcon;
    TextView carNumber;

    //Pre-scrutineering view components
    LinearLayout preScrutineeringContainer;
    TextView preScrutineeringTime;


    RecyclerViewLongClickedListener longClickListener;
    RecyclerViewClickListener clickListener;

    public EventRegistersViewHolder(View itemView, RecyclerViewLongClickedListener longClickListener, RecyclerViewClickListener clickListener, int eventType) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.acceleration_item_user);
        userTeam =  itemView.findViewById(R.id.acceleration_item_team);
        registerDate = itemView.findViewById(R.id.acceleration_item_date);
        carTypeIcon = itemView.findViewById(R.id.carTypeIcon);
        carNumber = itemView.findViewById(R.id.carNumber);
        preScrutineeringContainer = itemView.findViewById(R.id.prescruti_container);
        preScrutineeringTime = itemView.findViewById(R.id.prescruti_time);
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
        itemView.setOnLongClickListener(this);

        //PreScrutineering
        if(eventType == EVENT_TYPE_PRESCRUTINEERING){
            preScrutineeringContainer.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

    }

    @Override
    public boolean onLongClick(View view) {
        longClickListener.recyclerViewListLongClicked(view, this.getLayoutPosition());
        return true;
    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

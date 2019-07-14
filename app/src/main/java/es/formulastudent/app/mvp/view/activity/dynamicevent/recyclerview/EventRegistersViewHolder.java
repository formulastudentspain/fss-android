package es.formulastudent.app.mvp.view.activity.dynamicevent.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;

public class EventRegistersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final static int EVENT_TYPE_PRESCRUTINEERING = 1;


    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    TextView registerDate;
    ImageView carTypeIcon;
    TextView carNumber;
    CardView mainElement;

    //Swipe components
    SwipeRevealLayout swipeRevealLayout;
    CardView repeatRunLayout;
    CardView deleteRunLayout;

    //Pre-scrutineering view components
    LinearLayout preScrutineeringContainer;
    TextView preScrutineeringTime;

    RecyclerViewClickListener clickListener;

    public EventRegistersViewHolder(View itemView, RecyclerViewClickListener clickListener, int eventType) {
        super(itemView);
        swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.acceleration_item_user);
        userTeam =  itemView.findViewById(R.id.acceleration_item_team);
        registerDate = itemView.findViewById(R.id.acceleration_item_date);
        carTypeIcon = itemView.findViewById(R.id.carTypeIcon);
        carNumber = itemView.findViewById(R.id.carNumber);
        preScrutineeringContainer = itemView.findViewById(R.id.prescruti_container);
        preScrutineeringTime = itemView.findViewById(R.id.prescruti_time);
        mainElement = itemView.findViewById(R.id.main_element);
        this.clickListener = clickListener;

        //Swipe components
        repeatRunLayout = itemView.findViewById(R.id.repeat_run_button);
        repeatRunLayout.setOnClickListener(this);
        deleteRunLayout = itemView.findViewById(R.id.delete_run_button);
        deleteRunLayout.setOnClickListener(this);


        //PreScrutineering
        if(eventType == EVENT_TYPE_PRESCRUTINEERING){
            preScrutineeringContainer.setVisibility(View.VISIBLE);
            mainElement.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

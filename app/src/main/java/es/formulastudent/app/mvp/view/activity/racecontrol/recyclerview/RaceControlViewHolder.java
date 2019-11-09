package es.formulastudent.app.mvp.view.activity.racecontrol.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;

public class RaceControlViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView stateIcon;
    TextView carNumber;
    CardView mainElement;
    TextView currentStateLabel;
    TextView currentStateName;
    LinearLayout typeColor;
    LinearLayout stateColor;

    //Swipe components
    SwipeRevealLayout swipeRevealLayout;
    CardView state1;
    CardView state2;
    TextView state1Label;
    TextView state2Label;
    ImageView state1Icon;
    ImageView state2Icon;

    RecyclerViewClickListener clickListener;

    public RaceControlViewHolder(View itemView, RecyclerViewClickListener clickListener, int eventType) {
        super(itemView);

        this.clickListener = clickListener;
        mainElement = itemView.findViewById(R.id.main_element);
        swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
        carNumber = itemView.findViewById(R.id.carNumber);
        currentStateLabel = itemView.findViewById(R.id.currentStateLabel);
        currentStateName = itemView.findViewById(R.id.currentStateName);
        stateIcon = itemView.findViewById(R.id.stateIcon);
        typeColor = itemView.findViewById(R.id.typeColor);
        stateColor = itemView.findViewById(R.id.stateColor);

        //Swipe components
        state1 = itemView.findViewById(R.id.state1);
        state1.setOnClickListener(this);
        state1Label = itemView.findViewById(R.id.state1Label);
        state1Icon = itemView.findViewById(R.id.state1Icon);

        state2 = itemView.findViewById(R.id.state2);
        state2.setOnClickListener(this);
        state2Label = itemView.findViewById(R.id.state2Label);
        state2Icon = itemView.findViewById(R.id.state2Icon);
    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

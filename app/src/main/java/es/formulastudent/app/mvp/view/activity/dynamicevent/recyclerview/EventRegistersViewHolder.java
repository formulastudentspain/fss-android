package es.formulastudent.app.mvp.view.activity.dynamicevent.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;

public class EventRegistersViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    TextView registerDate;
    ImageView carTypeIcon;
    TextView carNumber;
    RecyclerViewLongClickedListener longPressedListener;

    public EventRegistersViewHolder(View itemView, RecyclerViewLongClickedListener longPressedListener) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.acceleration_item_user);
        userTeam =  itemView.findViewById(R.id.acceleration_item_team);
        registerDate = itemView.findViewById(R.id.acceleration_item_date);
        carTypeIcon = itemView.findViewById(R.id.carTypeIcon);
        carNumber = itemView.findViewById(R.id.carNumber);
        this.longPressedListener = longPressedListener;
        itemView.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        longPressedListener.recyclerViewListLongClicked(view, this.getLayoutPosition());
        return true;
    }
}

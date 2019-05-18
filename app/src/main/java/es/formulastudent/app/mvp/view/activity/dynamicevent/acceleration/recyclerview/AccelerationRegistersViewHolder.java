package es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;

public class AccelerationRegistersViewHolder extends RecyclerView.ViewHolder{

    ImageView profileImage;
    TextView userName;
    TextView userTeam;
    TextView registerDate;

    public AccelerationRegistersViewHolder(View itemView) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.acceleration_item_user);
        userTeam =  itemView.findViewById(R.id.acceleration_item_team);
        registerDate = itemView.findViewById(R.id.acceleration_item_date);
    }
}

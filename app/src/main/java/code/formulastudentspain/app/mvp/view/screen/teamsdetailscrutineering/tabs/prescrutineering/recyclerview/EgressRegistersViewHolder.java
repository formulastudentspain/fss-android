package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.prescrutineering.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;

public class EgressRegistersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ImageView profileImage;
    TextView userName;
    TextView registerDate;
    LinearLayout mainElement;
    TextView preScrutineeringTime;

    RecyclerViewClickListener clickListener;

    public EgressRegistersViewHolder(View itemView, RecyclerViewClickListener clickListener, int eventType) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.acceleration_item_user);
        registerDate = itemView.findViewById(R.id.acceleration_item_date);
        preScrutineeringTime = itemView.findViewById(R.id.prescruti_time);
        mainElement = itemView.findViewById(R.id.main_element);
        this.clickListener = clickListener;
        mainElement.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

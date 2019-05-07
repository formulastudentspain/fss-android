package es.formulastudent.app.mvp.view.activity.briefing.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;

public class BriefingRegistersViewHolder extends RecyclerView.ViewHolder{

    ImageView profileImage;
    TextView userName;
    TextView userTeam;

    public BriefingRegistersViewHolder(View itemView) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.user_name);
        userTeam =  itemView.findViewById(R.id.user_team);
    }
}

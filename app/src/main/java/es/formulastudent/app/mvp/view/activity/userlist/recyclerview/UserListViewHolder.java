package es.formulastudent.app.mvp.view.activity.userlist.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import es.formulastudent.app.R;

public class UserListViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView time;
    ImageView icon;
    ImageView photo;
    MaterialButton button;

    public UserListViewHolder(View itemView, int type) {
        super(itemView);
        title =  itemView.findViewById(R.id.timelineTitleValue);
        time =  itemView.findViewById(R.id.timelineTimeValue);
        icon =  itemView.findViewById(R.id.timelineIcon);
        photo =  itemView.findViewById(R.id.timeline_photo);
        button = itemView.findViewById(R.id.timelineReadMore);
    }
}

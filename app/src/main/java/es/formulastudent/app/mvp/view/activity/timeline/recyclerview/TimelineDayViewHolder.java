package es.formulastudent.app.mvp.view.activity.timeline.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;

public class TimelineDayViewHolder extends RecyclerView.ViewHolder {

    TextView date;

    public TimelineDayViewHolder(View itemView, int type) {
        super(itemView);
        date = itemView.findViewById(R.id.timeline_day_value);
    }
}

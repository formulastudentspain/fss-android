package es.formulastudent.app.mvp.view.activity.conecontrolstats.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;

public class ConeControlStatsViewHolder extends RecyclerView.ViewHolder{

    TextView carNumber;
    TextView conesNumber;
    TextView offCourseNumber;

    public ConeControlStatsViewHolder(View itemView) {
        super(itemView);

        carNumber = itemView.findViewById(R.id.carNumber);
        conesNumber = itemView.findViewById(R.id.conesNumber);
        offCourseNumber = itemView.findViewById(R.id.offCourseNumber);
    }
}

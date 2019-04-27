package es.formulastudent.app.mvp.view.activity.timeline.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.TimelineItem;
import es.formulastudent.app.mvp.view.activity.timeline.TimelinePresenter;


public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Date formats
    private DateFormat dateFormat = new SimpleDateFormat("EEE, K:mm a", Locale.US);
    private DateFormat dateFormatDay = new SimpleDateFormat("EEE, d MMM", Locale.US);


    private List<TimelineItem> timelineItemList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private TimelinePresenter presenter;


    public TimelineAdapter(List<TimelineItem> timelineItemList, Context context, TimelinePresenter presenter) {
        this.timelineItemList = timelineItemList;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        switch (viewType) {
            case -1:
                view = mLayoutInflater.inflate(R.layout.activity_timeline_day_item, parent, false);
                return new TimelineDayViewHolder(view, viewType);

            default:
                view = mLayoutInflater.inflate(R.layout.activity_timeline_item, parent, false);
                return new TimelineViewHolder(view, viewType);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TimelineItem timeLineItem = timelineItemList.get(position);

        switch (holder.getItemViewType()) {

            case -1: //Item day
                TimelineDayViewHolder timelineDayViewHolder = (TimelineDayViewHolder)holder;
                timelineDayViewHolder.date.setText(dateFormatDay.format(timeLineItem.getDate()));
                break;

            default: //Rest of item types
                TimelineViewHolder timelineViewHolder = (TimelineViewHolder)holder;
                timelineViewHolder.time.setText(dateFormat.format(timeLineItem.getDate()));
                timelineViewHolder.title.setText(timeLineItem.getTitle());
                Picasso.get().load(timeLineItem.getPhotoURL()).into(timelineViewHolder.photo);
                timelineViewHolder.button.setOnClickListener(presenter);

                switch (timeLineItem.getType()) {
                    case EVENT:
                        timelineViewHolder.icon.setImageResource(R.drawable.ic_timeline_event);
                        break;

                    case INCIDENT:
                        timelineViewHolder.icon.setImageResource(R.drawable.ic_timeline_incidence);
                        break;

                    case RESULT:
                        timelineViewHolder.icon.setImageResource(R.drawable.ic_timeline_result);
                        break;

                    case SOCIAL:
                        timelineViewHolder.icon.setImageResource(R.drawable.ic_timeline_social);
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return timelineItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        TimelineItem timeLineItem = timelineItemList.get(position);

        return timeLineItem.getType().getCode();
    }

}



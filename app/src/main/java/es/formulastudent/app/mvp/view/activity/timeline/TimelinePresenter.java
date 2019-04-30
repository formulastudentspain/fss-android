package es.formulastudent.app.mvp.view.activity.timeline;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.mvp.data.model.TimelineItem;
import es.formulastudent.app.mvp.data.model.TimelineItemType;
import es.formulastudent.app.mvp.view.activity.NFCReaderActivity;
import es.formulastudent.app.mvp.view.activity.timelinedetail.TimelineDetailActivity;

public class TimelinePresenter implements View.OnClickListener {

    //Dependencies
    private View view;
    private Context context;
    TimelineHelper helper;

    //Data
    private List<TimelineItem> timelineItemList = new ArrayList<>();
    private RecyclerView recyclerView;


    public TimelinePresenter(TimelinePresenter.View view, Context context, TimelineHelper helper) {
        this.view = view;
        this.context = context;
        this.helper = helper;
    }


    //TODO borrar
    private List<TimelineItem> setDataListItems(){

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US);

        List<TimelineItem> list = new ArrayList<>();

        String message = "Lorem ipsum dolor sit amet consectetur adipiscing elit habitant, in vulputate libero mus nostra rutrum imperdiet lectus, habitasse enim nibh eleifend bibendum fusce erat. Tempor justo nibh mi cras vestibulum vehicula ut fringilla mattis, pharetra quis sed aliquet vulputate faucibus ultricies sapien integer condimentum, nunc nec magna molestie euismod purus scelerisque odio. Viverra elementum ad quisque at maecenas curabitur facilisi scelerisque praesent ante inceptos, dictumst natoque magna lobortis ridiculus vitae pretium semper vehicula curae class, penatibus eget gravida pulvinar interdum libero feugiat primis vestibulum tellus.\n" +
                "\n" +
                "Quam imperdiet et vulputate sodales habitasse cubilia maecenas condimentum, porttitor non volutpat ac pulvinar venenatis habitant. Pellentesque duis dictumst viverra curae a tristique fames justo magnis, eget molestie montes netus feugiat nullam ullamcorper. Aliquet eros congue neque risus integer fermentum lacinia, morbi ad felis phasellus proin curae euismod, conubia facilisis elementum tortor convallis cras.";

        try {
            list.add(new TimelineItem("Timeline title 1", format.parse("2019-08-20 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 2", format.parse("2019-08-20 12:08 PM"), TimelineItemType.INCIDENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 3", format.parse("2019-08-20 12:08 PM"), TimelineItemType.SOCIAL, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 4", format.parse("2019-08-21 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 5", format.parse("2019-08-21 12:08 PM"), TimelineItemType.RESULT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 6", format.parse("2019-08-21 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 7", format.parse("2019-08-22 12:08 PM"), TimelineItemType.RESULT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 8", format.parse("2019-08-22 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 9", format.parse("2019-08-23 12:08 PM"), TimelineItemType.INCIDENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 10", format.parse("2019-08-23 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 11", format.parse("2019-08-23 12:08 PM"), TimelineItemType.INCIDENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 12", format.parse("2019-08-23 12:08 PM"), TimelineItemType.SOCIAL, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 13", format.parse("2019-08-23 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 14", format.parse("2019-08-24 12:08 PM"), TimelineItemType.RESULT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 15", format.parse("2019-08-24 12:08 PM"), TimelineItemType.SOCIAL, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 16", format.parse("2019-08-24 12:08 PM"), TimelineItemType.RESULT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 17", format.parse("2019-08-25 12:08 PM"), TimelineItemType.EVENT, "https://picsum.photos/200/300", message));
            list.add(new TimelineItem("Timeline title 18", format.parse("2019-08-25 12:08 PM"), TimelineItemType.INCIDENT, "https://picsum.photos/200/300", message));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return list;
    }

    public void getTimelineItems(){
        //TODO business operation to get the Timelineitems
        List<TimelineItem> items = setDataListItems();

       //Once we have the timeline items, we need to add the day items
       List<TimelineItem> modifiedItems = helper.addDayItems(items);

       //Update the itemList
       this.updateTimeLineItems(modifiedItems);

    }

    private void updateTimeLineItems(List<TimelineItem> newItems){
        this.timelineItemList.clear();
        this.timelineItemList.addAll(newItems);
        this.view.refreshTimelineItems();
    }

    public List<TimelineItem> getTimelineItemList() {
        return timelineItemList;
    }

    @Override
    public void onClick(android.view.View view) {
        int itemPosition = recyclerView.getChildLayoutPosition((android.view.View)view.getParent().getParent().getParent());
        TimelineItem item = timelineItemList.get(itemPosition);

        Intent intent = new Intent(context, TimelineDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("timelineItem", item);
        context.startActivity(intent);

    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }


    public interface View {

        /**
         * On retrieved timeline items
         */
        void refreshTimelineItems();

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();
    }

}

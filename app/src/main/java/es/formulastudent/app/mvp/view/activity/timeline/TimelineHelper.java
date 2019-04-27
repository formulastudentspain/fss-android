package es.formulastudent.app.mvp.view.activity.timeline;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.model.TimelineItem;
import es.formulastudent.app.mvp.data.model.TimelineItemType;

public class TimelineHelper {

    public List<TimelineItem> addDayItems(List<TimelineItem> items){

        List<TimelineItem> auxList = new ArrayList<>();

        for(int i = 0; i<items.size(); i++){

            //Current item
            Date itemDate = items.get(i).getDate();
            String day = (String) DateFormat.format("dd", itemDate);

            //Last item
            if(i==items.size()-1){
                auxList.add(items.get(i));
                break;
            }

            //Next item
            Date itemDateNext = items.get(i+1).getDate();
            String dayNext = (String) DateFormat.format("dd", itemDateNext);

            //If the day is different, add the item day for the list
            if(!day.equals(dayNext)){
                TimelineItem itemDay = new TimelineItem();
                itemDay.setType(TimelineItemType.DAY_TYPE);
                itemDay.setDate(itemDateNext);
                auxList.add(items.get(i));

                auxList.add(itemDay);
            }else{
                auxList.add(items.get(i));
            }
        }

        return auxList;
    }

}

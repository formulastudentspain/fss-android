package es.formulastudent.app.mvp.view.activity.dynamicevent.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.EventRegister;


public class EventRegistersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EventRegister> eventRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewLongClickedListener longClickedListener;


    public EventRegistersAdapter(List<EventRegister> eventRegisterList, Context context, RecyclerViewLongClickedListener longClickedListener) {
        this.eventRegisterList = eventRegisterList;
        this.longClickedListener = longClickedListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_dynamic_event_list_item, parent, false);
        return new EventRegistersViewHolder(view, longClickedListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        EventRegister register = eventRegisterList.get(position);

        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        EventRegistersViewHolder eventRegistersViewHolder = (EventRegistersViewHolder)holder;
        eventRegistersViewHolder.userName.setText(register.getUser());
        eventRegistersViewHolder.userTeam.setText(register.getTeam());
        eventRegistersViewHolder.registerDate.setText(sdf.format(register.getDate()));
        eventRegistersViewHolder.carNumber.setText(register.getCarNumber().toString());

        Picasso.get().load(register.getUserImage()).into(eventRegistersViewHolder.profileImage);

        switch(register.getCarType()) {
            case Car.CAR_TYPE_COMBUSTION:
                eventRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_combustion);
                break;
            case Car.CAR_TYPE_ELECTRIC:
                eventRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_electric_icon);
                break;
            case Car.CAR_TYPE_AUTONOMOUS_COMBUSTION:
                eventRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_steering_wheel);
                break;
            case Car.CAR_TYPE_AUTONOMOUS_ELECTRIC:
                eventRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_steering_wheel);
                break;
            default:
                // code block
        }
    }

    @Override
    public int getItemCount() {
        return eventRegisterList.size();
    }

}



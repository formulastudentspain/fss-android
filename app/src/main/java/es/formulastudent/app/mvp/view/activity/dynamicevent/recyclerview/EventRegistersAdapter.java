package es.formulastudent.app.mvp.view.activity.dynamicevent.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.view.Utils;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class EventRegistersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<EventRegister> eventRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;


    public EventRegistersAdapter(List<EventRegister> eventRegisterList, Context context, RecyclerViewClickListener clickListener) {
        this.eventRegisterList = eventRegisterList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);
        View view;

        view = mLayoutInflater.inflate(R.layout.activity_dynamic_event_list_item, parent, false);
        return new EventRegistersViewHolder(view, clickListener, viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        EventRegister register = eventRegisterList.get(position);
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        EventRegistersViewHolder eventRegistersViewHolder = (EventRegistersViewHolder)holder;
        viewBinderHelper.bind(eventRegistersViewHolder.swipeRevealLayout, register.getID());
        eventRegistersViewHolder.userName.setText(register.getUser());
        eventRegistersViewHolder.userTeam.setText(register.getTeam());
        eventRegistersViewHolder.registerDate.setText(sdf.format(register.getDate()));
        eventRegistersViewHolder.carNumber.setText(register.getCarNumber().toString());

        Picasso.get().load(register.getUserImage()).into(eventRegistersViewHolder.profileImage);

        //Pre-scrutineering details
        if(register instanceof PreScrutineeringRegister){
            Long time = ((PreScrutineeringRegister) register).getTime();
            if(time != 0L){
                eventRegistersViewHolder.preScrutineeringTime.setText(Utils.chronoFormatter(time));
                eventRegistersViewHolder.preScrutineeringTime.setTextColor(Color.parseColor("#A5D6A7"));

            }else{
                eventRegistersViewHolder.preScrutineeringTime.setText("N/A");
                eventRegistersViewHolder.preScrutineeringTime.setTextColor(Color.parseColor("#E0E0E0"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return eventRegisterList.size();
    }

    @Override
    public int getItemViewType(int position) {

        EventRegister register = eventRegisterList.get(position);
        if(register instanceof PreScrutineeringRegister){
            return 1;
        }

        return 0;
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}



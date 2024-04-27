package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.prescrutineering.recyclerview;

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

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.EventRegister;
import code.formulastudentspain.app.mvp.data.model.PreScrutineeringRegister;
import code.formulastudentspain.app.mvp.view.Utils;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;


public class EgressRegistersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<EventRegister> eventRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;


    public EgressRegistersAdapter(List<EventRegister> eventRegisterList, Context context, RecyclerViewClickListener clickListener) {
        this.eventRegisterList = eventRegisterList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);
        View view;

        view = mLayoutInflater.inflate(R.layout.fragment_pre_scrutineering_egress_item, parent, false);
        return new EgressRegistersViewHolder(view, clickListener, viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        EventRegister register = eventRegisterList.get(position);
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        EgressRegistersViewHolder egressRegistersViewHolder = (EgressRegistersViewHolder)holder;
        egressRegistersViewHolder.userName.setText(register.getUser());
        egressRegistersViewHolder.registerDate.setText(sdf.format(register.getDate()));

        Picasso.get().load(register.getUserImage()).into(egressRegistersViewHolder.profileImage);

        //Pre-scrutineering details
        if(register instanceof PreScrutineeringRegister){
            Long time = ((PreScrutineeringRegister) register).getTime();
            if(time != 0L){
                egressRegistersViewHolder.preScrutineeringTime.setText(Utils.chronoFormatter(time));
                egressRegistersViewHolder.preScrutineeringTime.setTextColor(Color.parseColor("#A5D6A7"));

            }else{
                egressRegistersViewHolder.preScrutineeringTime.setText("N/A");
                egressRegistersViewHolder.preScrutineeringTime.setTextColor(Color.parseColor("#E0E0E0"));
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



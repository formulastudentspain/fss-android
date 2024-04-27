package code.formulastudentspain.app.mvp.view.screen.racecontrol.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegister;
import code.formulastudentspain.app.mvp.data.model.RaceControlRegisterEndurance;
import code.formulastudentspain.app.mvp.data.model.RaceControlState;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewLongClickListener;


public class RaceControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<RaceControlRegister> raceControlRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;
    private RecyclerViewLongClickListener longClickListener;


    public RaceControlAdapter(List<RaceControlRegister> raceControlRegisterList, Context context, RecyclerViewClickListener clickListener, RecyclerViewLongClickListener longClickListener) {
        this.raceControlRegisterList = raceControlRegisterList;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);
        View view;

        view = mLayoutInflater.inflate(R.layout.fragment_race_control_list_item, parent, false);
        return new RaceControlViewHolder(view, clickListener, longClickListener, viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RaceControlRegister register = raceControlRegisterList.get(position);

        RaceControlViewHolder raceControlViewHolder = (RaceControlViewHolder)holder;
        viewBinderHelper.bind(raceControlViewHolder.swipeRevealLayout, register.getID());
        raceControlViewHolder.carNumber.setText(register.getCarNumber().toString());
        raceControlViewHolder.currentStateLabel.setText(register.getCurrentState().getAcronym());
        raceControlViewHolder.currentStateName.setText(register.getCurrentState().getName());


        //Set color and icon
        raceControlViewHolder.stateIcon.setImageResource(register.getCurrentState().getIcon());
        raceControlViewHolder.stateColor.setBackgroundColor(context.getResources().getColor(register.getCurrentState().getColor()));


        if(!register.getCurrentState().getStates().isEmpty()) {
            raceControlViewHolder.state1.setVisibility(View.VISIBLE);

            //Get state 1 object
            RaceControlState state1 = register.getNextStateAtIndex(0);
            raceControlViewHolder.state1.setBackgroundColor(context.getResources().getColor(state1.getColor()));
            raceControlViewHolder.state1Label.setText(state1.getAcronym());
            raceControlViewHolder.state1Icon.setImageResource(state1.getIcon());

        }else{
            raceControlViewHolder.state1.setVisibility(View.GONE);
        }

        if(register.getCurrentState().getStates().size()>1){
            raceControlViewHolder.state2.setVisibility(View.VISIBLE);

            //Get state 2 object
            RaceControlState state2 = register.getNextStateAtIndex(1);
            raceControlViewHolder.state2.setBackgroundColor(context.getResources().getColor(state2.getColor()));
            raceControlViewHolder.state2Label.setText(state2.getAcronym());
            raceControlViewHolder.state2Icon.setImageResource(state2.getIcon());

        }else{
            raceControlViewHolder.state2.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return raceControlRegisterList.size();
    }

    @Override
    public int getItemViewType(int position) {

        RaceControlRegister register = raceControlRegisterList.get(position);

        if(register instanceof RaceControlRegisterEndurance){
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

    public void closeSwipeRow(String id){
        viewBinderHelper.closeLayout(id);
    }
}



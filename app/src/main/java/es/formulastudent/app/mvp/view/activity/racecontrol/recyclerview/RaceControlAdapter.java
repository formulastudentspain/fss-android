package es.formulastudent.app.mvp.view.activity.racecontrol.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlRegisterEndurance;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class RaceControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<RaceControlRegister> raceControlRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;


    public RaceControlAdapter(List<RaceControlRegister> raceControlRegisterList, Context context, RecyclerViewClickListener clickListener) {
        this.raceControlRegisterList = raceControlRegisterList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);
        View view;

        view = mLayoutInflater.inflate(R.layout.activity_race_control_list_item, parent, false);
        return new RaceControlViewHolder(view, clickListener, viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RaceControlRegister register = raceControlRegisterList.get(position);

        RaceControlViewHolder raceControlViewHolder = (RaceControlViewHolder)holder;
        viewBinderHelper.bind(raceControlViewHolder.swipeRevealLayout, register.getID());
        raceControlViewHolder.carNumber.setText(register.getCarNumber().toString());
        raceControlViewHolder.currentStateLabel.setText(register.getCurrentState().getAcronym());
        raceControlViewHolder.currentStateName.setText(register.getCurrentState().getName());


        //Little line below in order to see easily the type of cars
        if(register.getRunFinal()){
            raceControlViewHolder.typeColor.setBackgroundColor(context.getResources().getColor(R.color.md_grey_900));

        }else{
            switch (register.getCarType()) {
                case Car.CAR_TYPE_COMBUSTION:
                    raceControlViewHolder.typeColor.setBackgroundColor(context.getResources().getColor(R.color.md_red_100));
                    break;

                case Car.CAR_TYPE_ELECTRIC:
                    raceControlViewHolder.typeColor.setBackgroundColor(context.getResources().getColor(R.color.md_blue_100));
                    break;
            }
        }


        //Set color and icon
        raceControlViewHolder.stateIcon.setImageResource(register.getCurrentState().getIcon());
        raceControlViewHolder.stateColor.setBackgroundColor(context.getResources().getColor(register.getCurrentState().getColor()));


        if(register.getCurrentState().getStates().size()>0) {
            raceControlViewHolder.state1.setVisibility(View.VISIBLE);

            //Get state 1 object
            RaceControlState state1 = RaceControlState.getStateByAcronym(register.getCurrentState().getStates().get(0));
            raceControlViewHolder.state1.setBackgroundColor(context.getResources().getColor(state1.getColor()));
            raceControlViewHolder.state1Label.setText(state1.getAcronym());
            raceControlViewHolder.state1Icon.setImageResource(state1.getIcon());

        }else{
            raceControlViewHolder.state1.setVisibility(View.GONE);
        }

        if(register.getCurrentState().getStates().size()>1){
            raceControlViewHolder.state2.setVisibility(View.VISIBLE);

            //Get state 2 object
            RaceControlState state2 = RaceControlState.getStateByAcronym(register.getCurrentState().getStates().get(1));
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



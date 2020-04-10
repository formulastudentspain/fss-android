package es.formulastudent.app.mvp.view.screen.conecontrol.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;


public class ConeControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<ConeControlRegister> coneControlRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;


    public ConeControlAdapter(List<ConeControlRegister> coneControlRegisterList, Context context, RecyclerViewClickListener clickListener) {
        this.coneControlRegisterList = coneControlRegisterList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);
        View view;

        view = mLayoutInflater.inflate(R.layout.activity_cone_control_list_item, parent, false);
        return new ConeControlViewHolder(view, clickListener, viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ConeControlRegister register = coneControlRegisterList.get(position);
        ConeControlViewHolder coneControlViewHolder = (ConeControlViewHolder)holder;
        coneControlViewHolder.carNumber.setText(String.valueOf(register.getCarNumber()));
        coneControlViewHolder.coneCount.setText(String.valueOf(register.getTrafficCones()));
        coneControlViewHolder.offCourseCount.setText(String.valueOf(register.getOffCourses()));

        String currentCones;
        String currentOffCourse;
        if(register.getCurrentConesCount()>0){
            currentCones = "+" + register.getCurrentConesCount();
        }else{
            currentCones = String.valueOf(register.getCurrentConesCount());
        }
        if(register.getCurrentOffCourseCount()>0){
            currentOffCourse = "+" + register.getCurrentOffCourseCount();
        }else{
            currentOffCourse = String.valueOf(register.getCurrentOffCourseCount());
        }
        coneControlViewHolder.coneCurrentCount.setText(currentCones);
        coneControlViewHolder.offCourseCurrentCount.setText(currentOffCourse);


        switch (register.getState()){
            case 0:
                coneControlViewHolder.coneIcon.setVisibility(View.VISIBLE);
                coneControlViewHolder.coneCurrentCount.setVisibility(View.GONE);
                coneControlViewHolder.coneLoading.setVisibility(View.GONE);
                coneControlViewHolder.smallConeIcon.setVisibility(View.GONE);
                coneControlViewHolder.coneCount.setVisibility(View.VISIBLE);

                coneControlViewHolder.offCourseIcon.setVisibility(View.VISIBLE);
                coneControlViewHolder.offCourseCurrentCount.setVisibility(View.GONE);
                coneControlViewHolder.offCourseLoading.setVisibility(View.GONE);
                coneControlViewHolder.smallOffCourseIcon.setVisibility(View.GONE);
                coneControlViewHolder.offCourseCount.setVisibility(View.VISIBLE);

                coneControlViewHolder.saveButton.setVisibility(View.GONE);
                coneControlViewHolder.saveButtonDisabled.setVisibility(View.VISIBLE);
                break;
            case 1:
                coneControlViewHolder.coneIcon.setVisibility(View.GONE);
                coneControlViewHolder.coneCurrentCount.setVisibility(View.VISIBLE);
                coneControlViewHolder.coneLoading.setVisibility(View.GONE);
                coneControlViewHolder.smallConeIcon.setVisibility(View.VISIBLE);
                coneControlViewHolder.coneCount.setVisibility(View.GONE);

                coneControlViewHolder.offCourseIcon.setVisibility(View.GONE);
                coneControlViewHolder.offCourseCurrentCount.setVisibility(View.VISIBLE);
                coneControlViewHolder.offCourseLoading.setVisibility(View.GONE);
                coneControlViewHolder.smallOffCourseIcon.setVisibility(View.VISIBLE);
                coneControlViewHolder.offCourseCount.setVisibility(View.GONE);

                coneControlViewHolder.saveButton.setVisibility(View.VISIBLE);
                coneControlViewHolder.saveButtonDisabled.setVisibility(View.GONE);
                break;
            case 2:
                coneControlViewHolder.coneIcon.setVisibility(View.GONE);
                coneControlViewHolder.coneCurrentCount.setVisibility(View.GONE);
                coneControlViewHolder.coneLoading.setVisibility(View.VISIBLE);
                coneControlViewHolder.smallConeIcon.setVisibility(View.VISIBLE);
                coneControlViewHolder.coneCount.setVisibility(View.GONE);

                coneControlViewHolder.offCourseIcon.setVisibility(View.GONE);
                coneControlViewHolder.offCourseCurrentCount.setVisibility(View.GONE);
                coneControlViewHolder.offCourseLoading.setVisibility(View.VISIBLE);
                coneControlViewHolder.smallOffCourseIcon.setVisibility(View.VISIBLE);
                coneControlViewHolder.offCourseCount.setVisibility(View.GONE);

                coneControlViewHolder.saveButton.setVisibility(View.GONE);
                coneControlViewHolder.saveButtonDisabled.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return coneControlRegisterList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}



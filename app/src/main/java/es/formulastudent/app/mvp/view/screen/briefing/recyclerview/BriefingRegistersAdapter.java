package es.formulastudent.app.mvp.view.screen.briefing.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;


public class BriefingRegistersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private List<BriefingRegister> briefingRegisterList;
    private Context context;
    private RecyclerViewClickListener clickListener;


    public BriefingRegistersAdapter(List<BriefingRegister> briefingRegisterList, Context context, RecyclerViewClickListener clickListener) {
        this.briefingRegisterList = briefingRegisterList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);

        View view = mLayoutInflater.inflate(R.layout.activity_briefing_list_item, parent, false);
        return new BriefingRegistersViewHolder(view, clickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BriefingRegister register = briefingRegisterList.get(position);

        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        BriefingRegistersViewHolder briefingRegistersViewHolder = (BriefingRegistersViewHolder)holder;
        viewBinderHelper.bind(briefingRegistersViewHolder.swipeRevealLayout, register.getID());
        briefingRegistersViewHolder.userName.setText(register.getUser());
        briefingRegistersViewHolder.userTeam.setText(register.getTeam());
        briefingRegistersViewHolder.registerDate.setText(sdf.format(register.getDate()));

        Picasso.get().load(register.getUserImage()).into(briefingRegistersViewHolder.profileImage);

    }

    @Override
    public int getItemCount() {
        return briefingRegisterList.size();
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}



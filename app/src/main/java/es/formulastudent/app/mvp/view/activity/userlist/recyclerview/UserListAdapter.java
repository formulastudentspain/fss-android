package es.formulastudent.app.mvp.view.activity.userlist.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.dto.UserDTO;



public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<UserDTO> timelineItemList;
    private Context context;
    private LayoutInflater mLayoutInflater;


    public UserListAdapter(List<UserDTO> timelineItemList, Context context) {
        this.timelineItemList = timelineItemList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_timeline_item, parent, false);
        return new UserListViewHolder(view, viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        UserDTO timeLineItem = timelineItemList.get(position);

    }

    @Override
    public int getItemCount() {
        return timelineItemList.size();
    }

}



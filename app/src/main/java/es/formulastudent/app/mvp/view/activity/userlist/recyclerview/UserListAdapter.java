package es.formulastudent.app.mvp.view.activity.userlist.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<User> timelineItemList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;



    public UserListAdapter(List<User> timelineItemList, Context context, RecyclerViewClickListener clickListener) {
        this.timelineItemList = timelineItemList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_user_list_item, parent, false);
        return new UserListViewHolder(view, clickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        User user = timelineItemList.get(position);

        UserListViewHolder userListViewHolder = (UserListViewHolder)holder;
        userListViewHolder.userName.setText(user.getName());
        userListViewHolder.userTeam.setText(user.getTeam());
        Picasso.get().load(user.getPhotoUrl()).into(userListViewHolder.profileImage);

        if(true){ //user.getRole()==driver
            userListViewHolder.roleImage.setImageResource(R.drawable.ic_user_role_driver);
        }

        if(user.getNFCTag()==null || user.getNFCTag().isEmpty()){
            userListViewHolder.registeredImage.setImageResource(R.drawable.ic_user_not_registered);
        }else{
            userListViewHolder.registeredImage.setImageResource(R.drawable.ic_user_registered);
        }

    }

    @Override
    public int getItemCount() {
        return timelineItemList.size();
    }

}



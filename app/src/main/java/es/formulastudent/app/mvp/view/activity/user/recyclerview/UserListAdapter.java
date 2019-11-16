package es.formulastudent.app.mvp.view.activity.user.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Role;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<User> userList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;



    public UserListAdapter(List<User> userList, Context context, RecyclerViewClickListener clickListener) {
        this.userList = userList;
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

        User user = userList.get(position);
        Role role = user.getRole();

        UserListViewHolder userListViewHolder = (UserListViewHolder)holder;
        userListViewHolder.userName.setText(user.getName());
        userListViewHolder.userRole.setText(role.getName().toUpperCase());
        userListViewHolder.userRole.setTextColor(context.getResources().getColor(role.getColor()));

        //For Officials, in bold
        if(role.equals(Role.ADMINISTRATOR) || role.equals(Role.OFFICIAL_MARSHALL)
                || role.equals(Role.OFFICIAL_SCRUTINEER) || role.equals(Role.OFFICIAL_STAFF)){
            userListViewHolder.userRole.setTypeface(null, Typeface.BOLD);

        }else{
            userListViewHolder.userRole.setTypeface(null, Typeface.NORMAL);
        }

        //Cell Phone
        if(user.getWithCellPhone() != null && user.getWithCellPhone()){
            userListViewHolder.withCellPhone.setImageResource(R.drawable.ic_cell_phone_on);
        }else{
            userListViewHolder.withCellPhone.setImageResource(R.drawable.ic_cell_phone_off);
        }

        //Walkie
        if(user.getWithWalkie() != null && user.getWithWalkie()){
            userListViewHolder.withWalkie.setImageResource(R.drawable.ic_walkie_talkie_on);
        }else{
            userListViewHolder.withWalkie.setImageResource(R.drawable.ic_walkie_talkie_off);
        }


        Picasso.get().load(user.getPhotoUrl()).into(userListViewHolder.profileImage);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}



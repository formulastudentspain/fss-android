package es.formulastudent.app.mvp.view.activity.userlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.presenter.UserListPresenter;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CategoryViewHolder> {

    private UserListPresenter presenter;
    private List<User> userList;

    public UsersAdapter(UserListPresenter presenter, List<User> userList) {
        this.userList = userList;
        this.presenter = presenter;
    }

    @Override
    public UsersAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.CategoryViewHolder holder, final int position) {

        //Get selected user from list
        final User user = userList.get(position);

        //Bind data
        holder.username.setText(user.getName().toString());
        holder.userLocation.setText(user.getLocation().toString());
        Picasso.get().load(user.getPicture().getMedium()).into(holder.imageView);

        //Add click event listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView username;
        TextView userLocation;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.userImage);
            username = itemView.findViewById(R.id.userName);
            userLocation = itemView.findViewById(R.id.userLocation);
        }
    }
}

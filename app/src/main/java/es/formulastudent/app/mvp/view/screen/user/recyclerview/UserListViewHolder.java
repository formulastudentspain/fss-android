package es.formulastudent.app.mvp.view.screen.user.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;

public class UserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView profileImage;
    TextView userName;
    TextView userRole;
    ImageView withWalkie;
    ImageView withCellPhone;
    LinearLayout rowContent;
    RecyclerViewClickListener itemListener;


    public UserListViewHolder(View itemView, RecyclerViewClickListener itemListener) {
        super(itemView);
        profileImage =  itemView.findViewById(R.id.user_profile_image);
        userName =  itemView.findViewById(R.id.user_name);
        userRole =  itemView.findViewById(R.id.user_role);
        rowContent = itemView.findViewById(R.id.user_row_container);
        withCellPhone = itemView.findViewById(R.id.user_cell_phone);
        withWalkie = itemView.findViewById(R.id.user_walkie_talkie);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

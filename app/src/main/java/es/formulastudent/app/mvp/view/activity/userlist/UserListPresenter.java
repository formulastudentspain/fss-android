package es.formulastudent.app.mvp.view.activity.userlist;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.model.dto.UserDTO;
import es.formulastudent.app.mvp.view.activity.timelinedetail.TimelineDetailActivity;

public class UserListPresenter implements View.OnClickListener {

    //Dependencies
    private View view;
    private Context context;

    //Data
    private List<UserDTO> userList = new ArrayList<>();
    private RecyclerView recyclerView;


    public UserListPresenter(UserListPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    private void updateUserListItems(List<UserDTO> newItems){
        this.userList.clear();
        this.userList.addAll(newItems);
        this.view.refreshUserItems();
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    @Override
    public void onClick(android.view.View view) {
        //Un poco guarro, mirar de cambiarlo
        int itemPosition = recyclerView.getChildLayoutPosition((android.view.View)view.getParent().getParent().getParent());
        UserDTO item = userList.get(itemPosition);

        Intent intent = new Intent(context, TimelineDetailActivity.class);
        intent.putExtra("timelineItem", item);
        context.startActivity(intent);

    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }


    public interface View {

        /**
         * On retrieved timeline items
         */
        void refreshUserItems();

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();
    }

}

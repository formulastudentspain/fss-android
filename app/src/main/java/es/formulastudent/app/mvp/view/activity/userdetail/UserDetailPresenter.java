package es.formulastudent.app.mvp.view.activity.userdetail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.formulastudent.app.mvp.data.model.dto.UserDTO;
import es.formulastudent.app.mvp.view.activity.timelinedetail.TimelineDetailActivity;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.RecyclerViewClickListener;

public class UserDetailPresenter {

    //Dependencies
    private View view;
    private Context context;


    public UserDetailPresenter(UserDetailPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    public void onNFCTagDetected(String tag){
        //TODO business to save read NFC tag

        view.updateNFCInformation(tag);
    }





    public interface View {

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

        /**
         * Update user NFC infomation
         */
        void updateNFCInformation(String TAG);
    }

}

package es.formulastudent.app.mvp.view.activity.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.fssinformation.FssInformationActivity;


public class LoginPresenter {

    //Dependencies
    private View view;
    private Context context;

    private User currentUser;

    public LoginPresenter(LoginPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void loginSuccess(){
        Intent myIntent = new Intent(context, FssInformationActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        context.startActivity(myIntent);
    }


    public void forgotPassword(){
        Toast.makeText(context, "Fuck, password forgotten!!", Toast.LENGTH_SHORT).show();
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
    }

}

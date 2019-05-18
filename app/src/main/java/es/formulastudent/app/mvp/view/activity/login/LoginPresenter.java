package es.formulastudent.app.mvp.view.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseUser;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.timeline.TimelineActivity;


public class LoginPresenter {

    //Dependencies
    private View view;
    private Context context;
    private AuthBO authBO;
    private InputMethodManager imm;

    public LoginPresenter(LoginPresenter.View view, Context context, AuthBO authBO) {
        this.view = view;
        this.context = context;
        this.authBO = authBO;
    }


    private void loginSuccess(){
        Intent myIntent = new Intent(context, TimelineActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }


    /**
     * Get an email with the steps to reset your password
     * @param mail
     */
    public void forgotPassword(String mail){

        authBO.resetPassword(mail, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.showMessage("Mail Sent."); //TODO Mirar la opcion de plantillas de correo electronico: https://support.google.com/firebase/answer/7000714?hl=es
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO
            }
        });
    }

    /**
     * Log in with mail and password
     * @param mail
     * @param password
     * @return
     */
    void doLogin(String mail, String password){

        //Hide keyboard
        this.hideKeyboard();

        //Call business to auth user
        authBO.doLoginWithMail(mail, password, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                FirebaseUser firebaseUser = (FirebaseUser) responseDTO.getData();
                User user = new User(firebaseUser);
                loginSuccess();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.hideLoadingIcon();
            }
        });
    }




    public void hideKeyboard(){
        imm = (InputMethodManager) view.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        android.view.View focus = view.getActivity().getCurrentFocus();
        if(focus == null){
            focus = new android.view.View(view.getActivity());
        }
        imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
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

        Activity getActivity();
    }

}

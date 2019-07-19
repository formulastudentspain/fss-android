package es.formulastudent.app.mvp.view.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import es.formulastudent.app.R;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.welcome.WelcomeActivity;


public class LoginPresenter {

    //Dependencies
    private View view;
    private Context context;
    private AuthBO authBO;
    private UserBO userBO;
    private SharedPreferences sharedPreferences;
    private InputMethodManager imm;

    public LoginPresenter(LoginPresenter.View view, Context context, AuthBO authBO, UserBO userBO,
                          SharedPreferences sharedPreferences) {
        this.view = view;
        this.context = context;
        this.authBO = authBO;
        this.userBO = userBO;
        this.sharedPreferences = sharedPreferences;
    }


    public void loginSuccess(User user){

        userBO.retrieveUserByMail(user.getMail(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                if(responseDTO.getData()!=null){

                    //If user exists in database, we store it in local storage
                    User user = (User) responseDTO.getData();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SharedPreferencesModule.PREFS_CURRENT_USER, new Gson().toJson(user));
                    editor.commit();

                    //Start Timeline activity
                    Intent myIntent = new Intent(context, WelcomeActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                    view.finishView();

                }else{ //The user is created for Login, but not in users table
                    view.hideLoadingIcon();
                    view.createMessage(context.getString(R.string.login_activity_user_not_found));
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO error obteniendo usuario por email
            }
        });


    }


    /**
     * Get an email with the steps to reset your password
     * @param mail
     */
    public void forgotPassword(String mail){

        if(mail != null && !mail.isEmpty()) {
            authBO.resetPassword(mail, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    view.createMessage("Mail Sent."); //TODO Mirar la opcion de plantillas de correo electronico: https://support.google.com/firebase/answer/7000714?hl=es
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    view.createMessage("Something Failed. Unknown error");
                }
            });
        } else {
            view.createMessage("Enter your mail account in order to send you the password recover mail. Thanks");
        }
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
                loginSuccess(user);
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
         * Create Snackbar Message
         * @param message
         */
        void createMessage(String message);

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

package code.formulastudentspain.app.mvp.view.screen.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.business.BusinessCallback;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.ResponseDTO;
import code.formulastudentspain.app.mvp.data.business.auth.AuthBO;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.welcome.MainActivity;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;


public class LoginPresenter extends DataConsumer {

    //Dependencies
    private View view;
    private Context context;
    private AuthBO authBO;
    private UserBO userBO;
    private SharedPreferences sharedPreferences;

    public LoginPresenter(LoginPresenter.View view, Context context, AuthBO authBO, UserBO userBO,
                          SharedPreferences sharedPreferences) {
        this.view = view;
        this.context = context;
        this.authBO = authBO;
        this.userBO = userBO;
        this.sharedPreferences = sharedPreferences;
    }

    void loginSuccess(User user) {
        userBO.retrieveUserByMail(user.getMail(),
                retrievedUser -> {
                    if (retrievedUser != null) {
                        //User not activated, cannot do login
                        if (retrievedUser.getRole() == null) {
                            setErrorToDisplay(new Message(R.string.login_activity_user_not_activated));
                            view.hideLoadingIcon();

                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(SharedPreferencesModule.PREFS_CURRENT_USER, new Gson().toJson(retrievedUser));
                            editor.commit();

                            //Start Timeline activity
                            Intent myIntent = new Intent(context, MainActivity.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(myIntent);
                            view.finishView();
                        }

                    } else { //The teamMember is created for Login, but not in users table
                        view.hideLoadingIcon();
                        setErrorToDisplay(new Message(R.string.login_activity_user_not_found));
                    }
                }, this::setErrorToDisplay);
    }


    /**
     * Get an email with the steps to reset your password
     * @param mail
     */
    void forgotPassword(String mail){
        if(mail != null && !mail.isEmpty()) {
            authBO.resetPassword(mail, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    //messages.showInfo(responseDTO.getInfo()); TODO
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    //messages.showError(responseDTO.getError()); TODO
                }
            });
        } else {
            //messages.showInfo(R.string.login_business_email_mandatory); TODO
        }
    }

    /**
     * Log in with mail and password
     * @param mail
     * @param password
     * @return
     */
    void doLogin(String mail, String password) {

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

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) view.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        android.view.View focus = view.getActivity().getCurrentFocus();
        if(focus == null){
            focus = new android.view.View(view.getActivity());
        }
        imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }


    public interface View {
        Activity getActivity();

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

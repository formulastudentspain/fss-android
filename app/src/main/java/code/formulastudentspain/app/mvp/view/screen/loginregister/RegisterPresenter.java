package code.formulastudentspain.app.mvp.view.screen.loginregister;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.auth.AuthBO;


public class RegisterPresenter extends DataConsumer {

    //Dependencies
    private View view;
    private Context context;
    private AuthBO authBO;
    private SharedPreferences sharedPreferences;
    private InputMethodManager imm;

    public RegisterPresenter(RegisterPresenter.View view, Context context, AuthBO authBO, SharedPreferences sharedPreferences) {
        this.view = view;
        this.context = context;
        this.authBO = authBO;
        this.sharedPreferences = sharedPreferences;
    }


    /**
     * Log in with mail and password
     * @param mail
     * @param password
     * @return
     */
    void createUser(String name, String mail, String password){

        //Hide keyboard
        this.hideKeyboard();

        //Call business to auth user
        authBO.createUser(name, mail, password,
                response -> {
                    view.hideLoadingIcon();
                    view.finishView();
                }, errorMessage -> {
                    view.hideLoadingIcon();
                    setErrorToDisplay(errorMessage);
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
        void createMessage(Integer message, Object... args);

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

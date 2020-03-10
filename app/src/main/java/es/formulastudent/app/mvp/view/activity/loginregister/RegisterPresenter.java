package es.formulastudent.app.mvp.view.activity.loginregister;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;


public class RegisterPresenter {

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
        authBO.createUser(name, mail, password, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.hideLoadingIcon();
                view.createMessage(responseDTO.getInfo());
                view.finishView();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.hideLoadingIcon();
                view.createMessage(responseDTO.getError());
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

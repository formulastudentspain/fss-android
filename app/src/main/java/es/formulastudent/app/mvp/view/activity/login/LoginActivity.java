package es.formulastudent.app.mvp.view.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerLoginComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.LoginModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.loginregister.RegisterActivity;


public class LoginActivity extends GeneralActivity implements LoginPresenter.View, View.OnClickListener{

    private final int REGISTER_REQUEST_CODE = 1;

    @Inject
    LoginPresenter presenter;

    //View components
    private EditText mailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView registerTextView;
    private ProgressBar loadingProgressBar;
    private LinearLayout loginLayout;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        //Check if user is still logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Correct login
            User user = new User(currentUser);
            presenter.loginSuccess(user);
        } else{
            //No TeamMember Logged
            initViews();
        }


    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void initViews(){

        //Login params
        mailEditText = findViewById(R.id.login_mail);
        passwordEditText = findViewById(R.id.login_password);

        //Login button
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        //Forgot password text
        forgotPasswordTextView = findViewById(R.id.login_forgot_password);
        forgotPasswordTextView.setOnClickListener(this);

        //Loading icon
        loadingProgressBar = findViewById(R.id.loading_status);

        //Register link
        registerTextView = findViewById(R.id.register_link);
        registerTextView.setOnClickListener(this);

        //Login layout
        loginLayout = findViewById(R.id.login_layout);

        //Show fields
        this.hideLoadingIcon();
    }

    @Override
    public void finishView() {
        this.finish();
    }

    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingIcon() {
        loadingProgressBar.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_button){

            showLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   presenter.doLogin(mailEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }, 1000);


        }else if(view.getId() == R.id.login_forgot_password){
            presenter.forgotPassword(mailEditText.getText().toString());

        }else if(view.getId() == R.id.register_link){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_REQUEST_CODE);
            overridePendingTransition(R.anim.open_activity_slide_out, R.anim.open_activity_slide_in);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REGISTER_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                createMessage(R.string.login_activity_user_not_activated);
            }
        }
    }//onActivityResult
}

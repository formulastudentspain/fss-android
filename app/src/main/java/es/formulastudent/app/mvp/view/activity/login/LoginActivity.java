package es.formulastudent.app.mvp.view.activity.login;

import android.app.Activity;
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
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;


public class LoginActivity extends GeneralActivity implements LoginPresenter.View, View.OnClickListener{


    @Inject
    LoginPresenter presenter;

    //View components
    private EditText mailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private ProgressBar loadingProgressBar;
    private LinearLayout loginLayout;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        initViews();
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


    @Override
    protected void onStart(){
        super.onStart();
        //Check if user is still logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Correct login
        } else{
            //failed Login
            //TODO redirigir directamente a la activity principal
        }

    }

    private void initViews(){

        //Login params
        mailEditText = findViewById(R.id.login_mail);
        passwordEditText = findViewById(R.id.login_password);
        mailEditText.setText("dpconde.me@gmail.com");
        passwordEditText.setText("dpc1234");

        //Login button
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        //Forgot password text
        forgotPasswordTextView = findViewById(R.id.login_forgot_password);
        forgotPasswordTextView.setOnClickListener(this);

        //Loading icon
        loadingProgressBar = findViewById(R.id.loading_status);

        //Login layout
        loginLayout = findViewById(R.id.login_layout);
    }

    @Override
    public void finishView() {

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

        }

    }
}

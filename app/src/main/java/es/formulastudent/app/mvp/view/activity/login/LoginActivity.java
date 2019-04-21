package es.formulastudent.app.mvp.view.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerLoginComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.LoginModule;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
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

        //TODO comprobar si la sesión del usuario sigue abierta, si sigue, redirigir directamente a la activity principal
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

        //Login layout
        loginLayout = findViewById(R.id.login_layout);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {

        if(view.getId() == R.id.login_button){

            showLoading();

            //TODO borrar
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.loginSuccess(); //TODO cambiar
                    finish(); //kill current activity
                }
            }, 1000);

        }else if(view.getId() == R.id.login_forgot_password){
            presenter.forgotPassword();
        }

    }
}

package es.formulastudent.app.mvp.view.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    EditText mailEditText;
    EditText passwordEditText;
    Button loginButton;
    TextView forgotPasswordTextView;


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

        mailEditText = findViewById(R.id.login_mail);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        forgotPasswordTextView = findViewById(R.id.login_forgot_password);
        forgotPasswordTextView.setOnClickListener(this);


    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void finishView() {

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_button){
            presenter.loginSuccess(); //TODO cambiar

        }else if(view.getId() == R.id.login_forgot_password){
            presenter.forgotPassword();

        }

    }
}

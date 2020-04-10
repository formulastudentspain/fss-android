package es.formulastudent.app.mvp.view.screen.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerRegisterComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.RegisterModule;
import es.formulastudent.app.mvp.view.screen.general.GeneralActivity;


public class RegisterActivity extends GeneralActivity implements RegisterPresenter.View, View.OnClickListener{

    @Inject
    RegisterPresenter presenter;

    //View components
    private EditText fullNameEditText;
    private EditText mailEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    private Button registerButton;
    private ProgressBar loadingProgressBar;
    private ImageView backArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);

        initViews();
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void initViews(){

        //Login params
        fullNameEditText = findViewById(R.id.login_full_name);
        mailEditText = findViewById(R.id.login_mail);
        passwordEditText = findViewById(R.id.login_password);
        rePasswordEditText = findViewById(R.id.login_re_password);

        //Back arrow
        backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(this);

        //Register button
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        //Loading icon
        loadingProgressBar = findViewById(R.id.loading_status);

        //Show fields
        this.hideLoadingIcon();
    }

    @Override
    public void finishView() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        loadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.register_button){
            if(fullNameEditText.getText().toString().trim().isEmpty()){
                fullNameEditText.setError(getString(R.string.login_Activity_mandatory_field));
            }
            else if(mailEditText.getText().toString().trim().isEmpty()){
                mailEditText.setError(getString(R.string.login_Activity_mandatory_field));
            }
            else if(passwordEditText.getText().toString().length()<6){
                passwordEditText.setError(getString(R.string.login_Activity_min_password_six));
            }
            else if(!passwordEditText.getText().toString().equals(rePasswordEditText.getText().toString())){
                passwordEditText.setError(getString(R.string.login_Activity_password_not_match));
            }else{
                showLoading();
                presenter.createUser(
                        fullNameEditText.getText().toString(),
                        mailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }

        }else if(view.getId() == R.id.back_arrow){
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_activity_slide_out, R.anim.close_activity_slide_in);
    }
}

package es.formulastudent.app.mvp.view.activity.adminoperations;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerAdminOpsComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.AdminOpsModule;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;

public class AdminOpsActivity extends GeneralActivity implements View.OnClickListener, AdminOpsPresenter.View {

    @Inject
    AdminOpsPresenter presenter;

    //Admin actions
    private Button deleteAllTeams;
    private Button deleteAllDrivers;
    private Button importTeamsAndDrivers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_admin_operations);
        super.onCreate(savedInstanceState);

        initViews();

    }



    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerAdminOpsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .adminOpsModule(new AdminOpsModule(this))
                .build()
                .inject(this);
    }




    private void initViews() {

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 10017L;

        //Add toolbar title
        setToolbarTitle(getString(R.string.statistics_admin_operations_title));

        deleteAllTeams = findViewById(R.id.deleteAllTeams);
        deleteAllTeams.setOnClickListener(this);

        deleteAllDrivers = findViewById(R.id.deleteAllDrivers);
        deleteAllDrivers.setOnClickListener(this);

        importTeamsAndDrivers = findViewById(R.id.importTeamsAndDrivers);
        importTeamsAndDrivers.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.deleteAllDrivers){
            presenter.deleteAllDrivers();

        }else if(view.getId() == R.id.deleteAllTeams){
            presenter.deleteAllTeams();

        }else if(view.getId() == R.id.importTeamsAndDrivers){
            presenter.importTeamsAndDrivers();
        }
    }



    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoadingIcon() {
        super.hideLoadingDialog();
    }

    @Override
    public AdminOpsActivity getActivity() {
        return this;
    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
    }
}

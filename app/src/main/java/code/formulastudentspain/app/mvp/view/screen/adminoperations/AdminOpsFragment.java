package code.formulastudentspain.app.mvp.view.screen.adminoperations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerAdminOpsComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.AdminOpsModule;

public class AdminOpsFragment extends Fragment implements View.OnClickListener, AdminOpsPresenter.View {

    @Inject
    AdminOpsPresenter presenter;

    //Admin actions
    private Button deleteAllTeams;
    private Button deleteAllDrivers;
    private Button importTeamsAndDrivers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_operations, container, false);
        initViews(view);
        return view;
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerAdminOpsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext()))
                .adminOpsModule(new AdminOpsModule(this))
                .build()
                .inject(this);
    }




    private void initViews(View view) {

        deleteAllTeams = view.findViewById(R.id.deleteAllTeams);
        deleteAllTeams.setOnClickListener(this);

        deleteAllDrivers = view.findViewById(R.id.deleteAllDrivers);
        deleteAllDrivers.setOnClickListener(this);

        importTeamsAndDrivers = view.findViewById(R.id.importTeamsAndDrivers);
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
    public void createMessage(Integer message, Object... args) {

    }

    @Override
    public void finishView() {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoadingIcon() {
    }

}

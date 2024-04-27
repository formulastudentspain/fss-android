package code.formulastudentspain.app.mvp.view.screen.statistics;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerStatisticsComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.StatisticsModule;
import code.formulastudentspain.app.mvp.data.model.EventType;

public class StatisticsFragment extends Fragment implements View.OnClickListener, StatisticsPresenter.View {

    @Inject
    StatisticsPresenter presenter;

    //View components
    private Button exportBriefing;
    private Button exportPreSrutineering;
    private Button exportPracticeTrack;
    private Button exportSkidpad;
    private Button exportAcceleration;
    private Button exportAutocross;
    private Button exportEndurance;
    private Button exportUsers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        initViews(view);
        checkWritePermissions();
        return view;
    }



    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerStatisticsComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext()))
                .statisticsModule(new StatisticsModule(this))
                .build()
                .inject(this);
    }




    private void initViews(View view) {

        //Bind components
        exportBriefing = view.findViewById(R.id.exportBriefing);
        exportBriefing.setOnClickListener(this);

        exportPreSrutineering = view.findViewById(R.id.exportPreScrutineering);
        exportPreSrutineering.setOnClickListener(this);

        exportPracticeTrack = view.findViewById(R.id.exportPracticeTrack);
        exportPracticeTrack.setOnClickListener(this);

        exportSkidpad = view.findViewById(R.id.exportSkidPad);
        exportSkidpad.setOnClickListener(this);

        exportAcceleration = view.findViewById(R.id.exportAcceleration);
        exportAcceleration.setOnClickListener(this);

        exportAutocross = view.findViewById(R.id.exportAutocross);
        exportAutocross.setOnClickListener(this);

        exportEndurance = view.findViewById(R.id.exportEndurance);
        exportEndurance.setOnClickListener(this);

        exportUsers = view.findViewById(R.id.exportUsers);
        exportUsers.setOnClickListener(this);

    }


    private void checkWritePermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        16);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.exportBriefing){
            presenter.exportDynamicEvent(EventType.BRIEFING);

        }else if(view.getId() == R.id.exportPreScrutineering){
            presenter.exportDynamicEvent(EventType.PRE_SCRUTINEERING);

        }else if(view.getId() == R.id.exportPracticeTrack){
            presenter.exportDynamicEvent(EventType.PRACTICE_TRACK);

        }else if(view.getId() == R.id.exportSkidPad){
            presenter.exportDynamicEvent(EventType.SKIDPAD);

        }else if(view.getId() == R.id.exportAcceleration){
            presenter.exportDynamicEvent(EventType.ACCELERATION);

        }else if(view.getId() == R.id.exportAutocross){
            presenter.exportDynamicEvent(EventType.AUTOCROSS);

        }else if(view.getId() == R.id.exportEndurance){
            presenter.exportDynamicEvent(EventType.ENDURANCE_EFFICIENCY);

        }else if(view.getId() == R.id.exportUsers){
            presenter.exportUsers();
        }

    }

}

package es.formulastudent.app.mvp.view.screen.conecontrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.ListenerRegistration;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.ConeControlModule;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.screen.conecontrol.recyclerview.ConeControlAdapter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;


public class ConeControlFragment extends Fragment implements ConeControlPresenter.View{

    @Inject
    ConeControlPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    private Long selectedSector;
    private String selectedRound;

    private ListenerRegistration registerListener;
    private ConeControlAdapter ccAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cone_control, container, false);

        //Get the event type (Endurance, AutoX, Skidpad)
        assert getArguments() != null;
        ConeControlFragmentArgs args = ConeControlFragmentArgs.fromBundle(getArguments());
        setupComponent(FSSApp.getApp().component(), args.getConeControlEvent());
        this.selectedRound = args.getSelectedRound();
        this.selectedSector = args.getSelectedSector();

        presenter.getLoadingData().observe(getViewLifecycleOwner(), loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        initViews(view);
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent, ConeControlEvent ccEvent) {
        DaggerConeControlComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .coneControlModule(new ConeControlModule(this, ccEvent))
                .build()
                .inject(this);
    }

    private void initViews(View view){

        //Recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ccAdapter = new ConeControlAdapter(presenter.getEventRegisterList(), getContext(), presenter);
        recyclerView.setAdapter(ccAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        //Titles
        TextView sectorNumber = view.findViewById(R.id.sector_number);
        sectorNumber.setText(selectedSector==-1 ? "-" : String.valueOf(selectedSector));
        TextView roundNumber = view.findViewById(R.id.round_number);
        roundNumber.setText(selectedRound==null ? "-" : selectedRound);
    }

    @Override
    public void refreshEventRegisterItems() {
        ccAdapter.notifyDataSetChanged();
    }

    @Override
    public String getSelectedRound() {
        return this.selectedRound;
    }

    @Override
    public Long getSelectedSector() {
        return this.selectedSector;
    }

    @Override
    public void onStop(){
        super.onStop();

        //Remove realTime listener
        registerListener.remove();
    }

    @Override
    public void onResume(){
        super.onResume();

        //Remove realTime listener
        if(registerListener != null){
            registerListener.remove();
        }

        //Refresh again the list after resuming activity
        registerListener = presenter.retrieveRegisterList();
    }
}

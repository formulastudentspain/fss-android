package code.formulastudentspain.app.mvp.view.screen.conecontrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.Objects;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerConeControlComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.ConeControlModule;
import code.formulastudentspain.app.mvp.data.model.ConeControlEvent;
import code.formulastudentspain.app.mvp.view.screen.conecontrol.recyclerview.ConeControlAdapter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


public class ConeControlFragment extends Fragment implements ConeControlPresenter.View{

    @Inject
    ConeControlPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    Messages messages;

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
        if(getArguments() != null) {
            ConeControlFragmentArgs args = ConeControlFragmentArgs.fromBundle(getArguments());
            setupComponent(FSSApp.getApp().component(), args.getConeControlEvent());
            this.selectedRound = args.getSelectedRound();
            this.selectedSector = args.getSelectedSector();
        }

        //Observer to display loading dialog
        presenter.getLoadingData().observe(getViewLifecycleOwner(), loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        //Remove elevation from Action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);

        //Observer to display errors
        presenter.getErrorToDisplay().observe(getViewLifecycleOwner(), message ->
                messages.showError(message.getStringID(), message.getArgs()));

        Objects.requireNonNull(getActivity()).getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        Objects.requireNonNull(getActivity()).getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

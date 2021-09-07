package es.formulastudent.app.mvp.view.screen.conecontrol;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerConeControlWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.ConeControlModule;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.screen.conecontrol.recyclerview.SectorAdapter;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.utils.messages.Messages;


public class ConeControlWelcomeFragment extends Fragment implements ConeControlPresenter.View, View.OnClickListener, RecyclerViewClickListener {

    @Inject
    ConeControlPresenter presenter;

    @Inject
    Messages messages;

    private static final int NUM_SECTORS = 7;

    private ConeControlEvent ccEvent;
    private Long selectedSector = -1L;
    private String selectedRound;

    //View elements
    private Button goButton;
    private LinearLayout round1;
    private LinearLayout round2;
    private LinearLayout roundFinal;
    private SectorAdapter sectorAdapter;

    private Map<Integer, Boolean> sectors = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cone_control_welcome, container, false);

        //Get the event type (Endurance, AutoX, Skidpad)
        assert getArguments() != null;
        ConeControlWelcomeFragmentArgs args = ConeControlWelcomeFragmentArgs.fromBundle(getArguments());
        this.ccEvent = args.getConeControlEvent();

        setHasOptionsMenu(true);

        initViews(view);
        checkWritePermissions();
        return view;
    }

    /**
     * Inject dependencies method
     *
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerConeControlWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .coneControlModule(new ConeControlModule(this, ccEvent))
                .build()
                .inject(this);
    }

    private void initViews(View view) {

        //Hide/show rounds and sectors depending on the event type
        LinearLayout roundContainer = view.findViewById(R.id.roundContainer);
        LinearLayout sectorContainer = view.findViewById(R.id.sectorContainer);
        if (ConeControlEvent.AUTOCROSS.equals(ccEvent)) {
            roundContainer.setVisibility(View.GONE);

        } else if (ConeControlEvent.ENDURANCE.equals(ccEvent)) {
            roundContainer.setVisibility(View.VISIBLE);

        } else if (ConeControlEvent.SKIDPAD.equals(ccEvent)) {
            sectorContainer.setVisibility(View.GONE);
            roundContainer.setVisibility(View.GONE);
        }

        TextView eventTypeText = view.findViewById(R.id.eventType);
        eventTypeText.setText(ccEvent.getName());

        goButton = view.findViewById(R.id.go_button);
        goButton.setOnClickListener(this);

        round1 = view.findViewById(R.id.button_round1);
        round1.setOnClickListener(this);

        round2 = view.findViewById(R.id.button_round2);
        round2.setOnClickListener(this);

        roundFinal = view.findViewById(R.id.button_roundFinal);
        roundFinal.setOnClickListener(this);


        for (int i = 1; i <= NUM_SECTORS; i++) {
            sectors.put(i, false);
        }

        RecyclerView sectorRecyclerView = view.findViewById(R.id.sectorRecyclerView);
        sectorAdapter = new SectorAdapter(sectors, getContext(), this);
        sectorRecyclerView.setAdapter(sectorAdapter);
        sectorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        checkEnableButton();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_round1) {
            round1.setSelected(true);
            round2.setSelected(false);
            roundFinal.setSelected(false);
            selectedRound = RaceControlRegister.RACE_ROUND_1;
            this.checkEnableButton();

        } else if (view.getId() == R.id.button_round2) {
            round1.setSelected(false);
            round2.setSelected(true);
            roundFinal.setSelected(false);
            selectedRound = RaceControlRegister.RACE_ROUND_2;
            this.checkEnableButton();

        } else if (view.getId() == R.id.button_roundFinal) {
            round1.setSelected(false);
            round2.setSelected(false);
            roundFinal.setSelected(true);
            selectedRound = RaceControlRegister.RACE_ROUND_FINAL;
            this.checkEnableButton();

        } else if (view.getId() == R.id.go_button) {
            assert getActivity() != null;
            NavController navController = Navigation.findNavController(getActivity(), R.id.myNavHostFragment);
            navController.navigate(ConeControlWelcomeFragmentDirections
                    .actionConeControlWelcomeFragmentToConeControlFragment(
                            ccEvent,
                            selectedRound,
                            selectedSector,
                            getString(ccEvent.getActivityTitle())));
        }
        checkEnableButton();
    }

    private void checkEnableButton() {

        if (ConeControlEvent.ENDURANCE.equals(ccEvent)) {
            if (selectedSector == null || selectedRound == null) {
                goButton.setVisibility(View.GONE);
            } else {
                goButton.setVisibility(View.VISIBLE);
            }
        } else if (ConeControlEvent.AUTOCROSS.equals(ccEvent)) {
            if (selectedSector == null) {
                goButton.setVisibility(View.GONE);
            } else {
                goButton.setVisibility(View.VISIBLE);
            }
        } else if (ConeControlEvent.SKIDPAD.equals(ccEvent)) {
            goButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        for (Integer i : sectors.keySet()) {
            sectors.put(i, false);
        }

        sectors.put(position + 1, !sectors.get(position + 1));
        sectorAdapter.notifyDataSetChanged();

        this.selectedSector = position + 1L;
        this.checkEnableButton();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cone_control_stats, menu);
        MenuItem filterItem = menu.findItem(R.id.cone_control_stats);
        filterItem.setOnMenuItemClickListener(menuItem -> {
            if(checkWritePermissions()){
                if(presenter.canUserExportCones()) {
                    presenter.exportConesToExcel(ccEvent);
                }else{
                    messages.showError(R.string.forbidden_required_role, UserRole.OFFICIAL_MARSHALL.getName());
                }
            }
            return true;
        });
    }

    //TODO
    private boolean checkWritePermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

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
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void refreshEventRegisterItems() { }

    @Override
    public String getSelectedRound() { return null; }

    @Override
    public Long getSelectedSector() { return null; }
}

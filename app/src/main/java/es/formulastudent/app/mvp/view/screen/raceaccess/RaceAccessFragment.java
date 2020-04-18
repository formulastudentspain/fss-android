package es.formulastudent.app.mvp.view.screen.raceaccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerRaceAccessComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.RaceAccessModule;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.view.screen.NFCReaderActivity;
import es.formulastudent.app.mvp.view.screen.raceaccess.recyclerview.EventRegistersAdapter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;

public class RaceAccessFragment extends Fragment implements RaceAccessPresenter.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private static final int NFC_REQUEST_CODE = 101;

    @Inject
    RaceAccessPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    private EventRegistersAdapter registersAdapter;
    private FloatingActionButton buttonAddRegister;
    private MenuItem filterItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_access, container, false);

        //Get selected team
        RaceAccessFragmentArgs args = RaceAccessFragmentArgs.fromBundle(getArguments());
        EventType eventType = args.getEventType();
        setupComponent(FSSApp.getApp().component(), eventType);

        presenter.getLoadingData().observe(getViewLifecycleOwner(), loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        initViews(view);
        setHasOptionsMenu(true);
        presenter.retrieveRegisterList();
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent, EventType eventType) {
        DaggerRaceAccessComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .raceAccessModule(new RaceAccessModule(this, eventType))
                .build()
                .inject(this);
    }

    private void initViews(View view){

        //View components
        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        registersAdapter = new EventRegistersAdapter(presenter.getEventRegisterList(), getContext(), presenter);
        recyclerView.setAdapter(registersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && buttonAddRegister.isShown())
                    buttonAddRegister.hide();
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    buttonAddRegister.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //Add acceleration register button
        buttonAddRegister = view.findViewById(R.id.button_add_acceleration_register);
        buttonAddRegister.setOnClickListener(this);
    }

    @Override
    public void refreshEventRegisterItems() {
        registersAdapter.notifyDataSetChanged();
        //this.hideLoading();
    }

    @Override
    public void filtersActivated(Boolean activated) {
        if(activated){
            filterItem.setIcon(R.drawable.ic_filter_active);
        }else{
            filterItem.setIcon(R.drawable.ic_filter_inactive);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_acceleration_register){
            Intent i = new Intent(getContext(), NFCReaderActivity.class);
            startActivityForResult(i, NFC_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //NFC reader
        if (requestCode == NFC_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                presenter.onNFCTagDetected(result);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dynamic_event, menu);
        filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(menuItem -> {
            presenter.filterIconClicked();
            return false;
        });
    }

    @Override
    public void onRefresh() {
        presenter.retrieveRegisterList();
    }
}

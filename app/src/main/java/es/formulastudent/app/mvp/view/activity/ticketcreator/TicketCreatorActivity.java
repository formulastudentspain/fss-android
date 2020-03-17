package es.formulastudent.app.mvp.view.activity.ticketcreator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.ListenerRegistration;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTicketCreatorComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TicketCreatorModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.ticketcreator.dialog.CreateTicketDialog;
import es.formulastudent.app.mvp.view.activity.ticketcreator.recyclerview.TicketCreatorAdapter;


public class TicketCreatorActivity extends GeneralActivity implements TicketCreatorPresenter.View, View.OnClickListener {

    //Real-time register listeners
    ListenerRegistration registrationTicketList;
    ListenerRegistration registrationCurrentStatus;

    @Inject
    TicketCreatorPresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private TicketCreatorAdapter ccAdapter;
    private FloatingActionButton buttonCreateTicket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ticket_creator);
        super.onCreate(savedInstanceState);
        setupComponent(FSSApp.getApp().component());

        initViews();
        setSupportActionBar(toolbar);

    }

    @Override
    public Activity getActivity(){
        return this;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerTicketCreatorComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .ticketCreatorModule(new TicketCreatorModule(this))
                .build()
                .inject(this);
    }

    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 50021L;

        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        ccAdapter = new TicketCreatorAdapter(presenter.getTicketList(), this);
        recyclerView.setAdapter(ccAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Button
        buttonCreateTicket = findViewById(R.id.button_create_ticket);
        buttonCreateTicket.setOnClickListener(this);

        //Add toolbar title
        setToolbarTitle(getString(R.string.ticket_activity_title));
    }

    @Override
    public void refreshEventRegisterItems() {
        ccAdapter.notifyDataSetChanged();
        this.hideLoading();
    }


    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        super.hideLoadingDialog();
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ccAdapter != null) {
            ccAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (ccAdapter != null) {
            ccAdapter.restoreStates(savedInstanceState);
        }
    }


    @Override
    public void onStop(){
        super.onStop();

        //Remove realTime listener
        registrationTicketList.remove();
        registrationCurrentStatus.remove();
    }

    @Override
    public void onResume(){
        super.onResume();

        //Remove realTime listener
        if(registrationTicketList != null){
            registrationTicketList.remove();
        }

        //Refresh again the list after resuming activity
        registrationCurrentStatus = presenter.retrieveCurrentTurns();
        registrationTicketList = presenter.retrieveTicketList();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_create_ticket){

            //With all the information, we create the dialog
            FragmentManager fm = getSupportFragmentManager();
            CreateTicketDialog createTicketDialog = CreateTicketDialog
                .newInstance(presenter, loggedUser.getTeam(), presenter.getTicketList());

            //Show the dialog
            createTicketDialog.show(fm, "fragment_event_confirm");


            //presenter.createTicket();
        }
    }
}

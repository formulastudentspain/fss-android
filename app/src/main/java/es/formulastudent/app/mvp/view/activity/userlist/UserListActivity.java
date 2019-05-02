package es.formulastudent.app.mvp.view.activity.userlist;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTimelineComponent;
import es.formulastudent.app.di.component.DaggerUserListComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.TimelineModule;
import es.formulastudent.app.di.module.UserListModule;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.UserListAdapter;


public class UserListActivity extends GeneralActivity implements UserListPresenter.View, View.OnClickListener{


    @Inject
    UserListPresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_user_list);
        super.onCreate(savedInstanceState);

        initViews();
        presenter.getUserList();
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerUserListComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .userListModule(new UserListModule(this))
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
        drawer.setSelection(10003L, false);
    }


    private void initViews(){

        //Add drawer
        addDrawer();
        mDrawerIdentifier = 10003L;

        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        userListAdapter = new UserListAdapter(presenter.getUserItemList(), this, presenter);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        presenter.setRecyclerView(recyclerView);

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_user_list_label));
    }


    @Override
    public void refreshUserItems() {
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {


    }
}

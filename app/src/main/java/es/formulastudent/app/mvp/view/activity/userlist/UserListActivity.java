package es.formulastudent.app.mvp.view.activity.userlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerUserListComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.UserListModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.userlist.adapter.UsersAdapter;
import es.formulastudent.app.mvp.view.presenter.UserListPresenter;

public class UserListActivity extends AppCompatActivity implements UserListPresenter.View, View.OnClickListener {

    @Inject
    UserListPresenter presenter;

    // View components
    RecyclerView recyclerView;
    Button prevButton;
    Button nextButton;
    RelativeLayout progressBarLayout;

    UsersAdapter adapter;
    List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_user_list);
        initViews();
        setupRecyclerView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        presenter.loadData();
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerUserListComponent.builder()
                .contextModule(new ContextModule(this))
                .appComponent(appComponent)
                .userListModule(new UserListModule(this))
                .build()
                .inject(this);
    }


    /**
     * Instantiate view components
     */
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        nextButton = findViewById(R.id.nextPageButton);
        nextButton.setOnClickListener(this);
        prevButton = findViewById(R.id.prevPageButton);
        prevButton.setOnClickListener(this);
        progressBarLayout = findViewById(R.id.progressBarLayout);
    }


    /**
     * Setup Recycler View component
     */
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UsersAdapter(presenter, userList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        progressBarLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        enableNextButton(false);
        enablePrevButton(false);
    }


    @Override
    public void hideProgress() {
        progressBarLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        presenter.checkCurrentPage();
    }


    @Override
    public void refreshUserList(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void enablePrevButton(boolean enable) {
        prevButton.setEnabled(enable);
    }


    @Override
    public void enableNextButton(boolean enable) {
        nextButton.setEnabled(enable);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.prevPageButton:
                presenter.loadPrevUsers();
                break;
            case R.id.nextPageButton:
                presenter.loadNextUsers();
                break;
            default:
                break;
        }
    }
}

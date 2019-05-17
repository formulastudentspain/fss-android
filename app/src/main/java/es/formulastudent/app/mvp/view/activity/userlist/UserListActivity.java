package es.formulastudent.app.mvp.view.activity.userlist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerUserListComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.UserListModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.userlist.dialog.CreateUserDialog;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.UserListAdapter;


public class UserListActivity extends GeneralActivity implements UserListPresenter.View, View.OnClickListener, TextWatcher {


    @Inject
    UserListPresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;
    private FloatingActionButton buttonAddUser;
    private EditText searchUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_user_list);
        super.onCreate(savedInstanceState);

        initViews();
        presenter.retrieveUsers();
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
        presenter.retrieveUsers();

    }


    @Override
    protected void onStart(){
        super.onStart();
        drawer.setSelection(mDrawerIdentifier, false);
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

        //Add user button
        buttonAddUser = findViewById(R.id.button_add_user);
        buttonAddUser.setOnClickListener(this);

        //Search user edit text
        searchUser = findViewById(R.id.search_user_field);
        searchUser.addTextChangedListener(this);

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_user_list_label));
    }


    @Override
    public void refreshUserItems() {
        userListAdapter.notifyDataSetChanged();
        this.hideLoading();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishView() {
        this.finish();
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
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_user){
            presenter.retrieveCreateUserDialogData();
        }
    }


    @Override
    public void showCreateUserDialog(List<Team> teams, List<UserRole> roles) {
        FragmentManager fm = getSupportFragmentManager();
        CreateUserDialog createUserDialog = CreateUserDialog.newInstance(presenter, this, teams, roles);
        createUserDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        presenter.filterUsers(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}

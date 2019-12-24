package es.formulastudent.app.mvp.view.activity.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerUserListComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.UserListModule;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.qrreader.QRReaderActivity;
import es.formulastudent.app.mvp.view.activity.user.dialog.CreateUserDialog;
import es.formulastudent.app.mvp.view.activity.user.recyclerview.UserListAdapter;
import info.androidhive.fontawesome.FontTextView;


public class UserActivity extends GeneralActivity implements UserPresenter.View, View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    private final static int QR_REQUEST_CODE_SEARCH = 2;

    @Inject
    UserPresenter presenter;

    //View components
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;
    private FloatingActionButton buttonAddUser;
    private EditText searchUser;
    private FontTextView qrCodeReaderButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_user_list);
        super.onCreate(savedInstanceState);

        initViews();
        requestPermissions();
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
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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

        //QR Code reader
        qrCodeReaderButton = findViewById(R.id.qr_code_reader);
        qrCodeReaderButton.setOnClickListener(this);

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_user_list_label));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       if(requestCode == QR_REQUEST_CODE_SEARCH){
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");

                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
       }
    }//onActivityResult



    private void requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }
    }


    @Override
    public void refreshUserItems() {
        userListAdapter.notifyDataSetChanged();
        this.hideLoading();
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_user){
            presenter.retrieveCreateUserDialogData();

        }else if(view.getId() == R.id.qr_code_reader){
            this.openQRCodeReader();
        }
    }

    @Override
    public void openQRCodeReader(){
        Intent i = new Intent(this, QRReaderActivity.class);
        startActivityForResult(i, QR_REQUEST_CODE_SEARCH);
    }


    @Override
    public void showCreateUserDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CreateUserDialog createUserDialog = CreateUserDialog.newInstance(presenter, this, loggedUser);
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

    @Override
    public void onRefresh() {
        presenter.retrieveUsers();
    }
}

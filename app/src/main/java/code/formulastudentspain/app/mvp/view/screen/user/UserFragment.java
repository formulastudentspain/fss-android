package code.formulastudentspain.app.mvp.view.screen.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerUserListComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.UserListModule;
import code.formulastudentspain.app.mvp.data.model.Role;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.qrreader.QRReaderActivity;
import code.formulastudentspain.app.mvp.view.screen.user.dialog.CreateUserDialog;
import code.formulastudentspain.app.mvp.view.screen.user.dialog.FilteringUsersDialog;
import code.formulastudentspain.app.mvp.view.screen.user.recyclerview.UserListAdapter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;
import info.androidhive.fontawesome.FontTextView;


public class UserFragment extends Fragment implements UserPresenter.View, View.OnClickListener,
        TextWatcher, SwipeRefreshLayout.OnRefreshListener{

    @Inject
    UserPresenter presenter;

    @Inject
    User loggedUser;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    Messages messages;

    private UserListAdapter userListAdapter;
    private MenuItem filterItem;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        //Observer for loading dialog
        presenter.getLoadingData().observe(this, loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        //Observer to display errors
        presenter.getErrorToDisplay().observe(this,
                error -> messages.showError(error.getStringID(), error.getArgs()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        initViews(view);
        setHasOptionsMenu(true);
        requestPermissions();
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerUserListComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .userListModule(new UserListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.retrieveUsers();
    }

    private void initViews(View view){

        //View components
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        userListAdapter = new UserListAdapter(presenter.getUserItemList(), getContext(), presenter);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        //Add user button
        FloatingActionButton buttonAddUser = view.findViewById(R.id.button_add_user);
        buttonAddUser.setOnClickListener(this);

        //Search user edit text
        EditText searchUser = view.findViewById(R.id.search_user_field);
        searchUser.addTextChangedListener(this);

        //QR Code reader
        FontTextView qrCodeReaderButton = view.findViewById(R.id.qr_code_reader);
        qrCodeReaderButton.setOnClickListener(this);
    }

    private ActivityResultLauncher<Intent> qrCodeResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    //TODO hacer algo con el QR code encontrado
                }
            });

    private void requestPermissions(){
        assert getActivity() != null;
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void filtersActivated(Boolean activated) {
        if(filterItem != null){
            if(activated){
                filterItem.setIcon(R.drawable.ic_filter_active);
            }else{
                filterItem.setIcon(R.drawable.ic_filter_inactive);
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
    public void refreshUserItems() {
        mSwipeRefreshLayout.setRefreshing(false);
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_user){
            showCreateUserDialog();

        }else if(view.getId() == R.id.qr_code_reader){
            this.openQRCodeReader();
        }
    }

    @Override
    public void openQRCodeReader(){
        Intent intent = new Intent(getContext(), QRReaderActivity.class);
        qrCodeResultLauncher.launch(intent);
    }

    @Override
    public void showCreateUserDialog() {
        FragmentManager fm = getParentFragmentManager();
        CreateUserDialog createUserDialog = CreateUserDialog.newInstance(presenter, getContext(), loggedUser);
        createUserDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void showFilteringDialog(Role selectedRole) {
        FilteringUsersDialog filteringUsersDialog = FilteringUsersDialog.newInstance(getContext(), presenter, selectedRole);
        filteringUsersDialog.show(getParentFragmentManager(), "filterUserDialog");
    }

    @Override
    public void openUserDetailFragment(User user) {
        assert getActivity() != null;
        NavController navController = Navigation.findNavController(getActivity(), R.id.myNavHostFragment);
        navController.navigate(UserFragmentDirections
                .actionUserFragmentToUserDetailFragment(user, user.getName()));
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

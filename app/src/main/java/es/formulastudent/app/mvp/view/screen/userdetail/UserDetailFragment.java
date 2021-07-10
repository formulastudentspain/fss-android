package es.formulastudent.app.mvp.view.screen.userdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerUserDetailComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.UserDetailModule;
import es.formulastudent.app.mvp.data.model.Device;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;

import static android.app.Activity.RESULT_OK;


public class UserDetailFragment extends Fragment implements UserDetailPresenter.View, View.OnClickListener{

    private static final int REQUEST_IMAGE_CAPTURE = 102;

    @Inject
    UserDetailPresenter presenter;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    User loggedUser;

    //View components
    private TextView userName;
    private ImageView userProfilePhoto;
    private ImageView userWalkie;
    private TextView userWalkieNumber;
    private ImageView userCellPhone;
    private TextView userCellPhoneNumber;
    private TextView userRole;

    //Selected user
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        presenter.getLoadingData().observe(this, loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_detail, container, false);

        UserDetailFragmentArgs args = UserDetailFragmentArgs.fromBundle(getArguments());
        user = args.getSelectedUser();

        initViews(view);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    private void setupComponent(AppComponent appComponent) {
        DaggerUserDetailComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .userDetailModule(new UserDetailModule(this))
                .build()
                .inject(this);
    }


    private void initViews(View view){

        //Instantiate vies components
        userName = view.findViewById(R.id.user_detail_name);
        userRole = view.findViewById(R.id.user_detail_role);
        userProfilePhoto = view.findViewById(R.id.user_detail_profile_image);
        userProfilePhoto.setOnClickListener(this);

        //Walkie
        userWalkie = view.findViewById(R.id.user_walkie_talkie);
        userWalkie.setOnClickListener(this);
        userWalkieNumber = view.findViewById(R.id.user_walkie_talkie_number);

        //Cell Phone
        userCellPhone = view.findViewById(R.id.user_cell_phone);
        userCellPhone.setOnClickListener(this);
        userCellPhoneNumber = view.findViewById(R.id.user_cell_phone_number);

        //Set info
        setUserDetails(user);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.user_detail_profile_image){
            dispatchTakePictureIntent();

        }else if(view.getId() == R.id.user_walkie_talkie){
            presenter.manageDeviceAssignment(Device.WALKIE);

        }else if(view.getId() == R.id.user_cell_phone){
            presenter.manageDeviceAssignment(Device.CELLPHONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            presenter.uploadProfilePicture(imageBitmap, user);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_detail, menu);
        MenuItem filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(menuItem -> {
            if(loggedUser.isAdministrator() || loggedUser.isOfficial()) {
                presenter.openEditUserDialog();
            }
            return true;
        });
    }

    @Override
    public void updateProfilePicture(Bitmap imageBitmap){
        userProfilePhoto.setImageBitmap(imageBitmap);
    }

    @Override
    public User getSelectedUser() {
        return user;
    }


    @Override
    public void setUserDetails(User user) {
        userName.setText(user.getName());
        Picasso.get().load(user.getPhotoUrl()).into(userProfilePhoto);

        if(user.getCellPhone() != null){
            userCellPhone.setImageResource(R.drawable.ic_cell_phone_on);
            userCellPhoneNumber.setText("#"+user.getCellPhone());
        }else{
            userCellPhone.setImageResource(R.drawable.ic_cell_phone_off);
            userCellPhoneNumber.setText("-");
        }
        if(user.getWalkie() != null){
            userWalkie.setImageResource(R.drawable.ic_walkie_talkie_on);
            userWalkieNumber.setText("#"+user.getWalkie());
        }else{
            userWalkie.setImageResource(R.drawable.ic_walkie_talkie_off);
            userWalkieNumber.setText("-");
        }

        if(user.getRole() != null){
            userRole.setText(user.getRole().getName().toUpperCase());
            userRole.setTextColor(getResources().getColor(user.getRole().getColor()));

            //For Officials, in bold
            if(user.getRole().equals(UserRole.ADMINISTRATOR) || user.getRole().equals(UserRole.OFFICIAL_MARSHALL)
                    || user.getRole().equals(UserRole.OFFICIAL_SCRUTINEER) || user.getRole().equals(UserRole.OFFICIAL_STAFF)){
                userRole.setTypeface(null, Typeface.BOLD);

            }else{
                userRole.setTypeface(null, Typeface.NORMAL);
            }
        }else{
            userRole.setText("PENDING");
            userRole.setTextColor(getResources().getColor(R.color.md_yellow_600));
            userRole.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        assert getActivity() != null;
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}

package es.formulastudent.app.mvp.view.screen.userdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerUserDetailComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.UserDetailModule;
import es.formulastudent.app.mvp.data.model.Device;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.general.GeneralActivity;


public class UserDetailActivity extends GeneralActivity implements UserDetailPresenter.View, View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 102;


    /**
     * TODO
     * Se tiene que poder cambiar:
     *  - Foto
     *
     */

    @Inject
    UserDetailPresenter presenter;

    //View components
    private TextView userName;
    private ImageView userProfilePhoto;
    private ImageView userWalkie;
    private TextView userWalkieNumber;
    private ImageView userCellPhone;
    private TextView userCellPhoneNumber;
    private TextView userRole;
    private MenuItem filterItem;

    //Selected user
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_user_detail);
        super.onCreate(savedInstanceState);

        user = (User) getIntent().getSerializableExtra("selectedUser");

        initViews();
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerUserDetailComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .userDetailModule(new UserDetailModule(this))
                .build()
                .inject(this);
    }


    private void initViews(){

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_volunteers_detail_title));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Instantiate vies components
        userName = findViewById(R.id.user_detail_name);
        userRole = findViewById(R.id.user_detail_role);
        userProfilePhoto = findViewById(R.id.user_detail_profile_image);
        userProfilePhoto.setOnClickListener(this);

        //Walkie
        userWalkie = findViewById(R.id.user_walkie_talkie);
        userWalkie.setOnClickListener(this);
        userWalkieNumber = findViewById(R.id.user_walkie_talkie_number);

        //Cell Phone
        userCellPhone = findViewById(R.id.user_cell_phone);
        userCellPhone.setOnClickListener(this);
        userCellPhoneNumber = findViewById(R.id.user_cell_phone_number);

        //Set info
        setUserDetails(user);

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
        if(view.getId() == R.id.user_detail_profile_image){
            dispatchTakePictureIntent();

        }else if(view.getId() == R.id.user_walkie_talkie){
            presenter.manageDeviceAssignment(Device.WALKIE);

        }else if(view.getId() == R.id.user_cell_phone){
            presenter.manageDeviceAssignment(Device.CELLPHONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        //Camera image
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            presenter.uploadProfilePicture(imageBitmap, user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_detail, menu);

        //Search menu item
        filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                presenter.openEditUserDialog();
                return true;
            }
        });

        return true;
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
    public UserDetailActivity getActivity() {
        return UserDetailActivity.this;
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}

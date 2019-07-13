package es.formulastudent.app.mvp.view.activity.general;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingActivity;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventActivity;
import es.formulastudent.app.mvp.view.activity.general.dialog.GeneralActivityExitDialog;
import es.formulastudent.app.mvp.view.activity.general.dialog.GeneralActivityLoadingDialog;
import es.formulastudent.app.mvp.view.activity.login.LoginActivity;
import es.formulastudent.app.mvp.view.activity.timeline.TimelineActivity;
import es.formulastudent.app.mvp.view.activity.userlist.UserListActivity;


public class GeneralActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {


    @Inject
    User loggedUser;

    //Drawer colors
    private static final String SELECTED_DRAWER_ITEM_COLOR = "#e6e6e6";
    private static final String TITLE_DRAWER_ITEM_COLOR = "#e30613";

    //Common elements
    protected Toolbar toolbar;
    protected Drawer drawer;
    protected Long mDrawerIdentifier = 0L;

    //Loading dialog
    GeneralActivityLoadingDialog loadingDialog;
    boolean isLoadingDisplayed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Get toolbar component
        toolbar = findViewById(R.id.my_toolbar);

        //loading dialog
        loadingDialog = GeneralActivityLoadingDialog.newInstance();

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(drawer!=null){
            drawer.setSelection(30003, false);
        }

    }

    protected void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }


    protected void addDrawer(){

        //TODO eliminar
        String userRole = "staff";

        //Create account header
        AccountHeader accountHeader = this.createDrawerHeader();

        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                });


        /*
         * Menu options for Staff
         */

        //Event control
        PrimaryDrawerItem eventControl = new PrimaryDrawerItem()
                .withEnabled(false)
                .withName(R.string.drawer_menu_staff)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Briefing
        PrimaryDrawerItem briefing = new PrimaryDrawerItem()
                .withIdentifier(10001)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_briefing)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Staff: Pre-Scrutineering
        PrimaryDrawerItem preScrutineering = new PrimaryDrawerItem()
                .withIdentifier(10002)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_pre_scrutineering)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Users management
        PrimaryDrawerItem staffUserManagement = new PrimaryDrawerItem()
                .withIdentifier(10003)
                .withName(R.string.drawer_menu_staff_users_management)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Practice track
        SecondaryDrawerItem practiceTrack = new SecondaryDrawerItem()
                .withIdentifier(10011)
                .withLevel(3)
                .withName(R.string.drawer_menu_staff_practice_track)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Skidpad
        SecondaryDrawerItem skidpad = new SecondaryDrawerItem()
                .withIdentifier(10012)
                .withLevel(3)
                .withName(R.string.drawer_menu_staff_skidpad)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Acceleration
        SecondaryDrawerItem acceleration = new SecondaryDrawerItem()
                .withIdentifier(10013)
                .withLevel(3)
                .withName(R.string.drawer_menu_staff_acceleration)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Autocross
        SecondaryDrawerItem autocross = new SecondaryDrawerItem()
                .withIdentifier(10014)
                .withLevel(3)
                .withName(R.string.drawer_menu_staff_autocross)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Endurance & Efficiency
        SecondaryDrawerItem enduranceEfficiency = new SecondaryDrawerItem()
                .withIdentifier(10015)
                .withLevel(3)
                .withName(R.string.drawer_menu_staff_endurance_efficiency)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Dynamic events
        PrimaryDrawerItem dynamicEvents = new PrimaryDrawerItem()
                .withIdentifier(10010)
                .withLevel(2)
                .withEnabled(false)
                .withName(R.string.drawer_menu_staff_dynamic_events)
                .withDisabledTextColor(Color.parseColor("#000000"))
                .withOnDrawerItemClickListener(this);
/*
        if (BuildConfig.FLAVOR.equals("dev_fss") || BuildConfig.FLAVOR.equals("pro_fss")){
            dynamicEvents.withSubItems(practiceTrack, skidpad, acceleration, autocross, enduranceEfficiency);

        }else if(BuildConfig.FLAVOR.equals("pro_ka")){
            dynamicEvents.withSubItems(acceleration, enduranceEfficiency);
        }
*/
        //Team
        PrimaryDrawerItem myTeam = new PrimaryDrawerItem()
                .withIdentifier(20001)
                .withEnabled(false)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withName(R.string.drawer_menu_participant_myTeam)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Team: Members
        SecondaryDrawerItem teamMembers = new SecondaryDrawerItem()
                .withIdentifier(20002)
                .withLevel(2)
                .withName(R.string.drawer_menu_participant_teamMembers)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Team: Status
        SecondaryDrawerItem teamStatus = new SecondaryDrawerItem()
                .withIdentifier(20003)
                .withLevel(2)
                .withName(R.string.drawer_menu_participant_status)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //FSS status
        PrimaryDrawerItem fss_status = new PrimaryDrawerItem()
                .withIdentifier(30001)
                .withEnabled(false)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withName(R.string.drawer_menu_common_fss_status)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Timeline
        SecondaryDrawerItem timeline = new SecondaryDrawerItem()
                .withIdentifier(30003)
                .withLevel(2)
                .withName(R.string.drawer_menu_common_timeline)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Scoring
        SecondaryDrawerItem scoring = new SecondaryDrawerItem()
                .withIdentifier(30004)
                .withLevel(2)
                .withName(R.string.drawer_menu_common_scoring)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Settings
        PrimaryDrawerItem settings = new PrimaryDrawerItem()
                .withIdentifier(30005)
                .withName(R.string.drawer_menu_common_settings)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Logout
        PrimaryDrawerItem logout = new PrimaryDrawerItem()
                .withIdentifier(30006)
                .withName(R.string.drawer_menu_common_logout)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);



     //   if (BuildConfig.FLAVOR.equals("dev_fss") || BuildConfig.FLAVOR.equals("pro_fss")){
//            builder.addDrawerItems(
//                    fss_status,
//                    timeline,
//                    scoring,
//                    new DividerDrawerItem(),
//                    eventControl,
//                    briefing,
//                    preScrutineering,
//                    dynamicEvents,
//                    new DividerDrawerItem(),
//                    staffUserManagement,
//                    settings,
//                    logout);

      //  } else if(BuildConfig.FLAVOR.equals("pro_ka")){
            builder.addDrawerItems(
                    eventControl,
                    briefing,
                    preScrutineering,
                    dynamicEvents,
                    practiceTrack, skidpad, acceleration, autocross, enduranceEfficiency,
                    new DividerDrawerItem(),
                    staffUserManagement,
                    //settings,
                    logout);

       // }

        //Build drawer
        drawer = builder.build();

    }


    /**
     * Create the account header for the drawer menu
     * @return
     */
    private AccountHeader createDrawerHeader(){

        //Change the way the images are loaded
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.get().load(uri).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {}
        });


        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.drawer_background)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(loggedUser.getName())
                                .withEmail(loggedUser.getMail())
                                .withIcon(loggedUser.getPhotoUrl())
                ).build();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        if(mDrawerIdentifier == drawerItem.getIdentifier()){
            return false;
        }


        if(drawerItem.getIdentifier() == 10003){ //User management
            Intent intent = new Intent(this, UserListActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 30003){ //Timeline
            Intent intent = new Intent(this, TimelineActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10001){ //Briefing
            Intent intent = new Intent(this, BriefingActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10013){ //Acceleration
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.ACCELERATION);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10015){ //Endurance
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.ENDURANCE_EFFICIENCY);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10014){ //Autocross
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.AUTOCROSS);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10012){ //SkidPad
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.SKIDPAD);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10011){ //Practice Track
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.PRACTICE_TRACK);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10002){ //Pre-Scrutineering
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.PRE_SCRUTINEERING);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 30006){ //Logout
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            finish();
        }

        return false;
    }

    /**
     * Create a Snackbar Message
     * @param message
     */
    public void createMessage(String message){
        View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    public void showLoadingDialog(){
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        //Only show loading if it not already on screen
        if(!isLoadingDisplayed){
            ft.add(loadingDialog, "loading_dialog");
            ft.commitAllowingStateLoss();
            isLoadingDisplayed = true;
        }

    }

    public void hideLoadingDialog(){
       if(loadingDialog != null){
            loadingDialog.dismiss();
            isLoadingDisplayed = false;
        }
    }


    @Override
    public void onBackPressed() {
        if(isTaskRoot()){

            //Open exit dialog
            GeneralActivityExitDialog exitDialog = GeneralActivityExitDialog.newInstance(this);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(exitDialog, "exit_dialog");
            ft.commitAllowingStateLoss();

        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            openCloseDrawer();
        }
        return true;
    }

    protected void openCloseDrawer(){
        if(drawer != null){
            if(drawer.isDrawerOpen()){
                drawer.closeDrawer();
            }else{
                drawer.openDrawer();
            }
        }
    }
}

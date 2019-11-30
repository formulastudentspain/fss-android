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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.adminoperations.AdminOpsActivity;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingActivity;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventActivity;
import es.formulastudent.app.mvp.view.activity.general.dialog.GeneralActivityExitDialog;
import es.formulastudent.app.mvp.view.activity.general.dialog.GeneralActivityLoadingDialog;
import es.formulastudent.app.mvp.view.activity.login.LoginActivity;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlWelcomeActivity;
import es.formulastudent.app.mvp.view.activity.statistics.StatisticsActivity;
import es.formulastudent.app.mvp.view.activity.teammember.TeamMemberActivity;
import es.formulastudent.app.mvp.view.activity.teams.TeamsActivity;
import es.formulastudent.app.mvp.view.activity.user.UserActivity;


public class GeneralActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {


    @Inject
    protected User loggedUser;

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


        /********** Event control ************/

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
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Briefing
        PrimaryDrawerItem teams = new PrimaryDrawerItem()
                .withIdentifier(70001)
                .withLevel(2)
                .withName(R.string.drawer_menu_teams)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        /********** Race control ************/

        //Race control
        PrimaryDrawerItem raceControl = new PrimaryDrawerItem()
                .withEnabled(false)
                .withName(R.string.drawer_menu_race_control)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);
/*
        //Race Control: Skidpad
        PrimaryDrawerItem rcSkidpad = new PrimaryDrawerItem()
                .withIdentifier(10018)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_skidpad)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Race Control: Acceleration
        PrimaryDrawerItem rcAcceleration = new PrimaryDrawerItem()
                .withIdentifier(10019)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_acceleration)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Race Control: Autocross
        PrimaryDrawerItem rcAutocross = new PrimaryDrawerItem()
                .withIdentifier(10020)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_autocross)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

*/
        //Race Control: Endurance
        PrimaryDrawerItem rcEndurance = new PrimaryDrawerItem()
                .withIdentifier(10021)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_endurance_efficiency)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        /********** Access control ************/

        //Race access
        PrimaryDrawerItem raceAccess = new PrimaryDrawerItem()
                .withEnabled(false)
                .withName(R.string.drawer_menu_race_access)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Practice track
        PrimaryDrawerItem practiceTrack = new PrimaryDrawerItem()
                .withIdentifier(10011)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_practice_track)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Skidpad
        PrimaryDrawerItem skidpad = new PrimaryDrawerItem()
                .withIdentifier(10012)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_skidpad)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Acceleration
        PrimaryDrawerItem acceleration = new PrimaryDrawerItem()
                .withIdentifier(10013)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_acceleration)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Autocross
        PrimaryDrawerItem autocross = new PrimaryDrawerItem()
                .withIdentifier(10014)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_autocross)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Endurance
        PrimaryDrawerItem endurance = new PrimaryDrawerItem()
                .withIdentifier(10015)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_endurance_efficiency)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        /********** TeamMember Management ************/

        //TeamMember management
        PrimaryDrawerItem userManagement = new PrimaryDrawerItem()
                .withEnabled(false)
                .withName(R.string.drawer_menu_staff_users_management)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //TeamMember management: Drivers
        PrimaryDrawerItem drivers = new PrimaryDrawerItem()
                .withIdentifier(10020)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_users_management_drivers)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //TeamMember management: ESOS
        PrimaryDrawerItem esos = new PrimaryDrawerItem()
                .withIdentifier(10025)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_users_management_esos)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //TeamMember management: Volunteers
        PrimaryDrawerItem volunteers = new PrimaryDrawerItem()
                .withIdentifier(10026)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_users_management_volunteers)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);



        //Staff: Admin Operations
        PrimaryDrawerItem adminOperations = new PrimaryDrawerItem()
                .withIdentifier(10017)
                .withName(R.string.drawer_menu_staff_admin_operations)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Statistics
        PrimaryDrawerItem staffStatistics = new PrimaryDrawerItem()
                .withIdentifier(10016)
                .withName(R.string.drawer_menu_staff_statistics)
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


        //Logout
        PrimaryDrawerItem logout = new PrimaryDrawerItem()
                .withIdentifier(30006)
                .withName(R.string.drawer_menu_common_logout)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        if(loggedUser.getRole().equals(UserRole.ADMINISTRATOR)){
            builder.addDrawerItems(

                    eventControl,
                    briefing, preScrutineering, teams,
                    new DividerDrawerItem(),

                    raceAccess,
                    practiceTrack, skidpad, acceleration, autocross, endurance,
                    new DividerDrawerItem(),

                    raceControl,
                   // rcSkidpad, rcAcceleration, rcAutocross,
                    rcEndurance,
                    new DividerDrawerItem(),

                    userManagement,
                    drivers,esos,volunteers,
                    new DividerDrawerItem(),

                    staffStatistics,
                    adminOperations,
                    logout);


        }else if(loggedUser.getRole().equals(UserRole.MARSHALL)){
            builder.addDrawerItems(

                    raceAccess,
                    practiceTrack, skidpad, acceleration, autocross, endurance,
                    new DividerDrawerItem(),

                    raceControl,
                    // rcSkidpad, rcAcceleration, rcAutocross,
                    rcEndurance,
                    new DividerDrawerItem(),

                    userManagement,
                    volunteers,
                    new DividerDrawerItem(),

                    logout);

        }else if(loggedUser.getRole().equals(UserRole.SCRUTINEER)){
            builder.addDrawerItems(

                    eventControl,
                    briefing, preScrutineering, teams,
                    new DividerDrawerItem(),

                    userManagement,
                    volunteers,
                    new DividerDrawerItem(),

                    logout);

        }else if(loggedUser.getRole().equals(UserRole.STAFF)){
            builder.addDrawerItems(

                    eventControl,
                    briefing, teams,
                    new DividerDrawerItem(),

                    userManagement,
                    drivers,esos,volunteers,
                    new DividerDrawerItem(),

                    logout);

        }else if(loggedUser.getRole().equals(UserRole.OFFICIAL_MARSHALL)){
            builder.addDrawerItems(

                    raceAccess,
                    practiceTrack, skidpad, acceleration, autocross, endurance,
                    new DividerDrawerItem(),

                    raceControl,
                    // rcSkidpad, rcAcceleration, rcAutocross,
                    rcEndurance,
                    new DividerDrawerItem(),

                    userManagement,
                    volunteers,
                    new DividerDrawerItem(),

                    staffStatistics,
                    logout);

        }else if(loggedUser.getRole().equals(UserRole.OFFICIAL_SCRUTINEER)){
            builder.addDrawerItems(

                    preScrutineering, teams,
                    new DividerDrawerItem(),

                    userManagement,
                    volunteers,
                    new DividerDrawerItem(),

                    logout);

        }else if(loggedUser.getRole().equals(UserRole.OFFICIAL_STAFF)){
            builder.addDrawerItems(

                    eventControl, briefing, teams,
                    new DividerDrawerItem(),

                    userManagement,
                    drivers,esos,volunteers,
                    new DividerDrawerItem(),

                    logout);
        }


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


        if(drawerItem.getIdentifier() == 10020){ //TeamMember management
            Intent intent = new Intent(this, TeamMemberActivity.class);
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
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.ENDURANCE_EFFICIENCY);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10014){ //Autocross
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.AUTOCROSS);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10012){ //SkidPad
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.SKIDPAD);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10011){ //Practice Track
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.PRACTICE_TRACK);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10002){ //Pre-Scrutineering
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.PRE_SCRUTINEERING);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 30006){ //Logout
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10016){ //Statistics
            Intent intent = new Intent(this, StatisticsActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10017) { //Admin operations
            Intent intent = new Intent(this, AdminOpsActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10021){ //Race Control Endurance
            Intent intent = new Intent(this, RaceControlWelcomeActivity.class);
            intent.putExtra("eventType", RaceControlEvent.ENDURANCE);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 10026){ //Users
            Intent intent = new Intent(this, UserActivity.class);
            this.startActivity(intent);
            finish();

        }else if(drawerItem.getIdentifier() == 70001){ //Teams
            Intent intent = new Intent(this, TeamsActivity.class);
            this.startActivity(intent);
            finish();
        }



        return false;
    }

    /**
     * Create a Snackbar Message
     * @param message
     */
    public void createMessage(Integer message, Object... args){
        if(message != null){
            View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);

            if(args != null){
                Snackbar.make(rootView, getString(message, args), Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
            }
        }
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

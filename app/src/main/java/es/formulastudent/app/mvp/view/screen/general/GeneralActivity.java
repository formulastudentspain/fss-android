package es.formulastudent.app.mvp.view.screen.general;

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
import androidx.core.content.ContextCompat;
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
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.screen.adminoperations.AdminOpsActivity;
import es.formulastudent.app.mvp.view.screen.conecontrol.ConeControlWelcomeActivity;
import es.formulastudent.app.mvp.view.screen.dynamicevent.DynamicEventActivity;
import es.formulastudent.app.mvp.view.screen.general.dialog.GeneralActivityExitDialog;
import es.formulastudent.app.mvp.view.screen.general.dialog.GeneralActivityLoadingDialog;
import es.formulastudent.app.mvp.view.screen.login.LoginActivity;
import es.formulastudent.app.mvp.view.screen.racecontrol.RaceControlWelcomeActivity;
import es.formulastudent.app.mvp.view.screen.statistics.StatisticsActivity;
import es.formulastudent.app.mvp.view.screen.teammember.TeamMemberActivity;
import es.formulastudent.app.mvp.view.screen.teams.TeamsActivity;
import es.formulastudent.app.mvp.view.screen.user.UserActivity;
import es.formulastudent.app.mvp.view.screen.welcome.MainActivity;
import info.androidhive.fontawesome.FontDrawable;

//arcaya
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
        FontDrawable briefingIcon = new FontDrawable(this, R.string.fa_comment_dots, false, false);
        briefingIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem briefing = new PrimaryDrawerItem()
                .withIdentifier(10001)
                .withLevel(2)
                .withIcon(briefingIcon)
                .withName(R.string.drawer_menu_staff_briefing)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Teams
        FontDrawable teamsIcon = new FontDrawable(this, R.string.fa_car_side_solid, true, false);
        teamsIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem teams = new PrimaryDrawerItem()
                .withIdentifier(70001)
                .withLevel(2)
                .withIcon(teamsIcon)
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

        //Race Control: Skidpad
        FontDrawable skidpadIconRC = new FontDrawable(this, R.string.fa_infinity_solid, true, false);
        skidpadIconRC.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem rcSkidpad = new PrimaryDrawerItem()
                .withIdentifier(10023)
                .withLevel(2)
                .withIcon(skidpadIconRC)
                .withName(R.string.drawer_menu_staff_skidpad)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Race Control: Autocross
        FontDrawable autocrossIconRC = new FontDrawable(this, R.string.fa_stopwatch_solid, true, false);
        autocrossIconRC.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem rcAutocross = new PrimaryDrawerItem()
                .withIdentifier(10022)
                .withLevel(2)
                .withIcon(autocrossIconRC)
                .withName(R.string.drawer_menu_staff_autocross)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Race Control: Endurance
        FontDrawable enduranceIconRC = new FontDrawable(this, R.string.fa_flag_checkered_solid, true, false);
        enduranceIconRC.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem rcEndurance = new PrimaryDrawerItem()
                .withIdentifier(10021)
                .withLevel(2)
                .withIcon(enduranceIconRC)
                .withName(R.string.drawer_menu_staff_endurance_efficiency)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        /********** Cone control ************/

        //Cone control
        PrimaryDrawerItem coneControl = new PrimaryDrawerItem()
                .withEnabled(false)
                .withName(R.string.drawer_menu_cone_control)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Cone Control: Skidpad
        FontDrawable skidpadIconCC = new FontDrawable(this, R.string.fa_infinity_solid, true, false);
        skidpadIconCC.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem ccSkidpad = new PrimaryDrawerItem()
                .withIdentifier(80023)
                .withLevel(2)
                .withIcon(skidpadIconCC)
                .withName(R.string.drawer_menu_staff_skidpad)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Cone Control: Autocross
        FontDrawable autocrossIconCC = new FontDrawable(this, R.string.fa_stopwatch_solid, true, false);
        autocrossIconCC.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem ccAutocross = new PrimaryDrawerItem()
                .withIdentifier(80022)
                .withLevel(2)
                .withIcon(autocrossIconCC)
                .withName(R.string.drawer_menu_staff_autocross)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Cone Control: Endurance
        FontDrawable enduranceIconCC = new FontDrawable(this, R.string.fa_flag_checkered_solid, true, false);
        enduranceIconCC.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem ccEndurance = new PrimaryDrawerItem()
                .withIdentifier(80021)
                .withLevel(2)
                .withIcon(enduranceIconCC)
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
        FontDrawable practiceTrackIcon = new FontDrawable(this, R.string.fa_graduation_cap_solid, true, false);
        practiceTrackIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem practiceTrack = new PrimaryDrawerItem()
                .withIdentifier(10011)
                .withLevel(2)
                .withIcon(practiceTrackIcon)
                .withName(R.string.drawer_menu_staff_practice_track)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Skidpad
        FontDrawable skidPadIcon = new FontDrawable(this, R.string.fa_infinity_solid, true, false);
        skidPadIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem skidpad = new PrimaryDrawerItem()
                .withIdentifier(10012)
                .withLevel(2)
                .withIcon(skidPadIcon)
                .withName(R.string.drawer_menu_staff_skidpad)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Acceleration
        FontDrawable accelerationIcon = new FontDrawable(this, R.string.fa_road_solid, true, false);
        accelerationIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem acceleration = new PrimaryDrawerItem()
                .withIdentifier(10013)
                .withLevel(2)
                .withIcon(accelerationIcon)
                .withName(R.string.drawer_menu_staff_acceleration)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Autocross
        FontDrawable autocrossIcon = new FontDrawable(this, R.string.fa_stopwatch_solid, true, false);
        autocrossIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem autocross = new PrimaryDrawerItem()
                .withIdentifier(10014)
                .withLevel(2)
                .withIcon(autocrossIcon)
                .withName(R.string.drawer_menu_staff_autocross)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Access control: Endurance
        FontDrawable enduranceIcon = new FontDrawable(this, R.string.fa_flag_checkered_solid, true, false);
        enduranceIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem endurance = new PrimaryDrawerItem()
                .withIdentifier(10015)
                .withLevel(2)
                .withIcon(enduranceIcon)
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

        //TeamMember management: Team members
        FontDrawable teamMembersIcon = new FontDrawable(this, R.string.fa_users_solid, true, false);
        teamMembersIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem drivers = new PrimaryDrawerItem()
                .withIdentifier(10020)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_users_management_team_members)
                .withIcon(teamMembersIcon)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //TeamMember management: Volunteers
        FontDrawable volunteersIcon = new FontDrawable(this, R.string.fa_laugh, false, false);
        volunteersIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem volunteers = new PrimaryDrawerItem()
                .withIdentifier(10026)
                .withLevel(2)
                .withIcon(volunteersIcon)
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


        //Logout
        FontDrawable logoutIcon = new FontDrawable(this, R.string.fa_sign_out_alt_solid, true, false);
        logoutIcon.setTextColor(ContextCompat.getColor(this, R.color.md_grey_700));
        PrimaryDrawerItem logout = new PrimaryDrawerItem()
                .withIdentifier(30006)
                .withIcon(logoutIcon)
                .withName(R.string.drawer_menu_common_logout)
                .withLevel(2)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        if(loggedUser.getRole().equals(UserRole.ADMINISTRATOR)){
            builder.addDrawerItems(

                    eventControl,
                    briefing,
                    teams,
                    new DividerDrawerItem(),

                    raceAccess,
                    practiceTrack, skidpad, acceleration, autocross, endurance,
                    new DividerDrawerItem(),

                    raceControl,
                    rcSkidpad,
                    rcAutocross,
                    rcEndurance,
                    new DividerDrawerItem(),

                    coneControl,
                    ccSkidpad,
                    ccAutocross,
                    ccEndurance,
                    new DividerDrawerItem(),

                    userManagement,
                    drivers,volunteers,
                    new DividerDrawerItem(),

                    //staffStatistics,
                    //adminOperations,
                    logout);


        }else if(loggedUser.getRole().equals(UserRole.MARSHALL)){
            builder.addDrawerItems(

                    raceAccess,
                    practiceTrack, skidpad, acceleration, autocross, endurance,
                    new DividerDrawerItem(),

                    raceControl,
                    rcSkidpad,
                    rcAutocross,
                    rcEndurance,
                    new DividerDrawerItem(),

                    coneControl,
                    ccSkidpad,
                    ccAutocross,
                    ccEndurance,
                    new DividerDrawerItem(),

                    userManagement,
                    volunteers,
                    new DividerDrawerItem(),

                    logout);

        }else if(loggedUser.getRole().equals(UserRole.SCRUTINEER)){
            builder.addDrawerItems(

                    eventControl,
                    briefing,
                    teams,
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
                    drivers,volunteers,
                    new DividerDrawerItem(),

                    logout);

        }else if(loggedUser.getRole().equals(UserRole.OFFICIAL_MARSHALL)){
            builder.addDrawerItems(

                    raceAccess,
                    practiceTrack, skidpad, acceleration, autocross, endurance,
                    new DividerDrawerItem(),

                    raceControl,
                    rcAutocross,
                    rcEndurance,
                    new DividerDrawerItem(),

                    coneControl,
                    ccAutocross,
                    ccEndurance,
                    new DividerDrawerItem(),

                    userManagement,
                    volunteers,
                    new DividerDrawerItem(),

                    staffStatistics,
                    logout);

        }else if(loggedUser.getRole().equals(UserRole.OFFICIAL_SCRUTINEER)){
            builder.addDrawerItems(

                    teams,
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
                    drivers,volunteers,
                    new DividerDrawerItem(),

                    logout);
        }

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

        Intent intent = null;
        if(drawerItem.getIdentifier() == 10020){ //TeamMember management
            intent = new Intent(this, TeamMemberActivity.class);

        }else if(drawerItem.getIdentifier() == 10001){ //Briefing

        }else if(drawerItem.getIdentifier() == 10013){ //Acceleration
            intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.ACCELERATION);

        }else if(drawerItem.getIdentifier() == 10015){ //Endurance
            intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.ENDURANCE_EFFICIENCY);

        }else if(drawerItem.getIdentifier() == 10014){ //Autocross
            intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.AUTOCROSS);

        }else if(drawerItem.getIdentifier() == 10012){ //SkidPad
            intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.SKIDPAD);

        }else if(drawerItem.getIdentifier() == 10011){ //Practice Track
            intent = new Intent(this, DynamicEventActivity.class);
            intent.putExtra("eventType", EventType.PRACTICE_TRACK);

        }else if(drawerItem.getIdentifier() == 30006){ //Logout
            FirebaseAuth.getInstance().signOut();
            intent = new Intent(this, LoginActivity.class);

        }else if(drawerItem.getIdentifier() == 10016){ //Statistics
            intent = new Intent(this, StatisticsActivity.class);

        }else if(drawerItem.getIdentifier() == 10017) { //Admin operations
            intent = new Intent(this, AdminOpsActivity.class);

        }else if(drawerItem.getIdentifier() == 10021){ //Race Control Endurance
            intent = new Intent(this, RaceControlWelcomeActivity.class);
            intent.putExtra("eventType", RaceControlEvent.ENDURANCE);

        } else if(drawerItem.getIdentifier() == 80021){ //Cone Control Endurance
            intent = new Intent(this, ConeControlWelcomeActivity.class);
            intent.putExtra("eventType", ConeControlEvent.ENDURANCE);

        }else if(drawerItem.getIdentifier() == 10022){ //Race Control Autocross
            intent = new Intent(this, RaceControlWelcomeActivity.class);
            intent.putExtra("eventType", RaceControlEvent.AUTOCROSS);

        }else if(drawerItem.getIdentifier() == 10023){ //Race Control Skidpad
            intent = new Intent(this, RaceControlWelcomeActivity.class);
            intent.putExtra("eventType", RaceControlEvent.SKIDPAD);

        } else if(drawerItem.getIdentifier() == 80022){ //Cone Control Autocross
            intent = new Intent(this, ConeControlWelcomeActivity.class);
            intent.putExtra("eventType", ConeControlEvent.AUTOCROSS);

        } else if(drawerItem.getIdentifier() == 80023){ //Cone Control Skidpad
            intent = new Intent(this, ConeControlWelcomeActivity.class);
            intent.putExtra("eventType", ConeControlEvent.SKIDPAD);

        }else if(drawerItem.getIdentifier() == 10026){ //Users
            intent = new Intent(this, UserActivity.class);

        }else if(drawerItem.getIdentifier() == 70001){ //Teams
            intent = new Intent(this, TeamsActivity.class);
        }

        this.startActivity(intent);
        super.finish();

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

            if(false){
                //Open exit dialog
                GeneralActivityExitDialog exitDialog = GeneralActivityExitDialog.newInstance(this);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(exitDialog, "exit_dialog");
                ft.commitAllowingStateLoss();
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                finish();
            }
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

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.close_activity_slide_out, R.anim.close_activity_slide_in);
    }

    @Override
    public void startActivity(Intent intent){
        super.startActivity(intent);
        overridePendingTransition(R.anim.open_activity_slide_out, R.anim.open_activity_slide_in);
    }
}

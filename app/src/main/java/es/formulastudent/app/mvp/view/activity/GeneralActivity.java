package es.formulastudent.app.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.timeline.TimelineActivity;
import es.formulastudent.app.mvp.view.activity.userlist.UserListActivity;


public class GeneralActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    //Drawer colors
    private static final String SELECTED_DRAWER_ITEM_COLOR = "#e6e6e6";
    private static final String TITLE_DRAWER_ITEM_COLOR = "#e30613";

    //Common elements
    protected Toolbar toolbar;
    protected Drawer drawer;
    protected Long mDrawerIdentifier = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get toolbar component
        toolbar = findViewById(R.id.my_toolbar);
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
                .withIdentifier(10001)
                .withEnabled(false)
                .withName(R.string.drawer_menu_staff)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);


        //Staff: Briefing
        SecondaryDrawerItem briefing = new SecondaryDrawerItem()
                .withIdentifier(10002)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_briefing)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this);

        //Staff: Pre-Scrutineering
        SecondaryDrawerItem preScrutineering = new SecondaryDrawerItem()
                .withIdentifier(10002)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_pre_scrutineering)
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
        SecondaryDrawerItem dynamicEvents = new SecondaryDrawerItem()
                .withIdentifier(10010)
                .withLevel(2)
                .withName(R.string.drawer_menu_staff_dynamic_events)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR))
                .withOnDrawerItemClickListener(this)
                .withSubItems(practiceTrack, skidpad, acceleration, autocross, enduranceEfficiency);

        /*
         * Menu options for participants
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

        /*
         * Menu options for everybody
         */

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


        switch(userRole) {
            case "staff": //TODO if userRole == Staff
                builder.addDrawerItems(
                        fss_status,
                        timeline,
                        scoring,
                        new DividerDrawerItem(),
                        eventControl,
                        briefing,
                        preScrutineering,
                        dynamicEvents,
                        new DividerDrawerItem(),
                        staffUserManagement,
                        settings,
                        logout
                );
                break;

            case "participant": //TODO if userRole == Participant
                builder.addDrawerItems(
                        fss_status,
                        timeline,
                        myTeam,
                        teamMembers,
                        teamStatus,
                        new DividerDrawerItem(),
                        settings,
                        logout
                );
                break;
            default:
        }

        //Build drawer
        drawer = builder.build();

    }


    /**
     * Create the account header for the drawer menu
     * @return
     */
    private AccountHeader createDrawerHeader(){

        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.drawer_background)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("John Doe")
                                .withEmail("john.doe@gmail.com")
                                .withIcon(getResources().getDrawable(R.mipmap.drawer_profile_image))
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
        }

        return false;
    }
}

package es.formulastudent.app.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.formulastudent.app.R;


public class GeneralActivity extends AppCompatActivity {

    //Drawer colors
    private static final String SELECTED_DRAWER_ITEM_COLOR = "#e6e6e6";
    private static final String TITLE_DRAWER_ITEM_COLOR = "#e30613";

    //Common elements
    protected Toolbar toolbar;
    protected Drawer drawer;


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
            drawer.setSelection(30003);
        }

    }


    protected void addDrawer(){

        //TODO eliminar
        String userRole = "participant";

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
         * Menu options for Stuff
         */

        //Stuff
        PrimaryDrawerItem stuff = new PrimaryDrawerItem()
                .withIdentifier(10001)
                .withName(R.string.drawer_menu_stuff)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //Stuff: Scrutineering
        SecondaryDrawerItem stuffScrutineering = new SecondaryDrawerItem()
                .withIdentifier(10002)
                .withName(R.string.drawer_menu_stuff_strutineering)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //Stuff: Users management
        SecondaryDrawerItem stuffUserManagement = new SecondaryDrawerItem()
                .withIdentifier(10003)
                .withName(R.string.drawer_menu_stuff_users_management)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        /*
         * Menu options for participants
         */

        //Team
        PrimaryDrawerItem myTeam = new PrimaryDrawerItem()
                .withIdentifier(20001)
                .withEnabled(false)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withName(R.string.drawer_menu_participant_myTeam)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //Team: Members
        SecondaryDrawerItem teamMembers = new SecondaryDrawerItem()
                .withIdentifier(20002)
                .withLevel(2)
                .withName(R.string.drawer_menu_participant_teamMembers)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //Team: Status
        SecondaryDrawerItem teamStatus = new SecondaryDrawerItem()
                .withIdentifier(20003)
                .withLevel(2)
                .withName(R.string.drawer_menu_participant_status)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        /*
         * Menu options for everybody
         */

        //FSS status
        PrimaryDrawerItem fss_status = new PrimaryDrawerItem()
                .withIdentifier(30001)
                .withEnabled(false)
                .withDisabledTextColor(Color.parseColor(TITLE_DRAWER_ITEM_COLOR))
                .withName(R.string.drawer_menu_common_fss_status)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //General information
        SecondaryDrawerItem generalInformation = new SecondaryDrawerItem()
                .withIdentifier(30003)
                .withLevel(2)
                .withName(R.string.drawer_menu_common_general_information)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //Rankings
        SecondaryDrawerItem rankings = new SecondaryDrawerItem()
                .withIdentifier(30004)
                .withLevel(2)
                .withName(R.string.drawer_menu_common_ranking)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));


        //Settings
        PrimaryDrawerItem settings = new PrimaryDrawerItem()
                .withIdentifier(30005)
                .withName(R.string.drawer_menu_common_settings)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));

        //Logout
        PrimaryDrawerItem logout = new PrimaryDrawerItem()
                .withIdentifier(30006)
                .withName(R.string.drawer_menu_common_logout)
                .withSelectedColor(Color.parseColor(SELECTED_DRAWER_ITEM_COLOR));


        switch(userRole) {
            case "stuff": //TODO if userRole == Stuff
                builder.addDrawerItems(
                        fss_status,
                        generalInformation,
                        rankings,
                        stuff,
                        stuffScrutineering,
                        stuffUserManagement,
                        new DividerDrawerItem(),
                        settings,
                        logout
                );
                break;

            case "participant": //TODO if userRole == Participant
                builder.addDrawerItems(
                        fss_status,
                        generalInformation,
                        rankings,
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

}

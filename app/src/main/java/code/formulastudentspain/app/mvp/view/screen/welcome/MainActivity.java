package code.formulastudentspain.app.mvp.view.screen.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.databinding.ActivityMainBinding;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerWelcomeComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.mvp.data.model.ConeControlEvent;
import code.formulastudentspain.app.mvp.data.model.DrawerMenu;
import code.formulastudentspain.app.mvp.data.model.EventType;
import code.formulastudentspain.app.mvp.data.model.RaceControlEvent;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.login.LoginActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    User loggedUser;

    private DrawerLayout drawerLayout;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(FSSApp.getApp().component());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        drawerLayout = binding.drawerLayout;
        binding.setMenu(new DrawerMenu(loggedUser));

        navController = Navigation.findNavController(this, R.id.myNavHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(binding.navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.drawerItemBriefing:
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToBriefingFragment());
                break;
            case R.id.drawerItemTeams:
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToTeamsFragment());
                break;
            case R.id.drawerItemRAPracticeTrack:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceAccessFragment(
                                EventType.PRACTICE_TRACK,
                                EventType.PRACTICE_TRACK.getName()));
                break;
            case R.id.drawerItemRASkidpad:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceAccessFragment(
                                EventType.SKIDPAD,
                                EventType.SKIDPAD.getName()));
                break;
            case R.id.drawerItemRAAcceleration:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceAccessFragment(
                                EventType.ACCELERATION,
                                EventType.ACCELERATION.getName()));
                break;
            case R.id.drawerItemRAAutoCross:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceAccessFragment(
                                EventType.AUTOCROSS,
                                EventType.AUTOCROSS.getName()));
                break;
            case R.id.drawerItemRAEndurance:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceAccessFragment(
                                EventType.ENDURANCE_EFFICIENCY,
                                EventType.ENDURANCE_EFFICIENCY.getName()));
                break;
            case R.id.drawerItemRCSkidpad:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceControlWelcomeFragment(
                                RaceControlEvent.SKIDPAD,
                                getString(RaceControlEvent.SKIDPAD.getActivityTitle())));
                break;
            case R.id.drawerItemRCAutocross:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceControlWelcomeFragment(
                                RaceControlEvent.AUTOCROSS,
                                getString(RaceControlEvent.AUTOCROSS.getActivityTitle())));
                break;
            case R.id.drawerItemRCEndurance:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToRaceControlWelcomeFragment(
                                RaceControlEvent.ENDURANCE,
                                getString(RaceControlEvent.ENDURANCE.getActivityTitle())));
                break;
            case R.id.drawerItemCCSkidpad:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToConeControlWelcomeFragment(
                                ConeControlEvent.SKIDPAD,
                                getString(ConeControlEvent.SKIDPAD.getActivityTitle())));
                break;
            case R.id.drawerItemCCAutocross:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToConeControlWelcomeFragment(
                                ConeControlEvent.AUTOCROSS,
                                getString(ConeControlEvent.AUTOCROSS.getActivityTitle())));
                break;
            case R.id.drawerItemCCEndurance:
                navController.navigate(WelcomeFragmentDirections
                        .actionWelcomeFragmentToConeControlWelcomeFragment(
                                ConeControlEvent.ENDURANCE,
                                getString(ConeControlEvent.ENDURANCE.getActivityTitle())));
                break;
            case R.id.drawerItemUser:
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToUserFragment());
                break;
            case R.id.drawerItemTeamMembers:
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToTeamMemberFragment());
                break;
            case R.id.drawerItemAdminOperations:
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAdminOpsFragment());
                break;
            case R.id.drawerItemStatistics:
                navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToStatistics());
                break;
            case R.id.drawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                super.finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        this.lockDrawer();
    }

    public void lockDrawer(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawer(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onBackPressed() {
        if(isBackPressedCritical()){
            Toast.makeText(this,
                    "Disabled for safety reasons. To exit, tap on back arrow on top bar.",
                    Toast.LENGTH_LONG).show(); //TODO avoid toasts
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            super.onBackPressed();
        }
    }

    public boolean isBackPressedCritical() {
        return Objects.requireNonNull(navController.getCurrentDestination())
                        .getArguments().containsKey("coneControlEvent")
                || navController.getCurrentDestination()
                        .getArguments().containsKey("raceControlEvent");
    }

}

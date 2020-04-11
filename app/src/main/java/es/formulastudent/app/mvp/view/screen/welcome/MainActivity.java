package es.formulastudent.app.mvp.view.screen.welcome;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import es.formulastudent.app.R;
import es.formulastudent.app.databinding.ActivityMainBinding;
import es.formulastudent.app.mvp.data.model.EventType;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        drawerLayout = binding.drawerLayout;

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
        switch (v.getId()){
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
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}

package code.formulastudentspain.app.mvp.view.screen.egresschrono;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

import javax.inject.Inject;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.R;
import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerPreScrutineeringComponent;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.activity.PreScrutineeringModule;
import code.formulastudentspain.app.mvp.data.model.EgressRegister;
import code.formulastudentspain.app.mvp.data.model.PreScrutineeringRegister;
import code.formulastudentspain.app.mvp.view.Utils;
import code.formulastudentspain.app.mvp.view.screen.general.GeneralActivity;
import code.formulastudentspain.app.mvp.view.screen.egresschrono.dialog.EgressChronoConfirmTimeDialog;

public class EgressChronoActivity extends GeneralActivity implements View.OnClickListener, EgressChronoPresenter.View {

    @Inject
    EgressChronoPresenter presenter;

    int progressStatus = 0;
    Thread progressBarThread;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int MilliSeconds ;

    //View components
    CircularProgressBar circularProgressBar;
    TextView chronoText;
    Button startStopChrono;
    TextView firstAttempt;
    TextView secondAttempt;
    TextView thirdAttempt;

    boolean chronoStarted = false;
    boolean chronoStopped = false;

    Handler handlerProgressBar = new Handler();
    Handler handlerChrono = new Handler();

    Runnable run;

    PreScrutineeringRegister preScrutineeringRegister;
    EgressRegister egressRegister;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_egress_chono);
        super.onCreate(savedInstanceState);

        //Get data from previous activity
        preScrutineeringRegister = (PreScrutineeringRegister) getIntent().getSerializableExtra("prescrutineering_register");

        //Get Egress register
        presenter.getEgressRegister(preScrutineeringRegister.getID());

        initViews();
    }



    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerPreScrutineeringComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .preScrutineeringModule(new PreScrutineeringModule(this))
                .build()
                .inject(this);
    }

    private void initViews() {

        //Bind components
        circularProgressBar = findViewById(R.id.progress_circular);
        chronoText = findViewById(R.id.chronoText);
        startStopChrono = findViewById(R.id.start_stop_chrono);
        startStopChrono.setOnClickListener(this);
        firstAttempt = findViewById(R.id.firstAttemptValue);
        secondAttempt = findViewById(R.id.secondAttemptValue);
        thirdAttempt = findViewById(R.id.thirdAttemptValue);

        initProgressStatusThread();
    }


    Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            chronoText.setText(Utils.chronoFormatter(UpdateTime));
            handlerChrono.postDelayed(this, 0);
        }
    };


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.start_stop_chrono){

            if(!chronoStarted){
                //Start
                chronoStarted = true;
                chronoStopped = false;
                StartTime = SystemClock.uptimeMillis();
                progressBarThread.start();
                handlerChrono.postDelayed(runnable, 0);
                startStopChrono.setText(getString(R.string.prescruti_detail_activity_button_stop));
            }else {
                //Stop
                chronoStarted = false;
                chronoStopped = true;
                progressStatus = 0;
                initProgressStatusThread();
                circularProgressBar.setProgress(progressStatus);
                circularProgressBar.enableIndeterminateMode(false);
                handlerChrono.removeCallbacks(runnable);
                startStopChrono.setText(getString(R.string.prescruti_detail_activity_button_start));
                if(MillisecondTime <= 5000){
                    openConfirmTimeDialog(MillisecondTime);
                }else{
                    resetTime();
                    handleUnsuccessfulTime(MillisecondTime);
                }
            }
        }
    }

    private void initProgressStatusThread() {

        // Start long running operation in a background thread
        progressBarThread = new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100 && !chronoStopped) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view

                    run = new Runnable() {
                        public void run() {
                            circularProgressBar.setProgress(progressStatus);
                        }
                    };

                    handlerProgressBar.post(run);


                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void resetTime(){
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        MilliSeconds = 0 ;
        chronoText.setText("00:00:000");
    }


    public void handleSuccessfulTime(Long time){
        presenter.saveTime(egressRegister.getId(), preScrutineeringRegister.getID(), time);
    }

    @Override
    public void returnResult(Long time){
        Intent returnIntent = new Intent();
        ArrayList<String> result = new ArrayList<>();
        result.add(0, time.toString());
        result.add(1, preScrutineeringRegister.getID());
        returnIntent.putStringArrayListExtra("result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    public void handleUnsuccessfulTime(Long time){
        presenter.saveTime(egressRegister.getId(), preScrutineeringRegister.getID(), time);
    }


    private void openConfirmTimeDialog(Long time){

        EgressChronoConfirmTimeDialog confirmDialog = EgressChronoConfirmTimeDialog.newInstance(this, time);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(confirmDialog, "confirm_dialog");
        ft.commitAllowingStateLoss();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoadingIcon() {
        super.hideLoadingDialog();
    }

    @Override
    public void updateTimingValues(EgressRegister register) {
        this.egressRegister = register;

        //Set values
        if(egressRegister.getFirstAttempt() != null){
            firstAttempt.setText(Utils.chronoFormatter(egressRegister.getFirstAttempt()));
        }else{
            firstAttempt.setText("-");
        }
        if(egressRegister.getSecondAttempt() != null){
            secondAttempt.setText(Utils.chronoFormatter(egressRegister.getSecondAttempt()));
        }else{
            secondAttempt.setText("-");
        }
        if(egressRegister.getThirdAttempt() != null){
            thirdAttempt.setText(Utils.chronoFormatter(egressRegister.getThirdAttempt()));
        }else{
            thirdAttempt.setText("-");
        }

        //Check times to disable button
        if(egressRegister.getFirstAttempt() != null && egressRegister.getFirstAttempt() <= 5000){
            startStopChrono.setEnabled(false);
            firstAttempt.setTextColor(Color.parseColor("#A5D6A7"));
        } else if(egressRegister.getSecondAttempt() != null && egressRegister.getSecondAttempt() <= 5000){
            startStopChrono.setEnabled(false);
            secondAttempt.setTextColor(Color.parseColor("#A5D6A7"));
        }else if(egressRegister.getThirdAttempt() != null && egressRegister.getThirdAttempt() <= 5000){
            startStopChrono.setEnabled(false);
            thirdAttempt.setTextColor(Color.parseColor("#A5D6A7"));
        }

    }
}

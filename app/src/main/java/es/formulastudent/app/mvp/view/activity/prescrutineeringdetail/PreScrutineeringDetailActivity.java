package es.formulastudent.app.mvp.view.activity.prescrutineeringdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.view.Utils;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;

public class PreScrutineeringDetailActivity extends GeneralActivity implements View.OnClickListener {

        int progressStatus = 0;
        Thread progressBarThread;

        long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
        int Seconds, Minutes, MilliSeconds ;

        //View components
        CircularProgressBar circularProgressBar;
        TextView chronoText;
        Button startStopChrono;

        boolean chronoStarted = false;
        boolean chronoStopped = false;

        Handler handlerProgressBar = new Handler();
        Handler handlerChrono = new Handler();

    Runnable run;

    PreScrutineeringRegister register;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.activity_prescrutineering_detail);
            super.onCreate(savedInstanceState);

            register = (PreScrutineeringRegister) getIntent().getSerializableExtra("prescrutineering_register");

            initViews();
        }

        private void initViews() {

            //Add toolbar title
            setToolbarTitle(getString(R.string.activity_timeline_detail_label));

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            //Bind components
            circularProgressBar = findViewById(R.id.progress_circular);
            chronoText = findViewById(R.id.chronoText);
            startStopChrono = findViewById(R.id.start_stop_chrono);
            startStopChrono.setOnClickListener(this);


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
                    startStopChrono.setText("Stop");
                }else {
                    //Stop
                    chronoStarted = false;
                    chronoStopped = true;
                    progressStatus = 0;
                    initProgressStatusThread();
                    circularProgressBar.setProgress(progressStatus);
                    circularProgressBar.enableIndeterminateMode(false);
                    handlerChrono.removeCallbacks(runnable);
                    startStopChrono.setText("Start");
                    if(MillisecondTime <= 5000){
                        handleSuccessfulTime(MillisecondTime);
                    }else{
                        resetTime();
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

    private void resetTime(){
            MillisecondTime = 0L ;
            StartTime = 0L ;
            TimeBuff = 0L ;
            UpdateTime = 0L ;
            Seconds = 0 ;
            Minutes = 0 ;
            MilliSeconds = 0 ;

            chronoText.setText("00:00:000");
        }


    protected void handleSuccessfulTime(Long time){
        Intent returnIntent = new Intent();
        ArrayList<String> result = new ArrayList<>();
        result.add(0, time.toString());
        result.add(1, register.getID());
        returnIntent.putStringArrayListExtra("result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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


}

package es.formulastudent.app.mvp.view.activity.fssinformation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.NFCReaderActivity;


public class FssInformationActivity extends NFCReaderActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_fss_information);
        super.onCreate(savedInstanceState);
        initViews();
    }


    @Override
    protected void onResume(){
        super.onResume();
    }

    private void initViews(){
        //Add drawer
        addDrawer();

        //Add toolbar title
        setToolbarTitle("eeeeiiiii");
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
       // Toast.makeText(this, "Discovered tag " + ++mCount + " with intent: " + intent, Toast.LENGTH_SHORT).show();
    }



}

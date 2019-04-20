package es.formulastudent.app.mvp.view.activity.fssinformation;

import android.os.Bundle;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;


public class FssInformationActivity extends GeneralActivity {


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
    }


}

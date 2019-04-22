package es.formulastudent.app.mvp.view.activity.fssinformation;

import android.os.Bundle;
import android.widget.Toast;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.NFCReaderActivity;


public class FssInformationActivity extends NFCReaderActivity {

    @Override
    public void onCreate(Bundle savedState) {
        setContentView(R.layout.activity_fss_information);
        super.onCreate(savedState);
        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initViews(){
        //Add drawer
        addDrawer();

        //Add toolbar title
        setToolbarTitle("eeeeiiiii");
    }

    @Override
    protected void handleReadTAG(String tag){
        Toast.makeText(this, "TAG: "+ tag, Toast.LENGTH_SHORT).show();
    }

}



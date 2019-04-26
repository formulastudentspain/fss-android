package es.formulastudent.app.mvp.view.activity.fssinformation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.NFCReaderActivity;


public class FssInformationActivity extends GeneralActivity implements View.OnClickListener{

    private Button readNFCButton;
    private static final int NFC_REQUEST_CODE = 101;


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

        readNFCButton = findViewById(R.id.readNFCButton);
        readNFCButton.setOnClickListener(this);

        //Add toolbar title
        setToolbarTitle("eeeeiiiii");
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.readNFCButton){
            Intent i = new Intent(this, NFCReaderActivity.class);
            startActivityForResult(i, NFC_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NFC_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(this, "TAG: "+result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}



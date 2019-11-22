package es.formulastudent.app.mvp.view.activity.qrreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import es.formulastudent.app.R;

public class QRReaderActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_reader);
        this.activity = this;
        initViews();

    }

   private void initViews(){
       CodeScannerView scannerView = findViewById(R.id.scanner_view);
       mCodeScanner = new CodeScanner(this, scannerView);
       mCodeScanner.setDecodeCallback(new DecodeCallback() {
           @Override
           public void onDecoded(@NonNull final Result result) {
               activity.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Intent returnIntent = new Intent();
                       returnIntent.putExtra("result", result.getText());
                       setResult(Activity.RESULT_OK, returnIntent);
                       finish();
                   }
               });
           }
       });

       scannerView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mCodeScanner.startPreview();
           }
       });
   }

    @Override
    protected void onResume(){
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}

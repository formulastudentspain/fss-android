package es.formulastudent.app.mvp.view.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;

import es.formulastudent.app.R;


public class NFCReaderActivity extends GeneralActivity {

    private NfcAdapter mAdapter;

    //public static final String MIME_TEXT_PLAIN = "text/plain";
    //public static final String TAG = "NfcDemo";
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    @Override
    public void onCreate(Bundle savedState) {
        setContentView(R.layout.activity_nfc_reader);
        super.onCreate(savedState);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, mAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity, activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }


    protected void handleIntent(Intent intent) {
        String action = intent.getAction();


       /* if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                //new AsyncNdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    // new AsyncNdefReaderTask().execute(tag);
                    break;
                }
            }
        }else */
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String tagHex = bytesToHexString(tag.getId());
            handleReadTAG(tagHex);
        }
    }


    private String bytesToHexString(byte[] src) {
        char[] hexChars = new char[src.length * 2];
        for ( int j = 0; j < src.length; j++ ) {
            int v = src[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected void handleReadTAG(String tag){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", tag);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}



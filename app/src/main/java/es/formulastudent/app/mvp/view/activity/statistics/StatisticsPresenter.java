package es.formulastudent.app.mvp.view.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;

import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.IOException;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.statistics.StatisticsBO;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.view.activity.statistics.dialog.ExportDialog;

public class StatisticsPresenter {

    //Dependencies
    private StatisticsPresenter.View view;
    private Context context;
    private StatisticsBO statisticsBO;

    //Generated File
    private String filePath;


    public StatisticsPresenter(StatisticsPresenter.View view, Context context, StatisticsBO statisticsBO) {
        this.view = view;
        this.context = context;
        this.statisticsBO = statisticsBO;
    }


    public void exportDynamicEvent(EventType eventType){

        try {
            statisticsBO.exportDynamicEvent(eventType, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {

                    String path = (String)responseDTO.getData();
                    filePath = path;

                    openExportDialog();

                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDirectory() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try {
            File file = new File(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
            context.startActivity(intent);
        }catch (Exception e){
            view.createMessage("Unable to open file, try send it my email.");
        }
    }

    public void sendMail() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File fileLocation = new File(filePath);
        Uri path = Uri.fromFile(fileLocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        // set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"dpconde.me@gmail.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        // the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
        context.startActivity(Intent.createChooser(emailIntent , "Send email..."));

    }

    private void openExportDialog(){
        //With all the information, we open the dialog
        FragmentManager fm = ((StatisticsActivity)view.getActivity()).getSupportFragmentManager();
        ExportDialog exportDialog = ExportDialog.newInstance(this);
        exportDialog.show(fm, "fragment_export_dialog");
    }


    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void createMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();

        /**
         * Return the activity
         * @return
         */
        StatisticsActivity getActivity();
    }
}

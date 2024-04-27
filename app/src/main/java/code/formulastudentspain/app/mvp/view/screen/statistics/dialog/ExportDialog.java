package code.formulastudentspain.app.mvp.view.screen.statistics.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.view.screen.statistics.StatisticsPresenter;

public class ExportDialog extends DialogFragment implements View.OnClickListener {

    private AlertDialog dialog;

    //View elements
    private ImageView sendMail;
    private ImageView openDirectory;

    //Presenter
    private StatisticsPresenter presenter;

    public ExportDialog() {}

    public static ExportDialog newInstance(StatisticsPresenter presenter) {
        ExportDialog frag = new ExportDialog();
        frag.setPresenter(presenter);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_export_statistics, null);
        initializeElements(rootView);

        builder.setView(rootView)
                    .setTitle(R.string.statistics_dialog_title)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ExportDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }



    private void initializeElements(View rootView){

        sendMail = rootView.findViewById(R.id.exportSendMail);
        sendMail.setOnClickListener(this);

        openDirectory = rootView.findViewById(R.id.exportOpenDirectory);
        openDirectory.setOnClickListener(this);

    }


    public void setPresenter(StatisticsPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.exportSendMail){
            presenter.sendMail();
        }else if(view.getId() == R.id.exportOpenDirectory){
            presenter.openDirectory();
        }
        ExportDialog.this.getDialog().cancel();
    }
}
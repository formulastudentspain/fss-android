package code.formulastudentspain.app.mvp.view.screen.userdetail.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.Device;
import code.formulastudentspain.app.mvp.view.screen.userdetail.UserDetailPresenter;

public class ReturnDeviceDialog extends DialogFragment {

    private UserDetailPresenter presenter;
    private Context context;
    private AlertDialog dialog;
    private Device device;

    public ReturnDeviceDialog() {}

    public static ReturnDeviceDialog newInstance(UserDetailPresenter presenter, Context context, Device device) {
        ReturnDeviceDialog frag = new ReturnDeviceDialog();
        frag.setPresenter(presenter);
        frag.setContext(context);
        frag.setDevice(device);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_user_detail_return_device, null);

        builder.setView(rootView)
                .setTitle(context.getString(R.string.dialog_return_device_title, device))

                //Action buttons
                .setPositiveButton(R.string.dialog_return_device_return, null)
                .setNegativeButton(R.string.dialog_edit_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ReturnDeviceDialog.this.getDialog().cancel();
                    }
                });

        dialog = builder.create();

        return dialog;
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Call business
                presenter.returnDevice(device);

                //Close dialog
                dialog.dismiss();

            }
        });
    }


    public void setPresenter(UserDetailPresenter presenter) {
        this.presenter = presenter;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setDevice(Device device) {
        this.device = device;
    }

}

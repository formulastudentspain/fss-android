package es.formulastudent.app.mvp.view.screen.userdetail.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Device;
import es.formulastudent.app.mvp.view.screen.userdetail.UserDetailPresenter;

public class AssignDeviceDialog extends DialogFragment {

    private UserDetailPresenter presenter;
    private Context context;
    private AlertDialog dialog;
    private Device device;

    //View components
    private EditText deviceNumber;


    public AssignDeviceDialog() {}

    public static AssignDeviceDialog newInstance(UserDetailPresenter presenter, Context context, Device device) {
        AssignDeviceDialog frag = new AssignDeviceDialog();
        frag.setPresenter(presenter);
        frag.setContext(context);
        frag.setDevice(device);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_user_detail_assign_device, null);

        // Get view components
        deviceNumber = rootView.findViewById(R.id.assign_user_device_number);

        builder.setView(rootView)
                .setTitle(context.getString(R.string.dialog_assign_device_title, device))

                //Action buttons
                .setPositiveButton(R.string.dialog_assign_device_assign, null)
                .setNegativeButton(R.string.dialog_edit_user_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AssignDeviceDialog.this.getDialog().cancel();
                    }
                });

        dialog = builder.create();

        return dialog;
    }

    private boolean validateFields(){

        //Device number
        Long deviceNumberValue = Long.parseLong(deviceNumber.getText().toString().trim());
        if(deviceNumberValue == 0L || deviceNumberValue < 0L){
            deviceNumber.setError(getString(R.string.dialog_assign_device_error_wrong_number));
            return false;
        }

        return true;
    }

    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateFields()){
                    Long deviceNumberValue = Long.parseLong(deviceNumber.getText().toString().trim());

                    //Call business
                    presenter.assignDevice(device, deviceNumberValue);

                    //Close dialog
                    dialog.dismiss();
                }
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

package code.formulastudentspain.app.mvp.view.screen.briefing.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.EventRegister;
import code.formulastudentspain.app.mvp.view.screen.briefing.BriefingPresenter;

public class DeleteEventRegisterDialog extends DialogFragment {

    private BriefingPresenter presenter;
    private EventRegister register;

    public DeleteEventRegisterDialog() {
    }

    public static DeleteEventRegisterDialog newInstance(BriefingPresenter presenter, EventRegister register) {
        DeleteEventRegisterDialog frag = new DeleteEventRegisterDialog();
        frag.setPresenter(presenter);
        frag.setRegister(register);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String message = getString(R.string.dynamic_event_delete_register_dialog_message);

        builder.setTitle(getString(R.string.dynamic_event_delete_register_dialog_title))
            .setMessage(message)
            .setPositiveButton(R.string.dynamic_event_delete_register_dialog_delete_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    presenter.deleteBriefingRegister(register.getID());
                }
            })
            .setNegativeButton(R.string.dynamic_event_delete_register_dialog_cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    DeleteEventRegisterDialog.this.dismiss();
                }
            });

        return builder.create();
    }

    public void setPresenter(BriefingPresenter presenter) {
        this.presenter = presenter;
    }

    public void setRegister(EventRegister register) {
        this.register = register;
    }
}


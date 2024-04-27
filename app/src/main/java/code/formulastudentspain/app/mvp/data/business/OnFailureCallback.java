package code.formulastudentspain.app.mvp.data.business;

import code.formulastudentspain.app.mvp.view.utils.messages.Message;

public interface OnFailureCallback {

    /**
     * Callback method to manage failure response
     * @param errorMessage
     */
    void onFailure(Message errorMessage);
}

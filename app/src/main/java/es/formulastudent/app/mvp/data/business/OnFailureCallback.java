package es.formulastudent.app.mvp.data.business;

import es.formulastudent.app.mvp.view.utils.messages.Message;

public interface OnFailureCallback {

    /**
     * Callback method to manage failure response
     * @param errorMessage
     */
    void onFailure(Message errorMessage);
}

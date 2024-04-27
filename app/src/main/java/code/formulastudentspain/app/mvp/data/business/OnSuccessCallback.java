package code.formulastudentspain.app.mvp.data.business;

public interface OnSuccessCallback<T> {

    /**
     * Callback method to manage success response
     * @param response
     */
    void onSuccess(T response);
}

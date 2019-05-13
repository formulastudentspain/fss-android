package es.formulastudent.app.mvp.data.api;

public interface BusinessCallback {

    /**
     * Callback method to manage success response
     * @param responseDTO
     */
    void onSuccess(ResponseDTO responseDTO);

    /**
     * Callback method to manage failure response
     * @param responseDTO
     */
    void onFailure(ResponseDTO responseDTO);


}

package es.formulastudent.app.mvp.data.business;

public interface DataConsumer {
    /**
     * Method to know if data is actually being loaded
     * @param loading: True if data is loading, false if not
     */
    void loadingData(boolean loading);
}

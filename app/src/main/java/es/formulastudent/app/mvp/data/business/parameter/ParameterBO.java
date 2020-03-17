package es.formulastudent.app.mvp.data.business.parameter;

import com.google.firebase.firestore.ListenerRegistration;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface ParameterBO {

    /**
     * Get parameter by key
     * @param key
     * @param callback
     */
    void getItemByKey(String key, BusinessCallback callback);

    /**
     * Get parameter by key in Real-Time
     * @param key
     * @param callback
     * @return
     */
    ListenerRegistration getItemByKeyInRealTime(String key, BusinessCallback callback);

    /**
     * Create a parameter
     * @param key
     * @param value
     * @param callback
     */
    void createItem(String key, Object value, BusinessCallback callback);

}

package es.formulastudent.app.mvp.data.api.user;

import es.formulastudent.app.mvp.data.api.BusinessCallback;

public interface UserBO {


    /**
     * Retrieve user by NFC tag
     * @param tag
     * @param callback
     */
    void retrieveUserByNFCTag(String tag, BusinessCallback callback);

}
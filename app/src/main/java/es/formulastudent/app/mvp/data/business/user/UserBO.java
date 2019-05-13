package es.formulastudent.app.mvp.data.business.user;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface UserBO {


    /**
     * Retrieve user by NFC tag
     * @param tag
     * @param callback
     */
    void retrieveUserByNFCTag(String tag, BusinessCallback callback);

}
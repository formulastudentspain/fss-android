package es.formulastudent.app.mvp.data.business.user;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.User;

public interface UserBO {


    /**
     * Retrieve user by NFC tag
     * @param tag
     * @param callback
     */
    void retrieveUserByNFCTag(String tag, BusinessCallback callback);

    /**
     * Retrieve all users
     * @param callback
     */
    void retrieveUsers(BusinessCallback callback);


    /**
     * Create user
     * @param user
     * @param callback
     */
    void createUser(User user, BusinessCallback callback);

}
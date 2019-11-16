package es.formulastudent.app.mvp.data.business.auth;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface AuthBO {

    /**
     * Method to authenticate user with mail
     * @param mail
     * @param password
     * @param callback
     */
    void doLoginWithMail(String mail, String password, BusinessCallback callback);

    /**
     * Reset password when password is forgotten
     * @param mail
     * @param callback
     */
    void resetPassword(String mail, BusinessCallback callback);


    /**
     * Create user in auth system
     * @param mail
     */
    void createUser(String mail, BusinessCallback callback);

}
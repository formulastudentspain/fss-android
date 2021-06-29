package es.formulastudent.app.mvp.data.business.auth;

import androidx.annotation.NonNull;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;

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
     * @param name
     * @param mail
     * @param password
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createUser(String name, String mail, String password,
                    @NonNull OnSuccessCallback<?> onSuccessCallback,
                    @NonNull OnFailureCallback onFailureCallback);

}
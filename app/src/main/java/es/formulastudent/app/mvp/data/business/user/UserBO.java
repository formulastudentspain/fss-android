package es.formulastudent.app.mvp.data.business.user;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;

public interface UserBO extends DataLoader.Consumer {

    /**
     * Retrieve all users
     * @param callback
     */
    void retrieveUsers(UserRole selectedRole, BusinessCallback callback);


    /**
     * Create user in both Database and Auth
     * @param user
     * @param callback
     */
    void createUser(User user, BusinessCallback callback);


    /**
     * Get a user by mail
     * @param mail
     * @param callback
     */
    void retrieveUserByMail(String mail, BusinessCallback callback);


    /**
     * Edit user in Database
     * @param user
     * @param callback
     */
    void editUser(User user, BusinessCallback callback);
}

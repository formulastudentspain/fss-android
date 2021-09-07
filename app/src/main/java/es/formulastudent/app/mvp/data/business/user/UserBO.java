package es.formulastudent.app.mvp.data.business.user;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;

public interface UserBO extends DataLoader.Consumer {

    /**
     * Retrieve all users
     * @param selectedRole
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveUsers(UserRole selectedRole, @NotNull OnSuccessCallback<List<User>> onSuccessCallback,
                       @NotNull OnFailureCallback onFailureCallback);


    /**
     * Create user in both Database and Auth
     * @param user
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void createUser(User user, @NotNull OnSuccessCallback<?> onSuccessCallback,
                    @NotNull OnFailureCallback onFailureCallback);


    /**
     * Get a user by mail
     * @param mail
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void retrieveUserByMail(String mail, @NotNull OnSuccessCallback<User> onSuccessCallback,
                            @NotNull OnFailureCallback onFailureCallback);

    /**
     * Edit user in Database
     * @param user
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void editUser(User user, @NotNull OnSuccessCallback<?> onSuccessCallback,
                  @NotNull OnFailureCallback onFailureCallback);
}

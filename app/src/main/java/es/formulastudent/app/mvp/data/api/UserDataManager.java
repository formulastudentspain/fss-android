package es.formulastudent.app.mvp.data.api;


import java.util.List;

import es.formulastudent.app.mvp.data.model.User;


public interface UserDataManager {

    /**
     * Delete user
     * @param userId
     * @return success
     */
    boolean deleteUser(String userId);


    /**
     * Get all users
     * @param callback
     */
    void getAllUsers(Callback callback);


    /**
     * Get paginated user results
     * @param pageNumber
     * @param resultsPerPage
     * @return paginated users
     */
    List<User> getPaginatedUsers(int pageNumber, int resultsPerPage);


    /**
     * Get user by ID
     * @param userId
     * @return the retrieved user
     */
    User getUserById(Long userId);


    /**
     * Create a new user
     * @param user
     * @return the user created with the persisted details
     */
    User createUser(User user);


    /**
     * Create a list of users
     * @param userList
     * @return the list of user created with the persisted details
     */
    List<User> createUsers(List<User> userList);


    /**
     * Get total number of users
     * @return results count
     */
    int countTotalResults();


    /**
     * Delete all users
     */
    void deleteAllUsers();


    /**
     * Load paginated data and use callback to manage the results
     * @param callback
     * @param pageNumber
     * @param resultsPerPage
     */
    void loadData(Callback callback, int pageNumber, int resultsPerPage);
}

package es.formulastudent.app.mvp.data.api;


import java.util.List;

import es.formulastudent.app.mvp.data.model.User;


public interface Callback {

    /**
     * Method to manage retrieved users
     * @param userList
     */
    void onRetrievedUsers(List<User> userList);
}

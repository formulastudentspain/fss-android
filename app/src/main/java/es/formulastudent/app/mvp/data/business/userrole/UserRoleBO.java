package es.formulastudent.app.mvp.data.business.userrole;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface UserRoleBO {

    /**
     * Retrieve all users roles
     * @param callback
     */
    void retrieveUserRoles(BusinessCallback callback);

}
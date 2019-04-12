package es.formulastudent.app.mvp.data.api.greendao;

import android.os.AsyncTask;

import es.formulastudent.app.mvp.data.api.Callback;
import es.formulastudent.app.mvp.data.api.UserDataManager;
import es.formulastudent.app.mvp.data.model.DaoSession;
import es.formulastudent.app.mvp.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataManagerGreenDAO implements UserDataManager{

    UserDataManager userDataManagerRetrofit;
    DaoSession daoSession;

    public UserDataManagerGreenDAO(UserDataManager userDataManagerRetrofit, DaoSession daoSession) {
        this.userDataManagerRetrofit = userDataManagerRetrofit;
        this.daoSession = daoSession;
    }

    @Override
    public boolean deleteUser(String userId) {
        try{
            daoSession.getUserDao().deleteByKey(Long.parseLong(userId));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void getAllUsers(Callback callback) {
        callback.onRetrievedUsers(daoSession.getUserDao().loadAll());
    }

    @Override
    public List<User> getPaginatedUsers(int pageNumber, int resultsPerPage) {
        List<User> userList = new ArrayList<>(daoSession.getUserDao().queryBuilder().offset(resultsPerPage * (pageNumber - 1)).limit(resultsPerPage).list());
        return userList;
    }


    @Override
    public User getUserById(Long userId) {
        return daoSession.getUserDao().load(userId);
    }


    @Override
    public User createUser(User user) {
        user.setLocationId(daoSession.getLocationDao().insert(user.getLocation(null)));
        user.setNameId(daoSession.getNameDao().insert(user.getName(null)));
        user.setPictureId(daoSession.getPictureDao().insert(user.getPicture(null)));
        daoSession.getUserDao().insert(user);
        return user;
    }

    @Override
    public int countTotalResults() {
        return daoSession.getUserDao().loadAll().size();
    }


    @Override
    public List<User> createUsers(List<User> userList) {
        List<User> persistedUserList = new ArrayList<>();
        for(User user: userList){
            persistedUserList.add(createUser(user));
        }
        return persistedUserList;
    }


    @Override
    public void deleteAllUsers(){
        daoSession.getUserDao().deleteAll();
    }


    @Override
    public void loadData(final Callback callback, final int pageNumber, final int resultsPerPage){

        //If there is no users in the local persistence...
        if(countTotalResults()==0){

            //Get users from online API and save them in Realm Database
            userDataManagerRetrofit.getAllUsers(new Callback() {
                @Override
                public void onRetrievedUsers(final List<User> userList) {
                   new InitDataExecutor(callback, userList).execute(pageNumber, resultsPerPage);
                }
            });

        }else{
            callback.onRetrievedUsers(getPaginatedUsers(pageNumber, resultsPerPage));
        }
    }


    /**
     * AsyncTask to create all users in the GreenDAO environment
     */
    private class InitDataExecutor extends AsyncTask<Integer, Void, List<User>> {
        Callback callback;
        List<User> userList;

        public InitDataExecutor(Callback callback, List<User> userList) {
            this.callback = callback;
            this.userList = userList;
        }

        @Override
        protected List<User> doInBackground(Integer... params) {
            deleteAllUsers();
            createUsers(userList);
            return getPaginatedUsers(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            callback.onRetrievedUsers(userList);
        }
    }

}

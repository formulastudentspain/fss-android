package es.formulastudent.app.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.data.api.UserDataManager;
import es.formulastudent.app.mvp.data.api.greendao.UserDataManagerGreenDAO;
import es.formulastudent.app.mvp.data.api.retrofit.UserDataManagerRetrofit;
import es.formulastudent.app.mvp.data.api.retrofit.UsersApi;
import es.formulastudent.app.mvp.data.model.DaoSession;


@Module(includes = {RetrofitModule.class, GreenDAOModule.class})
public class UsersModule {


    @Provides
    @Named("local")
    @Singleton
    UserDataManager provideUserDataManagerLocal(@Named("online") UserDataManager userDataManager, DaoSession daoSession){
        return new UserDataManagerGreenDAO(userDataManager, daoSession);
    }


    @Provides
    @Named("online")
    @Singleton
    UserDataManager provideUserDataManagerOnline(UsersApi usersApi){
        return new UserDataManagerRetrofit(usersApi);
    }

}

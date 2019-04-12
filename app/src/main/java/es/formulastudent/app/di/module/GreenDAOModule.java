package es.formulastudent.app.di.module;


import android.content.Context;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.data.model.DaoMaster;
import es.formulastudent.app.mvp.data.model.DaoSession;


@Module(includes = ContextModule.class)
public class GreenDAOModule {

    @Provides
    @Singleton
    public DaoSession providesGreenDAO(Context context){
        return new DaoMaster(
                new DaoMaster.DevOpenHelper(context, "greendao_demo.db").getWritableDb()).newSession();
    }
}

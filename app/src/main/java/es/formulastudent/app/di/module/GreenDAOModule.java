package es.formulastudent.app.di.module;


import dagger.Module;


@Module(includes = ContextModule.class)
public class GreenDAOModule {
/*
    @Provides
    @Singleton
    public DaoSession providesGreenDAO(Context context){
        return new DaoMaster(
                new DaoMaster.DevOpenHelper(context, "greendao_demo.db").getWritableDb()).newSession();
    }
    */
}

package code.formulastudentspain.app.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.FSSApp;


@Module
public class AppModule {

    private FSSApp FSSApp;

    public AppModule(FSSApp FSSApp) {
        this.FSSApp = FSSApp;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return FSSApp;
    }
}
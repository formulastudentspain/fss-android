package code.formulastudentspain.app;

import android.app.Application;

import code.formulastudentspain.app.di.component.AppComponent;
import code.formulastudentspain.app.di.component.DaggerAppComponent;

public class FSSApp extends Application {

    private AppComponent appComponent;
    private static FSSApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initializeInjector();
    }

    public static FSSApp getApp() {
        return app;
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }
}

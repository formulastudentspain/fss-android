package es.formulastudent.app;

import android.app.Application;

import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerAppComponent;

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

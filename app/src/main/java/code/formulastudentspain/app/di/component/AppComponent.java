package code.formulastudentspain.app.di.component;

import code.formulastudentspain.app.FSSApp;
import code.formulastudentspain.app.di.module.AppModule;

import dagger.Component;


@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(FSSApp categoryApplication);
}

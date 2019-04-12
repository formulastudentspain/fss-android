package es.formulastudent.app.di.component;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.di.module.AppModule;

import dagger.Component;


@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(FSSApp categoryApplication);
}

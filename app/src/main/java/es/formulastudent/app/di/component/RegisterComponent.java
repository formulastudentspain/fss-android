package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.RegisterModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.loginregister.RegisterActivity;
import es.formulastudent.app.mvp.view.screen.loginregister.RegisterPresenter;


@Singleton
@Component(modules = {RegisterModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RegisterComponent {

    void inject(RegisterActivity loginActivity);
    RegisterPresenter getMainPresenter();
    User getLoggedUser();

}

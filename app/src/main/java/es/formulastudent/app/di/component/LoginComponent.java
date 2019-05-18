package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.LoginModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.login.LoginActivity;
import es.formulastudent.app.mvp.view.activity.login.LoginPresenter;


@Singleton
@Component(modules = {LoginModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
    LoginPresenter getMainPresenter();
    User getLoggedUser();

}

package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.LoginModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.login.LoginActivity;
import code.formulastudentspain.app.mvp.view.screen.login.LoginPresenter;


@Singleton
@Component(modules = {LoginModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
    LoginPresenter getMainPresenter();
    User getLoggedUser();

}

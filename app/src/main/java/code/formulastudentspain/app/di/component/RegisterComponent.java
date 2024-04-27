package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.RegisterModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.loginregister.RegisterActivity;
import code.formulastudentspain.app.mvp.view.screen.loginregister.RegisterPresenter;


@Singleton
@Component(modules = {RegisterModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RegisterComponent {

    void inject(RegisterActivity loginActivity);
    RegisterPresenter getMainPresenter();
    User getLoggedUser();

}

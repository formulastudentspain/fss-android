package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.welcome.MainActivity;


@Singleton
@Component(modules = {SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface WelcomeComponent {

    void inject(MainActivity mainActivity);
    User getLoggedUser();

}

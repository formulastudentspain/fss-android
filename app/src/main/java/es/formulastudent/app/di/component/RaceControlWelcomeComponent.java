package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.racecontrol.RaceControlWelcomeActivity;


@Singleton
@Component(modules = {SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RaceControlWelcomeComponent {

    void inject(RaceControlWelcomeActivity raceControlWelcomeActivity);
    User getLoggedUser();

}

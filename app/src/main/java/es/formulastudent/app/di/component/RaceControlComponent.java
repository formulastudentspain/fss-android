package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.RaceControlModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlActivity;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;


@Singleton
@Component(modules = {RaceControlModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RaceControlComponent {

    void inject(RaceControlActivity raceControlActivity);
    RaceControlPresenter getMainPresenter();
    User getLoggedUser();

}

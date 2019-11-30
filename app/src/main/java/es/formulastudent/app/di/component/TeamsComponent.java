package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TeamsModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.teams.TeamsActivity;
import es.formulastudent.app.mvp.view.activity.teams.TeamsPresenter;


@Singleton
@Component(modules = {TeamsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamsComponent {

    void inject(TeamsActivity teamsActivity);
    TeamsPresenter getMainPresenter();
    User getLoggedUser();

}

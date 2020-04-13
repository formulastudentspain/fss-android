package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TeamsDetailScrutineeringModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringFragment;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;


@Singleton
@Component(modules = {TeamsDetailScrutineeringModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamsDetailScrutineeringComponent {

    User getLoggedUser();
    void inject(TeamsDetailScrutineeringFragment teamsDetailScrutineeringFragment);
    TeamsDetailScrutineeringPresenter getTeamsDetailScrutineeringPresenter();
}

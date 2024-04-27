package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.TeamsDetailScrutineeringModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringFragment;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {TeamsDetailScrutineeringModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamsDetailScrutineeringComponent {

    User getLoggedUser();
    void inject(TeamsDetailScrutineeringFragment teamsDetailScrutineeringFragment);
    TeamsDetailScrutineeringPresenter getTeamsDetailScrutineeringPresenter();
    LoadingDialog getLoadingDialog();
}

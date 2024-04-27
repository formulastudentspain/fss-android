package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.TeamsModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.teams.TeamsFragment;
import code.formulastudentspain.app.mvp.view.screen.teams.TeamsPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;

@Singleton
@Component(modules = {UtilsModule.class, TeamsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamsComponent {
    void inject(TeamsFragment teamsFragment);
    TeamsPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();
}

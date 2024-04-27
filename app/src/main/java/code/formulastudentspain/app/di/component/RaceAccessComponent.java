package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.RaceAccessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.raceaccess.RaceAccessFragment;
import code.formulastudentspain.app.mvp.view.screen.raceaccess.RaceAccessPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {UtilsModule.class, RaceAccessModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RaceAccessComponent {

    void inject(RaceAccessFragment raceAccessFragment);
    RaceAccessPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();

}

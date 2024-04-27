package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.RaceControlModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.RaceControlFragment;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.RaceControlPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {UtilsModule.class, RaceControlModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RaceControlComponent {

    void inject(RaceControlFragment raceControlFragment);
    RaceControlPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();

}

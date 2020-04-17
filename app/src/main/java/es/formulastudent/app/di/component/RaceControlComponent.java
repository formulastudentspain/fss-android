package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.RaceControlModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.racecontrol.RaceControlFragment;
import es.formulastudent.app.mvp.view.screen.racecontrol.RaceControlPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {UtilsModule.class, RaceControlModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RaceControlComponent {

    void inject(RaceControlFragment raceControlFragment);
    RaceControlPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();

}

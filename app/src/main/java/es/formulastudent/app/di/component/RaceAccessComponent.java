package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.RaceAccessModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.raceaccess.RaceAccessFragment;
import es.formulastudent.app.mvp.view.screen.raceaccess.RaceAccessPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {UtilsModule.class, RaceAccessModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface RaceAccessComponent {

    void inject(RaceAccessFragment raceAccessFragment);
    RaceAccessPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();

}

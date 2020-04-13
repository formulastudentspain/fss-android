package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.ConeControlStatsModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.conecontrolstats.ConeControlStatsActivity;
import es.formulastudent.app.mvp.view.screen.conecontrolstats.ConeControlStatsPresenter;


@Singleton
@Component(modules = {ConeControlStatsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface ConeControlStatsComponent {

    void inject(ConeControlStatsActivity coneControlStatsActivity);
    ConeControlStatsPresenter getMainPresenter();
    User getLoggedUser();

}

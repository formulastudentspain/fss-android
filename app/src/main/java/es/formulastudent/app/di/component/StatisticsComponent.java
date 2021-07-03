package es.formulastudent.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.StatisticsModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.statistics.StatisticsFragment;
import es.formulastudent.app.mvp.view.screen.statistics.StatisticsPresenter;


@Singleton
@Component(modules = {StatisticsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface StatisticsComponent {

    void inject(StatisticsFragment statisticsFragment);
    StatisticsPresenter getMainPresenter();
    User getLoggedUser();
}

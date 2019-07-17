package es.formulastudent.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.StatisticsModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.statistics.StatisticsActivity;
import es.formulastudent.app.mvp.view.activity.statistics.StatisticsPresenter;


@Singleton
@Component(modules = {StatisticsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface StatisticsComponent {

    void inject(StatisticsActivity statisticsActivity);
    StatisticsPresenter getMainPresenter();
    User getLoggedUser();
}

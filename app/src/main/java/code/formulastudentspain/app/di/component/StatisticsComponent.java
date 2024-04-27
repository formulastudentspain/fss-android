package code.formulastudentspain.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.StatisticsModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.statistics.StatisticsFragment;
import code.formulastudentspain.app.mvp.view.screen.statistics.StatisticsPresenter;


@Singleton
@Component(modules = {StatisticsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface StatisticsComponent {

    void inject(StatisticsFragment statisticsFragment);
    StatisticsPresenter getMainPresenter();
    User getLoggedUser();
}

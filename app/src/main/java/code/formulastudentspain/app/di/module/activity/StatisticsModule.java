package code.formulastudentspain.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.business.statistics.StatisticsBO;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.statistics.StatisticsPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class, SharedPreferencesModule.class})
public class StatisticsModule {

    private StatisticsPresenter.View view;

    public StatisticsModule(StatisticsPresenter.View view) {
        this.view = view;
    }

    @Provides
    public StatisticsPresenter.View provideView() {
        return view;
    }

    @Provides
    public StatisticsPresenter providePresenter(StatisticsPresenter.View categoryView,
                                                Context context, StatisticsBO statisticsBO,
                                                User loggedUser) {
        return new StatisticsPresenter(categoryView, context, statisticsBO, loggedUser);
    }


}

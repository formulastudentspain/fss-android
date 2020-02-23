package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.view.activity.conecontrolstats.ConeControlStatsPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class ConeControlStatsModule {

    private ConeControlStatsPresenter.View view;

    public ConeControlStatsModule(ConeControlStatsPresenter.View view) {
        this.view = view;
    }

    @Provides
    public ConeControlStatsPresenter.View provideView() {
        return view;
    }

    @Provides
    public ConeControlStatsPresenter providePresenter(ConeControlStatsPresenter.View categoryView, ConeControlBO coneControlBO) {
        return new ConeControlStatsPresenter(categoryView, coneControlBO);
    }
}

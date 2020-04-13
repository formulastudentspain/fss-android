package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.screen.conecontrolstats.ConeControlStatsPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class ConeControlStatsModule {

    private ConeControlStatsPresenter.View view;
    private ConeControlEvent event;

    public ConeControlStatsModule(ConeControlStatsPresenter.View view, ConeControlEvent event) {
        this.view = view;
        this.event = event;
    }

    @Provides
    public ConeControlStatsPresenter.View provideView() {
        return view;
    }

    @Provides
    public ConeControlStatsPresenter providePresenter(ConeControlStatsPresenter.View categoryView, ConeControlBO coneControlBO) {
        return new ConeControlStatsPresenter(categoryView, coneControlBO, event);
    }
}

package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class RaceControlModule {

    private RaceControlPresenter.View view;
    private RaceControlEvent raceControlEvent;
    private String raceType;

    public RaceControlModule(RaceControlPresenter.View view, RaceControlEvent raceControlEvent, String raceType) {
        this.view = view;
        this.raceControlEvent = raceControlEvent;
        this.raceType = raceType;
    }

    @Provides
    public RaceControlEvent provideRaceControlEvent() {
        return raceControlEvent;
    }

    @Provides
    public RaceControlPresenter.View provideView() {
        return view;
    }

    @Provides
    public RaceControlPresenter providePresenter(RaceControlPresenter.View categoryView, Context context, RaceControlBO raceControlBO) {
        return new RaceControlPresenter(categoryView, context, raceControlEvent, raceType, raceControlBO);
    }


}

package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class RaceControlModule {

    private RaceControlPresenter.View view;
    private RaceControlEvent raceControlEvent;
    private String raceType;
    private String raceArea;

    public RaceControlModule(RaceControlPresenter.View view, RaceControlEvent raceControlEvent, String raceType, String raceArea) {
        this.view = view;
        this.raceControlEvent = raceControlEvent;
        this.raceType = raceType;
        this.raceArea = raceArea;
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
    public RaceControlPresenter providePresenter(RaceControlPresenter.View categoryView, Context context, RaceControlBO raceControlBO, TeamMemberBO teamMemberBO, BriefingBO briefingBO, DynamicEventBO dynamicEventBO) {
        return new RaceControlPresenter(categoryView, context, raceControlEvent, raceType, raceArea, raceControlBO, teamMemberBO, briefingBO, dynamicEventBO);
    }


}

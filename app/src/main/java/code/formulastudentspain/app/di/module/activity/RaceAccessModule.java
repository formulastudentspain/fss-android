package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.briefing.BriefingBO;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.data.business.raceaccess.RaceAccessBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.EventType;
import code.formulastudentspain.app.mvp.view.screen.raceaccess.RaceAccessPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class RaceAccessModule {

    private RaceAccessPresenter.View view;

    private EventType eventType;

    public RaceAccessModule(RaceAccessPresenter.View view, EventType eventType) {
        this.view = view;
        this.eventType = eventType;
    }

    @Provides
    public EventType provideEventType() {
        return eventType;
    }

    @Provides
    public RaceAccessPresenter.View provideView() {
        return view;
    }

    @Provides
    public RaceAccessPresenter providePresenter(RaceAccessPresenter.View categoryView,
                                                TeamBO teamBO, RaceAccessBO raceAccessBO,
                                                TeamMemberBO teamMemberBO, BriefingBO briefingBO,
                                                EventType eventType, EgressBO egressBO) {
        return new RaceAccessPresenter(categoryView, teamBO, raceAccessBO, teamMemberBO, briefingBO,
                eventType, egressBO);
    }
}

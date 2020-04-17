package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.raceaccess.RaceAccessBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.view.screen.raceaccess.RaceAccessPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

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
                                                EventType eventType, EgressBO egressBO,
                                                LoadingDialog loadingDialog, Messages messages) {
        return new RaceAccessPresenter(categoryView, teamBO, raceAccessBO, teamMemberBO, briefingBO,
                eventType, egressBO, loadingDialog, messages);
    }


}

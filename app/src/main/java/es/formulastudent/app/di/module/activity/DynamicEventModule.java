package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.view.screen.dynamicevent.DynamicEventPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class DynamicEventModule {

    private DynamicEventPresenter.View view;

    private EventType eventType;

    public DynamicEventModule(DynamicEventPresenter.View view, EventType eventType) {
        this.view = view;
        this.eventType = eventType;
    }

    @Provides
    public EventType provideEventType() {
        return eventType;
    }

    @Provides
    public DynamicEventPresenter.View provideView() {
        return view;
    }

    @Provides
    public DynamicEventPresenter providePresenter(DynamicEventPresenter.View categoryView,
                                                  TeamBO teamBO, DynamicEventBO dynamicEventBO, TeamMemberBO teamMemberBO,
                                                  BriefingBO briefingBO, EventType eventType, EgressBO egressBO) {
        return new DynamicEventPresenter(categoryView, teamBO, dynamicEventBO, teamMemberBO, briefingBO, eventType, egressBO);
    }


}

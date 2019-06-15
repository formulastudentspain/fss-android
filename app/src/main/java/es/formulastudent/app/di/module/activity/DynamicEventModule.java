package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventPresenter;

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
    public DynamicEventPresenter providePresenter(DynamicEventPresenter.View categoryView, Context context,
                                                  TeamBO teamBO, DynamicEventBO dynamicEventBO, UserBO userBO,
                                                  BriefingBO briefingBO, EventType eventType) {
        return new DynamicEventPresenter(categoryView, context, teamBO, dynamicEventBO, userBO, briefingBO, eventType);
    }


}

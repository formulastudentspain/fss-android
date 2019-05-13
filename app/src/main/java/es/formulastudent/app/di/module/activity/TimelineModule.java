package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.view.activity.timeline.TimelineHelper;
import es.formulastudent.app.mvp.view.activity.timeline.TimelinePresenter;

@Module(includes = {ContextModule.class})
public class TimelineModule {

    private TimelinePresenter.View view;
    private TimelineHelper helper;

    public TimelineModule(TimelinePresenter.View view, TimelineHelper helper) {
        this.view = view;
        this.helper = helper;
    }

    @Provides
    public TimelineHelper provideHelper() {
        return helper;
    }

    @Provides
    public TimelinePresenter.View provideView() {
        return view;
    }

    @Provides
    public TimelinePresenter providePresenter(TimelinePresenter.View categoryView, Context context, TimelineHelper helper) {
        return new TimelinePresenter(categoryView, context, helper);
    }


}

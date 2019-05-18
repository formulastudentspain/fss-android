package es.formulastudent.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TimelineModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.timeline.TimelineActivity;
import es.formulastudent.app.mvp.view.activity.timeline.TimelinePresenter;


@Singleton
@Component(modules = {TimelineModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TimelineComponent {

    void inject(TimelineActivity timelineActivity);
    TimelinePresenter getMainPresenter();
    User getLoggedUser();
}

package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.BriefingModule;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingActivity;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingPresenter;


@Singleton
@Component(modules = {BriefingModule.class}, dependencies = {AppComponent.class})
public interface BriefingComponent {

    void inject(BriefingActivity userListActivity);
    BriefingPresenter getMainPresenter();
}

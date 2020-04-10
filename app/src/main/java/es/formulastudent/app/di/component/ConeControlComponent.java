package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.ConeControlModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.conecontrol.ConeControlActivity;
import es.formulastudent.app.mvp.view.screen.conecontrol.ConeControlPresenter;


@Singleton
@Component(modules = {ConeControlModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface ConeControlComponent {

    void inject(ConeControlActivity coneControlActivity);
    ConeControlPresenter getMainPresenter();
    User getLoggedUser();

}

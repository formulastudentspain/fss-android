package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.EnduranceModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.EnduranceActivity;
import es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.EndurancePresenter;


@Singleton
@Component(modules = {EnduranceModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface EnduranceComponent {

    void inject(EnduranceActivity enduranceActivity);
    EndurancePresenter getMainPresenter();
    User getLoggedUser();

}

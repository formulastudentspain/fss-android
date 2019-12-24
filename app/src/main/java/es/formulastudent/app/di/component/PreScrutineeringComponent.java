package es.formulastudent.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.PreScrutineeringModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.egresschrono.EgressChronoActivity;
import es.formulastudent.app.mvp.view.activity.egresschrono.EgressChronoPresenter;


@Singleton
@Component(modules = {PreScrutineeringModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface PreScrutineeringComponent {

    void inject(EgressChronoActivity activity);
    EgressChronoPresenter getMainPresenter();
    User getLoggedUser();
}

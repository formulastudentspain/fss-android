package code.formulastudentspain.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.PreScrutineeringModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.egresschrono.EgressChronoActivity;
import code.formulastudentspain.app.mvp.view.screen.egresschrono.EgressChronoPresenter;


@Singleton
@Component(modules = {PreScrutineeringModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface PreScrutineeringComponent {

    void inject(EgressChronoActivity activity);
    EgressChronoPresenter getMainPresenter();
    User getLoggedUser();
}

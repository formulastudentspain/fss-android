package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.AccelerationModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.AccelerationActivity;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.AccelerationPresenter;


@Singleton
@Component(modules = {AccelerationModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface AccelerationComponent {

    void inject(AccelerationActivity accelerationActivity);
    AccelerationPresenter getMainPresenter();
    User getLoggedUser();

}

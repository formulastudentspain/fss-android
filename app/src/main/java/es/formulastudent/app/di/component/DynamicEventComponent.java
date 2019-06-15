package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.DynamicEventModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventActivity;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventPresenter;


@Singleton
@Component(modules = {DynamicEventModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface DynamicEventComponent {

    void inject(DynamicEventActivity dynamicEventActivity);
    DynamicEventPresenter getMainPresenter();
    User getLoggedUser();

}

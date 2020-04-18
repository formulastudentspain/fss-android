package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.UserListModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.view.screen.user.UserFragment;


@Singleton
@Component(modules = {UserListModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface UserListComponent {
    void inject(UserFragment userFragment);
}

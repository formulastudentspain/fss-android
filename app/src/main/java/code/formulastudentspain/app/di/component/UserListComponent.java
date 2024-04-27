package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.UserListModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.view.screen.user.UserFragment;


@Singleton
@Component(modules = {UserListModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface UserListComponent {
    void inject(UserFragment userFragment);
}

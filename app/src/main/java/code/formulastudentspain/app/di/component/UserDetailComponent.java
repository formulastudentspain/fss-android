package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.UserDetailModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.view.screen.userdetail.UserDetailFragment;


@Singleton
@Component(modules = {UserDetailModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface UserDetailComponent {
    void inject(UserDetailFragment userDetailFragment);
}

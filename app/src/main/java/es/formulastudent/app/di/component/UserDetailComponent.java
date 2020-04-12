package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.UserDetailModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.userdetail.UserDetailFragment;
import es.formulastudent.app.mvp.view.screen.userdetail.UserDetailPresenter;


@Singleton
@Component(modules = {UserDetailModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface UserDetailComponent {
    void inject(UserDetailFragment userDetailFragment);
    UserDetailPresenter getMainPresenter();
    User getLoggedUser();
}

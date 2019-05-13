package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.UserDetailModule;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailPresenter;


@Singleton
@Component(modules = {UserDetailModule.class}, dependencies = {AppComponent.class})
public interface UserDetailComponent {

    void inject(UserDetailActivity userListActivity);
    UserDetailPresenter getMainPresenter();
}

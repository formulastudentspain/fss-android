package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.UserDetailModule;
import es.formulastudent.app.di.module.UserListModule;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailPresenter;
import es.formulastudent.app.mvp.view.activity.userlist.UserListActivity;
import es.formulastudent.app.mvp.view.activity.userlist.UserListPresenter;


@Singleton
@Component(modules = {UserDetailModule.class}, dependencies = {AppComponent.class})
public interface UserDetailComponent {

    void inject(UserDetailActivity userListActivity);
    UserDetailPresenter getMainPresenter();
}

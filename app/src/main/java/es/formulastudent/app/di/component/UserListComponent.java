package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.UserListModule;
import es.formulastudent.app.di.module.UsersModule;
import es.formulastudent.app.mvp.view.activity.userlist.UserListActivity;
import es.formulastudent.app.mvp.view.presenter.UserListPresenter;


@Singleton
@Component(modules = {UsersModule.class, UserListModule.class}, dependencies = {AppComponent.class})
public interface UserListComponent {

    void inject(UserListActivity userListActivity);
    UserListPresenter getMainPresenter();
}

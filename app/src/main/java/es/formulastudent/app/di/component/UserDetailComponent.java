package es.formulastudent.app.di.component;



import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.UserDetailModule;
import es.formulastudent.app.di.module.UsersModule;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.presenter.UserDetailPresenter;


@Singleton
@Component(modules = {UsersModule.class, UserDetailModule.class}, dependencies = {AppComponent.class})
public interface UserDetailComponent {

    void inject(UserDetailActivity userDetailActivity);
    UserDetailPresenter getDetailPresenter();
}

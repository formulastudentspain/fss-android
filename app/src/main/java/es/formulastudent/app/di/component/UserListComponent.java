package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.UserListModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.teammember.TeamMemberActivity;
import es.formulastudent.app.mvp.view.activity.teammember.TeamMemberPresenter;


@Singleton
@Component(modules = {UserListModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface UserListComponent {

    void inject(TeamMemberActivity teamMemberActivity);
    TeamMemberPresenter getMainPresenter();
    User getLoggedUser();

}

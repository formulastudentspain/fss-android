package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TeamMemberListModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.teammember.TeamMemberActivity;
import es.formulastudent.app.mvp.view.activity.teammember.TeamMemberPresenter;


@Singleton
@Component(modules = {TeamMemberListModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamMemberListComponent {

    void inject(TeamMemberActivity teamMemberActivity);
    TeamMemberPresenter getMainPresenter();
    User getLoggedUser();

}

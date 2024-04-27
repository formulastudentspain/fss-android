package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.TeamMemberListModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.teammember.TeamMemberFragment;
import code.formulastudentspain.app.mvp.view.screen.teammember.TeamMemberPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {TeamMemberListModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamMemberListComponent {
    void inject(TeamMemberFragment teamMemberFragment);
    TeamMemberPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();
}

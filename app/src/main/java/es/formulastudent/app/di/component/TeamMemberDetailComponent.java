package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TeamMemberDetailModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.view.screen.teammemberdetail.TeamMemberDetailFragment;
import es.formulastudent.app.mvp.view.screen.teammemberdetail.TeamMemberDetailPresenter;
import es.formulastudent.app.mvp.view.utils.Messages;


@Singleton
@Component(modules = {UtilsModule.class, TeamMemberDetailModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamMemberDetailComponent {

    void inject(TeamMemberDetailFragment teamMemberDetailFragment);
    TeamMemberDetailPresenter getMainPresenter();
    Messages getMessages();
}

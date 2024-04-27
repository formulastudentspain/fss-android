package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.TeamMemberDetailModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.view.screen.teammemberdetail.TeamMemberDetailFragment;


@Singleton
@Component(modules = {TeamMemberDetailModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamMemberDetailComponent {
    void inject(TeamMemberDetailFragment teamMemberDetailFragment);
}

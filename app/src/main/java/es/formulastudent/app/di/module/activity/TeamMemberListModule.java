package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.view.screen.teammember.TeamMemberPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class TeamMemberListModule {

    private TeamMemberPresenter.View view;

    public TeamMemberListModule(TeamMemberPresenter.View view) {
        this.view = view;
    }

    @Provides
    public TeamMemberPresenter.View provideView() {
        return view;
    }

    @Provides
    public TeamMemberPresenter providePresenter(TeamMemberPresenter.View categoryView, TeamMemberBO teamMemberBO,
                                                TeamBO teamBO, BriefingBO briefingBO) {
        return new TeamMemberPresenter(categoryView, teamMemberBO, teamBO, briefingBO);
    }
}

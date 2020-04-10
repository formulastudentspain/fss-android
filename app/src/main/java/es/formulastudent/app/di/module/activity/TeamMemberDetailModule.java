package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.view.screen.teammemberdetail.TeamMemberDetailPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class TeamMemberDetailModule {

    private TeamMemberDetailPresenter.View view;

    public TeamMemberDetailModule(TeamMemberDetailPresenter.View view) {
        this.view = view;
    }

    @Provides
    public TeamMemberDetailPresenter.View provideView() {
        return view;
    }

    @Provides
    public TeamMemberDetailPresenter providePresenter(TeamMemberDetailPresenter.View categoryView, TeamMemberBO teamMemberBO, ImageBO imageBO, TeamBO teamBO) {
        return new TeamMemberDetailPresenter(categoryView, teamMemberBO, imageBO, teamBO);
    }
}

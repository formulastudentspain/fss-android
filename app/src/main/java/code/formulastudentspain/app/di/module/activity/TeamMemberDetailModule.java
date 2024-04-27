package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.imageuploader.ImageBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.view.screen.teammemberdetail.TeamMemberDetailPresenter;

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
    public TeamMemberDetailPresenter providePresenter(TeamMemberDetailPresenter.View categoryView,
                                                      TeamMemberBO teamMemberBO, ImageBO imageBO,
                                                      TeamBO teamBO) {
        return new TeamMemberDetailPresenter(categoryView, teamMemberBO, imageBO, teamBO);
    }
}

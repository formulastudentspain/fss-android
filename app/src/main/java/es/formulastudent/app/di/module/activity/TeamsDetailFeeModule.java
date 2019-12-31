package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.view.activity.teamsdetailfee.TeamsDetailFeePresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class TeamsDetailFeeModule {

    private TeamsDetailFeePresenter.View view;

    public TeamsDetailFeeModule(TeamsDetailFeePresenter.View view) {
        this.view = view;
    }

    @Provides
    public TeamsDetailFeePresenter.View provideView() {
        return view;
    }

    @Provides
    public TeamsDetailFeePresenter providePresenter(TeamsDetailFeePresenter.View categoryView,
                                                              TeamBO teamBO, DynamicEventBO dynamicEventBO, TeamMemberBO teamMemberBO) {
        return new TeamsDetailFeePresenter(categoryView, teamBO, dynamicEventBO, teamMemberBO);
    }
}

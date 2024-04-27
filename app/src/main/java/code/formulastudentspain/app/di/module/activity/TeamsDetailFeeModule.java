package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeePresenter;

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
                                                    TeamBO teamBO) {
        return new TeamsDetailFeePresenter(categoryView, teamBO);
    }
}

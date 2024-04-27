package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.view.screen.teams.TeamsPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class TeamsModule {

    private TeamsPresenter.View view;

    public TeamsModule(TeamsPresenter.View view) {
        this.view = view;
    }

    @Provides
    public TeamsPresenter.View provideView() {
        return view;
    }

    @Provides
    public TeamsPresenter providePresenter(TeamsPresenter.View categoryView, TeamBO teamBO) {
        return new TeamsPresenter(categoryView, teamBO);
    }
}

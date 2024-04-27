package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.business.briefing.BriefingBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.briefing.BriefingPresenter;

@Module(includes = {BusinessModule.class, SharedPreferencesModule.class, UtilsModule.class})
public class BriefingModule {

    private BriefingPresenter.View view;

    public BriefingModule(BriefingPresenter.View view) {
        this.view = view;
    }

    @Provides
    public BriefingPresenter.View provideView() {
        return view;
    }

    @Provides
    public BriefingPresenter providePresenter(BriefingPresenter.View categoryView, TeamBO teamBO,
                                              BriefingBO briefingBO, TeamMemberBO teamMemberBO,
                                              User loggedUser) {
        return new BriefingPresenter(categoryView, teamBO, briefingBO, teamMemberBO, loggedUser);
    }
}

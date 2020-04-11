package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.briefing.BriefingPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

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
                                              User loggedUser, LoadingDialog loadingDialog, Messages messages) {
        return new BriefingPresenter(categoryView, teamBO, briefingBO, teamMemberBO, loggedUser,
                loadingDialog, messages);
    }
}

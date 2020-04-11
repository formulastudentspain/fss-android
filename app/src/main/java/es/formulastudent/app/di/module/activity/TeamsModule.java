package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.view.screen.teams.TeamsPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

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
    public TeamsPresenter providePresenter(TeamsPresenter.View categoryView, TeamBO teamBO,
                                           LoadingDialog loadingDialog, Messages messages) {
        return new TeamsPresenter(categoryView, teamBO, loadingDialog, messages);
    }
}

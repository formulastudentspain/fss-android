package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.raceaccess.RaceAccessBO;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class TeamsDetailScrutineeringModule {

    private TeamsDetailScrutineeringPresenter.View view;

    public TeamsDetailScrutineeringModule(TeamsDetailScrutineeringPresenter.View view) {
        this.view = view;
    }

    @Provides
    public TeamsDetailScrutineeringPresenter.View provideView() {
        return view;
    }

    @Provides
    public TeamsDetailScrutineeringPresenter providePresenter(TeamsDetailScrutineeringPresenter.View categoryView,
                                                              TeamBO teamBO, RaceAccessBO raceAccessBO,
                                                              EgressBO egressBO, TeamMemberBO teamMemberBO,
                                                              LoadingDialog loadingDialog, Messages messages) {
        return new TeamsDetailScrutineeringPresenter(categoryView, teamBO, raceAccessBO, egressBO,
                teamMemberBO, messages);
    }
}

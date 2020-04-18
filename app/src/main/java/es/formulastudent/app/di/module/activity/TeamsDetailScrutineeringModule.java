package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.raceaccess.RaceAccessBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.messages.Messages;

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

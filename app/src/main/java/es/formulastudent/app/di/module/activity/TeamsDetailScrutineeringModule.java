package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.view.activity.teamsdetailscrutineering.TeamsDetailScrutineeringPresenter;

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
                                                              TeamBO teamBO, DynamicEventBO dynamicEventBO, EgressBO egressBO, TeamMemberBO teamMemberBO) {
        return new TeamsDetailScrutineeringPresenter(categoryView, teamBO, dynamicEventBO, egressBO, teamMemberBO);
    }
}

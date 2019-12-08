package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
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
    public TeamsDetailScrutineeringPresenter providePresenter(TeamsDetailScrutineeringPresenter.View categoryView, Context context, TeamBO teamBO) {
        return new TeamsDetailScrutineeringPresenter(categoryView, context, teamBO);
    }
}

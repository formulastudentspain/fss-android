package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.view.screen.adminoperations.AdminOpsPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class AdminOpsModule {

    private AdminOpsPresenter.View view;

    public AdminOpsModule(AdminOpsPresenter.View view) {
        this.view = view;
    }

    @Provides
    public AdminOpsPresenter.View provideView() {
        return view;
    }

    @Provides
    public AdminOpsPresenter providePresenter(AdminOpsPresenter.View categoryView, Context context, TeamBO teamBO, TeamMemberBO teamMemberBO) {
        return new AdminOpsPresenter(categoryView, context, teamBO, teamMemberBO);
    }


}

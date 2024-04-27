package code.formulastudentspain.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.view.screen.adminoperations.AdminOpsPresenter;

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

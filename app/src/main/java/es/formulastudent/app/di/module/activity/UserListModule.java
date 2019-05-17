package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;
import es.formulastudent.app.mvp.view.activity.userlist.UserListPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class UserListModule {

    private UserListPresenter.View view;

    public UserListModule(UserListPresenter.View view) {
        this.view = view;
    }

    @Provides
    public UserListPresenter.View provideView() {
        return view;
    }

    @Provides
    public UserListPresenter providePresenter(UserListPresenter.View categoryView, Context context, UserBO userBO, TeamBO teamBO, UserRoleBO userRoleBO) {
        return new UserListPresenter(categoryView, context, userBO, teamBO, userRoleBO);
    }
}

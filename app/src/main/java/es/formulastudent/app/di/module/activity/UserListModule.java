package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.view.activity.timeline.TimelineHelper;
import es.formulastudent.app.mvp.view.activity.timeline.TimelinePresenter;
import es.formulastudent.app.mvp.view.activity.userlist.UserListPresenter;

@Module(includes = {ContextModule.class})
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
    public UserListPresenter providePresenter(UserListPresenter.View categoryView, Context context) {
        return new UserListPresenter(categoryView, context);
    }


}

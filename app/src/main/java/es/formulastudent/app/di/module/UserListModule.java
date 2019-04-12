package es.formulastudent.app.di.module;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.data.api.UserDataManager;
import es.formulastudent.app.mvp.view.presenter.UserListPresenter;

@Module(includes = {UsersModule.class, ContextModule.class})
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
    public UserListPresenter providePresenter(UserListPresenter.View categoryView,
                                              @Named("local") UserDataManager userDataManager,
                                              Context context) {
        return new UserListPresenter(categoryView, userDataManager, context);
    }

}

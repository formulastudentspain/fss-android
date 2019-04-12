package es.formulastudent.app.di.module;

import android.content.Context;
import android.location.Geocoder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.data.api.UserDataManager;
import es.formulastudent.app.mvp.view.presenter.UserDetailPresenter;


@Module(includes = {UsersModule.class, MapModule.class, ContextModule.class})
public class UserDetailModule {

    private UserDetailPresenter.View view;

    public UserDetailModule(UserDetailPresenter.View view) {
        this.view = view;
    }

    @Provides
    public UserDetailPresenter.View provideView() {
        return view;
    }

    @Provides
    public UserDetailPresenter providePresenter(UserDetailPresenter.View categoryView,
                                                @Named("local") UserDataManager userDataManager,
                                                Geocoder geocoder,
                                                Context context) {
        return new UserDetailPresenter(categoryView, userDataManager, geocoder, context);
    }

}

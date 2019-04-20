package es.formulastudent.app.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.view.activity.login.LoginPresenter;

@Module(includes = {ContextModule.class})
public class LoginModule {

    private LoginPresenter.View view;

    public LoginModule(LoginPresenter.View view) {
        this.view = view;
    }

    @Provides
    public LoginPresenter.View provideView() {
        return view;
    }

    @Provides
    public LoginPresenter providePresenter(LoginPresenter.View categoryView, Context context) {
        return new LoginPresenter(categoryView, context);
    }

}

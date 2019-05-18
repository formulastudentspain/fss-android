package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;
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
    public LoginPresenter providePresenter(LoginPresenter.View categoryView, Context context, AuthBO authBO) {
        return new LoginPresenter(categoryView, context, authBO);
    }

}

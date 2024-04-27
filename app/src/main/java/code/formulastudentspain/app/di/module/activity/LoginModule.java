package code.formulastudentspain.app.di.module.activity;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.business.auth.AuthBO;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.view.screen.login.LoginPresenter;

@Module(includes = {UtilsModule.class, ContextModule.class, BusinessModule.class, SharedPreferencesModule.class})
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
    public LoginPresenter providePresenter(LoginPresenter.View categoryView, Context context,
                                           AuthBO authBO, UserBO userBO,
                                           SharedPreferences sharedPreferences) {
        return new LoginPresenter(categoryView, context, authBO, userBO, sharedPreferences);
    }

}

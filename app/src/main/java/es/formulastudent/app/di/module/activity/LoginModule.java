package es.formulastudent.app.di.module.activity;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.view.screen.login.LoginPresenter;
import es.formulastudent.app.mvp.view.utils.messages.Messages;

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
                                           AuthBO authBO, UserBO userBO, Messages messages,
                                           SharedPreferences sharedPreferences) {
        return new LoginPresenter(categoryView, context, authBO, userBO, sharedPreferences, messages);
    }

}

package code.formulastudentspain.app.di.module.activity;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.business.auth.AuthBO;
import code.formulastudentspain.app.mvp.view.screen.loginregister.RegisterPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class, SharedPreferencesModule.class})
public class RegisterModule {

    private RegisterPresenter.View view;

    public RegisterModule(RegisterPresenter.View view) {
        this.view = view;
    }

    @Provides
    public RegisterPresenter.View provideView() {
        return view;
    }

    @Provides
    public RegisterPresenter providePresenter(RegisterPresenter.View categoryView, Context context,
                                              AuthBO authBO, SharedPreferences sharedPreferences) {
        return new RegisterPresenter(categoryView, context, authBO, sharedPreferences);
    }

}

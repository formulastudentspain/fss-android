package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.TeamsDetailFeeModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeeFragment;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeePresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {TeamsDetailFeeModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamsDetailFeeComponent {

    User getLoggedUser();
    void inject(TeamsDetailFeeFragment teamsDetailFeeFragment);
    TeamsDetailFeePresenter getTeamsDetailFeePresenter();
    LoadingDialog getLoadingDialog();
}

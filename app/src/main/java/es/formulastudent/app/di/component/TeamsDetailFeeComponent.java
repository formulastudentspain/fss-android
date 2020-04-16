package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TeamsDetailFeeModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeeFragment;
import es.formulastudent.app.mvp.view.screen.teamsdetailfee.TeamsDetailFeePresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {TeamsDetailFeeModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TeamsDetailFeeComponent {

    User getLoggedUser();
    void inject(TeamsDetailFeeFragment teamsDetailFeeFragment);
    TeamsDetailFeePresenter getTeamsDetailFeePresenter();
    LoadingDialog getLoadingDialog();
}

package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.ConeControlModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.conecontrol.ConeControlFragment;
import code.formulastudentspain.app.mvp.view.screen.conecontrol.ConeControlPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;


@Singleton
@Component(modules = {UtilsModule.class, ConeControlModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface ConeControlComponent {

    void inject(ConeControlFragment coneControlFragment);
    ConeControlPresenter getMainPresenter();
    User getLoggedUser();
    LoadingDialog getLoadingDialog();

}

package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.ConeControlModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.conecontrol.ConeControlPresenter;
import code.formulastudentspain.app.mvp.view.screen.conecontrol.ConeControlWelcomeFragment;


@Singleton
@Component(modules = {ConeControlModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface ConeControlWelcomeComponent {
    void inject(ConeControlWelcomeFragment coneControlWelcomeFragment);
    ConeControlPresenter getMainPresenter();
    User getLoggedUser();
}

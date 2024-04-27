package code.formulastudentspain.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.BriefingModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.briefing.BriefingFragment;
import code.formulastudentspain.app.mvp.view.screen.briefing.BriefingPresenter;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


@Singleton
@Component(modules = {BriefingModule.class, SharedPreferencesModule.class, UtilsModule.class}, dependencies = {AppComponent.class})
public interface BriefingComponent {

    void inject(BriefingFragment briefingFragment);
    BriefingPresenter getMainPresenter();
    User getLoggedUser();
    Messages getMessages();
    LoadingDialog getLoadingDialog();
}

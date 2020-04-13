package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.BriefingModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.briefing.BriefingFragment;
import es.formulastudent.app.mvp.view.screen.briefing.BriefingPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;


@Singleton
@Component(modules = {BriefingModule.class, SharedPreferencesModule.class, UtilsModule.class}, dependencies = {AppComponent.class})
public interface BriefingComponent {

    void inject(BriefingFragment briefingFragment);
    BriefingPresenter getMainPresenter();
    User getLoggedUser();
    Messages getMessages();
    LoadingDialog getLoadingDialog();
}

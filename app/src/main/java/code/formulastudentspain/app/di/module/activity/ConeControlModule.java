package code.formulastudentspain.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.business.conecontrol.ConeControlBO;
import code.formulastudentspain.app.mvp.data.business.mailsender.MailSender;
import code.formulastudentspain.app.mvp.data.model.ConeControlEvent;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.conecontrol.ConeControlPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class, SharedPreferencesModule.class,})
public class ConeControlModule {

    private ConeControlPresenter.View view;
    private ConeControlEvent coneControlEvent;

    public ConeControlModule(ConeControlPresenter.View view, ConeControlEvent coneControlEvent) {
        this.view = view;
        this.coneControlEvent = coneControlEvent;
    }

    @Provides
    public ConeControlEvent provideConeControlEvent() {
        return coneControlEvent;
    }

    @Provides
    public ConeControlPresenter.View provideView() {
        return view;
    }

    @Provides
    public ConeControlPresenter providePresenter(ConeControlPresenter.View categoryView,
                                                 ConeControlBO coneControlBO, MailSender mailSender,
                                                 User loggedUser) {
        return new ConeControlPresenter(categoryView, coneControlEvent, coneControlBO, mailSender, loggedUser);
    }
}

package es.formulastudent.app.di.module.activity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.mailsender.MailSender;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.conecontrol.ConeControlPresenter;

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

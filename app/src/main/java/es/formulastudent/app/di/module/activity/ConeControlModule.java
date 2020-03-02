package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.mailsender.MailSender;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.view.activity.conecontrol.ConeControlPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
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
    public ConeControlPresenter providePresenter(ConeControlPresenter.View categoryView, Context context, ConeControlBO coneControlBO, MailSender mailSender) {
        return new ConeControlPresenter(categoryView, context, coneControlEvent, coneControlBO, mailSender);
    }


}

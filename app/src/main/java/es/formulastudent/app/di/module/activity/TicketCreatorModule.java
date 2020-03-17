package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.ticket.TicketBO;
import es.formulastudent.app.mvp.view.activity.ticketcreator.TicketCreatorPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class TicketCreatorModule {

    private TicketCreatorPresenter.View view;

    public TicketCreatorModule(TicketCreatorPresenter.View view) {
        this.view = view;
    }

    @Provides
    public TicketCreatorPresenter.View provideView() {
        return view;
    }

    @Provides
    public TicketCreatorPresenter providePresenter(TicketCreatorPresenter.View categoryView,
                                                   Context context, TicketBO ticketBO) {
        return new TicketCreatorPresenter(categoryView, context, ticketBO);
    }
}

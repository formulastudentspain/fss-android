package es.formulastudent.app.di.component;

import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.TicketCreatorModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.ticketcreator.TicketCreatorActivity;
import es.formulastudent.app.mvp.view.activity.ticketcreator.TicketCreatorPresenter;


@Singleton
@Component(modules = {TicketCreatorModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface TicketCreatorComponent {

    void inject(TicketCreatorActivity ticketCreatorActivity);
    TicketCreatorPresenter getMainPresenter();
    User getLoggedUser();

}

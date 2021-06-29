package es.formulastudent.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import es.formulastudent.app.di.module.activity.AdminOpsModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.adminoperations.AdminOpsFragment;
import es.formulastudent.app.mvp.view.screen.adminoperations.AdminOpsPresenter;


@Singleton
@Component(modules = {AdminOpsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface AdminOpsComponent {

    void inject(AdminOpsFragment statisticsActivity);
    AdminOpsPresenter getMainPresenter();
    User getLoggedUser();
}

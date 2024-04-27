package code.formulastudentspain.app.di.component;


import javax.inject.Singleton;

import dagger.Component;
import code.formulastudentspain.app.di.module.activity.AdminOpsModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.adminoperations.AdminOpsFragment;
import code.formulastudentspain.app.mvp.view.screen.adminoperations.AdminOpsPresenter;


@Singleton
@Component(modules = {AdminOpsModule.class, SharedPreferencesModule.class}, dependencies = {AppComponent.class})
public interface AdminOpsComponent {

    void inject(AdminOpsFragment statisticsActivity);
    AdminOpsPresenter getMainPresenter();
    User getLoggedUser();
}

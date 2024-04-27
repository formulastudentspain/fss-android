package code.formulastudentspain.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.view.screen.egresschrono.EgressChronoPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class PreScrutineeringModule {

    private EgressChronoPresenter.View view;

    public PreScrutineeringModule(EgressChronoPresenter.View view) {
        this.view = view;
    }

    @Provides
    public EgressChronoPresenter.View provideView() {
        return view;
    }

    @Provides
    public EgressChronoPresenter providePresenter(EgressChronoPresenter.View categoryView, Context context, EgressBO egressBO) {
        return new EgressChronoPresenter(categoryView, context, egressBO);
    }


}

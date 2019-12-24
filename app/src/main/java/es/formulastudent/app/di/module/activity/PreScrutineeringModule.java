package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.view.activity.egresschrono.EgressChronoPresenter;

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

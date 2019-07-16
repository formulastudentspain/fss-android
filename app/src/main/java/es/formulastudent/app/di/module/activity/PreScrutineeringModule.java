package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.view.activity.prescrutineeringdetail.PreScrutineeringDetailPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class PreScrutineeringModule {

    private PreScrutineeringDetailPresenter.View view;

    public PreScrutineeringModule(PreScrutineeringDetailPresenter.View view) {
        this.view = view;
    }

    @Provides
    public PreScrutineeringDetailPresenter.View provideView() {
        return view;
    }

    @Provides
    public PreScrutineeringDetailPresenter providePresenter(PreScrutineeringDetailPresenter.View categoryView, Context context, EgressBO egressBO) {
        return new PreScrutineeringDetailPresenter(categoryView, context, egressBO);
    }


}

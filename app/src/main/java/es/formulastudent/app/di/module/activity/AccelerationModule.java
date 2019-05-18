package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.acceleration.AccelerationBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.AccelerationPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class AccelerationModule {

    private AccelerationPresenter.View view;

    public AccelerationModule(AccelerationPresenter.View view) {
        this.view = view;
    }

    @Provides
    public AccelerationPresenter.View provideView() {
        return view;
    }

    @Provides
    public AccelerationPresenter providePresenter(AccelerationPresenter.View categoryView, Context context,
                                                  TeamBO teamBO, AccelerationBO accelerationBO, UserBO userBO) {
        return new AccelerationPresenter(categoryView, context, teamBO, accelerationBO, userBO);
    }


}

package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.endurance.EnduranceBO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.EndurancePresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
public class EnduranceModule {

    private EndurancePresenter.View view;

    public EnduranceModule(EndurancePresenter.View view) {
        this.view = view;
    }

    @Provides
    public EndurancePresenter.View provideView() {
        return view;
    }

    @Provides
    public EndurancePresenter providePresenter(EndurancePresenter.View categoryView, Context context,
                                                  TeamBO teamBO, EnduranceBO enduranceBO, UserBO userBO, BriefingBO briefingBO) {
        return new EndurancePresenter(categoryView, context, teamBO, enduranceBO, userBO, briefingBO);
    }


}

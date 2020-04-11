package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.di.module.business.SharedPreferencesModule;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.racecontrol.RaceControlPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

@Module(includes = {ContextModule.class, BusinessModule.class, SharedPreferencesModule.class})
public class RaceControlModule {

    private RaceControlPresenter.View view;
    private RaceControlEvent raceControlEvent;
    private String raceType;
    private String raceArea;

    public RaceControlModule(RaceControlPresenter.View view, RaceControlEvent raceControlEvent, String raceType, String raceArea) {
        this.view = view;
        this.raceControlEvent = raceControlEvent;
        this.raceType = raceType;
        this.raceArea = raceArea;
    }

    @Provides
    public RaceControlEvent provideRaceControlEvent() {
        return raceControlEvent;
    }

    @Provides
    public RaceControlPresenter.View provideView() {
        return view;
    }

    @Provides
    public RaceControlPresenter providePresenter(RaceControlPresenter.View categoryView, Context context,
                                                 RaceControlBO raceControlBO, User user,
                                                 LoadingDialog loadingDialog, Messages messages) {
        return new RaceControlPresenter(categoryView, context, raceControlEvent, raceType, raceArea,
                raceControlBO, user, loadingDialog, messages);
    }


}

package code.formulastudentspain.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.SharedPreferencesModule;
import code.formulastudentspain.app.mvp.data.business.racecontrol.RaceControlBO;
import code.formulastudentspain.app.mvp.data.model.RaceControlEvent;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.racecontrol.RaceControlPresenter;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;

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
                                                 Messages messages) {
        return new RaceControlPresenter(categoryView, context, raceControlEvent, raceType, raceArea,
                raceControlBO, user, messages);
    }


}

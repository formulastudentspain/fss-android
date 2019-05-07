package es.formulastudent.app.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.view.activity.briefing.BriefingPresenter;

@Module(includes = {ContextModule.class})
public class BriefingModule {

    private BriefingPresenter.View view;

    public BriefingModule(BriefingPresenter.View view) {
        this.view = view;
    }

    @Provides
    public BriefingPresenter.View provideView() {
        return view;
    }

    @Provides
    public BriefingPresenter providePresenter(BriefingPresenter.View categoryView, Context context) {
        return new BriefingPresenter(categoryView, context);
    }


}

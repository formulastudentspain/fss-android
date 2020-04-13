package es.formulastudent.app.di.module.business;

import androidx.fragment.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

@Module(includes = {ContextModule.class})
public class UtilsModule {

    @Provides
    public LoadingDialog providesLoadingDialog(FragmentActivity activity){
        return LoadingDialog.newInstance(activity);
    }

    @Provides
    public Messages providesMessages(FragmentActivity activity){
        return new Messages(activity);
    }
}

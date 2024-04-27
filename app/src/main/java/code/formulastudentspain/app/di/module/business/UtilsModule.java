package code.formulastudentspain.app.di.module.business;

import androidx.fragment.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.mvp.view.utils.LoadingDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;

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

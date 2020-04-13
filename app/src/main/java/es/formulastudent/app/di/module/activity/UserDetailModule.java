package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.di.module.business.UtilsModule;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.userdetail.UserDetailPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

@Module(includes = {UtilsModule.class, ContextModule.class, BusinessModule.class})
public class UserDetailModule {

    private UserDetailPresenter.View view;

    public UserDetailModule(UserDetailPresenter.View view) {
        this.view = view;
    }

    @Provides
    public UserDetailPresenter.View provideView() {
        return view;
    }

    @Provides
    public UserDetailPresenter providePresenter(UserDetailPresenter.View categoryView, Context context,
                                                UserBO userBO, User loggedUser, ImageBO imageBO,
                                                LoadingDialog loadingDialog, Messages messages) {
        return new UserDetailPresenter(categoryView, context, userBO, loggedUser, imageBO,
                loadingDialog, messages);
    }


}

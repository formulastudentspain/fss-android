package code.formulastudentspain.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.di.module.business.BusinessModule;
import code.formulastudentspain.app.di.module.business.UtilsModule;
import code.formulastudentspain.app.mvp.data.business.imageuploader.ImageBO;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.userdetail.UserDetailPresenter;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;

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
                                                Messages messages) {
        return new UserDetailPresenter(categoryView, context, userBO, loggedUser, imageBO, messages);
    }
}

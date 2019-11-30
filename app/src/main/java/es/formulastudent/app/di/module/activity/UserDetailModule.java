package es.formulastudent.app.di.module.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.business.BusinessModule;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageUploaderBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailPresenter;

@Module(includes = {ContextModule.class, BusinessModule.class})
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
    public UserDetailPresenter providePresenter(UserDetailPresenter.View categoryView, Context context, UserBO userBO, User loggedUser, ImageUploaderBO imageUploaderBO) {
        return new UserDetailPresenter(categoryView, context, userBO, loggedUser, imageUploaderBO);
    }


}

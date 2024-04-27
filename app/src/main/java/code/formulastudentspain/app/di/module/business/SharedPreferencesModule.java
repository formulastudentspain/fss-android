package code.formulastudentspain.app.di.module.business;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.mvp.data.model.User;

@Module (includes = {ContextModule.class})
public class SharedPreferencesModule {

    public static final String PREFS_NAME = "myPrefsFile";
    public static final String PREFS_CURRENT_USER = "currentUser";

    @Provides
    public User providesLoggedUser(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String serializedUser = prefs.getString(PREFS_CURRENT_USER, null);

        if(serializedUser == null){
            return new User();
        }else{
            User user = new GsonBuilder().create().fromJson(serializedUser, User.class);
            return user;
        }
    }


    @Provides
    public SharedPreferences providesPrefs(Context context){
        return context.getSharedPreferences(PREFS_NAME, 0);
    }

}

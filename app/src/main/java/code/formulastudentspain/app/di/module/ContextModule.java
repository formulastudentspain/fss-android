package code.formulastudentspain.app.di.module;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class ContextModule {

    private Context context;
    private FragmentActivity activity;

    public ContextModule(Context context){
        this.context = context;
    }

    public ContextModule(Context context, FragmentActivity activity){
        this.context = context;
        this.activity = activity;
    }

    @Provides
    public Context context(){
        return context.getApplicationContext();
    }

    @Provides
    public FragmentActivity activity(){
        return activity;
    }
}
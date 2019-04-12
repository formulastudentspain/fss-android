package es.formulastudent.app.di.module;

import android.content.Context;
import android.location.Geocoder;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class MapModule {

    @Provides
    public Geocoder geocoder(Context context){
        return new Geocoder(context);
    }
}

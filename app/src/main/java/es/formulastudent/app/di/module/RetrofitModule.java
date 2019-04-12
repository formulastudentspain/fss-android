package es.formulastudent.app.di.module;

import dagger.Module;
import dagger.Provides;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.formulastudent.app.mvp.data.api.retrofit.UsersApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RetrofitModule {

    @Provides
    public UsersApi randomUsersApi(Retrofit retrofit){
        return retrofit.create(UsersApi.class);
    }


    @Provides
    public Retrofit retrofit(GsonConverterFactory gsonConverterFactory){
        return new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }


    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder  = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient();

        return gsonBuilder.create();
    }


    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }
}

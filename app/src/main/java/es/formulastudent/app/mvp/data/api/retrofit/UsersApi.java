package es.formulastudent.app.mvp.data.api.retrofit;

import es.formulastudent.app.mvp.data.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface UsersApi {

    @GET("api/")
    Call<ApiResponse> getUsers(@Query("results")int results, @Query("seed")String seed);

}

package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ranjith.suda on 12/30/2015.
 * API to retrieve the Application  ImageURL Config .
 * https://api.themoviedb.org/3/configuration?api_key=57a2fe1fb88623756080330e465f20f7
 */
public interface TheMovieDbConfigAPI {

    @GET("configuration")
    Observable<Response<TheMovieDbConfigResponse>> getAppConfig(@Query("api_key") String api_key);
}

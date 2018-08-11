package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ranjith.suda on 12/29/2015.
 * <p>
 * API to retrieve the List of Genre's across the Application..
 * https://api.themoviedb.org/3/genre/movie/list?api_key=57a2fe1fb88623756080330e465f20f7
 */
public interface AllGenreListAPI {

    @GET("genre/movie/list")
    Observable<Response<AllMovieGenreListResponse>> getMovieGenreList(@Query("api_key") String api_key);
}

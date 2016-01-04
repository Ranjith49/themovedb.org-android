package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ranjith.suda on 12/29/2015.
 *
 * API to retrieve the List of Genre's across the Application..
 * https://api.themoviedb.org/3/genre/movie/list?api_key=57a2fe1fb88623756080330e465f20f7
 */
public interface AllGenreListAPI {

  @GET("genre/movie/list")
  Call<AllMovieGenreListResponse> getMovieGenreList(@Query("api_key") String api_key);
}

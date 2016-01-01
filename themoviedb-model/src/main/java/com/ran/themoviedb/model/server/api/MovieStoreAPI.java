package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.MovieStoreResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public interface MovieStoreAPI {

  /**
   * Method to get the Popular Movie List
   * https://api.themoviedb.org/3/movie/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
   *
   * @param api_key -- API Key
   * @param page    -- Page Index
   * @return -- Json of format {@Link MovieStoreResponse}
   */
  @GET("movie/popular")
  Call<MovieStoreResponse> getPopularMovieList(@Query("api_key") String api_key,
                                               @Query("page") int page);

  /**
   * Method to get the Top Rated Movie List
   * https://api.themoviedb.org/3/movie/top_rated?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
   *
   * @param api_key -- API Key
   * @param page    -- Page Index
   * @return -- Json of Format {@Link MovieStoreResponse}
   */
  @GET("movie/top_rated")
  Call<MovieStoreResponse> getTopRatedMovieList(@Query("api_key") String api_key,
                                                @Query("page") int page);

  /**
   * Method to get the Upcoming Movie List
   * https://api.themoviedb.org/3/movie/upcoming?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
   *
   * @param api_key -- API Key
   * @param page    -- Page Index
   * @return -- Json of Format {@Link MovieStoreResponse}
   */
  @GET("movie/upcoming")
  Call<MovieStoreResponse> getUpComingMovieList(@Query("api_key") String api_key,
                                                @Query("page") int page);

  /**
   * Method to get the Now Showing Movie List
   * https://api.themoviedb.org/3/movie/now_playing?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
   *
   * @param api_key -- API Key
   * @param page    -- page Index
   * @return -- Json of format {@Link MovieStoreResponse}
   */
  @GET("movie/now_playing")
  Call<MovieStoreResponse> getNowShowingMovieList(@Query("api_key") String api_key,
                                                  @Query("page") int page);
}

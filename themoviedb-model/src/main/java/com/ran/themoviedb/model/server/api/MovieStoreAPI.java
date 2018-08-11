package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.MovieStoreResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Movie Store API
 *
 * @author ranjithsuda
 */
public interface MovieStoreAPI {

    /**
     * Method to get the Popular Movie List
     * https://api.themoviedb.org/3/movie/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key -- API Key
     * @param page    -- Page Index
     * @param lang    -- Language Attribute
     * @return -- Json of format {@Link MovieStoreResponse}
     */
    @GET("movie/popular")
    Observable<Response<MovieStoreResponse>> getPopularMovieList(@Query("api_key") String api_key,
                                                                 @Query("page") int page,
                                                                 @Query("language") String lang);

    /**
     * Method to get the Top Rated Movie List
     * https://api.themoviedb.org/3/movie/top_rated?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key -- API Key
     * @param page    -- Page Index
     * @param lang    -- Language Attribute
     * @return -- Json of Format {@Link MovieStoreResponse}
     */
    @GET("movie/top_rated")
    Observable<Response<MovieStoreResponse>> getTopRatedMovieList(@Query("api_key") String api_key,
                                                                  @Query("page") int page,
                                                                  @Query("language") String lang);

    /**
     * Method to get the Upcoming Movie List
     * https://api.themoviedb.org/3/movie/upcoming?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key -- API Key
     * @param page    -- Page Index
     * @param lang    -- Language Attribute
     * @return -- Json of Format {@Link MovieStoreResponse}
     */
    @GET("movie/upcoming")
    Observable<Response<MovieStoreResponse>> getUpComingMovieList(@Query("api_key") String api_key,
                                                                  @Query("page") int page,
                                                                  @Query("language") String lang);

    /**
     * Method to get the Now Showing Movie List
     * https://api.themoviedb.org/3/movie/now_playing?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key -- API Key
     * @param page    -- page Index
     * @param lang    -- Language Attribute
     * @return -- Json of format {@Link MovieStoreResponse}
     */
    @GET("movie/now_playing")
    Observable<Response<MovieStoreResponse>> getNowShowingMovieList(@Query("api_key") String api_key,
                                                                    @Query("page") int page,
                                                                    @Query("language") String lang);
}

package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public interface SearchAPI {

  /**
   * Retrofit API to get the Search Movie Response based on query String ..
   * <p/>
   * https://api.themoviedb.org/3/genre/movie/list?api_key=57a2fe1fb88623756080330e465f20f7@query=Star+wars&page=1
   *
   * @param api_key -- Application API key
   * @param page    -- page number
   * @param query   -- Query String
   * @return -- Response
   */
  @GET("search/movie")
  Call<MovieSearchResponse> getMovieSearchResults(@Query("api_key") String api_key,
                                                  @Query("page") int page,
                                                  @Query("query") String query);

  /**
   * Retrofit API to get the Search TV  Response based on query String ..
   * <p/>
   * https://api.themoviedb.org/3/search/tv?api_key=57a2fe1fb88623756080330e465f20f7&query=vampire&page=1
   *
   * @param api_key -- Application API key
   * @param page    -- page number
   * @param query   -- Query String
   * @return -- Response
   */
  @GET("search/movie")
  Call<TvShowSearchResponse> getTvSearchResults(@Query("api_key") String api_key,
                                                @Query("page") int page,
                                                @Query("query") String query);

  /**
   * Retrofit API to get the Search People  Response based on query String ..
   * <p/>
   * https://api.themoviedb.org/3/search/person/?api_key=57a2fe1fb88623756080330e465f20f7&query=ranjith&page=1
   *
   * @param api_key -- Application API key
   * @param page    -- page number
   * @param query   -- Query String
   * @return -- Response
   */
  @GET("search/movie")
  Call<PeopleSearchResponse> getPeopleSearchResults(@Query("api_key") String api_key,
                                                    @Query("page") int page,
                                                    @Query("query") String query);
}

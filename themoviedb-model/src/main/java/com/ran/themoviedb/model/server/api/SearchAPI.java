package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Search API
 *
 * @author ranjithsuda
 */
public interface SearchAPI {

    /**
     * Retrofit API to get the Search Movie Response based on query String ..
     * <p>
     * https://api.themoviedb.org/3/genre/movie/list?api_key=57a2fe1fb88623756080330e465f20f7@query=Star+wars&page=1
     *
     * @param api_key -- Application API key
     * @param page    -- page number
     * @param query   -- Query String
     * @return -- Response
     */
    @GET("search/movie")
    Observable<Response<MovieSearchResponse>> getMovieSearchResults(@Query("api_key") String api_key,
                                                                    @Query("page") int page,
                                                                    @Query("query") String query);

    /**
     * Retrofit API to get the Search TV  Response based on query String ..
     * <p>
     * https://api.themoviedb.org/3/search/tv?api_key=57a2fe1fb88623756080330e465f20f7&query=vampire&page=1
     *
     * @param api_key -- Application API key
     * @param page    -- page number
     * @param query   -- Query String
     * @return -- Response
     */
    @GET("search/tv")
    Observable<Response<TvShowSearchResponse>> getTvSearchResults(@Query("api_key") String api_key,
                                                                  @Query("page") int page,
                                                                  @Query("query") String query);

    /**
     * Retrofit API to get the Search People  Response based on query String ..
     * <p>
     * https://api.themoviedb.org/3/search/person/?api_key=57a2fe1fb88623756080330e465f20f7&query=ranjith&page=1
     *
     * @param api_key -- Application API key
     * @param page    -- page number
     * @param query   -- Query String
     * @return -- Response
     */
    @GET("search/person")
    Observable<Response<PeopleSearchResponse>> getPeopleSearchResults(@Query("api_key") String api_key,
                                                                      @Query("page") int page,
                                                                      @Query("query") String query);
}

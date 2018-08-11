package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.TVShowStoreResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * TV Store API
 *
 * @author ranjithsuda
 */
public interface TVShowStoreAPI {

    /**
     * Method to get the Popular Tv shows List
     * http://api.themoviedb.org/3/tv/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key   -- API Key
     * @param pageIndex -- Index of the Page
     * @param lang      -- Language Attribute
     * @return -- Json of format {@link TVShowStoreResponse}
     */
    @GET("tv/popular")
    Observable<Response<TVShowStoreResponse>> getPopularTVShows(@Query("api_key") String api_key,
                                                                @Query("page") int pageIndex,
                                                                @Query("language") String lang);

    /**
     * Method to get the Top Rated tv Shows List
     * http://api.themoviedb.org/3/tv/top_rated?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key   -- API Key
     * @param pageIndex -- Index of the Page
     * @param lang      -- Language Attribute
     * @return -- Json of format {@link TVShowStoreResponse}
     */
    @GET("tv/top_rated")
    Observable<Response<TVShowStoreResponse>> getTopRatedTVShows(@Query("api_key") String api_key,
                                                                 @Query("page") int pageIndex,
                                                                 @Query("language") String lang);

    /**
     * Method to get the On The Air Tv Shows
     * http://api.themoviedb.org/3/tv/on_the_air?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param api_key   -- API Key
     * @param pageIndex -- index of the Page
     * @param lang      -- Language Attribute
     * @return -- Json of format {@link TVShowStoreResponse}
     */
    @GET("tv/on_the_air")
    Observable<Response<TVShowStoreResponse>> getOnAirTVShows(@Query("api_key") String api_key,
                                                              @Query("page") int pageIndex,
                                                              @Query("language") String lang);
}

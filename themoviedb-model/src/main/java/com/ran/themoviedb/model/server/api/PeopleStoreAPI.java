package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.PeopleStoreResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * People Store API
 *
 * @author ranjithsuda
 */
public interface PeopleStoreAPI {

    /**
     * Method to get list of Popular People
     * http://api.themoviedb.org/3/person/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
     *
     * @param key   -- API key
     * @param index -- Index of page
     * @param lang  -- Language Attribute
     * @return -- Json of format {@link PeopleStoreResponse}
     */
    @GET("person/popular")
    Observable<Response<PeopleStoreResponse>> getPeopleStoreList(@Query("api_key") String key,
                                                                 @Query("page") int index,
                                                                 @Query("language") String lang);
}

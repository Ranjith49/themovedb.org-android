package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.PeopleDetailResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public interface PeopleDetailsAPI {

  /**
   * Method to get list of Popular People
   * http://api.themoviedb.org/3/person/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
   *
   * @param id      -- id of person
   * @param api_key -- Api key
   * @param lang    -- Language Attribute
   * @return -- Json of format {@link com.ran.themoviedb.model.server.response.PeopleDetailResponse}
   */
  @GET("person/{id}")
  Call<PeopleDetailResponse> getPeopleDetailResponse(@Path("id") int id,
                                                     @Query("api_key") String api_key,
                                                     @Query("language") String lang);

}

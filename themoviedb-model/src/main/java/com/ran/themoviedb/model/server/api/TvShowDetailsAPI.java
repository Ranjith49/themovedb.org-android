package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.TvShowDetailResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ranjith.suda on 2/29/2016.
 * <p/>
 * Interface containing the REST API's for the Tv Show Details
 */
public interface TvShowDetailsAPI {

  /**
   * Method to get the Tv Show Basic Details
   * https://api.themoviedb.org/3/tv/1418?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- id of the Movie
   * @param api_key -- API Key of the App
   * @param lang    -- Language of the Tv show
   * @return -- TvShowDetailResponse in return
   */
  @GET("tv/{id}")
  Call<TvShowDetailResponse> getTvShowBasicDetails(@Path("id") int id,
                                                   @Query("api_key") String api_key,
                                                   @Query("language") String lang);
}

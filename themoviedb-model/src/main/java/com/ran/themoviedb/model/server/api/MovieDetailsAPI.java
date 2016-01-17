package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.model.server.response.ImageDetailResponse;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ranjith.suda on 1/6/2016.
 * <p/>
 * This API is responsible for getting the All Information required for the Movie Detail Pages
 */
public interface MovieDetailsAPI {

  /**
   * Method to get the Movie Basic Details
   * https://api.themoviedb.org/3/movie/140607?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- id of the Movie
   * @param api_key -- API Key of the App
   * @param lang    -- Language of the Movie
   * @return -- MovieDetailResponse in return
   */
  @GET("movie/{id}")
  Call<MovieDetailResponse> getMovieBasicDetails(@Path("id") int id,
                                                 @Query("api_key") String api_key,
                                                 @Query("language") String lang);

  /**
   * Method to get the Movie Image Details [Backdrops and Posters]
   * https://api.themoviedb.org/3/movie/140607/images?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- Id of the Movie
   * @param api_key -- API Key of the App
   * @return -- ImageDetailResponse
   */
  @GET("movie/{id}/images")
  Call<ImageDetailResponse> getMovieImageDetails(@Path("id") int id,
                                                 @Query("api_key") String api_key);


  /**
   * Method to get the Movie Video Details
   * https://api.themoviedb.org/3/movie/140607/videos?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- Id of the Movie
   * @param api_key -- API Key of the App
   * @return -- VideoDetailResponse
   */
  @GET("movie/{id}/videos")
  Call<VideoDetailResponse> getMovieVideoDetails(@Path("id") int id,
                                                 @Query("api_key") String api_key);


  /**
   * Method to get the Movie Cast and Crew Details
   * https://api.themoviedb.org/3/movie/140607/credits?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- Id of the Movie
   * @param api_key -- API Key of the App
   * @param lang    -- Language of the Movie
   * @return -- CastCrewDetailResponse
   */
  @GET("movie/{id}/credits")
  Call<CastCrewDetailResponse> getCastCrewDetails(@Path("id") int id,
                                                  @Query("api_key") String api_key,
                                                  @Query("language") String lang);

  /**
   * Method to get the Movie Reviews Details
   * https://api.themoviedb.org/3/movie/140607/reviews?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- Id of the Movie
   * @param api_key -- API Key of the App
   * @param lang    -- Language of the Movie
   * @param page    -- index of the page
   * @return -- ReviewsDetailResponse
   */
  @GET("movie/{id}/reviews")
  Call<ReviewsDetailResponse> getReviewDetails(@Path("id") int id,
                                               @Query("page") int page,
                                               @Query("api_key") String api_key,
                                               @Query("language") String lang);

  /**
   * Method to get the Similar Movies Details
   * https://api.themoviedb.org/3/movie/140607/similar?api_key=57a2fe1fb88623756080330e465f20f7
   *
   * @param id      -- Id of the Movie
   * @param api_key -- API Key of the App
   * @param lang    -- Language of the Movie
   * @param page    -- index of the page
   * @return -- MovieSimilarDetailsResponse
   */
  @GET("movie/{id}/similar")
  Call<MovieSimilarDetailsResponse> getSimilarMovies(@Path("id") int id,
                                                     @Query("page") int page,
                                                     @Query("api_key") String api_key,
                                                     @Query("language") String lang);
}

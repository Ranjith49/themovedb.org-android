package com.ran.themoviedb.model.server.api;

import com.ran.themoviedb.model.server.response.ImageDetailResponse;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Observable<Response<TvShowDetailResponse>> getTvShowBasicDetails(@Path("id") int id,
                                                                     @Query("api_key") String api_key,
                                                                     @Query("language") String lang);

    /**
     * Method to get the Similar TvShow Details
     * https://api.themoviedb.org/3/tv/1418/similar?api_key=57a2fe1fb88623756080330e465f20f7
     *
     * @param id      -- Id of the Movie
     * @param api_key -- API Key of the App
     * @param lang    -- Language of the Movie
     * @param page    -- index of the page
     * @return -- TvShowSimilarDetailsResponse
     */
    @GET("tv/{id}/similar")
    Observable<Response<TvShowSimilarDetailsResponse>> getSimilarTvShows(@Path("id") int id,
                                                                         @Query("page") int page,
                                                                         @Query("api_key") String api_key,
                                                                         @Query("language") String lang);

    /**
     * Method to get the Tv show Image Details [Backdrops and Posters]
     * https://api.themoviedb.org/3/tv/1418/images?api_key=57a2fe1fb88623756080330e465f20f7
     *
     * @param id      -- Id of the Movie
     * @param api_key -- API Key of the App
     * @return -- ImageDetailResponse
     */
    @GET("tv/{id}/images")
    Observable<Response<ImageDetailResponse>> getTvShowImageDetails(@Path("id") int id,
                                                                    @Query("api_key") String api_key);


    /**
     * Method to get the Tv show Video Details
     * https://api.themoviedb.org/3/tv/1418/videos?api_key=57a2fe1fb88623756080330e465f20f7
     *
     * @param id      -- Id of the Movie
     * @param api_key -- API Key of the App
     * @return -- VideoDetailResponse
     */
    @GET("movie/{id}/videos")
    Observable<Response<VideoDetailResponse>> getTvShowVideoDetails(@Path("id") int id,
                                                                    @Query("api_key") String api_key);
}

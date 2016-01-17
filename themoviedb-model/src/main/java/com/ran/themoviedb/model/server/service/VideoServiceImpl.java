package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class VideoServiceImpl extends BaseRetrofitService<VideoDetailResponse> {

  private int id;
  private String language;
  private DisplayStoreType storeType;
  private Handler handler;

  public VideoServiceImpl(int id, DisplayStoreType storeType, Handler handler, String language) {
    this.id = id;
    this.language = language;
    this.storeType = storeType;
    this.handler = handler;
  }

  @Override
  protected void handleApiResponse(VideoDetailResponse response, int uniqueId) {
    if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
      handler.onVideoError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
    } else {
      handler.onVideoResponse(response, uniqueId);
    }
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onVideoError(errorType, uniqueId);
  }

  @Override
  protected Call<VideoDetailResponse> getRetrofitCall() {
    switch (storeType) {
      case MOVIE_STORE:
        MovieDetailsAPI api = RetrofitAdapters.getAppRestAdapter().create(MovieDetailsAPI.class);
        return api.getMovieVideoDetails(id, TheMovieDbConstants.APP_API_KEY);
      case TV_STORE:
        return null;
      default:
        return null;
    }
  }

  public interface Handler {

    void onVideoResponse(VideoDetailResponse response, int uniqueId);

    void onVideoError(UserAPIErrorType errorType, int uniqueId);

  }
}

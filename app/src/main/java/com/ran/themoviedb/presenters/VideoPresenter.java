package com.ran.themoviedb.presenters;

import android.content.Context;
import android.util.Log;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.entities.VideoDetails;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;
import com.ran.themoviedb.model.server.service.VideoServiceImpl;
import com.ran.themoviedb.view_pres_med.VideoDisplayView;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class VideoPresenter extends BasePresenter implements VideoServiceImpl.Handler {

  private VideoDisplayView videoDisplayView;
  private final int uniqueId;
  private final VideoServiceImpl service;
  private final DisplayStoreType storeType;
  private final String KEY_FILTER_YOUTUBE = "YouTube";

  public VideoPresenter(Context context, VideoDisplayView videoDisplayView, int uniqueId,
                        int id, DisplayStoreType storeType) {
    this.uniqueId = uniqueId;
    this.videoDisplayView = videoDisplayView;
    this.storeType = storeType;
    service = new VideoServiceImpl(id, storeType, this,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
  }


  @Override
  public void onVideoResponse(VideoDetailResponse response, int uniqueId) {

    ArrayList<VideoDetails> filteredResults = new ArrayList<>();
    for (VideoDetails videoDetails : response.getResults()) {
      if (videoDetails.getSite().equalsIgnoreCase(KEY_FILTER_YOUTUBE)) {
        filteredResults.add(videoDetails);
      }
    }
    videoDisplayView.onVideoResponse(filteredResults);
  }

  @Override
  public void onVideoError(UserAPIErrorType errorType, int uniqueId) {
    //Nothing to do
    Log.d("Video", "Response is null /Empty");
  }
}

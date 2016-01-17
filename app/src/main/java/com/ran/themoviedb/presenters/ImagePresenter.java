package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ImageDetailResponse;
import com.ran.themoviedb.model.server.service.ImageServiceImpl;
import com.ran.themoviedb.view_pres_med.ImageDisplayView;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class ImagePresenter extends BasePresenter implements ImageServiceImpl.Handler {

  private ImageDisplayView imageDisplayView;
  private final int uniqueId;
  private final ImageServiceImpl service;
  private final DisplayStoreType storeType;

  public ImagePresenter(Context context, ImageDisplayView imageDisplayView, int uniqueId,
                        int id, DisplayStoreType storeType) {
    this.uniqueId = uniqueId;
    this.imageDisplayView = imageDisplayView;
    this.storeType = storeType;
    service = new ImageServiceImpl(id, storeType, this,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    imageDisplayView.showProgressBar(true);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    imageDisplayView.showProgressBar(false);
  }

  @Override
  public void onImageResponse(ImageDetailResponse response, int uniqueId) {
    imageDisplayView.showProgressBar(false);
    imageDisplayView.imageResponse(response.getBackdrops(), response.getPosters());
  }

  @Override
  public void onImageError(UserAPIErrorType errorType, int uniqueId) {
    imageDisplayView.showProgressBar(false);
    imageDisplayView.imageError(errorType);
  }
}

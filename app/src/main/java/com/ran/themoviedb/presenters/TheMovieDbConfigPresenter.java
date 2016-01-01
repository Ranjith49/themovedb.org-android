package com.ran.themoviedb.presenters;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;
import com.ran.themoviedb.model.server.service.TheMovieDbConfigServiceImpl;
import com.ran.themoviedb.view_pres_med.TheMovieDbConfigView;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p>
 * Presenter for TheMovieDb Config Values from {@Link TheMovieDbConfigServiceImpl} , propagate to UI
 */
public class TheMovieDbConfigPresenter extends BasePresenter implements
    TheMovieDbConfigServiceImpl.Handler {

  private Context context;
  private TheMovieDbConfigView movieDbConfigView;
  private TheMovieDbConfigServiceImpl service;
  private int uniqueId;


  public TheMovieDbConfigPresenter(Context context, TheMovieDbConfigView movieDbConfigView, int
      uniqueId) {
    this.context = context;
    this.movieDbConfigView = movieDbConfigView;
    this.uniqueId = uniqueId;
    service = new TheMovieDbConfigServiceImpl(this);
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
  public void onConfigReturned(TheMovieDbConfigResponse response, int uniqueId) {
    TheMovieDbImagesConfig imagesConfig = response.getImages();
    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();
    AppSharedPreferences.getInstance(context).setMovieImageConfigData(gson.toJson(imagesConfig,
        type));
    movieDbConfigView.isConfigRetrievalSuccess(true);
  }

  @Override
  public void onConfigError(UserAPIErrorType errorType, int uniqueId) {
    movieDbConfigView.isConfigRetrievalSuccess(false);
  }
}

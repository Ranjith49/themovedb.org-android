package com.ran.themoviedb.presenters;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.preloaddb.SnappyDBEntityTypes;
import com.ran.themoviedb.model.preloaddb.SnappyPreloadData;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;
import com.ran.themoviedb.model.server.service.TheMovieDbConfigServiceImpl;
import com.ran.themoviedb.model.utils.ApplicationUtils;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Presenter for TheMovieDb Config Values from {@Link TheMovieDbConfigServiceImpl} , save Date
 */
public class TheMovieDbConfigPresenter implements TheMovieDbConfigServiceImpl.Handler {

  private static TheMovieDbConfigPresenter instance;

  public synchronized static TheMovieDbConfigPresenter getInstance() {
    if (instance == null) {
      instance = new TheMovieDbConfigPresenter();
    }
    return instance;
  }

  private TheMovieDbConfigPresenter() {
    //Nothing to do here ..
  }

  /**
   * Method to Fetch the MovieDb Configuration at any point of time
   *
   * @param uniqueId - Unique id
   */
  public void fetchMovieDbConfig(int uniqueId) {
    new TheMovieDbConfigServiceImpl(this).request(SnappyDBEntityTypes.MOVIE_DB_CONFIG,
        SnappyPreloadData.MOVIE_DB_CONFIG, uniqueId, new TypeToken<TheMovieDbConfigResponse>() {
        });
  }

  @Override
  public void onConfigReturned(TheMovieDbConfigResponse response, int uniqueId) {
    TheMovieDbImagesConfig imagesConfig = response.getImages();
    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();
    AppSharedPreferences.getInstance(ApplicationUtils.getApplication())
        .setMovieImageConfigData(gson.toJson(imagesConfig, type));
  }
}

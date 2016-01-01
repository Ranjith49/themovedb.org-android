package com.ran.themoviedb;

import android.app.Application;

import com.ran.themoviedb.model.utils.ApplicationUtils;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p>
 * Application Class for Initializations
 */
public class TheMovieDbAppController extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ApplicationUtils.setApplication(this);
  }
}

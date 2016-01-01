package com.ran.themoviedb.model.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by ranjith.suda on 12/30/2015.
 *
 * Utils Class holding anything related to the Application Class {@Link com.ran.themoviedb.TheMovieDbAppController}
 */
public class ApplicationUtils {

  private static Application application;

  public static Context getApplication() {
    return application;
  }

  public static void setApplication(Application application) {
    ApplicationUtils.application = application;
  }

}

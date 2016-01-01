package com.ran.themoviedb.model.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ranjith.suda on 12/29/2015.
 */
public class ServerUtils {

  /**
   * Method to check Whether the Internet Connection is there or not
   *
   * @param context -- Context of the Application
   * @return -- Whether there is internet or not ..
   */
  public static boolean isConnected(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
        .CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnected();
  }
}

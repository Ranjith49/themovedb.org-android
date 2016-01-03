package com.ran.themoviedb.utils;

import android.content.Context;
import android.content.Intent;

import com.ran.themoviedb.activities.HomeActivity;
import com.ran.themoviedb.activities.LanguageSelectionActivity;
import com.ran.themoviedb.activities.StoreActivity;
import com.ran.themoviedb.entities.DisplayStoreType;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Class responsible for navigating to Other Screens /Views
 */
public class Navigator {

  /**
   * Method used to navigate to Language Screen
   *
   * @param context -- Context of the App
   */
  public static void navigateToLanguageScreen(Context context) {
    Intent intent = new Intent(context, LanguageSelectionActivity.class);
    context.startActivity(intent);
  }

  /**
   * Method used to navigate to Home Screen
   *
   * @param context -- Context of the App ..
   */
  public static void navigateToAppHome(Context context) {
    Intent intent = new Intent(context, HomeActivity.class);
    context.startActivity(intent);
  }


  public static void navigateToStore(Context context, DisplayStoreType storeType) {
    Intent intent = new Intent(context, StoreActivity.class);
    intent.putExtra(TheMovieDbConstants.STORE_TYPE_KEY, DisplayStoreType.getStoreName(storeType));
    context.startActivity(intent);
  }
}

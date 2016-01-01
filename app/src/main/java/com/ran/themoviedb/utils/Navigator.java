package com.ran.themoviedb.utils;

import android.content.Context;
import android.content.Intent;

import com.ran.themoviedb.activities.HomeActivity;
import com.ran.themoviedb.activities.LanguageSelectionActivity;

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
}

package com.ran.themoviedb.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.ran.themoviedb.db.AppSharedPreferenceKeys;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class AppUiUtils {

  /**
   * Method to decide whether the Initial Language Selection is done or not
   *
   * @param context -- Context of the App ..
   * @return
   */
  public static boolean isInitialLangDone(Context context) {
    if (AppSharedPreferences.getInstance(context)
        .getSharedPrefApp().contains(AppSharedPreferenceKeys.APP_LANG_KEY)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to deliver the Current Screen Dimens [width and height]
   *
   * @param context -- Context from Screen to retrieve
   * @return -- Two dimensional Array [0 -- width , 1 -- height]
   */
  public static int[] getScreenDimens(Context context) {

    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return new int[]{size.x, size.y};
  }

  /**
   * Method to say whether Entered String is Null / "" /"null"
   *
   * @param string -- String
   * @return -- true/false
   */
  public static boolean isStringEmpty(String string) {
    if (string == null || string.equalsIgnoreCase(TheMovieDbConstants.EMPTY_STRING) || string
        .equalsIgnoreCase(TheMovieDbConstants.NULL_STRING)) {
      return true;
    } else {
      return false;
    }
  }
}

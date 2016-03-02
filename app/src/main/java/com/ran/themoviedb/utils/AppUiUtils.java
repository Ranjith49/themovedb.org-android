package com.ran.themoviedb.utils;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.ran.themoviedb.db.AppSharedPreferenceKeys;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.TheMovieDbConstants;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class AppUiUtils {

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
    if (string == null || string.equalsIgnoreCase(TheMovieDbConstants.EMPTY_STRING) ||
        string.equalsIgnoreCase(TheMovieDbConstants.NULL_STRING)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to return the Comma separated String for a list
   *
   * @param list -- list
   * @return -- String comma separated
   */
  public static String generateCommaString(ArrayList<String> list) {
    if (list == null || list.size() == 0) {
      return TheMovieDbConstants.EMPTY_STRING;
    }
    return TextUtils.join(TheMovieDbConstants.COMMA_STRING, list);
  }

  /**
   * Method to get Size based on size passed
   *
   * @param size -- Bytes
   * @return -- Readable format size
   */
  public static String getUnitSize(long size) {
    if (size <= 0) {
      return TheMovieDbConstants.ZERO_STRING;
    }
    int digitGroups = (int) (Math.log10(size) / Math.log10(TheMovieDbConstants.TWO_POWER_TEN));
    return new DecimalFormat("#,##0.#").format(
        size / Math.pow(TheMovieDbConstants.TWO_POWER_TEN, digitGroups)) +
        TheMovieDbConstants.SPACE_STRING +
        TheMovieDbConstants.UNITS[digitGroups];
  }

  public static int dpToPx(Context context, float dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) ((dp * scale) + 0.5f);
  }
}

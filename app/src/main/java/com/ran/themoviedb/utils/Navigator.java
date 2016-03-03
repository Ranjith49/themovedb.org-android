package com.ran.themoviedb.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;

import com.ran.themoviedb.BuildConfig;
import com.ran.themoviedb.R;
import com.ran.themoviedb.activities.FullImageActivity;
import com.ran.themoviedb.activities.HomeActivity;
import com.ran.themoviedb.activities.LanguageSelectionActivity;
import com.ran.themoviedb.activities.MovieDetailActivity;
import com.ran.themoviedb.activities.PeopleDetailActivity;
import com.ran.themoviedb.activities.StoreActivity;
import com.ran.themoviedb.activities.TvShowDetailActivity;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
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
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
  }

  /**
   * Method to navigate to the Store
   *
   * @param context   -- context
   * @param storeType -- Store Type
   */
  public static void navigateToStore(Context context, DisplayStoreType storeType) {
    Intent intent = new Intent(context, StoreActivity.class);
    intent.putExtra(TheMovieDbConstants.STORE_TYPE_KEY, DisplayStoreType.getStoreName(storeType));
    context.startActivity(intent);
  }

  /**
   * Method to navigate to the Movie Details Screen
   *
   * @param context -- Context
   * @param movieId -- Id of the Movie
   */
  public static void navigateToMovieDetails(Context context, int movieId) {
    Intent intent = new Intent(context, MovieDetailActivity.class);
    intent.putExtra(TheMovieDbConstants.MOVIE_ID_KEY, movieId);
    context.startActivity(intent);
  }

  /**
   * Method to navigate to the Tv Show Details Screen
   *
   * @param context  -- Context
   * @param tvShowId -- Id of the Tv Show
   */
  public static void navigateToTvShowDetails(Context context, int tvShowId) {
    Intent intent = new Intent(context, TvShowDetailActivity.class);
    intent.putExtra(TheMovieDbConstants.TV_SHOW_ID_KEY, tvShowId);
    context.startActivity(intent);
  }

  /**
   * Method to navigate to the Tv Show Details Screen
   *
   * @param context  -- Context
   * @param peopleId -- Id of the people
   */
  public static void navigateToPeopleDetails(Context context, int peopleId) {
    Intent intent = new Intent(context, PeopleDetailActivity.class);
    intent.putExtra(TheMovieDbConstants.PEOPLE_ID_KEY, peopleId);
    context.startActivity(intent);
  }

  /**
   * Method to navigate to Full Image Screen
   *
   * @param context  -- Context
   * @param imageUrl -- url
   */
  public static void navigateToFullImageScreen(Context context, String imageUrl, String imageType) {
    Intent intent = new Intent(context, FullImageActivity.class);
    intent.putExtra(TheMovieDbConstants.IMAGE_URL_KEY, imageUrl);
    intent.putExtra(TheMovieDbConstants.IMAGE_TYPE_KEY, imageType);
    context.startActivity(intent);
  }

  /**
   * Method to send the Email for About Us
   *
   * @param context -- Context
   */
  public static void sendEmail(Context context) {
    String emailTitle = context.getString(R.string.about_us_emailTitle);
    String emailId = context.getString(R.string.about_us_emailId);
    String emailChooserTitle = context.getString(R.string.about_us_emailDialogTitle);

    Intent email = new Intent(Intent.ACTION_SEND);
    email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailId});
    email.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
    email.putExtra(Intent.EXTRA_TEXT, buildEmailBody(context));
    // need this to prompts email client only
    email.setType("message/rfc822");
    context.startActivity(Intent.createChooser(email, emailChooserTitle));
  }

  private static String buildEmailBody(Context context) {
    String body = TheMovieDbConstants.EMPTY_STRING;
    String deviceVersion = context.getString(R.string.about_us_deviceVersion) +
        TheMovieDbConstants.EMPTY_STRING + Build.VERSION.RELEASE;
    String appVersion =
        context.getString(R.string.about_us_appVersion) + TheMovieDbConstants.EMPTY_STRING;
    try {
      PackageInfo packageInfo = context.getPackageManager()
          .getPackageInfo(context.getPackageName(), 0);
      appVersion = appVersion + packageInfo.versionName;
    } catch (Exception e) {
      appVersion = appVersion + BuildConfig.VERSION_NAME + TheMovieDbConstants.OPEN_BRACKET +
          BuildConfig.VERSION_CODE + TheMovieDbConstants.CLOSE_BRACKET;
    }

    return body + deviceVersion + System.getProperty("line.separator") + appVersion;
  }
}

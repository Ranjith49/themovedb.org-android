package com.ran.themoviedb.utils;

import android.content.Context;
import android.content.Intent;

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
 * <p>
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
}

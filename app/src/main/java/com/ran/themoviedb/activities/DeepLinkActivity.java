package com.ran.themoviedb.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.utils.Navigator;

import java.util.List;

/**
 * Created by ranjith.suda on 3/1/2016.
 * <p/>
 * Single place to Parse the Deep links , and launch Corresponding Views.
 */
public class DeepLinkActivity extends Activity {

  private final String TAG = DeepLinkActivity.class.getSimpleName();

  //These EndPoints match to {@link DisplayStoreType}
  private final String MOVIE_END_POINT = "movie";
  private final String TV_END_POINT = "tv";
  private final String PERSON_ENDPOINT = "person";

  private final String Dash_Seperator = "-";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    if (intent == null || intent.getData() == null) {
      Log.d(TAG, "Intent and its date are null");
      finish();
    } else {
      String action = intent.getAction();
      Uri data = intent.getData();
      parseDeepLink(data, action);
    }

    setContentView(R.layout.activity_deeplink);
  }

  /**
   * Method to separate Host , Path and Path Segments from uri
   *
   * @param dataUri      -- uri
   * @param intentAction -- intent Action
   */

  private void parseDeepLink(Uri dataUri, String intentAction) {
    if (!intentAction.equalsIgnoreCase(Intent.ACTION_VIEW)) {
      Log.d(TAG, "Intent Action is not the View , Ignore and Finish");
      finish();
    }

    if (dataUri != null && dataUri.getPathSegments() != null) {
      Log.d(TAG, "Url Host : " + dataUri.getHost());
      Log.d(TAG, "Url Path : " + dataUri.getPath());
      Log.d(TAG, "Url path Segments : " + dataUri.getPathSegments().toString());

      List<String> pathSegments = dataUri.getPathSegments();
      if (pathSegments.size() <= 1) {
        Log.d(TAG, "Path Segments are Zero / One , We cannot proceed");
        Navigator.navigateToAppHome(this);
        finish();
      } else {
        String path_Zero = pathSegments.get(0);
        String path_One = pathSegments.get(1);

        if (path_Zero.equalsIgnoreCase(MOVIE_END_POINT) ||
            path_Zero.equalsIgnoreCase(TV_END_POINT) ||
            path_Zero.equalsIgnoreCase(PERSON_ENDPOINT)) {
          validateSecondPathSegment(path_Zero, path_One);
        } else {
          Log.d(TAG, "Path Segment at Zero is not correct , finish");
          finish();
        }
      }
    } else {
      Log.d(TAG, "Data Uri / Path Segment are null");
      finish();
    }
  }

  /**
   * Validate the Second Path , to extract the Id for the Launch
   *
   * @param storeName -- storeName [Movie , tv ,person]
   * @param pathOne   -- pathOne to be validated
   */
  private void validateSecondPathSegment(String storeName, String pathOne) {

    //a) Try Split the Path One for the Integer only
    int id = -1;
    try {
      id = Integer.parseInt(pathOne);
    } catch (NumberFormatException exception) {
      Log.d(TAG, "Number Format Exception , Path one is not just a number");
    }

    // b) Try to split the Path one by using Delimiter
    if (id == -1) {
      try {
        id = Integer.parseInt(pathOne.split(Dash_Seperator)[0]);
        launchMovieDbActivity(DisplayStoreType.getStoreType(storeName), id);
      } catch (Exception exception) {
        Log.d(TAG, "All tries to Parse the Path One are done");
        finish();
      }
    } else {
      launchMovieDbActivity(DisplayStoreType.getStoreType(storeName), id);
    }
  }

  /**
   * Method to launch the Movie Db Screen
   *
   * @param storeType -- storetype
   * @param id        -- Id
   */
  private void launchMovieDbActivity(DisplayStoreType storeType, int id) {
    switch (storeType) {
      case MOVIE_STORE:
        Navigator.navigateToMovieDetails(this, id);
        break;
      case TV_STORE:
        Navigator.navigateToTvShowDetails(this, id);
        break;
      case PERSON_STORE:
        Navigator.navigateToPeopleDetails(this, id);
        break;
    }
    finish();
  }
}

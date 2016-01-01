package com.ran.themoviedb.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.ran.themoviedb.entities.LanguageType;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 12/31/2015.
 * <p/>
 * Single Place to Store all App Required Shared Preferences
 */
public class AppSharedPreferences {

  private static AppSharedPreferences instance = null;
  private SharedPreferences sharedPreferences;

  /**
   * To get Single Instance holding all the App Shared Preferences
   *
   * @param context -- Context of the App
   * @return -- Single Instance
   */
  public static AppSharedPreferences getInstance(Context context) {
    if (instance == null) {
      synchronized (AppSharedPreferences.class) {
        if (instance == null) {
          instance = new AppSharedPreferences(context);
        }
      }
    }
    return instance;
  }

  private AppSharedPreferences(Context context) {
    sharedPreferences = context.getSharedPreferences(AppSharedPreferenceKeys.APP_SHARED_PREF_KEY,
        Context.MODE_PRIVATE);
  }

  /**
   * Method to return the Current Shared Preference of APp
   *
   * @return -- SharedPreferences Obj.
   */
  public SharedPreferences getSharedPrefApp() {
    return sharedPreferences;
  }

  /**
   * Set the All Genre Data retrieved from the Rest API {@Link com.ran.themoviedb.model.server.api.AllMovieGenreListAPI}
   *
   * @param data -- String Json
   */
  public void setGenreListData(String data) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(AppSharedPreferenceKeys.MOVIE_GENRE_LIST_KEY, data);
    editor.commit();
  }

  /**
   * Set Part (a) of the Config data from Rest API {@Link com.ran.themoviedb.model.server.api.TheMovieDbConfigAPI}
   *
   * @param data -- String Json
   */
  public void setMovieImageConfigData(String data) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(AppSharedPreferenceKeys.MOVIE_CONFIG_IMAGES_KEY, data);
    editor.commit();
  }

  /**
   * Get the Genre List data of Model {@Link com.ran.themoviedb.model.server.response.AllMovieGenreListResponse}
   *
   * @return -- Json data
   */
  public String getGenreListData() {
    return sharedPreferences.getString(AppSharedPreferenceKeys.MOVIE_GENRE_LIST_KEY,
        TheMovieDbConstants.EMPTY_STRING);
  }

  /**
   * Get the Image Config data of Model {@Link com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig}
   *
   * @return -- Json Data
   */
  public String getMovieImageConfigData() {
    return sharedPreferences.getString(AppSharedPreferenceKeys.MOVIE_CONFIG_IMAGES_KEY,
        TheMovieDbConstants.EMPTY_STRING);
  }

  /**
   * Set the App Language String code
   *
   * @param langCode -- language Code
   */
  public void setAppLanguageData(String langCode) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(AppSharedPreferenceKeys.APP_LANG_KEY, langCode);
    editor.commit();
  }

  /**
   * Get the App Language String Code
   *
   * @return -- String Code for App Language
   */
  public String getAppLanguageData() {
    return sharedPreferences.getString(AppSharedPreferenceKeys.APP_LANG_KEY, LanguageType
        .getLangString(LanguageType.ENGLISH));
  }
}

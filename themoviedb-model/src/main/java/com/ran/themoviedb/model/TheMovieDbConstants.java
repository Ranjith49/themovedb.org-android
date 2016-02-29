package com.ran.themoviedb.model;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Constants Used across the Application.
 */
public interface TheMovieDbConstants {

  /**
   * HTTP AUTH Error Code for API call
   */
  int AUTH_HTTP_RESPONSE_CODE = 401;

  /**
   * Application Base URL for Retrieving REST Data
   * Ex: a) https://api.themoviedb.org/3/configuration?api_key=57a2fe1fb88623756080330e465f20f7
   * b) https://api.themoviedb.org/3/configuration?api_key=57a2fe1fb88623756080330e465f20f7
   */
  String APP_BASE_URL = "http://api.themoviedb.org/3/";

  /**
   * API Key to Access the REST data from Server
   */
  String APP_API_KEY = "57a2fe1fb88623756080330e465f20f7";

  /**
   * TIME INTERVAL For BACK Clicks in Millis for Exit
   */
  int TIME_INTERVAL_APP_EXIT = 3000;

  /**
   * Empty String
   */
  String EMPTY_STRING = "";

  /**
   * COLON String
   */
  String COLON_STRING = ":";

  /**
   * Space String
   */
  String SPACE_STRING = " ";

  /**
   * Comma String
   */
  String COMMA_STRING = ",";

  /**
   * Null String
   */
  String NULL_STRING = "null";

  /**
   * Home Banner Millis Seconds Delay
   */
  int HOME_BANNER_MILLS_SECS = 5000;

  /**
   * Home Posters Millis Seconds Delay
   */
  int HOME_POSTER_MILLS_SECS = 5000;

  /**
   * Key for Store Type Navigation
   */
  String STORE_TYPE_KEY = "store_type";

  /**
   * Key for the MovieStore Fragment Type
   */
  String MOVIE_STORE_TYPE_KEY = "movie_store_type";

  /**
   * Key for the Tv Store Fragment Type
   */
  String TV_STORE_TYPE_KEY = "tv_store_type";

  /**
   * Key for the People Store Fragment Type
   */
  String PEOPLE_STORE_TYPE_KEY = "people_store_type";

  /**
   * Span Count of the GRID LAYOUT Manager
   */
  int GRID_SPAN_COUNT = 2;

  /**
   * Search Query String passed to Search Fragments [Key]
   */
  String SEARCH_KEY = "search_key";

  /**
   * Key for the Movie [Id] ..
   */
  String MOVIE_ID_KEY = "movie_id_key";

  /**
   * Key for the TvShow [Id] ..
   */
  String TV_SHOW_ID_KEY = "tvShow_id_key";

  /**
   * Base URL of IMDB for deeplinking
   */
  String IMDB_BASE_URL = "www.imdb.com/title/";

  /**
   * Base URL of Youtube to be handled..
   */
  String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
}

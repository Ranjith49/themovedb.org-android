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
   * X string
   */
  String MULTIPLY_STRING = "x";

  /**
   * Null String
   */
  String NULL_STRING = "null";

  /**
   * Zero String
   */
  String ZERO_STRING = "0";

  /**
   * Back Slash String
   */
  String BACKSLASH = "/";
  /**
   * Two power Ten Constant
   */
  int TWO_POWER_TEN = 1024;

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
   * Key for the People [Id] ..
   */
  String PEOPLE_ID_KEY = "people_id_key";

  /**
   * Base URL of IMDB Title for deeplinking
   */
  String IMDB_BASE_TITLE_URL = "www.imdb.com/title/";

  /**
   * Base URL of IMDB People for deeplinking
   */
  String IMDB_BASE_PEOPLE_URL = "www.imdb.com/name/";

  /**
   * Base URL of Youtube to be handled..
   */
  String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

  /**
   * Open Anchor Tag to create the URL link
   */
  String ANCHOR_OPEN_TAG = "<br/><a href=";
  /**
   * Close Tag for the Url
   */
  String CLOSE_TAG = ">";
  /**
   * Close Anchor Tag for the Url link
   */
  String ANCHOR_CLOSE_TAG = "</a><br/>";

  /**
   * Wap Movie Base Url
   */
  String WAP_MOVIE_URL = "https://www.themoviedb.org/movie/";

  /**
   * Wap Tv Base Url
   */
  String WAP_TV_URL = "https://www.themoviedb.org/tv/";

  /**
   * Wap Person Base Url
   */
  String WAP_PERSON_URL = "https://www.themoviedb.org/person/";

  /**
   * Key for sending the Image Url
   */
  String IMAGE_URL_KEY = "image_url_key";
  /**
   * Open Bracket
   */
  String OPEN_BRACKET = "(";

  /**
   * Close Bracket
   */
  String CLOSE_BRACKET = ")";
  /*
* String array of size units
*/
  String[] UNITS = {"B", "KB", "MB", "GB", "TB"};

  /**
   * Key and Values for the Image Types..
   */
  String IMAGE_TYPE_KEY = "image_type_key";
  String IMAGE_POSTER_TYPE = "image_poster";
  String IMAGE_BANNER_TYPE = "image_banner";
}

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
   * Null String
   */
  String NULL_STRING = "null";
}

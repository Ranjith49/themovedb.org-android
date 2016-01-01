package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public interface HomeScreenView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);

  /**
   * List of Banners and Posters retrieved
   *
   * @param movieBanners  -- Movie Banners
   * @param tvBanners     -- tv Banners
   * @param peoplePosters -- People Posters
   */
  void homeScreenData(ArrayList<String> movieBanners, ArrayList<String> tvBanners,
                      ArrayList<String> peoplePosters);

  /**
   * Error type of Home Presenters
   *
   * @param errorType -- Error Type
   */
  void homeScreenError(UserAPIErrorType errorType);
}

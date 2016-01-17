package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.ImageDetails;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public interface ImageDisplayView {
  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);

  /**
   * List of backdrops and posters
   *
   * @param backdrops -- Array list
   * @param posters   --Array list
   */
  void imageResponse(ArrayList<ImageDetails> backdrops, ArrayList<ImageDetails> posters);

  /**
   * Error type of Movie Description Presenters
   *
   * @param errorType -- Error Type
   */
  void imageError(UserAPIErrorType errorType);
}

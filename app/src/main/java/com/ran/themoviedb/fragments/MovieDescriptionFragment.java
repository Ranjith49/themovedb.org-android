package com.ran.themoviedb.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;
import com.ran.themoviedb.presenters.MovieDescriptionPresenter;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;
import com.ran.themoviedb.view_pres_med.MovieDescriptionView;

import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDescriptionFragment extends Fragment implements GenericErrorBuilder.Handler,
    MovieDescriptionView {

  private View view;
  private ImageView moviePoster;
  private TextView movieTitle;
  private TextView movieRating;
  private TextView movieReleaseDate;
  private TextView movieRunningTime;

  private TextView movieDescription;
  private TextView movieTagLine;
  private TextView movieBudget;
  private TextView movieExternalUrl;

  private ScrollView movieContainer;
  private LinearLayout movieErrorLayout;
  private ProgressBar movieFetchProgressBar;

  private ImageView movieImdbLink;
  private LinearLayout movie_genre_container;
  private LinearLayout movie_production_container;

  private GenericErrorBuilder genericErrorBuilder;
  private MovieDescriptionPresenter presenter;
  private int movieId;
  private final int INDEX_POSTER_SIZE = 2; //Todo [ranjith ,do better logic]


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_movie_description, container, false);
    movieId = getArguments().getInt(TheMovieDbConstants.MOVIE_ID_KEY);
    initView();
    initializePresenter();
    return view;
  }

  private void initView() {
    movieContainer = (ScrollView) view.findViewById(R.id.movie_scroll_view);
    movieErrorLayout = (LinearLayout) view.findViewById(R.id.genre_error_layout_container);
    movieFetchProgressBar = (ProgressBar) view.findViewById(R.id.movie_description_progress);

    moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
    movieTitle = (TextView) view.findViewById(R.id.movie_name);
    movieRating = (TextView) view.findViewById(R.id.movie_rating);
    movieReleaseDate = (TextView) view.findViewById(R.id.movie_date);
    movieRunningTime = (TextView) view.findViewById(R.id.movie_runtime);

    movieDescription = (TextView) view.findViewById(R.id.overview_description);
    movieTagLine = (TextView) view.findViewById(R.id.overview_tagline);
    movieExternalUrl = (TextView) view.findViewById(R.id.overview_url);
    movieBudget = (TextView) view.findViewById(R.id.overview_price);

    movieImdbLink = (ImageView) view.findViewById(R.id.imdb_image);
    movie_genre_container = (LinearLayout) view.findViewById(R.id.genre_list);
    movie_production_container = (LinearLayout) view.findViewById(R.id.production_list);
  }

  private void initializePresenter() {
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, movieErrorLayout, this);
    presenter = new MovieDescriptionPresenter(getActivity(), this, movieId,
        MovieDescriptionFragment.class.getName().hashCode());
    presenter.start();
  }

  @Override
  public void onDestroyView() {
    presenter.stop();
    super.onDestroyView();
  }

  private void processResponse(MovieDetailResponse response) {
    //Top View Done ..
    if (!AppUiUtils.isStringEmpty(response.getTitle())) {
      movieTitle.setText(response.getTitle());
      movieTitle.setVisibility(View.VISIBLE);
    }
    movieRating.setText(String.valueOf(response.getVote_average()));
    movieRating.setVisibility(View.VISIBLE);
    String runningTime = String.format(getResources().getString(R.string.movie_running_time),
        String.valueOf(response.getRuntime()));
    movieRunningTime.setText(runningTime);
    movieRunningTime.setVisibility(View.VISIBLE);
    if (!AppUiUtils.isStringEmpty(response.getRelease_date())) {
      movieReleaseDate.setText(response.getRelease_date());
      movieReleaseDate.setVisibility(View.VISIBLE);
    }
    loadImage(response.getPoster_path());

    //Over View Container
    if (!AppUiUtils.isStringEmpty(response.getOverview())) {
      movieDescription.setText(response.getOverview());
      movieDescription.setVisibility(View.VISIBLE);
    }
    if (!AppUiUtils.isStringEmpty(response.getTagline())) {
      movieTagLine.setText(response.getTagline());
      movieTagLine.setVisibility(View.VISIBLE);
    }
    if (!AppUiUtils.isStringEmpty(response.getHomepage())) {
      movieExternalUrl.setText(response.getHomepage());
      movieExternalUrl.setVisibility(View.VISIBLE);
    }
    String movie_Budget = String.format(getResources().getString(R.string.movie_budget), String
        .valueOf(response.getBudget()));
    movieBudget.setText(movie_Budget);
    movieBudget.setVisibility(View.VISIBLE);

    //Bottom Container ..(TODO ranjith)
    if (!AppUiUtils.isStringEmpty(response.getImdb_id())) {
      movieImdbLink.setTag(response.getImdb_id());
      movieImdbLink.setVisibility(View.VISIBLE);
    }
  }

  private void loadImage(String url) {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(getActivity(), moviePoster, ImageLoaderUtils
        .getImageUrl(IMAGE_BASE_URL, url), R.drawable.image_error_placeholder);
  }

  // -- Call Backs from various Interfaces .. ---
  @Override
  public void onRefreshClicked() {
    initializePresenter();
  }

  @Override
  public void showProgressBar(boolean show) {
    if (show) {
      movieFetchProgressBar.setVisibility(View.VISIBLE);
    } else {
      movieFetchProgressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void movieResponse(MovieDetailResponse response) {
    processResponse(response);
    movieContainer.setVisibility(View.VISIBLE);
  }

  @Override
  public void movieError(UserAPIErrorType errorType) {
    genericErrorBuilder.setUserAPIError(errorType);
  }
}

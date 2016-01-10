package com.ran.themoviedb.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.MovieGenre;
import com.ran.themoviedb.model.server.entities.ProductionCompany;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;
import com.ran.themoviedb.presenters.MovieDescriptionPresenter;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;
import com.ran.themoviedb.view_pres_med.MovieDescriptionView;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
  private TextView movieImdbUrl;

  private ScrollView movieContainer;
  private LinearLayout movieErrorLayout;
  private ProgressBar movieFetchProgressBar;

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
    movieImdbUrl = (TextView) view.findViewById(R.id.overview_imdb);

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
    //Top View  ..
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

    if (!AppUiUtils.isStringEmpty(response.getImdb_id())) {
      movieImdbUrl.setText(TheMovieDbConstants.IMDB_BASE_URL.concat(response.getImdb_id()));
      movieImdbUrl.setTag(response.getImdb_id());
      movieImdbUrl.setVisibility(View.VISIBLE);
    }

    loadGenreDetails(response.getGenres());
    loadProductionCompanies(response.getProduction_companies());
  }

  /**
   * Utility to add the Production company list in Vertical layout from Response
   *
   * @param data -- List of Production Company
   */
  private void loadProductionCompanies(ArrayList<ProductionCompany> data) {
    if (data != null && data.size() >= 0) {

      for (ProductionCompany productionCompany : data) {
        TextView textView = new TextView(getActivity());
        textView.setText(productionCompany.getName());
        textView.setTag(productionCompany.getId());
        textView.setMaxLines(2);
        textView.setBackgroundDrawable(
            getResources().getDrawable(R.drawable.other_info_item_background));
        textView.setTextColor(getResources().getColor(R.color.color_text_white));
        textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_production, 0, 0, 0);
        textView.setPadding(
            getResources().getDimensionPixelSize(R.dimen.otherInfo_container_padding), 0,
            getResources().getDimensionPixelSize(R.dimen.otherInfo_container_padding), 0);
        textView.setGravity(Gravity.CENTER);
        textView.setMaxWidth(
            getResources().getDimensionPixelSize(R.dimen.other_info_container_genre_maxWidth));
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(getActivity(), "Productions : " + String.valueOf(v.getTag()), Toast
                .LENGTH_SHORT).show();
          }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
            .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,
            getResources().getDimensionPixelSize(R.dimen.other_info_item_margin_vertical), 0,
            getResources().getDimensionPixelSize(R.dimen.other_info_item_margin_vertical));

        movie_production_container.addView(textView, params);
      }
    }
  }

  /**
   * Utility to add the Genre company list in Vertical layout from Response
   *
   * @param data -- List of Genre Company
   */
  private void loadGenreDetails(ArrayList<MovieGenre> data) {
    if (data != null && data.size() >= 0) {

      for (MovieGenre movieGenre : data) {
        TextView textView = new TextView(getActivity());
        textView.setText(movieGenre.getName());
        textView.setTag(movieGenre.getId());
        textView.setMaxLines(2);
        textView.setBackgroundDrawable(
            getResources().getDrawable(R.drawable.other_info_item_background));
        textView.setTextColor(getResources().getColor(R.color.color_text_white));
        textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_genre, 0, 0, 0);
        textView.setPadding(
            getResources().getDimensionPixelSize(R.dimen.otherInfo_container_padding), 0,
            getResources().getDimensionPixelSize(R.dimen.otherInfo_container_padding), 0);
        textView.setGravity(Gravity.CENTER);
        textView.setMaxWidth(
            getResources().getDimensionPixelSize(R.dimen.other_info_container_genre_maxWidth));
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(getActivity(), "Genre : " + String.valueOf(v.getTag()),
                Toast.LENGTH_SHORT).show();
          }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
            .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,
            getResources().getDimensionPixelSize(R.dimen.other_info_item_margin_vertical), 0,
            getResources().getDimensionPixelSize(R.dimen.other_info_item_margin_vertical));

        movie_genre_container.addView(textView, params);
      }
    }
  }

  /**
   * Load the Image Poster of the Movie
   *
   * @param url -- Url passed
   */
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

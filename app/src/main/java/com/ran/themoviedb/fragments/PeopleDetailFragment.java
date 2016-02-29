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
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.presenters.PeopleDetailPresenter;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;
import com.ran.themoviedb.view_pres_med.PeopleDetailView;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleDetailFragment extends Fragment
    implements PeopleDetailView, GenericErrorBuilder.Handler {

  private View view;
  private ImageView peoplePoster;
  private TextView peopleName;
  private TextView peopleRating;
  private TextView peopleBirthDate;

  private TextView peopleDescription;
  private TextView peopleImdb;
  private TextView peopleExternalUrl;

  private ScrollView peopleContainer;
  private LinearLayout peopleErrorLayout;
  private ProgressBar peopleProgressBar;

  private GenericErrorBuilder genericErrorBuilder;
  private PeopleDetailPresenter presenter;
  private int peopleId;
  private final int INDEX_PROFILE_SIZE = 2; //Todo [ranjith ,do better logic]


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_people_detail, container, false);
    peopleId = getArguments().getInt(TheMovieDbConstants.PEOPLE_ID_KEY);
    initView();
    initializePresenter();
    return view;
  }

  private void initView() {
    peopleContainer = (ScrollView) view.findViewById(R.id.people_scroll_view);
    peopleErrorLayout = (LinearLayout) view.findViewById(R.id.genre_error_layout_container);
    peopleProgressBar = (ProgressBar) view.findViewById(R.id.people_description_progress);

    peoplePoster = (ImageView) view.findViewById(R.id.people_poster);
    peopleName = (TextView) view.findViewById(R.id.people_name);
    peopleRating = (TextView) view.findViewById(R.id.people_rating);
    peopleBirthDate = (TextView) view.findViewById(R.id.people_birthdate);

    peopleDescription = (TextView) view.findViewById(R.id.overview_description);
    peopleImdb = (TextView) view.findViewById(R.id.overview_imdb);
    peopleExternalUrl = (TextView) view.findViewById(R.id.overview_url);
  }

  private void initializePresenter() {
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, peopleErrorLayout, this);
    presenter = new PeopleDetailPresenter(getActivity(), this, peopleId,
        PeopleDetailFragment.class.getName().hashCode());
    presenter.start();
  }

  @Override
  public void onDestroyView() {
    presenter.stop();
    super.onDestroyView();
  }

  private void processResponse(PeopleDetailResponse response) {
    //Top View  ..
    if (!AppUiUtils.isStringEmpty(response.getName())) {
      peopleName.setText(response.getName());
      peopleName.setVisibility(View.VISIBLE);
    }
    peopleRating.setText(String.valueOf(response.getPopularity()));
    peopleRating.setVisibility(View.VISIBLE);

    if (!AppUiUtils.isStringEmpty(response.getBirthday())) {
      peopleBirthDate.setText(response.getBirthday());
      peopleBirthDate.setVisibility(View.VISIBLE);
    }
    loadProfileImage(response.getProfile_path());

    //Over View Container
    if (!AppUiUtils.isStringEmpty(response.getBiography())) {
      peopleDescription.setText(response.getBiography());
      peopleDescription.setVisibility(View.VISIBLE);
    }

    if (!AppUiUtils.isStringEmpty(response.getImdb_id())) {
      peopleImdb.setText(TheMovieDbConstants.IMDB_BASE_PEOPLE_URL.concat(response.getImdb_id()));
      peopleImdb.setTag(response.getImdb_id());
      peopleImdb.setVisibility(View.VISIBLE);
    }

    if (!AppUiUtils.isStringEmpty(response.getHomepage())) {
      peopleExternalUrl.setText(response.getHomepage());
      peopleExternalUrl.setVisibility(View.VISIBLE);
    }
  }

  /**
   * Helper Method to Load the Image in the View ..
   */
  private void loadProfileImage(String url) {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getProfile_sizes().get(INDEX_PROFILE_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(getActivity(), peoplePoster,
        ImageLoaderUtils.getImageUrl(IMAGE_BASE_URL, url), R.drawable.image_error_placeholder);
  }

  // -- Call Backs from various Interfaces .. //

  @Override
  public void onRefreshClicked() {
    initializePresenter();
  }

  @Override
  public void showProgressBar(boolean show) {
    if (show) {
      peopleProgressBar.setVisibility(View.VISIBLE);
    } else {
      peopleProgressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void onPeopleDetailResponse(PeopleDetailResponse response) {
    processResponse(response);
    peopleContainer.setVisibility(View.VISIBLE);
  }

  @Override
  public void onPeopleDetailError(UserAPIErrorType errorType) {
    genericErrorBuilder.setUserAPIError(errorType);
  }
}



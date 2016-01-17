package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.ImageDetailAdapter;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.ImageDetails;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.presenters.ImagePresenter;
import com.ran.themoviedb.view_pres_med.ImageDisplayView;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class ImageDetailFragment extends Fragment
    implements ImageDisplayView, GenericErrorBuilder.Handler {

  private View view;
  private LinearLayout image_container;
  private LinearLayout image_poster_container;
  private LinearLayout image_banner_container;
  private RecyclerView image_poster_recycler;
  private RecyclerView image_banner_recycler;
  private TextView image_poster_count;
  private TextView image_banner_count;
  private ProgressBar progressBar;
  private GenericErrorBuilder genericErrorBuilder;
  private LinearLayout errorLayoutHolder;
  private int id;

  private ImagePresenter imagePresenter;
  private ImageDetailAdapter imagePosterAdapter;
  private ImageDetailAdapter imageBannerAdapter;
  private final int INDEX_BANNER_SIZE = 2; //Todo [ranjith ,do better logic]
  private final int INDEX_POSTER_SIZE = 2; //Todo [ranjith ,do better logic]


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_image_details, container, false);
    //TODO [ranjith ,Here enable TV show logic also ..]
    Bundle bundle = getArguments();
    if (bundle.containsKey(TheMovieDbConstants.MOVIE_ID_KEY)) {
      id = bundle.getInt(TheMovieDbConstants.MOVIE_ID_KEY);
    }

    image_container = (LinearLayout) view.findViewById(R.id.image_container);
    image_poster_count = (TextView) view.findViewById(R.id.image_poster_title);
    image_banner_count = (TextView) view.findViewById(R.id.image_banner_title);
    image_poster_container = (LinearLayout) view.findViewById(R.id.image_poster_container);
    image_banner_container = (LinearLayout) view.findViewById(R.id.image_banner_container);
    image_poster_recycler = (RecyclerView) view.findViewById(R.id.image_poster_recycler);
    image_banner_recycler = (RecyclerView) view.findViewById(R.id.image_banner_recycler);
    progressBar = (ProgressBar) view.findViewById(R.id.image_screen_progress);
    errorLayoutHolder =
        (LinearLayout) view.findViewById(R.id.image_error_layout_container);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);

    initializePresenter();
    return view;
  }


  private void initializePresenter() {
    imagePresenter = new ImagePresenter(getActivity(), this, ImageDetailFragment.class.hashCode(),
        id, DisplayStoreType.MOVIE_STORE);
    imagePresenter.start();
  }

  @Override
  public void onDestroyView() {
    imagePresenter.stop();
    super.onDestroyView();
  }

  private String generateImagePosterBaseUrl() {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_SIZE);

    return image_url.concat(image_url_config);
  }

  private String generateImageBannerBaseUrl() {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getBackdrop_sizes().get(INDEX_BANNER_SIZE);

    return image_url.concat(image_url_config);
  }

  //-- CallBacks from different Views -- //
  @Override
  public void onRefreshClicked() {
    image_container.setVisibility(View.GONE);
    initializePresenter();
  }

  @Override
  public void showProgressBar(boolean show) {
    if (show) {
      progressBar.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void imageResponse(ArrayList<ImageDetails> backdrops, ArrayList<ImageDetails> posters) {
    if (backdrops.size() > 0) {
      imageBannerAdapter = new ImageDetailAdapter(false, backdrops, getActivity(),
          generateImageBannerBaseUrl());
      image_banner_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
          LinearLayoutManager.VERTICAL, false));
      String count = String.format(getActivity().getResources().
          getString(R.string.image_banner_title), String.valueOf(backdrops.size()));
      image_banner_count.setText(count);
      image_banner_recycler.setAdapter(imageBannerAdapter);
      image_banner_container.setVisibility(View.VISIBLE);
    }

    if (posters.size() > 0) {
      imagePosterAdapter = new ImageDetailAdapter(true, posters, getActivity(),
          generateImagePosterBaseUrl());
      image_poster_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
          LinearLayoutManager.HORIZONTAL, false));
      String count = String.format(getActivity().getResources().
          getString(R.string.image_poster_title), String.valueOf(posters.size()));
      image_poster_count.setText(count);
      image_poster_recycler.setAdapter(imagePosterAdapter);
      image_poster_container.setVisibility(View.VISIBLE);
    }
    //Total Response container here ..
    image_container.setVisibility(View.VISIBLE);
  }

  @Override
  public void imageError(UserAPIErrorType errorType) {
    genericErrorBuilder.setUserAPIError(errorType);
  }
}

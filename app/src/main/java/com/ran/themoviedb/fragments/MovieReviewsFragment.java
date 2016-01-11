package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.MovieReviewRecyclerAdapter;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.ReviewClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.view_pres_med.MovieReviewView;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieReviewsFragment extends Fragment implements MovieReviewView, GenericErrorBuilder
    .Handler, ReviewClickListener {

  public static final int FIRST_PAGE_INDEX = 1;
  private final String TAG = MovieReviewsFragment.class.getSimpleName();
  private View view;
  private int movieId;
  private CustomRecyclerView customRecyclerView;
  private GenericErrorBuilder genericErrorBuilder;
  private LinearLayout errorLayoutHolder;
  private ProgressBar progressBar;
  private MovieReviewRecyclerAdapter movieReviewRecyclerAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
    movieId = getArguments().getInt(TheMovieDbConstants.MOVIE_ID_KEY);

    customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.movie_reviews_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.movie_reviews_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.movie_reviews_error_layout_container);

    initializeAdaptersAndPresenters();
    return view;
  }

  private void initializeAdaptersAndPresenters() {
    movieReviewRecyclerAdapter =
        new MovieReviewRecyclerAdapter(getActivity(), this, FIRST_PAGE_INDEX, this, movieId);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    customRecyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false),
        getActivity().getResources().getDimensionPixelSize(R.dimen.review_card_height_max_rough));
    customRecyclerView.setAdapter(movieReviewRecyclerAdapter);
  }

  @Override
  public void onRefreshClicked() {
    Log.d(TAG, "error Handling Refresh click");
    initializeAdaptersAndPresenters();
  }

  @Override
  public void showProgressBar(boolean show, boolean isFirstPage) {
    if (isFirstPage) {
      if (show) {
        progressBar.setVisibility(View.VISIBLE);
      } else {
        progressBar.setVisibility(View.GONE);
      }
    } else {
      customRecyclerView.showBottomProgressBar(show);
    }
  }

  @Override
  public void onDestroyView() {
    movieReviewRecyclerAdapter.onDestroyView();
    super.onDestroyView();
  }


  @Override
  public void movieReviewData(ReviewsDetailResponse reviewsDetailResponse, boolean isFirstPage) {
    if (isFirstPage) {
      customRecyclerView.showRecyclerViewContainer(true);
    }
    customRecyclerView.updateRefreshIndicator(false);
    movieReviewRecyclerAdapter.addMovieReviews(reviewsDetailResponse.getResults(),
        reviewsDetailResponse.getPage(), reviewsDetailResponse.getTotal_pages());
  }

  @Override
  public void movieReviewError(UserAPIErrorType errorType, boolean isFirstPage) {
    if (isFirstPage) {
      genericErrorBuilder.setUserAPIError(errorType);
    } else {
      customRecyclerView.showBottomErrorMessage(errorType, true);
      customRecyclerView.updateRefreshIndicator(false);
    }
  }

  @Override
  public void onReviewClick(String id, String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    startActivity(intent);
  }
}

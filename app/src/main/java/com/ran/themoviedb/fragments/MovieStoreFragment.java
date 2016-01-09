package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.MovieStoreRecyclerAdapter;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.DisplayStoreType;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.MovieStoreView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Movie Store Fragment showing the Movies in {@link com.ran.themoviedb.viewholders
 * .MovieStoreViewHolder} and bind data from {@link com.ran.themoviedb.model.server.entities.MovieStoreResults}
 */
public class MovieStoreFragment extends Fragment
    implements MovieStoreView, GenericErrorBuilder.Handler, StoreClickListener {

  private final String TAG = MovieStoreFragment.class.getSimpleName();
  public static final int FIRST_PAGE_INDEX = 1;

  private View view;
  private MovieStoreType movieStoreType;
  private MovieStoreRecyclerAdapter movieStoreRecyclerAdapter;
  private GenericErrorBuilder genericErrorBuilder;

  CustomRecyclerView customRecyclerView;
  ProgressBar progressBar;
  LinearLayout errorLayoutHolder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.fragment_movie_store, container, false);
    String type = getArguments().getString(TheMovieDbConstants.MOVIE_STORE_TYPE_KEY,
        MovieStoreType.getStoreName(MovieStoreType.MOVIE_POPULAR));
    movieStoreType = MovieStoreType.getStoreType(type);

    customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.movie_store_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.movie_store_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.movie_store_error_layout_container);

    initializeAdaptersAndPresenters();

    return view;
  }

  private void initializeAdaptersAndPresenters() {
    movieStoreRecyclerAdapter =
        new MovieStoreRecyclerAdapter(getActivity(), FIRST_PAGE_INDEX, movieStoreType, this, this);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    customRecyclerView.setLayoutManager(
        new GridLayoutManager(getActivity(), TheMovieDbConstants.GRID_SPAN_COUNT),
        getActivity().getResources().getDimensionPixelSize(R.dimen.recycler_store_item_height));
    customRecyclerView.setAdapter(movieStoreRecyclerAdapter);
  }


  // -- Call Backs from the Presenter and Error Handlers and Item Clicks .. --//
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
  public void movieScreenData(MovieStoreResponse movieStoreResponse, boolean isFirstPage) {
    if (isFirstPage) {
      customRecyclerView.showRecyclerViewContainer(true);
    }
    customRecyclerView.updateRefreshIndicator(false);
    movieStoreRecyclerAdapter.addMovieStoreResultsData(movieStoreResponse.getResults(),
        movieStoreResponse.getPage(), movieStoreResponse.getTotal_pages());
  }

  @Override
  public void movieScreenError(UserAPIErrorType errorType, boolean isFirstPage) {
    if (isFirstPage) {
      genericErrorBuilder.setUserAPIError(errorType);
    } else {
      customRecyclerView.showBottomErrorMessage(errorType, true);
      customRecyclerView.updateRefreshIndicator(false);
    }
  }

  @Override
  public void onRefreshClicked() {
    Log.d(TAG, "error Handling Refresh click");
    initializeAdaptersAndPresenters();
  }

  @Override
  public void onDestroyView() {
    movieStoreRecyclerAdapter.onDestroyView();
    super.onDestroyView();
  }

  @Override
  public void onStoreItemClick(int id, String name, DisplayStoreType displayStoreType) {
    Navigator.navigateToMovieDetails(getActivity(),id);
  }

  @Override
  public void onStoreItemShare(int id, String name, DisplayStoreType displayStoreType) {
    Toast.makeText(getActivity(), name + " -- Share", Toast.LENGTH_SHORT).show();
  }
}

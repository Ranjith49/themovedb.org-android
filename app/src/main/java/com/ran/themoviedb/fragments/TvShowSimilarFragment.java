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
import com.ran.themoviedb.adapters.TvShowSimilarRecyclerAdapter;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.TvShowSimilarView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Movie Store Fragment showing the Movies in {@link com.ran.themoviedb.viewholders.MovieStoreViewHolder} and
 * bind data from {@link com.ran.themoviedb.model.server.entities.MovieStoreResults}
 */
public class TvShowSimilarFragment extends Fragment
    implements TvShowSimilarView, GenericErrorBuilder.Handler, StoreClickListener {

  private final String TAG = MovieStoreFragment.class.getSimpleName();
  public static final int FIRST_PAGE_INDEX = 1;

  private View view;
  private TvShowSimilarRecyclerAdapter tvShowSimilarRecyclerAdapter;
  private GenericErrorBuilder genericErrorBuilder;
  private int tvShowId;

  CustomRecyclerView customRecyclerView;
  ProgressBar progressBar;
  LinearLayout errorLayoutHolder;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    //We are using the same layout here [as Store]
    view = inflater.inflate(R.layout.fragment_tv_store, container, false);
    tvShowId = getArguments().getInt(TheMovieDbConstants.TV_SHOW_ID_KEY);

    customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.tv_store_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.tv_store_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.tv_store_error_layout_container);

    initializeAdaptersAndPresenters();

    return view;
  }

  private void initializeAdaptersAndPresenters() {
    tvShowSimilarRecyclerAdapter =
        new TvShowSimilarRecyclerAdapter(getActivity(), this, FIRST_PAGE_INDEX, this, tvShowId);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    customRecyclerView.setLayoutManager(
        new GridLayoutManager(getActivity(), TheMovieDbConstants.GRID_SPAN_COUNT),
        getActivity().getResources().getDimensionPixelSize(R.dimen.recycler_store_item_height));
    customRecyclerView.setAdapter(tvShowSimilarRecyclerAdapter);
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
  public void similarTvShowsResponse(TvShowSimilarDetailsResponse tvShowSimilarDetailsResponse,
                                     boolean isFirstPage) {
    if (isFirstPage) {
      customRecyclerView.showRecyclerViewContainer(true);
    }
    customRecyclerView.updateRefreshIndicator(false);
    tvShowSimilarRecyclerAdapter.addTvShowsResultsData(tvShowSimilarDetailsResponse.getResults(),
        tvShowSimilarDetailsResponse.getPage(), tvShowSimilarDetailsResponse.getTotal_pages());
  }

  @Override
  public void similarTvShowsError(UserAPIErrorType errorType, boolean isFirstPage) {
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
    tvShowSimilarRecyclerAdapter.onDestroyView();
    super.onDestroyView();
  }

  @Override
  public void onStoreItemClick(int id, String name, DisplayStoreType displayStoreType) {
    getActivity().finish();
    Navigator.navigateToTvShowDetails(getActivity(), id);
  }

  @Override
  public void onStoreItemShare(int id, String name, DisplayStoreType displayStoreType) {
    String shareDesc = String.format(getActivity().getResources().
        getString(R.string.share_tv_description), name);

    ShareContent shareContent = new ShareContent(name, shareDesc, id, displayStoreType);
    ShareContentHelper.buildShareIntent(getActivity(), shareContent,
        getResources().getString(R.string.share_app_dialog_title));
    Log.d(TAG, "Share Id  : " + id + " Name : " + name);
  }
}

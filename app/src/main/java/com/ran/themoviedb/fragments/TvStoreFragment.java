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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.ad.inmobi.InMobiAdTypes;
import com.ran.themoviedb.ad.inmobi.InMobiWrapper;
import com.ran.themoviedb.adapters.TvStoreRecyclerAdapter;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.TvStoreView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p>
 * Tv Store Data information shown in {@link com.ran.themoviedb.viewholders.TVStoreViewHolder}
 * from data source of type {@link com.ran.themoviedb.model.server.entities.TvShowStoreResults}
 */
public class TvStoreFragment extends Fragment implements TvStoreView, GenericErrorBuilder.Handler,
    StoreClickListener {

  private View view;
  private TVShowStoreType tvShowStoreType;
  private final String TAG = TvStoreFragment.class.getSimpleName();
  public static final int FIRST_PAGE_INDEX = 1;
  private TvStoreRecyclerAdapter tvStoreRecyclerAdapter;
  private GenericErrorBuilder genericErrorBuilder;

  CustomRecyclerView customRecyclerView;
  ProgressBar progressBar;
  LinearLayout errorLayoutHolder;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.fragment_tv_store, container, false);
    String type = getArguments().getString(TheMovieDbConstants.TV_STORE_TYPE_KEY, TVShowStoreType
        .getStoreName(TVShowStoreType.TV_POPULAR));
    tvShowStoreType = TVShowStoreType.getStoreType(type);

    customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.tv_store_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.tv_store_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.tv_store_error_layout_container);
    initializeAdaptersAndPresenters();
    return view;
  }

  private void initializeAdaptersAndPresenters() {
    tvStoreRecyclerAdapter =
        new TvStoreRecyclerAdapter(getActivity(), FIRST_PAGE_INDEX, tvShowStoreType, this, this);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    customRecyclerView.setLayoutManager(
        new GridLayoutManager(getActivity(), TheMovieDbConstants.GRID_SPAN_COUNT),
        getActivity().getResources().getDimensionPixelSize(R.dimen.recycler_store_item_height));
    customRecyclerView.setAdapter(tvStoreRecyclerAdapter);
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
  public void tvScreenData(TVShowStoreResponse tvShowStoreResponse, boolean isFirstPage) {
    if (isFirstPage) {
      customRecyclerView.showRecyclerViewContainer(true);
    }
    customRecyclerView.updateRefreshIndicator(false);
    tvStoreRecyclerAdapter.addTvStoreResultsData(tvShowStoreResponse.getResults(),
        tvShowStoreResponse.getPage(), tvShowStoreResponse.getTotal_pages());
  }

  @Override
  public void tvScreenError(UserAPIErrorType errorType, boolean isFirstPage) {
    if (isFirstPage) {
      genericErrorBuilder.setUserAPIError(errorType);
    } else {
      customRecyclerView.showBottomErrorMessage(errorType, true);
      customRecyclerView.updateRefreshIndicator(false);
    }
  }

  @Override
  public void onStoreItemClick(int id, String name, DisplayStoreType displayStoreType) {
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

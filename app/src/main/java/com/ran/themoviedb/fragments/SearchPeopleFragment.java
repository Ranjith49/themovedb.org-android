package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.PeopleSearchRecyclerAdapter;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.PeopleSearchView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class SearchPeopleFragment extends Fragment
    implements PeopleSearchView, GenericErrorBuilder.Handler, StoreClickListener {

  private final String TAG = SearchPeopleFragment.class.getSimpleName();
  public static final int FIRST_PAGE_INDEX = 1;

  private String searchString;
  private View view;
  private PeopleSearchRecyclerAdapter peopleSearchRecyclerAdapter;
  private GenericErrorBuilder genericErrorBuilder;

  CustomRecyclerView customRecyclerView;
  ProgressBar progressBar;
  LinearLayout errorLayoutHolder;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_people_search, container, false);
    String search_key =
        getArguments().getString(TheMovieDbConstants.SEARCH_KEY, TheMovieDbConstants.EMPTY_STRING);
    try {
      this.searchString = URLEncoder.encode(search_key, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      Log.d(TAG, "Exception in String Entered");
      searchString = TheMovieDbConstants.EMPTY_STRING;
    }

    customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.people_search_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.people_search_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.people_search_error_layout_container);

    initializeAdaptersAndPresenters();
    return view;
  }

  private void initializeAdaptersAndPresenters() {
    peopleSearchRecyclerAdapter =
        new PeopleSearchRecyclerAdapter(getActivity(), FIRST_PAGE_INDEX, searchString, this, this);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    customRecyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false),
        getActivity().getResources().getDimensionPixelSize(R.dimen.recycler_store_item_height));
    customRecyclerView.setAdapter(peopleSearchRecyclerAdapter);
  }

  @Override
  public void onDestroyView() {
    peopleSearchRecyclerAdapter.onDestroyView();
    super.onDestroyView();
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
  public void peopleScreenData(PeopleSearchResponse peopleSearchResponse, boolean isFirstPage) {
    if (isFirstPage) {
      customRecyclerView.showRecyclerViewContainer(true);
    }
    customRecyclerView.updateRefreshIndicator(false);
    peopleSearchRecyclerAdapter.addPeopleSearchData(peopleSearchResponse.getResults(),
        peopleSearchResponse.getPage(), peopleSearchResponse.getTotal_pages());
  }

  @Override
  public void peopleScreenError(UserAPIErrorType errorType, boolean isFirstPage) {
    if (isFirstPage) {
      genericErrorBuilder.setUserAPIError(errorType);
    } else {
      customRecyclerView.showBottomErrorMessage(errorType, true);
      customRecyclerView.updateRefreshIndicator(false);
    }
  }

  @Override
  public void onStoreItemClick(int id, String name, DisplayStoreType displayStoreType) {
    Navigator.navigateToPeopleDetails(getActivity(), id);
  }

  @Override
  public void onStoreItemShare(int id, String name, DisplayStoreType displayStoreType) {
    String shareDesc = String.format(getActivity().getResources().
        getString(R.string.share_people_desc), name);

    ShareContent shareContent = new ShareContent(name, shareDesc, id, displayStoreType);
    ShareContentHelper.buildShareIntent(getActivity(), shareContent,
        getResources().getString(R.string.share_app_dialog_title));
    Log.d(TAG, "Share Id  : " + id + " Name : " + name);
  }
}

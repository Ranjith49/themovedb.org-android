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
import android.widget.RelativeLayout;

import com.ran.themoviedb.R;
import com.ran.themoviedb.ad.inmobi.InMobiAdTypes;
import com.ran.themoviedb.ad.inmobi.InMobiWrapper;
import com.ran.themoviedb.adapters.PeopleStoreRecyclerAdapter;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.PeopleStoreView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * People Store Information from {@link com.ran.themoviedb.model.server.entities
 * .PeopleStoreResults} displayed in form of {@link com.ran.themoviedb.viewholders.PeopleStoreViewHolder}
 */
public class PeopleStoreFragment extends Fragment
    implements PeopleStoreView, GenericErrorBuilder.Handler, StoreClickListener {

  private PeopleStoreType peopleStoreType;

  private final String TAG = PeopleStoreFragment.class.getSimpleName();
  public static final int FIRST_PAGE_INDEX = 1;

  private View view;
  private PeopleStoreRecyclerAdapter peopleStoreReyclerAdapter;
  private GenericErrorBuilder genericErrorBuilder;

  CustomRecyclerView customRecyclerView;
  ProgressBar progressBar;
  LinearLayout errorLayoutHolder;

  //Ad Container..
  RelativeLayout inMobiAdContainer;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_people_store, container, false);
    String type = getArguments().getString(TheMovieDbConstants.PEOPLE_STORE_TYPE_KEY,
        PeopleStoreType.getStoreName(PeopleStoreType.PEOPLE_POPULAR));
    peopleStoreType = PeopleStoreType.getStoreType(type);

    customRecyclerView = (CustomRecyclerView) view.findViewById(R.id.people_store_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.people_store_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.people_store_error_layout_container);

    inMobiAdContainer = (RelativeLayout) view.findViewById(R.id.ad_container);
    initializeAdaptersAndPresenters();
    return view;
  }

  private void initializeAdaptersAndPresenters() {
    peopleStoreReyclerAdapter =
        new PeopleStoreRecyclerAdapter(getActivity(), FIRST_PAGE_INDEX, peopleStoreType, this,
            this);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    customRecyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false),
        getActivity().getResources().getDimensionPixelSize(R.dimen.recycler_store_item_height));
    customRecyclerView.setAdapter(peopleStoreReyclerAdapter);
  }

  @Override
  public void onDestroyView() {
    peopleStoreReyclerAdapter.onDestroyView();
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
  public void peopleScreenData(PeopleStoreResponse peopleStoreResponse, boolean isFirstPage) {
    if (isFirstPage) {
      customRecyclerView.showRecyclerViewContainer(true);
    }
    customRecyclerView.updateRefreshIndicator(false);
    peopleStoreReyclerAdapter.addPeopleStoreData(peopleStoreResponse.getResults(),
        peopleStoreResponse.getPage(), peopleStoreResponse.getTotal_pages());

    InMobiWrapper.showBannerAD(getActivity(), inMobiAdContainer, InMobiAdTypes.BANNER_AD_320_50);
  }

  @Override
  public void peopleScreenAPIError(UserAPIErrorType errorType, boolean isFirstPage) {
    if (isFirstPage) {
      genericErrorBuilder.setUserAPIError(errorType);
    } else {
      customRecyclerView.showBottomErrorMessage(errorType, true);
      customRecyclerView.updateRefreshIndicator(false);
    }
  }

  @Override
  public void onStoreItemClick(int id, String name, DisplayStoreType displayStoreType) {
    if (displayStoreType == DisplayStoreType.PERSON_STORE) {
      Navigator.navigateToPeopleDetails(getActivity(), id);
    } else if (displayStoreType == DisplayStoreType.MOVIE_STORE) {
      Navigator.navigateToMovieDetails(getActivity(), id);
    } else {
      Navigator.navigateToTvShowDetails(getActivity(), id);
    }
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

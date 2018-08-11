package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.PeopleKnowForRecyclerAdapter;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.PeopleKnowForData;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleKnownForResponse;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;
import com.ran.themoviedb.model.utils.UniqueIdCreator;
import com.ran.themoviedb.presenters.PeopleKnowForPresenter;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.PeopleKnowForView;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 3/4/2016.
 */

public class PeopleKnownForFragment extends Fragment
    implements PeopleKnowForView, GenericErrorBuilder.Handler, StoreClickListener {


  private final String TAG = PeopleKnownForFragment.class.getSimpleName();

  private View view;
  private PeopleKnowForRecyclerAdapter peopleKnowForRecyclerAdapter;
  private PeopleKnowForPresenter peopleKnowForPresenter;
  private GenericErrorBuilder genericErrorBuilder;

  RecyclerView recyclerView;
  ProgressBar progressBar;
  LinearLayout errorLayoutHolder;
  private int peopleId;
  private ArrayList<PeopleKnowForData> peopleKnowForData;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_people_known_for, container, false);
    peopleId = getArguments().getInt(TheMovieDbConstants.PEOPLE_ID_KEY);

    recyclerView = (RecyclerView) view.findViewById(R.id.people_known_recyclerView);
    progressBar = (ProgressBar) view.findViewById(R.id.people_known_error_screen_progress);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.people_known_error_layout_container);
    initializePresenter();
    return view;
  }

  private void initializePresenter() {
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    peopleKnowForPresenter = new PeopleKnowForPresenter(getActivity(), this, peopleId);
    peopleKnowForPresenter.start();
  }

  @Override
  public void onDestroyView() {
    if (peopleKnowForPresenter != null) {
      peopleKnowForPresenter.stop();
    }
    super.onDestroyView();
  }

  @Override
  public void onRefreshClicked() {
    Log.d(TAG, "error Handling Refresh click");
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
  public void onPeopleKnownForResponse(PeopleKnownForResponse response) {
    if (response == null || response.getCast() == null) {
      genericErrorBuilder.setUserAPIError(UserAPIErrorType.NOCONTENT_ERROR);
    } else {
      peopleKnowForData = response.getCast();
      peopleKnowForRecyclerAdapter =
          new PeopleKnowForRecyclerAdapter(getActivity(), peopleKnowForData, this);
      recyclerView.setLayoutManager(
          new GridLayoutManager(getActivity(), TheMovieDbConstants.GRID_SPAN_COUNT));
      recyclerView.setAdapter(peopleKnowForRecyclerAdapter);
      recyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onPeopleKnownForError(UserAPIErrorType errorType) {
    recyclerView.setVisibility(View.GONE);
    genericErrorBuilder.setUserAPIError(errorType);
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

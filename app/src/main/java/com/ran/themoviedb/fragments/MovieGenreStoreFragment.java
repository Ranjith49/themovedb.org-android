package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.StoreGenreAdapter;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.server.entities.MovieGenre;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 * <p/>
 * All List of Genre of Movie are shown here ..
 */
public class MovieGenreStoreFragment extends Fragment implements GenericErrorBuilder.Handler {

  private View view;
  private RecyclerView recyclerView;
  private StoreGenreAdapter storeGenreAdapter;
  private final int SPAN_COUNT = 2;
  private GenericErrorBuilder genericErrorBuilder;
  LinearLayout errorLayoutHolder;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_genre_store, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.genre_recycler_view);
    errorLayoutHolder = (LinearLayout) view.findViewById(R.id.genre_error_layout_container);
    genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
        .CENTER, errorLayoutHolder, this);
    initView();
    return view;
  }

  private void initView() {

    //Convert back from json string to Java Object
    Gson gson = new Gson();
    Type type = new TypeToken<AllMovieGenreListResponse>() {
    }.getType();
    String genreData = AppSharedPreferences.getInstance(getActivity()).getGenreListData();
    AllMovieGenreListResponse genreListResponse = gson.fromJson(genreData, type);
    if (genreListResponse != null && genreListResponse.getGenres() != null && genreListResponse
        .getGenres().size() > 0) {
      setLocalAdapter(genreListResponse.getGenres());
      recyclerView.setVisibility(View.VISIBLE);
    } else {
      recyclerView.setVisibility(View.GONE);
      genericErrorBuilder.setUserAPIError(UserAPIErrorType.UNEXPECTED_ERROR);
    }
  }

  private void setLocalAdapter(ArrayList<MovieGenre> data) {
    storeGenreAdapter = new StoreGenreAdapter(getActivity(), data);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
    recyclerView.setAdapter(storeGenreAdapter);
  }

  @Override
  public void onRefreshClicked() {
    initView();
  }
}

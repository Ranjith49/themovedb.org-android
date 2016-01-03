package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public class TvStoreFragment extends Fragment {

  private View view;
  private TVShowStoreType tvShowStoreType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.fragment_tv_store, container, false);
    String type = getArguments().getString(TheMovieDbConstants.TV_STORE_TYPE_KEY, TVShowStoreType
        .getStoreName(TVShowStoreType.TV_POPULAR));
    tvShowStoreType = TVShowStoreType.getStoreType(type);
    return view;
  }
}

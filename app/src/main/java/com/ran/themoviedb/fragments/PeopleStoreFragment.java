package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public class PeopleStoreFragment extends Fragment {

  private View view;
  private PeopleStoreType peopleStoreType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_people_store, container, false);
    String type = getArguments().getString(TheMovieDbConstants.PEOPLE_STORE_TYPE_KEY,
        PeopleStoreType.getStoreName(PeopleStoreType.PEOPLE_POPULAR));
    peopleStoreType = PeopleStoreType.getStoreType(type);

    return view;
  }
}

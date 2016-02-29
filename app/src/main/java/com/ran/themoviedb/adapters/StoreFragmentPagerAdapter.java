package com.ran.themoviedb.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.fragments.MovieStoreFragment;
import com.ran.themoviedb.fragments.PeopleStoreFragment;
import com.ran.themoviedb.fragments.TvStoreFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Store Fragment Page Adapter for Handling the different Fragments by StoreType [Movie,People,Tv]
 */
public class StoreFragmentPagerAdapter extends FragmentStatePagerAdapter {

  private DisplayStoreType storeType;
  private String[] movieTabNames;
  private String[] tvTabNames;
  private String[] peopleTabNames;

  public StoreFragmentPagerAdapter(FragmentManager fragmentManager, Context context,
                                   DisplayStoreType storeType) {
    super(fragmentManager);
    this.storeType = storeType;
    movieTabNames = context.getResources().getStringArray(R.array.movie_store_names);
    tvTabNames = context.getResources().getStringArray(R.array.tv_store_names);
    peopleTabNames = context.getResources().getStringArray(R.array.people_store_names);
  }

  @Override
  public Fragment getItem(int position) {

    switch (storeType) {
      case MOVIE_STORE:
        Fragment movieFragment = new MovieStoreFragment();
        Bundle bundle = new Bundle();
        switch (position) {
          case 0:
            bundle.putString(TheMovieDbConstants.MOVIE_STORE_TYPE_KEY, MovieStoreType
                .getStoreName(MovieStoreType.MOVIE_POPULAR));
            break;
          case 1:
            bundle.putString(TheMovieDbConstants.MOVIE_STORE_TYPE_KEY, MovieStoreType
                .getStoreName(MovieStoreType.MOVIE_TOP_RATED));
            break;
          case 2:
            bundle.putString(TheMovieDbConstants.MOVIE_STORE_TYPE_KEY, MovieStoreType
                .getStoreName(MovieStoreType.MOVIE_UPCOMING));
            break;
          case 3:
            bundle.putString(TheMovieDbConstants.MOVIE_STORE_TYPE_KEY, MovieStoreType
                .getStoreName(MovieStoreType.MOVIE_NOW_PLAYING));
            break;
        }
        movieFragment.setArguments(bundle);
        return movieFragment;

      case TV_STORE:
        Fragment tvFragment = new TvStoreFragment();
        Bundle bundle1 = new Bundle();
        switch (position) {
          case 0:
            bundle1.putString(TheMovieDbConstants.TV_STORE_TYPE_KEY, TVShowStoreType
                .getStoreName(TVShowStoreType.TV_POPULAR));
            break;
          case 1:
            bundle1.putString(TheMovieDbConstants.TV_STORE_TYPE_KEY, TVShowStoreType
                .getStoreName(TVShowStoreType.TV_TOP_RATED));
            break;
          case 2:
            bundle1.putString(TheMovieDbConstants.TV_STORE_TYPE_KEY, TVShowStoreType
                .getStoreName(TVShowStoreType.TV_AIR));
            break;
        }
        tvFragment.setArguments(bundle1);
        return tvFragment;

      case PEOPLE_STORE:
        Fragment peopleFragment = new PeopleStoreFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(TheMovieDbConstants.PEOPLE_STORE_TYPE_KEY, PeopleStoreType.getStoreName
            (PeopleStoreType.PEOPLE_POPULAR));
        peopleFragment.setArguments(bundle2);
        return peopleFragment;
    }
    return null;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (storeType) {
      case MOVIE_STORE:
        return movieTabNames[position];
      case TV_STORE:
        return tvTabNames[position];
      case PEOPLE_STORE:
        return peopleTabNames[position];
    }
    return super.getPageTitle(position);
  }

  @Override
  public int getCount() {
    switch (storeType) {
      case MOVIE_STORE:
        return movieTabNames.length;
      case TV_STORE:
        return tvTabNames.length;
      case PEOPLE_STORE:
        return peopleTabNames.length;
    }
    return 0;
  }
}

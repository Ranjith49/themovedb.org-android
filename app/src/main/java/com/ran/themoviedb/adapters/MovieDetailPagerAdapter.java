package com.ran.themoviedb.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ran.themoviedb.R;
import com.ran.themoviedb.fragments.MovieDescriptionFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 1/9/2016.
 */
public class MovieDetailPagerAdapter extends FragmentStatePagerAdapter {

  private String[] movieTabNames;
  private int movieId;

  public MovieDetailPagerAdapter(Context context, FragmentManager fm, int movieId) {
    super(fm);
    movieTabNames = context.getResources().getStringArray(R.array.movie_details_tabs);
    this.movieId = movieId;
  }

  @Override
  public Fragment getItem(int position) {

    Bundle bundle = new Bundle();
    bundle.putInt(TheMovieDbConstants.MOVIE_ID_KEY, movieId);

    Fragment movieFragment = new MovieDescriptionFragment();
    movieFragment.setArguments(bundle);
    return movieFragment;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return movieTabNames[position];
  }

  @Override
  public int getCount() {
    return movieTabNames.length;
  }
}

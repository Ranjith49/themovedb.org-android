package com.ran.themoviedb.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ran.themoviedb.R;
import com.ran.themoviedb.fragments.ImageDetailFragment;
import com.ran.themoviedb.fragments.MovieCastAndCrewFragment;
import com.ran.themoviedb.fragments.MovieDescriptionFragment;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.fragments.MovieSimilarFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 1/9/2016.
 * <p/>
 * Movie Detail View Pager Adapter bind to the ViewPager in the MovieDetailActivity
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
    switch (position) {
      case 0:
        Fragment movieFragment = new MovieDescriptionFragment();
        movieFragment.setArguments(bundle);
        return movieFragment;
      case 1:
        Fragment castAndCrewFragment = new MovieCastAndCrewFragment();
        castAndCrewFragment.setArguments(bundle);
        return castAndCrewFragment;
      case 2:
        Fragment imageFragment = new ImageDetailFragment();
        imageFragment.setArguments(bundle);
        return imageFragment;
      case 3:
        Fragment reviewFragment = new MovieReviewsFragment();
        reviewFragment.setArguments(bundle);
        return reviewFragment;
      case 4:
        Fragment relatedFragment = new MovieSimilarFragment();
        relatedFragment.setArguments(bundle);
        return relatedFragment;
      default:
        Fragment movieFragment1 = new MovieDescriptionFragment();
        movieFragment1.setArguments(bundle);
        return movieFragment1;
    }

  }

  @Override
  public CharSequence getPageTitle(int position) {
    return movieTabNames[position];
  }

  @Override
  public int getCount() {
    return movieTabNames.length;
  }

  @Override
  public Parcelable saveState() {
    return null;
  }
}

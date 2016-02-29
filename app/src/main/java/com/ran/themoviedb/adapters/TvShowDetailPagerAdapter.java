package com.ran.themoviedb.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ran.themoviedb.R;
import com.ran.themoviedb.fragments.TvShowDetailFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 1/9/2016.
 * <p/>
 * Movie Detail View Pager Adapter bind to the ViewPager in the MovieDetailActivity
 */
public class TvShowDetailPagerAdapter extends FragmentStatePagerAdapter {

  private String[] tvShowTabNames;
  private int tvShowId;

  public TvShowDetailPagerAdapter(Context context, FragmentManager fm, int tvShowId) {
    super(fm);
    tvShowTabNames = context.getResources().getStringArray(R.array.tv_details_tabs);
    this.tvShowId = tvShowId;
  }

  @Override
  public Fragment getItem(int position) {

    Bundle bundle = new Bundle();
    bundle.putInt(TheMovieDbConstants.TV_SHOW_ID_KEY, tvShowId);
    switch (position) {
      case 0:
        Fragment tvDetailFragment = new TvShowDetailFragment();
        tvDetailFragment.setArguments(bundle);
        return tvDetailFragment;
      default:
        Fragment tvDetailFragment1 = new TvShowDetailFragment();
        tvDetailFragment1.setArguments(bundle);
        return tvDetailFragment1;
    }
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return tvShowTabNames[position];
  }

  @Override
  public int getCount() {
    return tvShowTabNames.length;
  }

  @Override
  public Parcelable saveState() {
    return null;
  }
}

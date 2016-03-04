package com.ran.themoviedb.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ran.themoviedb.R;
import com.ran.themoviedb.fragments.ImageDetailFragment;
import com.ran.themoviedb.fragments.PeopleDetailFragment;
import com.ran.themoviedb.fragments.PeopleKnownForFragment;
import com.ran.themoviedb.fragments.TvShowDetailFragment;
import com.ran.themoviedb.fragments.TvShowSeasonsFragment;
import com.ran.themoviedb.fragments.TvShowSimilarFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;

/**
 * Created by ranjith.suda on 1/9/2016.
 * <p>
 * People Detail View Pager Adapter bind to the ViewPager in the PeopleDetailActivity
 */
public class PeopleDetailPagerAdapter extends FragmentStatePagerAdapter {

  private String[] peopleTabNames;
  private int peopleIdKey;

  public PeopleDetailPagerAdapter(Context context, FragmentManager fm, int peopleIdKey) {
    super(fm);
    peopleTabNames = context.getResources().getStringArray(R.array.people_detail_tabs);
    this.peopleIdKey = peopleIdKey;
  }

  @Override
  public Fragment getItem(int position) {

    Bundle bundle = new Bundle();
    bundle.putInt(TheMovieDbConstants.PEOPLE_ID_KEY, peopleIdKey);
    bundle.putString(TheMovieDbConstants.STORE_TYPE_KEY,
        DisplayStoreType.getStoreName(DisplayStoreType.PERSON_STORE));
    switch (position) {
      case 0:
        Fragment peopleDetailFragment = new PeopleDetailFragment();
        peopleDetailFragment.setArguments(bundle);
        return peopleDetailFragment;
      case 1:
        Fragment peopleKnownForFragment = new PeopleKnownForFragment();
        peopleKnownForFragment.setArguments(bundle);
        return peopleKnownForFragment;
      default:
        Fragment peopleDetailFragment1 = new PeopleDetailFragment();
        peopleDetailFragment1.setArguments(bundle);
        return peopleDetailFragment1;
    }
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return peopleTabNames[position];
  }

  @Override
  public int getCount() {
    return peopleTabNames.length;
  }

  @Override
  public Parcelable saveState() {
    return null;
  }
}

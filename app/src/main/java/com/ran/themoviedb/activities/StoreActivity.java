package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.StoreFragmentPagerAdapter;
import com.ran.themoviedb.entities.DisplayStoreType;
import com.ran.themoviedb.model.TheMovieDbConstants;

public class StoreActivity extends AppCompatActivity {

  ViewPager viewPager;
  DisplayStoreType displayStoreType = DisplayStoreType.MOVIE_STORE;
  StoreFragmentPagerAdapter storeFragmentPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store);

    if (getIntent().hasExtra(TheMovieDbConstants.STORE_TYPE_KEY)) {
      displayStoreType = DisplayStoreType.getStoreType(getIntent().getStringExtra
          (TheMovieDbConstants.STORE_TYPE_KEY));
    }

    initView();
  }

  /**
   * Initialise the View Accordingly ..
   */
  private void initView() {
    switch (displayStoreType) {
      case MOVIE_STORE:
        setTitle(R.string.title_activity_movie_store);
        break;
      case TV_STORE:
        setTitle(R.string.title_activity_tv_store);
        break;
      case PEOPLE_STORE:
        setTitle(R.string.title_activity_people_store);
        break;
    }

    viewPager = (ViewPager) findViewById(R.id.store_viewpager);
    storeFragmentPagerAdapter = new StoreFragmentPagerAdapter(getFragmentManager(), this,
        displayStoreType);
    viewPager.setAdapter(storeFragmentPagerAdapter);
  }
}

package com.ran.themoviedb.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.StoreFragmentPagerAdapter;
import com.ran.themoviedb.entities.DisplayStoreType;
import com.ran.themoviedb.model.TheMovieDbConstants;

public class StoreActivity extends AppCompatActivity {

  ViewPager viewPager;
  DisplayStoreType displayStoreType = DisplayStoreType.MOVIE_STORE;
  StoreFragmentPagerAdapter storeFragmentPagerAdapter;
  private MenuItem searchMenuItem;
  private SearchView searchView;

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

  @Override
  protected void onResume() {
    super.onResume();

    //Make Sure the Search View is in collapsed State [Rest Resumes ]
    if (searchView != null) {
      searchView.onActionViewCollapsed();
    }
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
    viewPager.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        searchView.onActionViewCollapsed();
        return false;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Bundle appData = new Bundle();
    appData.putString(TheMovieDbConstants.STORE_TYPE_KEY,
        DisplayStoreType.getStoreName(displayStoreType));

    getMenuInflater().inflate(R.menu.store_search_menu, menu);
    searchMenuItem = menu.findItem(R.id.menu_search);
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

    searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
    searchView.setAppSearchData(appData);
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    return true;
  }
}

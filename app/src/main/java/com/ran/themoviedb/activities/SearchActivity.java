package com.ran.themoviedb.activities;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.fragments.SearchMovieFragment;
import com.ran.themoviedb.fragments.SearchPeopleFragment;
import com.ran.themoviedb.fragments.SearchTvFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;

public class SearchActivity extends AppCompatActivity {

  private DisplayStoreType displayStoreType;
  private String searchQuery = TheMovieDbConstants.EMPTY_STRING;
  private final String TAG = SearchActivity.class.getSimpleName();
  private TextView searchTextView;
  private LinearLayout fragmentContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    searchTextView = (TextView) findViewById(R.id.search_query_view);
    fragmentContainer = (LinearLayout) findViewById(R.id.search_fragment_holder);
    handleSearchIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    handleSearchIntent(intent);
  }

  private void setCustomTitle() {
    switch (displayStoreType) {
      case MOVIE_STORE:
        setTitle(getResources().getString(R.string.title_activity_search_movie));
        break;
      case TV_STORE:
        setTitle(getResources().getString(R.string.title_activity_search_tv));
        break;
      case PERSON_STORE:
        setTitle(getResources().getString(R.string.title_activity_search_people));
        break;
    }
  }

  private void handleSearchIntent(Intent intent) {

    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      searchQuery = intent.getStringExtra(SearchManager.QUERY);
      searchTextView.setText(searchQuery);
      Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
      if (bundle != null) {
        String storetype = bundle.getString(TheMovieDbConstants.STORE_TYPE_KEY, DisplayStoreType
            .getStoreName(DisplayStoreType.MOVIE_STORE));
        displayStoreType = DisplayStoreType.getStoreType(storetype);
        setCustomTitle();
        initializeFragment();
      } else {
        Log.d(TAG, "Search bundle of Store Type is Null");
        finish();
      }
    } else {
      Log.d(TAG, "This is not a search query");
      finish();
    }
  }

  private void initializeFragment() {
    Bundle searchBundle = new Bundle();
    searchBundle.putString(TheMovieDbConstants.SEARCH_KEY, searchQuery);
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    switch (displayStoreType) {
      case MOVIE_STORE:
        SearchMovieFragment searchMovieFragment = new SearchMovieFragment();
        searchMovieFragment.setArguments(searchBundle);
        fragmentTransaction.replace(R.id.search_fragment_holder, searchMovieFragment);
        fragmentTransaction.commit();
        break;
      case TV_STORE:
        SearchTvFragment searchTvFragment = new SearchTvFragment();
        searchTvFragment.setArguments(searchBundle);
        fragmentTransaction.replace(R.id.search_fragment_holder, searchTvFragment);
        fragmentTransaction.commit();
        break;
      case PERSON_STORE:
        SearchPeopleFragment searchPeopleFragment = new SearchPeopleFragment();
        searchPeopleFragment.setArguments(searchBundle);
        fragmentTransaction.replace(R.id.search_fragment_holder, searchPeopleFragment);
        fragmentTransaction.commit();
        break;
    }
  }
}

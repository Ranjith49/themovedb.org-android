package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.TvShowDetailPagerAdapter;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 2/29/2016.
 * <p/>
 * Activity responsible for showing the Tv Show Details
 */
public class TvShowDetailActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private TvShowDetailPagerAdapter movieDetailPagerAdapter;
  private final int TV_DEFAULT_INVALID_INDEX = -1;
  int tvShowId = TV_DEFAULT_INVALID_INDEX;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    initView();
  }

  private void initView() {
    if (getIntent().hasExtra(TheMovieDbConstants.TV_SHOW_ID_KEY)) {
      tvShowId =
          getIntent().getIntExtra(TheMovieDbConstants.TV_SHOW_ID_KEY, TV_DEFAULT_INVALID_INDEX);
    }

    if (tvShowId != TV_DEFAULT_INVALID_INDEX) {
      viewPager = (ViewPager) findViewById(R.id.movie_viewpager);
      movieDetailPagerAdapter = new TvShowDetailPagerAdapter(this, getFragmentManager(), tvShowId);
      viewPager.setAdapter(movieDetailPagerAdapter);
    } else {
      Toast.makeText(this, R.string.tv_id_error_message, Toast.LENGTH_SHORT).show();
      finish();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.detail_screen_share, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.item_share:
        Toast.makeText(this, "Share " + tvShowId, Toast.LENGTH_SHORT).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}

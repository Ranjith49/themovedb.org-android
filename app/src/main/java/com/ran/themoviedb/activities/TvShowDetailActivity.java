package com.ran.themoviedb.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.ad.inmobi.InMobiAdTypes;
import com.ran.themoviedb.ad.inmobi.InMobiWrapper;
import com.ran.themoviedb.adapters.TvShowDetailPagerAdapter;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;

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

      //Try to show Interstitial AD
      InMobiWrapper.showInterstitialAD(this, InMobiAdTypes.INTERSTITIAL_AD);
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
        String shareDesc = String.format(getResources().getString(R.string.share_movie_description),
            TheMovieDbConstants.EMPTY_STRING);
        ShareContent shareContent = new ShareContent(TheMovieDbConstants.EMPTY_STRING,
            shareDesc, tvShowId, DisplayStoreType.MOVIE_STORE);
        ShareContentHelper.buildShareIntent(this, shareContent,
            getResources().getString(R.string.share_app_dialog_title));
        Log.d("Share", "Share Id  : " + tvShowId);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}

package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.MovieDetailPagerAdapter;
import com.ran.themoviedb.adapters.PeopleDetailPagerAdapter;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.share.ShareContent;
import com.ran.themoviedb.model.share.ShareContentHelper;

public class PeopleDetailActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private PeopleDetailPagerAdapter peopleDetailPagerAdapter;
  private final int PEOPLE_DEFAULT_INVALID_INDEX = -1;
  int  peopleId= PEOPLE_DEFAULT_INVALID_INDEX;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_people_detail);
    initView();
  }

  private void initView() {
    if (getIntent().hasExtra(TheMovieDbConstants.PEOPLE_ID_KEY)) {
      peopleId = getIntent().getIntExtra(TheMovieDbConstants.PEOPLE_ID_KEY,
          PEOPLE_DEFAULT_INVALID_INDEX);
    }

    if (peopleId != PEOPLE_DEFAULT_INVALID_INDEX) {
      viewPager = (ViewPager) findViewById(R.id.people_viewpager);
      peopleDetailPagerAdapter = new PeopleDetailPagerAdapter(this, getFragmentManager(), peopleId);
      viewPager.setAdapter(peopleDetailPagerAdapter);
    } else {
      Toast.makeText(this, R.string.movie_id_error_message, Toast.LENGTH_SHORT).show();
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
        String shareDesc = String.format(getResources().getString(R.string.share_people_desc),
            TheMovieDbConstants.EMPTY_STRING);
        ShareContent shareContent = new ShareContent(TheMovieDbConstants.EMPTY_STRING,
            shareDesc, peopleId, DisplayStoreType.PERSON_STORE);
        ShareContentHelper.buildShareIntent(this, shareContent,
            getResources().getString(R.string.share_app_dialog_title));
        Log.d("Share", "Share Id  : " + peopleId);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}

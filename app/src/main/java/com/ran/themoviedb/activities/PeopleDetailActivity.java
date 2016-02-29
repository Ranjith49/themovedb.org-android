package com.ran.themoviedb.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.fragments.PeopleDetailFragment;
import com.ran.themoviedb.model.TheMovieDbConstants;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleDetailActivity extends AppCompatActivity {

  private final int DEFAULT_INVALID_INDEX = -1;
  int peopleId = DEFAULT_INVALID_INDEX;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_people_detail);

    initView();
  }

  private void initView() {

    if (getIntent().hasExtra(TheMovieDbConstants.PEOPLE_ID_KEY)) {
      peopleId = getIntent().getIntExtra(TheMovieDbConstants.PEOPLE_ID_KEY, DEFAULT_INVALID_INDEX);
    }

    if (peopleId != DEFAULT_INVALID_INDEX) {
      Bundle bundle = new Bundle();
      bundle.putInt(TheMovieDbConstants.PEOPLE_ID_KEY, peopleId);
      PeopleDetailFragment peopleDetailFragment = new PeopleDetailFragment();
      peopleDetailFragment.setArguments(bundle);

      FragmentTransaction ft = getFragmentManager().beginTransaction();
      ft.replace(R.id.people_fragment_container, peopleDetailFragment);
      ft.commit();
    } else {
      Toast.makeText(this, R.string.people_id_error_message, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Share " + peopleId, Toast.LENGTH_SHORT).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}

package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.MovieDetailPagerAdapter;
import com.ran.themoviedb.model.TheMovieDbConstants;

public class MovieDetailActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private MovieDetailPagerAdapter movieDetailPagerAdapter;
  private final int MOVIE_DEFAULT_INVALID_INDEX = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    initView();
  }

  private void initView() {

    int movieId = MOVIE_DEFAULT_INVALID_INDEX;
    if (getIntent().hasExtra(TheMovieDbConstants.MOVIE_ID_KEY)) {
      movieId = getIntent().getIntExtra(TheMovieDbConstants.MOVIE_ID_KEY,
          MOVIE_DEFAULT_INVALID_INDEX);
    }

    if (movieId != MOVIE_DEFAULT_INVALID_INDEX) {
      viewPager = (ViewPager) findViewById(R.id.movie_viewpager);
      movieDetailPagerAdapter = new MovieDetailPagerAdapter(this, getFragmentManager(), movieId);
      viewPager.setAdapter(movieDetailPagerAdapter);
    } else {
      Toast.makeText(this, R.string.movie_id_error_message, Toast.LENGTH_SHORT).show();
      finish();
    }
  }
}

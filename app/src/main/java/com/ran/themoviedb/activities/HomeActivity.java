package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ran.themoviedb.R;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.customviews.HomeBannerView;
import com.ran.themoviedb.customviews.HomePosterView;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.presenters.HomeScreenDataPresenter;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.HomeScreenView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
    implements HomeScreenView, GenericErrorBuilder.Handler {

  private final String TAG = HomeActivity.class.getSimpleName();
  LinearLayout errorLayout;
  ProgressBar progressBar;
  LinearLayout contentLayout;

  HomeScreenDataPresenter dataPresenter;
  private GenericErrorBuilder genericErrorBuilder;

  HomeBannerView movieBanner;
  HomeBannerView tvBanner;
  HomePosterView peoplePoster;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    initView();
  }

  /**
   * Initialize the View ..
   */
  private void initView() {
    errorLayout = (LinearLayout) findViewById(R.id.home_error_layout_container);
    progressBar = (ProgressBar) findViewById(R.id.home_screen_progress);
    contentLayout = (LinearLayout) findViewById(R.id.home_screen_content);

    dataPresenter =
        new HomeScreenDataPresenter(HomeActivity.this, this, HomeActivity.class.hashCode(),
            MovieStoreType.MOVIE_POPULAR,
            TVShowStoreType.TV_POPULAR,
            PeopleStoreType.PEOPLE_POPULAR);
    genericErrorBuilder = new GenericErrorBuilder(HomeActivity.this, GenericUIErrorLayoutType
        .CENTER, errorLayout, this);

    movieBanner = (HomeBannerView) findViewById(R.id.home_movie_banner);
    tvBanner = (HomeBannerView) findViewById(R.id.home_tv_banner);
    peoplePoster = (HomePosterView) findViewById(R.id.home_people_poster);

    //Start the Presenter [New Intent handling may be required ]
    dataPresenter.start();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home_activity_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.home_menu_lang:
        Navigator.navigateToLanguageScreen(this);
        return true;
      case R.id.home_menu_aboutus:
        //Todo [ranjith] , Enable About Us UI ..
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    movieBanner.onViewVisible();
    tvBanner.onViewVisible();
    peoplePoster.onViewVisible();
  }

  @Override
  protected void onStop() {
    super.onStop();
    movieBanner.onViewHidden();
    tvBanner.onViewHidden();
    peoplePoster.onViewHidden();
  }

  @Override
  protected void onDestroy() {
    dataPresenter.stop();
    super.onDestroy();
  }

  // -- Call backs from Presenter and the Generic Error handling UI -- //
  @Override
  public void showProgressBar(boolean show) {
    if (show) {
      progressBar.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void homeScreenData(ArrayList<String> movieBanners, ArrayList<String> tvBanners,
                             ArrayList<String> peoplePosters) {
    Log.d(TAG, "Content is retrieved");
    contentLayout.setVisibility(View.VISIBLE);

    movieBanner.setBottomText(getResources().getString(R.string.home_movie_store_name));
    tvBanner.setBottomText(getResources().getString(R.string.home_tv_store_name));
    peoplePoster.setBottomText(getResources().getString(R.string.home_people_store_name));

    //Set Banners ..
    movieBanner.setBannerUrls(movieBanners);
    tvBanner.setBannerUrls(tvBanners);

    //Set Posters ..
    peoplePoster.setPosterUrls(peoplePosters);
  }

  @Override
  public void homeScreenError(UserAPIErrorType errorType) {
    genericErrorBuilder.setUserAPIError(errorType);
  }

  @Override
  public void onRefreshClicked() {
    Log.d(TAG, "error Handling Refresh click");
    dataPresenter.start();
  }
}

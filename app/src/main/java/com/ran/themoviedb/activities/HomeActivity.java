package com.ran.themoviedb.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.customviews.HomeBannerView;
import com.ran.themoviedb.customviews.HomePosterView;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.presenters.HomeScreenDataPresenter;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.HomeScreenView;

import java.util.ArrayList;

/**
 * Home Activity of the App, Giving different options possible through the App
 * i.e Movies , Tv Store , People Store
 */
public class HomeActivity extends AppCompatActivity
    implements HomeScreenView, GenericErrorBuilder.Handler, View.OnClickListener {

  private final String TAG = HomeActivity.class.getSimpleName();
  private Context context;
  LinearLayout errorLayout;
  ProgressBar progressBar;
  LinearLayout contentLayout;

  HomeScreenDataPresenter dataPresenter;
  private GenericErrorBuilder genericErrorBuilder;
  private long mBackPressedTime;

  HomeBannerView movieBannerView;
  HomeBannerView tvBannerView;
  HomePosterView peoplePosterView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    setContentView(R.layout.activity_home);
    initView();
  }

  /**
   * Initialize the View and Start Presenters ,Error Handler Stuff ..
   */
  private void initView() {
    errorLayout = (LinearLayout) findViewById(R.id.home_error_layout_container);
    errorLayout.setVisibility(View.GONE);
    progressBar = (ProgressBar) findViewById(R.id.home_screen_progress);
    progressBar.setVisibility(View.GONE);
    contentLayout = (LinearLayout) findViewById(R.id.home_screen_content);
    contentLayout.setVisibility(View.GONE);
    dataPresenter =
        new HomeScreenDataPresenter(HomeActivity.this, this, HomeActivity.class.hashCode(),
            MovieStoreType.MOVIE_POPULAR,
            TVShowStoreType.TV_POPULAR,
            PeopleStoreType.PEOPLE_POPULAR);
    genericErrorBuilder = new GenericErrorBuilder(HomeActivity.this, GenericUIErrorLayoutType
        .CENTER, errorLayout, this);

    movieBannerView = (HomeBannerView) findViewById(R.id.home_movie_banner);
    movieBannerView.resetHandler();
    movieBannerView.setOnClickListener(this);
    tvBannerView = (HomeBannerView) findViewById(R.id.home_tv_banner);
    tvBannerView.resetHandler();
    tvBannerView.setOnClickListener(this);
    peoplePosterView = (HomePosterView) findViewById(R.id.home_people_poster);
    peoplePosterView.resetHandler();
    peoplePosterView.setOnClickListener(this);

    dataPresenter.start();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    initView();
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
        Snackbar.make(contentLayout, R.string.about_us_string, Snackbar.LENGTH_LONG)
            .setAction(R.string.about_us_email, new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Navigator.sendEmail(context);
              }
            }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    movieBannerView.onViewVisible();
    tvBannerView.onViewVisible();
    peoplePosterView.onViewVisible();
  }

  @Override
  protected void onStop() {
    super.onStop();
    movieBannerView.onViewHidden();
    tvBannerView.onViewHidden();
    peoplePosterView.onViewHidden();
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

    movieBannerView.setBottomText(getResources().getString(R.string.home_movie_store_name));
    tvBannerView.setBottomText(getResources().getString(R.string.home_tv_store_name));
    peoplePosterView.setBottomText(getResources().getString(R.string.home_people_store_name));

    //Set Banners ..
    movieBannerView.setBannerUrls(movieBanners);
    tvBannerView.setBannerUrls(tvBanners);

    //Set Posters ..
    peoplePosterView.setPosterUrls(peoplePosters);
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

  @Override
  public void onBackPressed() {
    if (mBackPressedTime + TheMovieDbConstants.TIME_INTERVAL_APP_EXIT >
        System.currentTimeMillis()) {
      super.onBackPressed();
    } else {
      Toast.makeText(this, getResources().getString(R.string.app_back_pressed_toast),
          Toast.LENGTH_SHORT).show();
    }
    mBackPressedTime = System.currentTimeMillis();
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.home_movie_banner:
        Navigator.navigateToStore(this, DisplayStoreType.MOVIE_STORE);
        overridePendingTransition(R.anim.activity_right_left_enter,
            R.anim.activity_right_left_exit);
        break;

      case R.id.home_tv_banner:
        Navigator.navigateToStore(this, DisplayStoreType.TV_STORE);
        overridePendingTransition(R.anim.activity_right_left_enter,
            R.anim.activity_right_left_exit);
        break;

      case R.id.home_people_poster:
        Navigator.navigateToStore(this, DisplayStoreType.PERSON_STORE);
        overridePendingTransition(R.anim.activity_right_left_enter,
            R.anim.activity_right_left_exit);
        break;
    }
  }
}

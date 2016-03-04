package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ran.themoviedb.R;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.presenters.TheMovieDbConfigPresenter;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.TheMovieDbConfigView;


/**
 * Splash Screen launched for every Launch of the App , does the Initial Load
 * i.e Db Config [Preload Data is fetched first]
 */
public class SplashActivity extends AppCompatActivity
    implements TheMovieDbConfigView, GenericErrorBuilder.Handler {

  private final String TAG = SplashActivity.class.getSimpleName();
  private final int SPLASH_TIME_MILLS = 1000;
  private final int MESSAGE_SPLASH_TYPE = 100;

  private LinearLayout errorLayout;
  private ProgressBar progressBar;

  private TheMovieDbConfigPresenter theMovieDbConfigPresenter;
  private final Handler handler = new SplashHandler();
  private GenericErrorBuilder genericErrorBuilder;

  private class SplashHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case MESSAGE_SPLASH_TYPE:
          startPresenters();
          break;
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    theMovieDbConfigPresenter = new TheMovieDbConfigPresenter(SplashActivity.this, this,
        SplashActivity.class.hashCode());

    errorLayout = (LinearLayout) findViewById(R.id.splash_error_layout_container);
    progressBar = (ProgressBar) findViewById(R.id.splash_screen_progress);
    genericErrorBuilder = new GenericErrorBuilder(SplashActivity.this, GenericUIErrorLayoutType
        .CENTER, errorLayout, this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    handler.removeMessages(MESSAGE_SPLASH_TYPE);
    handler.sendEmptyMessageDelayed(MESSAGE_SPLASH_TYPE, SPLASH_TIME_MILLS);
  }

  @Override
  protected void onStop() {
    super.onStop();
    stopPresenters();
    handler.removeMessages(MESSAGE_SPLASH_TYPE);
  }

  private void startPresenters() {
    progressBar.setVisibility(View.VISIBLE);
    theMovieDbConfigPresenter.start();
  }

  private void stopPresenters() {
    progressBar.setVisibility(View.GONE);
    theMovieDbConfigPresenter.stop();
  }

  // --- Call Backs from the Initial Request Presenters /Error Layouts----- //

  @Override
  public void isConfigRetrievalSuccess(boolean success) {
    if (!success) {
      Log.d(TAG, "It is not success , Retry till we get it ..");
      genericErrorBuilder.setUserAPIError(UserAPIErrorType.UNEXPECTED_ERROR);
      progressBar.setVisibility(View.GONE);
      return;
    }
    Log.d(TAG, "Got the Data Successfully fine");
    if (AppSharedPreferences.getInstance(this).isAppFirstLaunch()) {
      Navigator.navigateToLanguageScreen(this);
    } else {
      Navigator.navigateToAppHome(this);
    }
    finish();
  }

  @Override
  public void onRefreshClicked() {
    handler.removeMessages(MESSAGE_SPLASH_TYPE);
    handler.sendEmptyMessageDelayed(MESSAGE_SPLASH_TYPE, SPLASH_TIME_MILLS);
  }
}

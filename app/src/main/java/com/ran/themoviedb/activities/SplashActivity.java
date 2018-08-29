package com.ran.themoviedb.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.presenters.TheMovieDbConfigPresenter;
import com.ran.themoviedb.utils.Navigator;


/**
 * Splash Screen launched for every Launch of the App , does the Initial Load
 * i.e Db Config [Preload Data is fetched first]
 */
public class SplashActivity extends AppCompatActivity {

  private final String TAG = SplashActivity.class.getSimpleName();
  private final int SPLASH_TIME_MILLS = 2500;
  private final int MESSAGE_SPLASH_TYPE = 100;

  private final Handler handler = new SplashHandler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    //Start Presenter to fetch the MovieDb Config..
    TheMovieDbConfigPresenter.getInstance().fetchMovieDbConfig();
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
    handler.removeMessages(MESSAGE_SPLASH_TYPE);
  }

  private void onSplashLoadTimeComplete() {
    if (TheMovieDbAppController.getAppInstance().appSharedPreferences.isAppFirstLaunch()) {
      Navigator.navigateToLanguageScreen(this);
    } else {
      Navigator.navigateToAppHome(this);
    }
    finish();
  }

  private class SplashHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case MESSAGE_SPLASH_TYPE:
          onSplashLoadTimeComplete();
          break;
      }
    }
  }
}

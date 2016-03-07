package com.ran.themoviedb.ad.inmobi;

import android.util.Log;

import com.inmobi.ads.InMobiInterstitial;

import java.util.Map;

/**
 * Wrapper around the InterstitialListener of the InMobi , that makes sure necessary ones are
 * populated to UI
 * <p/>
 * Created by ranjith.suda on 3/7/2016.
 * https://support.inmobi.com/monetize/integration/android/android-sdk-integration-guide/#creating-interstitial-or-native-int
 */
public abstract class InMobiInterstitialListener
    implements InMobiInterstitial.InterstitialAdListener {

  private final String TAG = InMobiInterstitialListener.class.getSimpleName();

  @Override
  public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial,
                                        Map<Object, Object> map) {
    Log.d(TAG, "On Ad reward Completed");
  }

  @Override
  public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
    Log.d(TAG, "On Ad Displayed");
  }

  @Override
  public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
    Log.d(TAG, "On Ad Dismissed");
  }


  @Override
  public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
    Log.d(TAG, "On Ad User Left Application");
  }
}

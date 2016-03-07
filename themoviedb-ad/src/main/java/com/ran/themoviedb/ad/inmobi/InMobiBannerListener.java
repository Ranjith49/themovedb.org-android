package com.ran.themoviedb.ad.inmobi;

import android.util.Log;

import com.inmobi.ads.InMobiBanner;

import java.util.Map;

/**
 * Created by ranjith.suda on 3/7/2016.
 * <p/>
 * The Listener that is added for InMobi Banners , This will remove extra call backs and group
 * to the required ones..
 * <p/>
 * https://support.inmobi.com/monetize/integration/android/android-sdk-integration-guide/#creating-banner-ad
 */
public abstract class InMobiBannerListener implements InMobiBanner.BannerAdListener {

  private final String TAG = InMobiBannerListener.class.getSimpleName();

  @Override
  public void onAdDismissed(InMobiBanner inMobiBanner) {
    Log.d(TAG, "Ad Dismissed : " + inMobiBanner.getId());
  }

  @Override
  public void onAdDisplayed(InMobiBanner inMobiBanner) {
    Log.d(TAG, "Ad Displayed : " + inMobiBanner.getId());
  }

  @Override
  public void onUserLeftApplication(InMobiBanner inMobiBanner) {
    Log.d(TAG, "Ad onUserLeftApplication : " + inMobiBanner.getId());
  }

  @Override
  public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {
    Log.d(TAG, "Ad onAdRewardActionCompleted : " + inMobiBanner.getId());
  }
}

package com.ran.themoviedb.ad.inmobi;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;
import com.ran.themoviedb.ad.inmobi.utils.InMobiAdUtils;
import com.ran.themoviedb.model.BuildConfig;

import java.util.Map;

/**
 * Created by ranjith.suda on 3/7/2016.
 * <p/>
 * Class Responsible for Wrapping up the calls between InMobi and App
 * Basically does the following :
 * a) Initialize the InMobi SDK.
 * b) Set Different IN Mobi Ad's [BANNER ,INTERSTITIAL && NATIVE]
 */
public class InMobiWrapper {

  private static boolean sdkInitialized;
  private static final String TAG = InMobiWrapper.class.getSimpleName();


  public static void initializeInMobiSdk(Context context, @NonNull String account_id) {
    InMobiSdk.init(context, account_id);
    sdkInitialized = true;
    if (BuildConfig.LOGGER_ENABLED) {
      InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
    }
  }

  public static void setUserLocation(@NonNull Location location) {
    if (!sdkInitialized) {
      Log.d(TAG, "SDK is not yet initialized , return");
      return;
    }
    InMobiSdk.setLocation(location);
  }

  public static void setUserLocationPrecisely(@NonNull String city, @NonNull String state,
                                              @NonNull String country) {
    if (!sdkInitialized) {
      Log.d(TAG, "SDK is not yet initialized , return");
      return;
    }
    InMobiSdk.setLocationWithCityStateCountry(city, state, country);
  }

  public static void showBannerAD(Context context, @NonNull final RelativeLayout parentContainer,
                                  @NonNull InMobiAdTypes adTypes) {
    if (!sdkInitialized) {
      Log.d(TAG, "SDK is not yet Initialized , return");
      return;
    }

    if (adTypes == InMobiAdTypes.INTERSTITIAL_AD || adTypes == InMobiAdTypes.NATIVE_AD) {
      Log.d(TAG, "This is not Banner type ,return");
      return;
    }

    //Initialize the Banner here ..
    InMobiBanner inMobiBanner = new InMobiBanner(context, InMobiAdUtils.BANNER_PLACEMENT_ID);
    inMobiBanner.setEnableAutoRefresh(true);
    inMobiBanner.setRefreshInterval(InMobiAdUtils.BANNER_REFRESH_INTERVAL);
    inMobiBanner.setAnimationType(InMobiBanner.AnimationType.ROTATE_HORIZONTAL_AXIS);

    // Add the In Mobi banner to the Parent Container ..
    parentContainer.addView(inMobiBanner, InMobiAdUtils.bannerRelativeLayoutParams(adTypes));

    //Enable the Custom Listener here ..
    inMobiBanner.setListener(new InMobiBannerListener() {
      @Override
      public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {
        parentContainer.setVisibility(View.VISIBLE);
      }

      @Override
      public void onAdLoadFailed(InMobiBanner inMobiBanner,
                                 InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "On Ad Load Failed id : " + inMobiBanner.getId() + "Status : " +
            inMobiAdRequestStatus.getMessage());
      }

      @Override
      public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {
        //Required for The Analytics Purpose ,
        Log.d(TAG, "On Ad Interacted : " + inMobiBanner.getId());
      }
    });

    //Load the Banner
    inMobiBanner.load();
  }

}

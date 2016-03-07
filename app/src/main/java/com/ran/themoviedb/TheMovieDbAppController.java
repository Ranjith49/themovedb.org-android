package com.ran.themoviedb;

import android.app.Application;

import com.ran.themoviedb.ad.inmobi.InMobiWrapper;
import com.ran.themoviedb.model.utils.ApplicationUtils;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Application Class for Initializations
 */
public class TheMovieDbAppController extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    //Set Application Class for further Use ..
    ApplicationUtils.setApplication(this);

    //Fabric SDK integration for Crash Findings ..
    Fabric.with(this, new Crashlytics());

    //Initialize the IN Mobi SDK here ..
    InMobiWrapper.initializeInMobiSdk(this, "YOUR_ACCOUNT_ID"); //TODO [ranjith , revalidate]

  }
}

package com.ran.themoviedb;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.ran.themoviedb.ad.inmobi.InMobiWrapper;
import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.di.DaggerNetworkComponent;
import com.ran.themoviedb.model.utils.ApplicationUtils;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Application Class for Initializations
 */
public class TheMovieDbAppController extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Build the Network component ..
        NetworkSDK.getInstance().initialize(this);

        //Set Application Class for further Use ..
        ApplicationUtils.setApplication(this);

        //Fabric SDK integration for Crash Findings ..
        Fabric.with(this, new Crashlytics());

        //Initialize the IN Mobi SDK here ..
        //https://www.inmobi.com/portal#properties/landing/
        InMobiWrapper.initializeInMobiSdk(this, "c1adff1aa19c4645a056fb57a81a5ef2");

    }
}

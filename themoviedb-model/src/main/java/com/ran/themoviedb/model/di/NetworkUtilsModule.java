package com.ran.themoviedb.model.di;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Utils module for Network
 *
 * @author ranjithsuda
 */

@Module
public class NetworkUtilsModule {

    @Provides
    @Named(NetworkDIConstants.NETWORK_IS_CONNECTED)
    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Provides
    public Gson providesGson() {
        return new Gson();
    }
}

package com.ran.themoviedb.model.di;

import android.content.Context;

import com.ran.themoviedb.model.NetworkSDK;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * NetworkComponent for the App
 * Includes basic network module , utils module as well as the API layer
 *
 * @author ranjithsuda
 */

@Singleton
@Component(modules = {NetworkModule.class,
        NetworkUtilsModule.class,
        NetworkAPIModule.class})

public interface NetworkComponent {

    void injectTo(NetworkSDK networkSDK);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder setContext(Context context);

        NetworkComponent build();
    }
}

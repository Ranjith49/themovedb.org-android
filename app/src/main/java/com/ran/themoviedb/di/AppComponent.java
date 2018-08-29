package com.ran.themoviedb.di;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.db.AppSharedPreferences;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Application Component , injected to Application Class
 *
 * @author ranjithsuda
 */

@Singleton
@Component
public interface AppComponent {

    AppSharedPreferences appPreferences();

    void injectTo(TheMovieDbAppController application);

    @Component.Builder
    interface Builder {


        @BindsInstance
        Builder setContext(Context appContext);

        AppComponent build();
    }
}

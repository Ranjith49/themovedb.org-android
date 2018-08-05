package com.ran.themoviedb.model.di;

import com.ran.themoviedb.model.BuildConfig;
import com.ran.themoviedb.model.TheMovieDbConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Network Module for the app , providing the Retrofit , Okhttp
 *
 * @author ranjithsuda
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    public GsonConverterFactory getGsonFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    public OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.LOGGER_ENABLED) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        builder.interceptors().add(interceptor);
        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit getRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(TheMovieDbConstants.APP_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }
}

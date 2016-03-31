package com.ran.themoviedb.model.utils;

import com.ran.themoviedb.model.BuildConfig;
import com.ran.themoviedb.model.TheMovieDbConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Retrofit Adapter Wrapper , to provide the Retrofit for all Server API Calls.
 * Exposes public method , to get Retrofit Instance
 */
public class RetrofitAdapters {

  private static volatile RetrofitAdapters instance = null;
  private Retrofit appAdapter;
  private static final boolean DEBUG = BuildConfig.LOGGER_ENABLED;

  private static RetrofitAdapters getInstance() {
    if (instance == null) {
      synchronized (RetrofitAdapters.class) {
        if (instance == null) {
          instance = new RetrofitAdapters();
        }
      }
    }
    return instance;
  }

  /**
   * Initialization of Retrofit Adapter by Builder [URL ,OkHttp Client , Conversion Factory]
   */
  private RetrofitAdapters() {
    appAdapter = new Retrofit.Builder()
        .baseUrl(TheMovieDbConstants.APP_BASE_URL)
        .client(buildOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  private OkHttpClient buildOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    if (DEBUG) {
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    builder.interceptors().add(interceptor);
    return builder.build();
  }

  /**
   * Method to get Application Retrofit API Adapter ..
   *
   * @return -- Application Rest Adapter Instance
   */
  public static Retrofit getAppRestAdapter() {
    return getInstance().appAdapter;
  }
}


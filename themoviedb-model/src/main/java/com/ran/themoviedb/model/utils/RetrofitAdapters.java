package com.ran.themoviedb.model.utils;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Retrofit Adapter Wrapper , to provide the Retrofit for all Server API Calls.
 * Exposes public method , to get Retrofit Instance
 */
public class RetrofitAdapters {

  private static volatile RetrofitAdapters instance = null;
  private Retrofit appAdapter;
  private static final boolean DEBUG = true;

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
    OkHttpClient okHttpClient = new OkHttpClient();
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    if (DEBUG) {
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    okHttpClient.interceptors().add(interceptor);
    return okHttpClient;
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


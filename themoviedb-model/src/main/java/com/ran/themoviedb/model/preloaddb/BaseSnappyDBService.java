package com.ran.themoviedb.model.preloaddb;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;
import com.ran.themoviedb.model.utils.ApplicationUtils;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.lang.reflect.Type;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ranjith.suda on 3/5/2016.
 * <p/>
 * Snappy DB Service , which does the following in sequence
 * a) Try to Read DB with DB_TYPE key
 * b) If OK , return the Response [UI]
 * c) if not OK , write from the Preload to DB
 * <p/>
 * d) Make the Retrofit call
 * e) Write the Response back to DB , return the Response[UI]
 */
public abstract class BaseSnappyDBService<T> {

  private final String TAG = BaseSnappyDBService.class.getSimpleName();
  private DB snappyDB;
  private SnappyDBEntityTypes snappyDBEntityTypes;
  private TypeToken typeToken;
  private int uniqueId;
  private Call<T> retrofitCall;
  private Callback<T> callback = new Callback<T>() {
    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
      if (response != null) {
        if (response.isSuccess()) {
          processApiResponse(response.body());
        } else {
          Log.d(TAG, "API Response is not Success , ignore");
        }
      }
    }

    @Override
    public void onFailure(Throwable t) {
      Log.d(TAG, "API Response is a failure case , ignore");
    }
  };

  /**
   * Internal Method to process the API response and send back the Response
   *
   * @param response -- response
   */
  private void processApiResponse(T response) {
    Gson gson = new Gson();
    try {
      snappyDB.put(snappyDBEntityTypes.name(), gson.toJson(response, typeToken.getType()));
    } catch (SnappydbException exception) {
      Log.d(TAG, exception.toString());
    }
    handleApiResponse(response, uniqueId);
  }

  /**
   * Make Request for the Data , prefetch DB and then Retrofit call
   *
   * @param snappyDBEntityType -- Entity Type
   * @param preloadData        -- Preload data for the Entity Type
   * @param uniqueId           -- Unique Id of Request
   * @param typeToken          -- Type Token for Reflection
   */
  public void request(SnappyDBEntityTypes snappyDBEntityType, String preloadData, int uniqueId,
                      TypeToken typeToken) {
    this.uniqueId = uniqueId;
    this.snappyDBEntityTypes = snappyDBEntityType;
    this.typeToken = typeToken;
    try {
      snappyDB = DBFactory.open(ApplicationUtils.getApplication());
      Gson gson = new Gson();
      String dbResponse;
      if (snappyDB.exists(snappyDBEntityType.name())) {
        Log.d(TAG, "Reading from DataBase");
        dbResponse = snappyDB.get(snappyDBEntityType.name(), String.class);
      } else {
        dbResponse = preloadData;
      }
      //Creating the Response for UI ..
      T apiResponse = gson.fromJson(dbResponse, typeToken.getType());
      handleApiResponse(apiResponse, uniqueId);
    } catch (SnappydbException exception) {
      Log.d(TAG, exception.toString());
    }

    //Make the Normal Retrofit Request as Usual..
    retrofitCall = getRetrofitCall();
    retrofitCall.enqueue(callback);
  }

  /**
   * Cancel Request -- Cancel Retrofit Request
   *
   * @param uniqueId -- Id
   */
  public void cancelRequest(final int uniqueId) {
    Log.d(TAG, "Cancel Request for Base Snappy Service");
    if (this.uniqueId == uniqueId && retrofitCall != null) {
      retrofitCall.cancel();
    }
  }

  /**
   * Child Class Need to Implement this for Response on Success
   *
   * @param response -- Response of Type T
   * @param uniqueId -- Id of the Request
   */
  protected abstract void handleApiResponse(T response, int uniqueId);

  /**
   * Child Class Need to Implement for Retrofit Call implementation
   *
   * @return -- Return the Retrofit Call Interface for Execution
   */
  protected abstract Call<T> getRetrofitCall();

}

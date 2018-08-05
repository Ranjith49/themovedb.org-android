package com.ran.themoviedb.model.server.service;

import android.util.Log;

import com.google.gson.Gson;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.BaseAPIStatus;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.utils.ApplicationUtils;
import com.ran.themoviedb.model.utils.ServerUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ranjith.suda on 12/29/2015.
 * <p/>
 * Base Retrofit Service Class of Generic Type T , expecting as Response
 * Handles the Network Calls Asynchronously
 */
public abstract class BaseRetrofitService<T> {

  private final String TAG = BaseRetrofitService.class.getSimpleName();
  private int uniqueId;
  private Call<T> retrofitCall;
  private Callback<T> callback = new Callback<T>() {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
      if (response != null) {
        if (response.code() == TheMovieDbConstants.OK_HTTP_RESPONSE_CODE) {
          handleApiResponse(response.body(), uniqueId);
        } else {
          handleApiErrorCases(response);
        }
      }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
      handleError(UserAPIErrorType.UNEXPECTED_ERROR, uniqueId);
    }
  };

  private void handleApiErrorCases(Response<T> response) {
    /**
     * Handle the following cases :
     * a) If errorBody is null ,throw Unexpected Error
     * b) TypeCase to Base API Status , and pass to UI method
     *    if fails , Throw Unexpected Error
     */
    if (response.errorBody() == null) {
      handleError(UserAPIErrorType.UNEXPECTED_ERROR, uniqueId);
    } else {
      try {
        Gson gson = new Gson();
        BaseAPIStatus apiStatus = gson.fromJson(response.errorBody().string(), BaseAPIStatus.class);
        if (apiStatus != null) {
          switch (response.code()) {
            case TheMovieDbConstants.AUTH_HTTP_RESPONSE_CODE:
              handleError(UserAPIErrorType.AUTH_ERROR, uniqueId);
              break;
            default:
              handleError(UserAPIErrorType.NON_HTTP_SUCCESS_ERROR, uniqueId);
              break;
          }
        } else {
          handleError(UserAPIErrorType.UNEXPECTED_ERROR, uniqueId);
        }
      } catch (Exception exception) {
        handleError(UserAPIErrorType.UNEXPECTED_ERROR, uniqueId);
      }
    }
  }

  public void request(final int uniqueId) {
    Log.d(TAG, "Request id : " + uniqueId);
    this.uniqueId = uniqueId;
    if (ServerUtils.isConnected(ApplicationUtils.getApplication())) {
      retrofitCall = getRetrofitCall();
      retrofitCall.enqueue(callback);
    } else {
      handleError(UserAPIErrorType.NETWORK_ERROR, uniqueId);
    }
  }

  public void cancelRequest(final int uniqueId) {
    if (this.uniqueId == uniqueId && retrofitCall != null) {
      Log.d(TAG, "Cancel Request id : " + uniqueId);
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
   * Child Class Need to Implement this for Error Handling
   *
   * @param errorType -- Error of Type {@Link UserAPIErrorType}
   * @param uniqueId  -- Id of the Request
   */
  protected abstract void handleError(UserAPIErrorType errorType, int uniqueId);

  /**
   * Child Class Need to Implement for Retrofit Call implementation
   *
   * @return -- Return the Retrofit Call Interface for Execution
   */
  protected abstract Call<T> getRetrofitCall();

}

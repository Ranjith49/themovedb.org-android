package com.ran.themoviedb.model.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.BaseAPIStatus;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Response;


/**
 * Created by ranjith.suda on 12/29/2015.
 * <p/>
 * <p>
 * Base Retrofit Service Class of Generic Type T , expecting as Response
 * Handles the Network Calls Synchronously ..
 * <p>
 * Responsible of caller to do operations in BG thread ..
 */
public abstract class BaseRetrofitService<T> {

    private final String TAG = BaseRetrofitService.class.getSimpleName();

    public Observable<T> requestData() {
        if (NetworkSDK.getInstance().getIsNwConnected()) {
            return getDataObservable()
                    .map(this::getSuccessResOrThrowEx)
                    .map(this::transformResponseIfReq);
        } else {
            return throwErrorException(UserAPIErrorType.NETWORK_ERROR);
        }
    }

    /**
     * Generic Success or Error logic implementation
     *
     * @param rawResponse -- raw Response
     * @return -- Response of Type T..
     */
    private T getSuccessResOrThrowEx(Response<T> rawResponse) {
        if (rawResponse == null) {
            throw new UserAPIErrorException(UserAPIErrorType.UNEXPECTED_ERROR);
        }

        if (rawResponse.code() == TheMovieDbConstants.OK_HTTP_RESPONSE_CODE) {
            return rawResponse.body();
        } else {
            throw new UserAPIErrorException(handleApiErrorCases(rawResponse));
        }
    }

    /**
     * Handle the following Error Cases :
     * <p>
     * a) If errorBody is null ,throw Unexpected Error
     * b) TypeCase to Base API Status , and pass to UI method
     * <p>
     * if fails , Throw Unexpected Error
     */
    private UserAPIErrorType handleApiErrorCases(Response<T> response) {
        if (response.errorBody() == null) {
            return UserAPIErrorType.UNEXPECTED_ERROR;
        } else {
            try {
                Gson gson = new Gson();
                BaseAPIStatus apiStatus = gson.fromJson(response.errorBody().string(), BaseAPIStatus.class);
                if (apiStatus != null) {
                    switch (response.code()) {
                        case TheMovieDbConstants.AUTH_HTTP_RESPONSE_CODE:
                            return UserAPIErrorType.AUTH_ERROR;
                        default:
                            return UserAPIErrorType.NON_HTTP_SUCCESS_ERROR;
                    }
                } else {
                    return UserAPIErrorType.UNEXPECTED_ERROR;
                }
            } catch (JsonSyntaxException exception) {
                return UserAPIErrorType.UNEXPECTED_ERROR;
            } catch (IOException ex) {
                return UserAPIErrorType.UNEXPECTED_ERROR;
            }
        }
    }

    private Observable<T> throwErrorException(UserAPIErrorType apiErrorType) {
        return Observable.error(new UserAPIErrorException(apiErrorType));
    }


    /**
     * Child Class Need to Implement for Retrofit Call implementation
     *
     * @return -- Return the Retrofit Call Interface for Execution
     */
    protected abstract Observable<Response<T>> getDataObservable();

    /**
     * Extra transformation need to be done on req , if required
     *
     * @param sourceInput -- source input
     * @return -- transformed output or throw error , if not required
     */
    protected abstract T transformResponseIfReq(T sourceInput);

}

package com.ran.themoviedb.model.preloaddb;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.utils.ApplicationUtils;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import io.reactivex.Observable;
import retrofit2.Response;

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
    private SnappyDBEntityTypes snappyDBEntityType;
    private TypeToken typeToken;

    public BaseSnappyDBService() {
        try {
            snappyDB = DBFactory.open(ApplicationUtils.getApplication());
        } catch (SnappydbException e) {
            Log.e(TAG, "Ex " + e);
        }
    }

    public Observable<T> requestData(SnappyDBEntityTypes snappyDBEntityType, String preloadData, TypeToken typeToken) {
        this.snappyDBEntityType = snappyDBEntityType;
        this.typeToken = typeToken;

        Observable<T> dbData = getDBData(preloadData)
                .map(s -> NetworkSDK.getInstance().getGson().fromJson(s, typeToken.getType()));

        Observable<T> nwData = getResponseOfType()
                .map(this::getSuccessResOrThrowEx)
                .map(this::writeToDb);

        return Observable.concat(dbData, nwData);
    }

    private T writeToDb(T data) {
        try {
            String input = NetworkSDK.getInstance().getGson().toJson(data, typeToken.getType());
            Log.d(TAG, input);
            snappyDB.put(snappyDBEntityType.name(), input.getBytes());
        } catch (SnappydbException exception) {
            Log.d(TAG, exception.toString());
        }
        return data;
    }

    private T getSuccessResOrThrowEx(Response<T> rawResponse) {
        if (rawResponse == null) {
            throw new RuntimeException("No Response from Server");
        }

        if (rawResponse.code() == TheMovieDbConstants.OK_HTTP_RESPONSE_CODE || rawResponse.body() == null) {
            return rawResponse.body();
        } else {
            throw new RuntimeException("Not OK Response // Body is empty .. ");
        }
    }

    private Observable<String> getDBData(String preloadData) {
        if (snappyDB == null) {
            return Observable.just(preloadData);
        }

        try {
            String dbResponse;
            if (snappyDB.exists(snappyDBEntityType.name())) {
                Log.d(TAG, "Reading from DataBase");
                dbResponse = new String(snappyDB.getBytes(snappyDBEntityType.name()));
            } else {
                dbResponse = preloadData;
            }
            return Observable.just(dbResponse);
        } catch (SnappydbException ex) {
            Log.e(TAG, "snappy ex  :" + ex);
            return Observable.just(preloadData);
        }
    }


    /**
     * Child Class Need to Implement for Retrofit Call implementation
     *
     * @return -- Return the Retrofit Call Interface for Execution
     */
    protected abstract Observable<Response<T>> getResponseOfType();

}

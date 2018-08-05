package com.ran.themoviedb.model;

import android.content.Context;

import com.ran.themoviedb.model.di.DaggerNetworkComponent;
import com.ran.themoviedb.model.di.NetworkDIConstants;
import com.ran.themoviedb.model.server.api.AllGenreListAPI;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.api.MovieStoreAPI;
import com.ran.themoviedb.model.server.api.PeopleDetailsAPI;
import com.ran.themoviedb.model.server.api.PeopleStoreAPI;
import com.ran.themoviedb.model.server.api.SearchAPI;
import com.ran.themoviedb.model.server.api.TVShowStoreAPI;
import com.ran.themoviedb.model.server.api.TheMovieDbConfigAPI;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import dagger.Lazy;

/**
 * NetworkSDK , where all our required modules inject corresponding things
 *
 * @author ranjithsuda
 */

public class NetworkSDK {

    private static NetworkSDK instance;

    @Inject
    Lazy<AllGenreListAPI> genreListAPILazy;

    @Inject
    Lazy<MovieDetailsAPI> movieDetailsAPILazy;

    @Inject
    Lazy<MovieStoreAPI> movieStoreAPILazy;

    @Inject
    Lazy<PeopleDetailsAPI> peopleDetailsAPILazy;

    @Inject
    Lazy<SearchAPI> searchAPILazy;

    @Inject
    Lazy<TheMovieDbConfigAPI> theMovieDbConfigAPILazy;

    @Inject
    Lazy<TvShowDetailsAPI> tvShowDetailsAPILazy;

    @Inject
    Lazy<TVShowStoreAPI> tvShowStoreAPILazy;

    @Inject
    Lazy<PeopleStoreAPI> peopleStoreAPILazy;

    @Named(NetworkDIConstants.NETWORK_IS_CONNECTED)
    @Inject
    Provider<Boolean> isNwConnected;

    private NetworkSDK() {
        //Nothing to do ..
    }

    public static NetworkSDK getInstance() {
        if (instance == null) {
            synchronized (NetworkSDK.class) {
                if (instance == null) {
                    instance = new NetworkSDK();
                }
            }
        }
        return instance;
    }

    public void initialize(Context context) {
        DaggerNetworkComponent
                .builder()
                .setContext(context)
                .build()
                .injectTo(this);
    }

    public AllGenreListAPI getGenreListAPI() {
        return genreListAPILazy.get();
    }

    public MovieDetailsAPI getMovieDetailsAPI() {
        return movieDetailsAPILazy.get();
    }

    public MovieStoreAPI getMovieStoreAPI() {
        return movieStoreAPILazy.get();
    }

    public PeopleDetailsAPI getPeopleDetailsAPI() {
        return peopleDetailsAPILazy.get();
    }

    public SearchAPI getSearchAPI() {
        return searchAPILazy.get();
    }

    public TheMovieDbConfigAPI getTheMovieDbConfigAPI() {
        return theMovieDbConfigAPILazy.get();
    }

    public TvShowDetailsAPI getTvShowDetailsAPI() {
        return tvShowDetailsAPILazy.get();
    }

    public TVShowStoreAPI getTvShowStoreAPI() {
        return tvShowStoreAPILazy.get();
    }

    public PeopleStoreAPI getPeopleStoreAPI() {
        return peopleStoreAPILazy.get();
    }

    public Boolean getIsNwConnected() {
        return isNwConnected.get();
    }
}

package com.ran.themoviedb.model.di;

import com.ran.themoviedb.model.server.api.AllGenreListAPI;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.api.MovieStoreAPI;
import com.ran.themoviedb.model.server.api.PeopleDetailsAPI;
import com.ran.themoviedb.model.server.api.PeopleStoreAPI;
import com.ran.themoviedb.model.server.api.SearchAPI;
import com.ran.themoviedb.model.server.api.TVShowStoreAPI;
import com.ran.themoviedb.model.server.api.TheMovieDbConfigAPI;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module for Movie Details
 *
 * @author ranjithsuda
 */

@Module
public class NetworkAPIModule {

    @Provides
    @Singleton
    public MovieDetailsAPI getMovieDetailAPI(Retrofit retrofit) {
        return retrofit.create(MovieDetailsAPI.class);
    }

    @Provides
    @Singleton
    public AllGenreListAPI getGenereListAPI(Retrofit retrofit) {
        return retrofit.create(AllGenreListAPI.class);
    }

    @Provides
    @Singleton
    public MovieStoreAPI getMovieStoreAPI(Retrofit retrofit) {
        return retrofit.create(MovieStoreAPI.class);
    }

    @Provides
    @Singleton
    public PeopleDetailsAPI getPeopleDetailAPI(Retrofit retrofit) {
        return retrofit.create(PeopleDetailsAPI.class);
    }

    @Provides
    @Singleton
    public PeopleStoreAPI getPeopleStoreAPI(Retrofit retrofit) {
        return retrofit.create(PeopleStoreAPI.class);
    }

    @Provides
    @Singleton
    public SearchAPI getSearchAPI(Retrofit retrofit) {
        return retrofit.create(SearchAPI.class);
    }

    @Provides
    @Singleton
    public TheMovieDbConfigAPI getMovieDBConfigAPI(Retrofit retrofit) {
        return retrofit.create(TheMovieDbConfigAPI.class);
    }

    @Provides
    @Singleton
    public TvShowDetailsAPI getTvShowDetailsAPI(Retrofit retrofit) {
        return retrofit.create(TvShowDetailsAPI.class);
    }

    @Provides
    @Singleton
    public TVShowStoreAPI getTvShowStoreAPI(Retrofit retrofit) {
        return retrofit.create(TVShowStoreAPI.class);
    }
}

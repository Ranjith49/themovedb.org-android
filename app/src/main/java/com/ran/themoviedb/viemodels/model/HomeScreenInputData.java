package com.ran.themoviedb.viemodels.model;

import com.google.auto.value.AutoValue;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;

/**
 * Input Data for Home Screen View model
 *
 * @author ranjithsuda
 */

@AutoValue
public abstract class HomeScreenInputData {

    public static Builder builder() {
        return new AutoValue_HomeScreenInputData.Builder();
    }

    public abstract MovieStoreType movieStoreType();

    public abstract TVShowStoreType tvShowStoreType();

    public abstract PeopleStoreType peopleStoreType();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder movieStoreType(MovieStoreType movieStoreType);

        public abstract Builder tvShowStoreType(TVShowStoreType tvShowStoreType);

        public abstract Builder peopleStoreType(PeopleStoreType peopleStoreType);

        public abstract HomeScreenInputData build();

    }
}

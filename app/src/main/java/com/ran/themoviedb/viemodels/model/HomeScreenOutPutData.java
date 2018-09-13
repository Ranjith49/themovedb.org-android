package com.ran.themoviedb.viemodels.model;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;

/**
 * Output data of Home Screen View Model
 *
 * @author ranjithsuda
 */

@AutoValue
public abstract class HomeScreenOutPutData {

    public static Builder builder() {
        return new AutoValue_HomeScreenOutPutData.Builder()
                .movieUrls(new ArrayList<String>())
                .tvUrls(new ArrayList<String>())
                .peopleUrls(new ArrayList<String>());
    }

    public abstract ArrayList<String> movieUrls();

    public abstract ArrayList<String> tvUrls();

    public abstract ArrayList<String> peopleUrls();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder movieUrls(ArrayList<String> movieUrls);

        public abstract Builder tvUrls(ArrayList<String> movieUrls);

        public abstract Builder peopleUrls(ArrayList<String> movieUrls);

        public abstract HomeScreenOutPutData build();
    }

}

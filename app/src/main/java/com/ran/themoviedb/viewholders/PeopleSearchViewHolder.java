package com.ran.themoviedb.viewholders;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.listeners.StoreUpdateViewHolder;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.PeopleSearchResults;
import com.ran.themoviedb.model.server.entities.PeopleStoreKnownFor;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * View Holder for the People Store Recycler View
 */
public class PeopleSearchViewHolder extends RecyclerView.ViewHolder implements
        StoreUpdateViewHolder<PeopleSearchResults> {

    private final int INDEX_POSTER_KNOWN_FOR_SIZE = 0; //Todo [ranjith ,do better logic]
    private final int MAX_KNOWN_FOR = 3;
    private final Context activityContext;
    private final StoreClickListener storeClickListener;
    DecimalFormat df = new DecimalFormat("####0.00");
    private TextView peopleName;
    private ImageView peopleImage;
    private TextView peopleRating;
    private TextView peopleMoreInfo;
    private FloatingActionButton peopleShare;
    private View view;
    //Known For Layouts ..
    private RelativeLayout known1_Layout;
    private ImageView known1_Image;
    private TextView known1_Rating;
    private TextView known1_Name;

    private RelativeLayout known2_Layout;
    private ImageView known2_Image;
    private TextView known2_Rating;
    private TextView known2_Name;

    private RelativeLayout known3_Layout;
    private ImageView known3_Image;
    private TextView known3_Rating;
    private TextView known3_Name;


    public PeopleSearchViewHolder(View itemView, StoreClickListener storeClickListener,
                                  Context context) {
        super(itemView);
        this.activityContext = context;
        this.storeClickListener = storeClickListener;
        this.view = itemView;

        peopleImage = view.findViewById(R.id.recycler_item_image);
        peopleName = view.findViewById(R.id.recycler_item_name);
        peopleRating = view.findViewById(R.id.recycler_item_rating);
        peopleMoreInfo = view.findViewById(R.id.recycler_item_more);
        peopleShare = view.findViewById(R.id.recycler_item_share);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // get rid of margins since shadow area is now the margin
            ViewGroup.MarginLayoutParams p =
                    (ViewGroup.MarginLayoutParams) peopleShare.getLayoutParams();
            p.setMargins(0, 0, AppUiUtils.dpToPx(context, 8), 0);
            peopleShare.setLayoutParams(p);
        }
        known1_Layout = view.findViewById(R.id.recycler_knownFor_1_container);
        known1_Image = view.findViewById(R.id.recycler_knownFor_1);
        known1_Name = view.findViewById(R.id.recycler_knownFor_1_name);
        known1_Rating = view.findViewById(R.id.recycler_knownFor_1_rating);

        known2_Layout = view.findViewById(R.id.recycler_knownFor_2_container);
        known2_Image = view.findViewById(R.id.recycler_knownFor_2);
        known2_Name = view.findViewById(R.id.recycler_knownFor_2_name);
        known2_Rating = view.findViewById(R.id.recycler_knownFor_2_rating);

        known3_Layout = view.findViewById(R.id.recycler_knownFor_3_container);
        known3_Image = view.findViewById(R.id.recycler_knownFor_3);
        known3_Name = view.findViewById(R.id.recycler_knownFor_3_name);
        known3_Rating = view.findViewById(R.id.recycler_knownFor_3_rating);
    }


    @Override
    public void updateViewItem(Context context, final PeopleSearchResults item) {

        /**
         * a) update the Name
         * b) update the Rating
         * c) Click the People Image ,More Info and share..
         * d) load Image
         * e) load known For Views ..
         */
        peopleName.setText(item.getName());
        peopleRating.setText(String.valueOf(df.format(item.getPopularity())));
        peopleRating.setVisibility(View.VISIBLE);
        peopleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeClickListener.onStoreItemClick(item.getId(), item.getName(),
                        DisplayStoreType.PERSON_STORE);
            }
        });
        peopleMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeClickListener.onStoreItemClick(item.getId(), item.getName(),
                        DisplayStoreType.PERSON_STORE);
            }
        });
        peopleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeClickListener.onStoreItemShare(item.getId(), item.getName(),
                        DisplayStoreType.PERSON_STORE);
            }
        });
        loadProfileImage(item);

        //Views for Known for Views ..
        loadKnownForViews(item.getKnown_for());
    }

    /**
     * Helper Method to load the Known for Views
     */

    private void loadKnownForViews(final ArrayList<PeopleStoreKnownFor> items) {
        known1_Layout.setVisibility(View.GONE);
        known2_Layout.setVisibility(View.GONE);
        known3_Layout.setVisibility(View.GONE);

        if (items == null) {
            return;
        }

        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_KNOWN_FOR_SIZE);
        String IMAGE_BASE_URL = image_url.concat(image_url_config);

        for (int i = 0; i < items.size() && i < MAX_KNOWN_FOR; i++) {
            final int item_id = items.get(i).getId();
            final String name;
            if (!AppUiUtils.isStringEmpty(items.get(i).getTitle())) {
                name = items.get(i).getTitle();
            } else {
                name = items.get(i).getOriginal_title();
            }
            switch (i) {
                case 0:
                    known1_Name.setText(name);
                    known1_Rating.setText(String.valueOf(items.get(i).getVote_average()));
                    loadPosterMovie(IMAGE_BASE_URL, items.get(i).getPoster_path(), known1_Image);
                    known1_Layout.setVisibility(View.VISIBLE);
                    known1_Layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            storeClickListener.onStoreItemClick(item_id, name, DisplayStoreType.MOVIE_STORE);
                        }
                    });
                    break;
                case 1:
                    known2_Name.setText(name);
                    known2_Rating.setText(String.valueOf(items.get(i).getVote_average()));
                    loadPosterMovie(IMAGE_BASE_URL, items.get(i).getPoster_path(), known2_Image);
                    known2_Layout.setVisibility(View.VISIBLE);
                    known2_Layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            storeClickListener.onStoreItemClick(item_id, name, DisplayStoreType.MOVIE_STORE);
                        }
                    });
                    break;
                case 2:
                    known3_Name.setText(name);
                    known3_Rating.setText(String.valueOf(items.get(i).getVote_average()));
                    loadPosterMovie(IMAGE_BASE_URL, items.get(i).getPoster_path(), known3_Image);
                    known3_Layout.setVisibility(View.VISIBLE);
                    known3_Layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            storeClickListener.onStoreItemClick(item_id, name, DisplayStoreType.MOVIE_STORE);
                        }
                    });
                    break;
            }
        }
    }

    private void loadPosterMovie(String baseUrl, String url, ImageView imageView) {
        ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, imageView,
                ImageLoaderUtils.buildImageUrl(baseUrl, url),
                R.drawable.image_error_placeholder);
    }

    /**
     * Helper Method to Load the Image in the View ..
     */
    private void loadProfileImage(PeopleSearchResults item) {
        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config =
                imagesConfig.getProfile_sizes().get(TheMovieDbConstants.INDEX_PROFILE_SIZE);
        String IMAGE_BASE_URL = image_url.concat(image_url_config);

        ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, peopleImage,
                ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, item.getProfile_path()),
                R.drawable.image_error_placeholder);
    }
}


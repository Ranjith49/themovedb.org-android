package com.ran.themoviedb.customviews;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Home Poster Custom View , loading the Images with Delay .
 */
public class HomePosterView extends RelativeLayout {

    private final int MESSAGE_IMAGE_LOAD = 100;
    private final int INDEX_POSTER_SIZE = 0;
    ImageView imagePoster1;
    ImageView imagePoster2;
    ImageView imagePoster3;
    TextView bottomTextView;
    private Context context;
    private String IMAGE_BASE_URL;
    private Handler imageHandler;
    private ArrayList<String> posterUrls;
    private int MAX_POSTERS = 3 * 3; //columns * Each for column
    private int currentIndex = -1;

    public HomePosterView(Context context) {
        super(context);
        initView(context);
    }

    public HomePosterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomePosterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imagePoster1 = findViewById(R.id.home_poster_image_1);
        imagePoster2 = findViewById(R.id.home_poster_image_2);
        imagePoster3 = findViewById(R.id.home_poster_image_3);
        bottomTextView = findViewById(R.id.home_poster_bottom_name);
    }

    /**
     * Initialize the View
     *
     * @param context -- Context of the App
     */
    private void initView(Context context) {
        this.context = context;
        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_SIZE);
        IMAGE_BASE_URL = image_url.concat(image_url_config);
    }

    /**
     * Called from the Activity to make sure the Text is set ..
     *
     * @param text -- text to be set
     */
    public void setBottomText(String text) {
        if (bottomTextView != null) {
            bottomTextView.setText(text);
        }
    }

    /**
     * Set the Banners from the Retrofit Movies API [Popular] ..
     *
     * @param bannerUrls
     */
    public void setPosterUrls(ArrayList<String> bannerUrls) {
        this.posterUrls = bannerUrls;
        MAX_POSTERS = (bannerUrls.size() > MAX_POSTERS) ? MAX_POSTERS : bannerUrls.size();
        loadImages();

        //Initialize the Handler here for the Change in banner for every 5 seconds ..
        imageHandler = new ImageHandler();
        imageHandler.sendEmptyMessageDelayed(MESSAGE_IMAGE_LOAD,
                TheMovieDbConstants.HOME_POSTER_MILLS_SECS);
    }

    private void loadImages() {
        //a) Image Poster 1 View
        currentIndex = (++currentIndex) % MAX_POSTERS; //Todo [Ranjith ,Better logic ]
        ImageLoaderUtils.loadImageWithPlaceHolder(context, imagePoster1,
                ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, posterUrls.get(currentIndex)),
                R.drawable.image_error_placeholder);

        //b) Image Poster 2 View
        currentIndex = (++currentIndex) % MAX_POSTERS; //Todo [Ranjith ,Better logic ]
        ImageLoaderUtils.loadImageWithPlaceHolder(context, imagePoster2,
                ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, posterUrls.get(currentIndex)),
                R.drawable.image_error_placeholder);

        //c) Image Poster 3 View
        currentIndex = (++currentIndex) % MAX_POSTERS; //Todo [Ranjith ,Better logic ]
        ImageLoaderUtils.loadImageWithPlaceHolder(context, imagePoster3,
                ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, posterUrls.get(currentIndex)),
                R.drawable.image_error_placeholder);
    }

    /**
     * Called from Activity to set the View Visible state
     */
    public void onViewVisible() {
        if (imageHandler != null) {
            if (imageHandler.hasMessages(MESSAGE_IMAGE_LOAD)) {
                imageHandler.removeMessages(MESSAGE_IMAGE_LOAD);
            }
            imageHandler.sendEmptyMessageDelayed(MESSAGE_IMAGE_LOAD,
                    TheMovieDbConstants.HOME_POSTER_MILLS_SECS);
        }
    }

    /**
     * Called from the Activity , to set the View Hidden State
     */
    public void onViewHidden() {
        if (imageHandler != null && imageHandler.hasMessages(MESSAGE_IMAGE_LOAD)) {
            imageHandler.removeMessages(MESSAGE_IMAGE_LOAD);
        }
    }

    public void resetHandler() {
        if (imageHandler != null) {
            imageHandler = null;
        }
    }

    private class ImageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MESSAGE_IMAGE_LOAD:
                    loadImages();
                    //Send to load again
                    imageHandler.sendEmptyMessageDelayed(MESSAGE_IMAGE_LOAD,
                            TheMovieDbConstants.HOME_POSTER_MILLS_SECS);
                    break;
            }
        }
    }
}

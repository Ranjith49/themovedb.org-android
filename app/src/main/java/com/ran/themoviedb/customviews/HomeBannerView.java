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
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class HomeBannerView extends RelativeLayout {

  ImageView imageBanner;
  TextView textBottom;
  private Context context;
  private String IMAGE_BASE_URL;
  private Handler imageHandler;
  private ArrayList<String> bannerUrls;

  private final int TIME_IN_MILLIS_DIFF = 5000;
  private final int MESSAGE_IMAGE_LOAD = 100;
  private final int INDEX_BACK_DROP_SIZE = 0;
  private int MAX_BAN = 5;
  private int currentIndex = -1;

  private class ImageHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);

      switch (msg.what) {
        case MESSAGE_IMAGE_LOAD:
          currentIndex = (++currentIndex) % MAX_BAN; //Todo [Ranjith ,Better logic ]
          ImageLoaderUtils.loadImageWithPlaceHolder(context, imageBanner,
              ImageLoaderUtils.getImageUrl(IMAGE_BASE_URL, bannerUrls.get(currentIndex)),
              R.drawable.image_error_placeholder);

          //Send to load again
          imageHandler.sendEmptyMessageDelayed(MESSAGE_IMAGE_LOAD, TIME_IN_MILLIS_DIFF);
          break;
      }
    }
  }

  public HomeBannerView(Context context) {
    super(context);
    initView(context);
  }

  public HomeBannerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context);
  }

  public HomeBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }


  /**
   * Initialize the View
   *
   * @param context -- Context of the App
   */
  private void initView(Context context) {
    this.context = context;
    String image_pref_json = AppSharedPreferences.getInstance(context).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getBackdrop_sizes().get(INDEX_BACK_DROP_SIZE);
    IMAGE_BASE_URL = image_url.concat(image_url_config);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    imageBanner = (ImageView) findViewById(R.id.home_banner_image);
    textBottom = (TextView) findViewById(R.id.home_banner_bottom_name);
  }

  /**
   * Called from the Activity to make sure the Text is set ..
   *
   * @param text -- text to be set
   */
  public void setBottomText(String text) {
    if (textBottom != null) {
      textBottom.setText(text);
    }
  }

  /**
   * Set the Banners from the Retrofit Movies API [Popular] ..
   *
   * @param bannerUrls
   */
  public void setBannerUrls(ArrayList<String> bannerUrls) {
    this.bannerUrls = bannerUrls;
    MAX_BAN = (bannerUrls.size() > MAX_BAN) ? MAX_BAN : bannerUrls.size();
    currentIndex = (++currentIndex) % MAX_BAN; //Todo [Ranjith ,Better logic ]
    ImageLoaderUtils.loadImageWithPlaceHolder(context, imageBanner,
        ImageLoaderUtils.getImageUrl(IMAGE_BASE_URL, bannerUrls.get(currentIndex)),
        R.drawable.image_error_placeholder);

    //Initialize the Handler here for the Change in banner for every 5 seconds ..
    imageHandler = new ImageHandler();
    imageHandler.sendEmptyMessageDelayed(MESSAGE_IMAGE_LOAD, TIME_IN_MILLIS_DIFF);
  }

  /**
   * Called from Activity to set the View Visible state
   */
  public void onViewVisible() {
    if (imageHandler != null) {
      if (imageHandler.hasMessages(MESSAGE_IMAGE_LOAD)) {
        imageHandler.removeMessages(MESSAGE_IMAGE_LOAD);
      }
      imageHandler.sendEmptyMessageDelayed(MESSAGE_IMAGE_LOAD, TIME_IN_MILLIS_DIFF);
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

}

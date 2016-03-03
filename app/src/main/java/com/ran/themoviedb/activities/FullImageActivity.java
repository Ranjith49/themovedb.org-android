package com.ran.themoviedb.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ran.themoviedb.R;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.FullImagePopupCreator;
import com.ran.themoviedb.utils.ImageDownloadUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ranjith.suda on 3/2/2016.
 * <p/>
 * Activity responsible for showing the Full Image of the Poster / Banner
 * Options are i)Download ii) Back View
 */
public class FullImageActivity extends Activity implements PhotoViewAttacher.OnPhotoTapListener {

  private final String TAG = FullImageActivity.class.getSimpleName();
  private Context context;
  RelativeLayout topContainer;
  ImageView backButton;
  ImageView fullImage;
  Button downloadButton;
  TextView detailsView;
  ProgressBar imageDownloadProgressBar;

  PhotoViewAttacher mAttacher;
  private final TopBottomViewHandler mHandler = new TopBottomViewHandler();
  private final int HIDE_MESSAGE = 101;
  private final int HIDE_AFTER_DURATION = 5000;
  private final int START_INDEX = 1;
  private String imageType = TheMovieDbConstants.IMAGE_BANNER_TYPE;
  private String imageBaseUrl = TheMovieDbConstants.EMPTY_STRING;
  private String imageEndPath = TheMovieDbConstants.EMPTY_STRING;

  //Info for the Bitmap Data ..
  private String bitmap_resolution;
  private String bitmap_size;
  private String bitmap_file_name;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    context = this;
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_full_image_ui);

    initView();
    if (getIntent().hasExtra(TheMovieDbConstants.IMAGE_TYPE_KEY)) {
      imageType = getIntent().getStringExtra(TheMovieDbConstants.IMAGE_TYPE_KEY);
    }
    if (getIntent().hasExtra(TheMovieDbConstants.IMAGE_URL_KEY)) {
      imageEndPath = getIntent().getStringExtra(TheMovieDbConstants.IMAGE_URL_KEY);
      bitmap_file_name = imageEndPath.substring(START_INDEX);
      imageBaseUrl = ImageLoaderUtils.generateOrgImageBaseUrl(this, imageType);
      Glide.with(this)
          .load(ImageLoaderUtils.buildImageUrl(imageBaseUrl, imageEndPath)).asBitmap()
          .animate(android.R.anim.fade_in) //Smooth Transition
          .error(R.drawable.image_error_placeholder)
          .listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target,
                                       boolean isFirstResource) {
              Log.d(TAG, "There is an exception in loading image");
              Toast.makeText(context, R.string.image_load_issue, Toast.LENGTH_SHORT).show();
              finish();
              return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target,
                                           boolean isFromMemoryCache, boolean isFirstResource) {
              bitmap_resolution = resource.getWidth() + TheMovieDbConstants.MULTIPLY_STRING
                  + resource.getHeight();
              bitmap_size = AppUiUtils.getUnitSize(resource.getByteCount());
              Log.d(TAG, "resolution : " + bitmap_resolution + " size : " + bitmap_size);
              imageDownloadProgressBar.setVisibility(View.GONE);
              initialisePhotoView();
              fullImage.setVisibility(View.VISIBLE);
              return false;
            }
          })
          .into(fullImage);
    } else {
      Log.d(TAG, "There is no url to process");
      Toast.makeText(context, R.string.image_load_issue, Toast.LENGTH_SHORT).show();
      finish();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (mAttacher != null) {
      mAttacher.cleanup();
    }
  }

  private void initView() {
    backButton = (ImageView) findViewById(R.id.full_image_back);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    fullImage = (ImageView) findViewById(R.id.full_image_ui);
    downloadButton = (Button) findViewById(R.id.full_image_download);
    downloadButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ImageDownloadUtils.startDownload(
            ImageLoaderUtils.buildImageUrl(imageBaseUrl, imageEndPath));
      }
    });
    imageDownloadProgressBar = (ProgressBar) findViewById(R.id.full_image_progressBar);
    topContainer = (RelativeLayout) findViewById(R.id.full_image_top_container);
    detailsView = (TextView) findViewById(R.id.full_image_details);
    detailsView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String data[] = new String[3];
        data[0] = getResources().getString(R.string.image_name) + TheMovieDbConstants
            .SPACE_STRING + bitmap_file_name;
        data[1] = getResources().getString(R.string.image_resolution) + TheMovieDbConstants
            .SPACE_STRING + bitmap_resolution;
        data[2] = getResources().getString(R.string.image_width) + TheMovieDbConstants
            .SPACE_STRING + bitmap_size;
        FullImagePopupCreator.showPopupWindow(context, data, detailsView);
      }
    });
  }

  private void initialisePhotoView() {
    mAttacher = new PhotoViewAttacher(fullImage);
    mAttacher.setScaleType(ImageView.ScaleType.CENTER);
    mAttacher.setZoomable(true);
    mAttacher.setOnPhotoTapListener(this);
  }

  @Override
  public void onPhotoTap(View view, float x, float y) {
    if (mHandler.hasMessages(HIDE_MESSAGE)) {
      mHandler.removeMessages(HIDE_MESSAGE);
    }

    //Make the View visible..
    topContainer.setVisibility(View.VISIBLE);
    downloadButton.setVisibility(View.VISIBLE);

    //Post message for removal
    mHandler.sendEmptyMessageDelayed(HIDE_MESSAGE, HIDE_AFTER_DURATION);
  }

  /**
   * Inner class responsible for Showing the Top and Bottom Views..
   */
  private class TopBottomViewHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case HIDE_MESSAGE:
          topContainer.setVisibility(View.GONE);
          downloadButton.setVisibility(View.GONE);
          FullImagePopupCreator.hidePopupWindow();
          break;
        default:
          super.handleMessage(msg);
          break;
      }
    }
  }
}

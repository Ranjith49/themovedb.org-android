package com.ran.themoviedb.adapters;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.ImageDetails;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageDownloadUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;
import com.ran.themoviedb.utils.Navigator;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 * <p/>
 * Image Details Adapter with support for Poster and Banner .
 */
public class ImageDetailAdapter extends RecyclerView.Adapter {

  private final boolean isPoster;
  private ArrayList<ImageDetails> imageDetails;
  private Context context;
  private String baseUrl;
  private String imageType;

  public ImageDetailAdapter(boolean isPoster, ArrayList<ImageDetails> imageDetails, Context context,
                            String baseUrl) {
    this.isPoster = isPoster;
    this.imageDetails = imageDetails;
    this.context = context;
    this.baseUrl = baseUrl;
    if (isPoster) {
      imageType = TheMovieDbConstants.IMAGE_POSTER_TYPE;
    } else {
      imageType = TheMovieDbConstants.IMAGE_BANNER_TYPE;
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (isPoster) {
      return new ImageHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.recycler_image_poster_item, parent, false));
    } else {
      return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
          .recycler_image_banner_item, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    ImageHolder imageHolder = (ImageHolder) holder;
    imageHolder.image_view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigator.navigateToFullImageScreen(context, imageDetails.get(position).getFile_path(),
            imageType);
      }
    });

    ImageLoaderUtils.loadImageWithPlaceHolder(context, imageHolder.image_view,
        ImageLoaderUtils.buildImageUrl(baseUrl, imageDetails.get(position).getFile_path()),
        R.drawable.image_error_placeholder);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // get rid of margins since shadow area is now the margin
      ViewGroup.MarginLayoutParams p =
          (ViewGroup.MarginLayoutParams) imageHolder.image_download.getLayoutParams();
      p.setMargins(0, 0, AppUiUtils.dpToPx(context, 2), 0);
      imageHolder.image_download.setLayoutParams(p);
    }
    imageHolder.image_download.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String baseUrl = ImageLoaderUtils.generateOrgImageBaseUrl(context, imageType);
        ImageDownloadUtils.startDownload(
            ImageLoaderUtils.buildImageUrl(baseUrl, imageDetails.get(position).getFile_path()));
      }
    });
  }

  @Override
  public int getItemCount() {
    return imageDetails.size();
  }

  private class ImageHolder extends RecyclerView.ViewHolder {

    private ImageView image_view;
    private FloatingActionButton image_download;

    public ImageHolder(View itemView) {
      super(itemView);
      image_view = (ImageView) itemView.findViewById(R.id.image_item_view);
      image_download = (FloatingActionButton) itemView.findViewById(R.id.image_item_download);
    }
  }
}

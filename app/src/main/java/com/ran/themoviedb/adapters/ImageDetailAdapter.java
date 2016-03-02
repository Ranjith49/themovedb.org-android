package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.server.entities.ImageDetails;
import com.ran.themoviedb.utils.ImageLoaderUtils;
import com.ran.themoviedb.utils.Navigator;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class ImageDetailAdapter extends RecyclerView.Adapter {

  private final boolean isPoster;
  private ArrayList<ImageDetails> imageDetails;
  private Context context;
  private String baseUrl;

  public ImageDetailAdapter(boolean isPoster, ArrayList<ImageDetails> imageDetails, Context context,
                            String baseUrl) {
    this.isPoster = isPoster;
    this.imageDetails = imageDetails;
    this.context = context;
    this.baseUrl = baseUrl;
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
        Navigator.navigateToFullImageScreen(context, imageDetails.get(position).getFile_path());
      }
    });
    imageHolder.image_download.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(context, "Download click", Toast.LENGTH_SHORT).show();
      }
    });

    ImageLoaderUtils.loadImageWithPlaceHolder(context, imageHolder.image_view,
        ImageLoaderUtils.getImageUrl(baseUrl, imageDetails.get(position).getFile_path()),
        R.drawable.image_error_placeholder);
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

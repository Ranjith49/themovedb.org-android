package com.ran.themoviedb.utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

import com.ran.themoviedb.R;
import com.ran.themoviedb.listeners.VideoPopupClickListener;
import com.ran.themoviedb.model.server.entities.VideoDetails;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 * <p/>
 * Helper class for creating the Video Popup ,
 * -- List Video popup created with video details
 */
public class VideoPopupCreator {

  private static ListPopupWindow listPopupWindow;

  /**
   * @param context
   * @param videoDetails
   * @param listener
   * @param anchorView
   */
  public static void createListPopupWindow(Context context,
                                           final ArrayList<VideoDetails> videoDetails,
                                           final VideoPopupClickListener listener,
                                           View anchorView) {
    listPopupWindow = new ListPopupWindow(context);
    String list_data[] = new String[videoDetails.size()];

    for (int i = 0; i < videoDetails.size(); i++) {
      list_data[i] = videoDetails.get(i).getName();
    }

    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list_data);

    listPopupWindow.setAdapter(adapter);
    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onVideoClickItem(videoDetails.get(position).getId(),
            videoDetails.get(position).getKey());
      }
    });
    listPopupWindow.setWidth(
        context.getResources().getDimensionPixelSize(R.dimen.video_popup_width));
    listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
    listPopupWindow.setAnchorView(anchorView);
    listPopupWindow.setModal(true);
    listPopupWindow.show();
  }


}

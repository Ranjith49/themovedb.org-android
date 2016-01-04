package com.ran.themoviedb.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p>
 * Utility used to create the Generic Error Layout based on
 * a) {@link com.ran.themoviedb.entities.GenericUIErrorLayoutType}
 * b) {@link com.ran.themoviedb.model.server.entities.UserAPIErrorType}
 */
public class GenericErrorBuilder {

  private Context context;
  private GenericUIErrorLayoutType errorLayoutType;
  private ViewGroup viewGroup;
  private Handler handler;

  private Button errorButton;
  private TextView errorMessage;
  private ImageView errorImage;

  /**
   * Constructor to Initialize the Generic Error Builder
   *
   * @param context         -- Context of the APp
   * @param errorLayoutType -- Error Layout Type
   * @param container       -- Container holding the Error layout
   * @param handler         -- Call back for Refresh Click
   */
  public GenericErrorBuilder(Context context, GenericUIErrorLayoutType errorLayoutType,
                             ViewGroup container, Handler handler) {
    this.context = context;
    this.errorLayoutType = errorLayoutType;
    this.viewGroup = container;
    this.handler = handler;
    initView();
  }

  /**
   * Init the view accordingly
   */
  private void initView() {
    viewGroup.removeAllViews();
    View errorLayout;
    switch (errorLayoutType) {
      case CENTER:
        errorLayout =
            LayoutInflater.from(context).inflate(R.layout.error_layout_generic, viewGroup, false);
        break;
      default:
        errorLayout =
            LayoutInflater.from(context).inflate(R.layout.error_layout_generic, viewGroup, false);
        break;
    }
    errorButton = (Button) errorLayout.findViewById(R.id.error_button_view);
    errorButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        handler.onRefreshClicked();
        updateVisibilities(false);
      }
    });
    errorImage = (ImageView) errorLayout.findViewById(R.id.error_image_view);
    errorMessage = (TextView) errorLayout.findViewById(R.id.error_textView);
    viewGroup.addView(errorLayout);
  }

  /**
   * Method to be used to set the UserAPI Error from the Retrofit calls
   *
   * @param userAPIErrorType -- UserAPIErrorType
   */
  public void setUserAPIError(UserAPIErrorType userAPIErrorType) {
    switch (userAPIErrorType) {
      case AUTH_ERROR:
        errorMessage.setText(R.string.error_auth_failure);
        break;
      case NON_HTTP_SUCCESS_ERROR:
        errorMessage.setText(R.string.error_server_failure);
        break;
      case NETWORK_ERROR:
        errorMessage.setText(R.string.error_no_internet_connection);
        break;
      case UNEXPECTED_ERROR:
        errorMessage.setText(R.string.error_unexpected_error);
        break;
    }
    updateVisibilities(true);
  }

  private void updateVisibilities(boolean visible) {
    if (visible) {
      viewGroup.setVisibility(View.VISIBLE);
    } else {
      viewGroup.setVisibility(View.GONE);
    }
  }

  public interface Handler {
    void onRefreshClicked();
  }
}

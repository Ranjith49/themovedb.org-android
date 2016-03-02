package com.ran.themoviedb.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.utils.AppUiUtils;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Custom Recycler View Responsible for showing the LINEAR and GRID Type layouts
 * Handles the Response from Retrofit
 * Swipe from Top is handled by it..
 */
public class CustomRecyclerView extends RelativeLayout {

  private static final String TAG = CustomRecyclerView.class.getSimpleName();
  private LinearLayout recycler_Container;
  private RelativeLayout error_BottomContainer;
  private ProgressBar bottom_ProgressBar;
  private FloatingActionButton button_move_Up;
  private RecyclerView recyclerView;
  private TextView bottom_error_message;
  private Button button_refresh;

  private final int TOP_POSITION = 0;
  private Adapter adapter;
  private SwipeRefreshLayout swipeRefreshLayout;
  private ScrollListener scrollListener;
  private RecyclerView.LayoutManager layoutManager;

  private int height_container = 0;
  private int height_item_recyclerView = 0;

  public CustomRecyclerView(Context context) {
    super(context);
    initView(context);
  }

  public CustomRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context);
  }

  public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initView(context);
  }

  private void initView(Context context) {
    final RelativeLayout root = (RelativeLayout) LayoutInflater.from(getContext()).inflate(
        R.layout.recycler_custom_view, this, true);
    recycler_Container = (LinearLayout) root.findViewById(R.id.recycler_content_view);
    error_BottomContainer =
        (RelativeLayout) root.findViewById(R.id.recycler_error_bottom_refresh_layout);
    bottom_ProgressBar = (ProgressBar) root.findViewById(R.id.recycler_footer_progress);
    recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
    scrollListener = new ScrollListener(this);
    recyclerView.addOnScrollListener(scrollListener);

    swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.color_Red_Accent, R
        .color.color_Green_Accent);
    swipeRefreshLayout.setOnRefreshListener(new RefreshListener(this));

    bottom_error_message = (TextView) root.findViewById(R.id.recycler_error_textView);
    button_refresh = (Button) root.findViewById(R.id.recycler_error_button_view);
    button_move_Up = (FloatingActionButton) root.findViewById(R.id.recycler_top_go_view);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // get rid of margins since shadow area is now the margin
      ViewGroup.MarginLayoutParams p =
          (ViewGroup.MarginLayoutParams) button_move_Up.getLayoutParams();
      p.setMargins(0, 0, AppUiUtils.dpToPx(context, 8), 0);
      button_move_Up.setLayoutParams(p);
    }

    button_refresh.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        showBottomErrorMessage(null, false);
        adapter.loadNextPageIndex(adapter.nextPathIndex);
      }
    });

    button_move_Up.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        recyclerView.smoothScrollToPosition(TOP_POSITION);
      }
    });

    height_container = AppUiUtils.getScreenDimens(context)[1];
  }

  /**
   * Set the Adapter here , after doing the Initialization..
   *
   * @param adapter
   */
  public void setAdapter(final Adapter adapter) {
    Log.d(TAG, "set Adapter");
    this.adapter = adapter;
    adapter.loadFirstPageIndex(adapter.firstPathIndex);
    scrollListener.reset();
    recyclerView.setAdapter(adapter);
  }

  /**
   * Set the Layout Manager here , [Linear Layout Vertical / Grid Layout with Span count 2]
   *
   * @param layoutManager
   */
  public void setLayoutManager(RecyclerView.LayoutManager layoutManager, int item_height_recycler) {
    Log.d(TAG, "set Layout Manager");
    this.layoutManager = layoutManager;
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);
    height_item_recyclerView = item_height_recycler;
  }

  /**
   * Call Back from the Activity /Fragment/Adapter to show the BottomError Messgae -->
   *
   * @param errorType -- Error Message to be shown
   * @param visible   -- Visibility Params
   */
  public void showBottomErrorMessage(UserAPIErrorType errorType, boolean visible) {
    if (visible) {
      switch (errorType) {
        case NETWORK_ERROR:
          bottom_error_message.setText(R.string.error_no_internet_connection);
          break;
        case NON_HTTP_SUCCESS_ERROR:
          bottom_error_message.setText(R.string.error_server_failure);
          break;
        case UNEXPECTED_ERROR:
        default:
          bottom_error_message.setText(R.string.error_unexpected_error);
          break;
      }
      error_BottomContainer.setVisibility(VISIBLE);
    } else {
      error_BottomContainer.setVisibility(GONE);
    }


  }

  /**
   * Call Backs from the Activity /Fragment /Adapter to show the Bottom Progress Bar -->
   *
   * @param visible -- Visibility of View
   */
  public void showBottomProgressBar(boolean visible) {
    if (visible) {
      bottom_ProgressBar.setVisibility(VISIBLE);
    } else {
      bottom_ProgressBar.setVisibility(GONE);
    }
  }

  /**
   * Call Back from Activity /Fragment /Adapter saying about the Refresh Indicator -->
   *
   * @param visible -- Refreshing or not ..
   */
  public void updateRefreshIndicator(boolean visible) {
    swipeRefreshLayout.setRefreshing(visible);
  }

  /**
   * Call Back from the Activity /Fragment /Adapter saying when to show the Recycler Container -->
   *
   * @param visible -- Visiblity of View ..
   */
  public void showRecyclerViewContainer(boolean visible) {
    if (visible) {
      recycler_Container.setVisibility(VISIBLE);
    } else {
      recycler_Container.setVisibility(GONE);
    }
  }

  // --------------  Inner StaticClasses ofOur CustomView  ---------------------- //

  //Recycler View Adapter , Need to be implemented by the Fragment ,Provides call backs
  // a) load First Page Index
  // b) load Next Page Index call backs
  public static abstract class Adapter<RESPONSE> extends RecyclerView.Adapter {

    private int firstPathIndex;
    private int nextPathIndex = -1;

    public Adapter(Context context, int firstPath) {
      this.firstPathIndex = firstPath;
    }

    public void setNextPageIndex(int nextPageIndex) {
      this.nextPathIndex = nextPageIndex;
    }

    public abstract void loadFirstPageIndex(int firstPageIndex);

    public abstract void loadNextPageIndex(int nextPageIndex);

  }

  // Refresh Listener Used to Monitor Swipe to refresh , makes following decisions
  // a) When to load the First Page and How ..
  private static class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

    private final CustomRecyclerView customRecyclerView;

    public RefreshListener(CustomRecyclerView customRecyclerView) {
      this.customRecyclerView = customRecyclerView;
    }

    @Override
    public void onRefresh() {
      customRecyclerView.button_move_Up.setVisibility(GONE); // Reset the Up Indicator
      customRecyclerView.scrollListener.reset(); // Reset the Scroll Listener
      customRecyclerView.showBottomProgressBar(false); // Remove any Bottom progress , if any
      customRecyclerView.showRecyclerViewContainer(false); // Remove View container completely
      customRecyclerView.showBottomErrorMessage(null, false); //Remove Bottom Error
      customRecyclerView.adapter.loadFirstPageIndex(customRecyclerView.adapter.firstPathIndex);
    }
  }

  // Scroll Listener Used to Monitor Scroll , makes following decisions
  // a) Go to Top View
  // b) When to load the next page URL ..
  private static class ScrollListener extends RecyclerView.OnScrollListener {

    final CustomRecyclerView customRecyclerView;
    private boolean nextLoading = false;
    private int previousTotal = 0;
    private int totalItemCount = 0;
    private int lastVisibleItemIndex = -1;

    public ScrollListener(CustomRecyclerView customRecyclerView) {
      this.customRecyclerView = customRecyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      onListUpdate();
    }

    private void reset() {
      totalItemCount = 0;
      previousTotal = 0;
      nextLoading = false;
    }

    private void onListUpdate() {
      totalItemCount = customRecyclerView.adapter.getItemCount();
      lastVisibleItemIndex = findLastVisibleItemPosition();
      Log.d(TAG, "total Item count :" + totalItemCount + " lastVisible Item index " + ":" +
          lastVisibleItemIndex);
      /**
       * a) Update the show Go Top indicator [Handled Linear /Grid Only]
       * b) Return if no Next Path ..
       * c) if nextLoading , make sure to Reset it
       * d) when to fire the nextPageIndex ..
       */
      int height_drawn = 0;
      if (customRecyclerView.layoutManager instanceof GridLayoutManager) {
        height_drawn = (lastVisibleItemIndex / TheMovieDbConstants.GRID_SPAN_COUNT)
            * customRecyclerView.height_item_recyclerView;
      } else if (customRecyclerView.layoutManager instanceof LinearLayoutManager) {
        height_drawn = lastVisibleItemIndex * customRecyclerView.height_item_recyclerView;
      }

      if (height_drawn > customRecyclerView.height_container) {
        customRecyclerView.button_move_Up.setVisibility(VISIBLE);
      } else {
        customRecyclerView.button_move_Up.setVisibility(GONE);
      }

      if (customRecyclerView.adapter.nextPathIndex == -1) {
        Log.d(TAG, "Next Path Index is invalid");
        return;
      }

      if (nextLoading) {
        if (totalItemCount > previousTotal) {
          nextLoading = false;
          previousTotal = totalItemCount;
        }
      }

      if (!customRecyclerView.recyclerView.canScrollVertically(1) && !nextLoading) {
        customRecyclerView.adapter.loadNextPageIndex(customRecyclerView.adapter.nextPathIndex);
        previousTotal = totalItemCount;
        nextLoading = true;
      }
    }


    /**
     * Method to give the Last Visible Item from layout Manager [GRID /Linear] to show go to Top
     * View
     *
     * @return -- last Visible item Position
     */
    private int findLastVisibleItemPosition() {
      if (customRecyclerView.layoutManager instanceof GridLayoutManager ||
          customRecyclerView.layoutManager instanceof LinearLayoutManager) {
        return ((LinearLayoutManager) customRecyclerView.layoutManager).findLastVisibleItemPosition();
      }
      return -1;
    }
  }
}

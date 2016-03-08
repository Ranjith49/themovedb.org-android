package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.listeners.ReviewClickListener;
import com.ran.themoviedb.model.server.entities.ReviewsDetail;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.presenters.MovieReviewPresenter;
import com.ran.themoviedb.view_pres_med.MovieReviewView;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieReviewRecyclerAdapter extends CustomRecyclerView.Adapter<ReviewsDetailResponse> {


  private ArrayList<ReviewsDetail> reviewsDetails = new ArrayList<>();
  private final MovieReviewView movieReviewView;
  private final int INVALID_NEXT_PAGE_INDEX = -1;
  private MovieReviewPresenter movieReviewPresenter;
  private Context context;
  private ReviewClickListener reviewClickListener;
  private final int movieId;

  public MovieReviewRecyclerAdapter(Context context, MovieReviewView movieReviewView, int firstPath,
                                    ReviewClickListener reviewClickListener, int movieId) {
    super(context, firstPath);
    this.movieReviewView = movieReviewView;
    this.reviewClickListener = reviewClickListener;
    this.movieId = movieId;
  }

  public void onDestroyView() {
    //Make any final Calls to be stopped here ..
    if (movieReviewPresenter != null) {
      movieReviewPresenter.stop();
    }
  }

  public void addMovieReviews(ArrayList<ReviewsDetail> response, int currentPage,
                              int totalPages) {
    if (currentPage < totalPages) {
      setNextPageIndex(++currentPage);
    } else {
      setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
    }
    reviewsDetails.addAll(response);
    notifyDataSetChanged();
  }

  @Override
  public void loadFirstPageIndex(int firstPageIndex) {
    reviewsDetails.clear();
    notifyDataSetChanged();

    //Start the Presenter , for First Page
    movieReviewPresenter = new MovieReviewPresenter(context, movieReviewView,
        firstPageIndex, MovieStoreRecyclerAdapter.class.hashCode(), movieId);
    movieReviewPresenter.start();
  }

  @Override
  public void loadNextPageIndex(int nextPageIndex) {
    movieReviewPresenter = new MovieReviewPresenter(context, movieReviewView,
        nextPageIndex, MovieStoreRecyclerAdapter.class.hashCode(), movieId);
    movieReviewPresenter.start();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ReviewViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review_itam, parent, false));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

    final ReviewViewHolder holder_review = (ReviewViewHolder) holder;
    holder_review.review.setText(reviewsDetails.get(position).getContent());
    holder_review.author.setText(reviewsDetails.get(position).getAuthor());
    holder_review.readMore.setVisibility(View.GONE);

    ViewTreeObserver viewTreeObserver = holder_review.review.getViewTreeObserver();
    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        ViewTreeObserver obs = holder_review.review.getViewTreeObserver();
        obs.removeGlobalOnLayoutListener(this);
        //TODO [ranjith , better logic]
        if (holder_review.review.getLineCount() >= 8) {
          holder_review.readMore.setVisibility(View.VISIBLE);
          holder_review.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              reviewClickListener.onReviewClick(reviewsDetails.get(position).getId(),
                  reviewsDetails.get(position).getUrl());
            }
          });
        }
      }
    });


  }

  @Override
  public int getItemCount() {
    return reviewsDetails.size();
  }

  private class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView author;
    private TextView review;
    private TextView readMore;

    public ReviewViewHolder(View itemView) {
      super(itemView);
      author = (TextView) itemView.findViewById(R.id.recycler_review_author);
      review = (TextView) itemView.findViewById(R.id.recycler_review_text);
      readMore = (TextView) itemView.findViewById(R.id.recycler_review_readmore);
    }
  }
}

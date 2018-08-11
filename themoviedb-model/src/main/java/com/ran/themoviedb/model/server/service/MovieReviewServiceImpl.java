package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * MovieReview Service Impl
 *
 * @author ranjithsuda
 */
public class MovieReviewServiceImpl extends BaseRetrofitService<ReviewsDetailResponse> {

    private int movieId;
    private String movieLang;
    private int pageIndex;

    public MovieReviewServiceImpl(int id, String lang, int pageIndex) {
        this.movieId = id;
        this.movieLang = lang;
        this.pageIndex = pageIndex;
    }


    @Override
    protected Observable<Response<ReviewsDetailResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getMovieDetailsAPI()
                .getReviewDetails(movieId, pageIndex, TheMovieDbConstants.APP_API_KEY, movieLang);
    }

    @Override
    protected ReviewsDetailResponse transformResponseIfReq(ReviewsDetailResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}

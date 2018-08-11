package com.ran.themoviedb.model.server.exception;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;

/**
 * User API Error type enclosed in an exception for Rx
 *
 * @author ranjithsuda
 */

public class UserAPIErrorException extends RuntimeException {

    private final UserAPIErrorType apiErrorType;

    public UserAPIErrorException(UserAPIErrorType userAPIErrorType) {
        this.apiErrorType = userAPIErrorType;
    }

    public UserAPIErrorType getApiErrorType() {
        return apiErrorType;
    }

}

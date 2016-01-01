package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 12/30/2015.
 *
 * User UI Error Messages shown to User on Errors
 */
public enum UserAPIErrorType {

  NETWORK_ERROR("network_error"), // Network Connection Error
  NON_HTTP_SUCCESS_ERROR("http_error"), // Non - 200 and ! 401 Error
  AUTH_ERROR("auth_error"), //401 Error
  UNEXPECTED_ERROR("unexpected_error"); // Any Unknown Error

  String name;

  UserAPIErrorType(String name) {
    this.name = name;
  }

  public static UserAPIErrorType fromName(String name) {
    for (UserAPIErrorType type : UserAPIErrorType.values()) {
      if (type.name.equalsIgnoreCase(name)) {
        return type;
      }
    }
    return UNEXPECTED_ERROR;
  }

  public String getName() {
    return name;
  }
}

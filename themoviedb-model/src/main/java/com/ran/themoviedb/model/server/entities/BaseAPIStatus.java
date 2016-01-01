package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 12/29/2015.
 * <p>
 * Status Messages received from API for Rest API calls
 * https://www.themoviedb.org/documentation/api/status-codes
 * <p>
 * Retrofit tries TypeCast the Non Success HTTP to this Class , and send to UI
 */
public class BaseAPIStatus implements Serializable {

  private static final long serialVersionUID = 11234567L;
  private int status_code;
  private String status_message;

  public int getStatus_code() {
    return status_code;
  }

  public void setStatus_code(int status_code) {
    this.status_code = status_code;
  }

  public String getStatus_message() {
    return status_message;
  }

  public void setStatus_message(String status_message) {
    this.status_message = status_message;
  }
}

package com.github.lgdd.liferay.external.search.api;

public class ExternalSearchException
    extends Exception {

  public ExternalSearchException() {

    super();
  }

  public ExternalSearchException(String message) {

    super(message);
  }

  public ExternalSearchException(String message, Throwable cause) {

    super(message, cause);
  }

  public ExternalSearchException(Throwable cause) {

    super(cause);
  }
}

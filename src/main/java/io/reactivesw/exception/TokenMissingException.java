package io.reactivesw.exception;

/**
 * Created by umasuo on 17/3/2.
 */
public class TokenMissingException extends RuntimeException {
  public TokenMissingException() {
    super();
  }

  public TokenMissingException(String msg) {
    super(msg);
  }

}

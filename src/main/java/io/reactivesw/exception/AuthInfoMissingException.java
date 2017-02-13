package io.reactivesw.exception;

/**
 * Created by umasuo on 17/2/3.
 */
public class AuthInfoMissingException extends RuntimeException {

  /**
   * default constructor.
   */
  public AuthInfoMissingException() {
    super();
  }

  /**
   * constructor with message.
   *
   * @param msg
   */
  public AuthInfoMissingException(String msg) {
    super(msg);
  }
}

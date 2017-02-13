package io.reactivesw.exception;

/**
 * Created by umasuo on 17/2/10.
 */
public class PasswordErrorException extends RuntimeException{
  /**
   * default constructor.
   */
  public PasswordErrorException() {
    super();
  }

  /**
   * constructor with message.
   *
   * @param message String
   */
  public PasswordErrorException(String message) {
    super(message);
  }
}

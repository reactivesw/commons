package io.reactivesw.exception;

/**
 * Invalid token exception.
 */
public class InvalidTokenException extends RuntimeException {
  /**
   * auto generated serial version id.
   */
  private static final long serialVersionUID = -5844970144494607691L;

  /**
   * default constructor.
   */
  public InvalidTokenException() {
    super();
  }

  /**
   * constructor with error message.
   *
   * @param msg
   */
  public InvalidTokenException(String msg) {
    super(msg);
  }

  /**
   * constructor with error message and source exception.
   *
   * @param msg
   * @param ex
   */
  public InvalidTokenException(String msg, Throwable ex) {
    super(msg, ex);
  }
}

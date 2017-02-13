package io.reactivesw.authentication;

/**
 * Created by umasuo on 17/2/9.
 */
public enum TokenType {

  /**
   * used for anonymous request.
   */
  ANONYMOUS("anonymous"),

  /**
   * used for normal customer's request.
   */
  CUSTOMER("customer"),

  /**
   * used for admin of the system.
   */
  ADMIN("admin"),

  /**
   * used for call between services.
   */
  SERVICE("service");

  /**
   * value value.
   */
  private String value;

  TokenType(String value) {
    this.value = value;
  }

  /**
   * @return String value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * get type by value.
   *
   * @param value String value
   * @return TokenType
   */
  public static TokenType getType(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Token type value should not be null.");
    }
    TokenType result = ANONYMOUS;
    switch (value) {
      case "anonymous":
        result = ANONYMOUS;
        break;
      case "customer":
        result = CUSTOMER;
        break;
      case "admin":
        result = ADMIN;
        break;
      case "service":
        result = SERVICE;
        break;
    }
    return result;
  }
}

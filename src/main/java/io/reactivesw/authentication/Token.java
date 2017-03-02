package io.reactivesw.authentication;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Customer token
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Token implements Serializable {

  /**
   * token id, each token has an unique id.
   * This is an random uuid.
   */
  public String tokenId;
  /**
   * subject id, this can be: customer id, service id, anonymous id.
   */
  private String subjectId;

  /**
   * king of token. it can be: customer, admin, employee, service, anonymous.
   */
  private TokenType tokenType;

  /**
   * the token will be expires in this time.
   */
  private long expiresIn;

  /**
   * the token generate time, usually be the sign in time.
   */
  private long generateTime;

  /**
   * basic scopes for the token.
   */
  private List<Scope> scopes;

}


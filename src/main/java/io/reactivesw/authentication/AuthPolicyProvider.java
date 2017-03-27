package io.reactivesw.authentication;

/**
 * This provider provide an tool to check the authentication of the token.
 * default policy provider.
 */
public interface AuthPolicyProvider {

  /**
   * check the authentication of the token
   *
   * @param token String token value.
   * @return call the authentication service. and return the userId that bind to the token.
   */
  String checkAuthentication(String token);
}

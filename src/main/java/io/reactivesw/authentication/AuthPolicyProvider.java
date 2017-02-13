package io.reactivesw.authentication;

import io.reactivesw.exception.AuthFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by umasuo on 17/2/6.
 * This provider provide an tool to check the authentication of the token.
 * default policy provider.
 */
@Component
public class AuthPolicyProvider {

  /**
   * logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthPolicyProvider.class);

  /**
   * jwt tool.
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * check the authentication of the token
   *
   * @param tokenString
   */
  public void checkToken(String tokenString) {
    Token token = jwtUtil.parseToken(tokenString);
    //check expire time
    this.checkExpireTime(token.getGenerateTime(), token.getExpiresIn());
    //TODO check scope
    //TODO Maybe should check black list
  }

  /**
   * check if the token has expired.
   *
   * @param generateTime token's generate time.
   * @param expireIn     duration that token can be legal.
   */
  private void checkExpireTime(long generateTime, long expireIn) {

    //here we should consider about different time zone
    long curTime = System.currentTimeMillis();
    long expectedExpireTime = generateTime + expireIn;
    if (expectedExpireTime < curTime) {
      throw new AuthFailedException("Token is illegal： token has expired.");
    }
  }

}

package io.reactivesw.authentication;

import io.reactivesw.exception.AuthFailedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by umasuo on 17/2/6.
 * This provider provide an tool to check the authentication of the token.
 * default policy provider.
 */
public interface AuthPolicyProvider {

  /**
   * check the authentication of the token
   *
   * @param subjectId subject Id, this can be: customer id, service id, anonymous id.
   * @param token     token input.
   */
  default void checkToken(String subjectId, Token token) {
    Assert.notNull(subjectId);
    Assert.notNull(token);

    // check if the token belong to the subject.
    checkSubjectId(subjectId, token.getSubjectId());
    //check expire time
    checkExpireTime(token.getGenerateTime(), token.getExpiresIn());
    // check if the token has the correct policy.
    checkScope(token.getScopes());
    //black list for disable a token.
    checkBlackList(token.getTokenId());
  }


  default void checkSubjectId(String idInput, String idInToken) {
    if (!StringUtils.equals(idInput, idInToken)) {
      throw new AuthFailedException("Token is illegal：token not belong to " + idInput);
    }
  }

  /**
   * check if the token has expired.
   *
   * @param generateTime token's generate time.
   * @param expireIn     duration that token can be legal.
   */
  default void checkExpireTime(long generateTime, long expireIn) {

    //here we should consider about different time zone
    long curTime = System.currentTimeMillis();
    long expectedExpireTime = generateTime + expireIn;
    if (expectedExpireTime < curTime) {
      throw new AuthFailedException("Token is illegal： token has expired.");
    }
  }

  void checkScope(List<Scope> scopes);

  void checkBlackList(String tokenId);
}

package io.reactivesw.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by umasuo on 17/2/3.
 * JSON WEB TOKEN UTIL.
 */
@Configuration
@Data
public class JwtUtil {

  /**
   * jwt secret for generate or parse token. if not set, then throw error.
   */
  @Value("${jwt.secret:QWERTYUIOPLKJHGFDSAZXCVBNM}")
  private String secret;

  /**
   * the default expires time for each token.
   */
  @Value("${jwt.expires-in:7200000}")
  private Long expiresIn;

  /**
   * Tries to parse specified String as a JWT token. If successful, returns User object with
   * username, id and role prefilled (extracted from token).
   * If unsuccessful (token is invalid or not containing all required user properties), simply
   * returns null.
   *
   * @param token the JWT token to parse
   * @return the User object extracted from specified token or null if a token is invalid.
   */
  public Token parseToken(String token) {
    return JwtUtil.parseToken(secret, token);
  }

  /**
   * static function for parse token.
   *
   * @param secret secret used.
   * @param token  token.
   * @return Token
   */
  public static Token parseToken(String secret, String token) {
    try {
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();

      Token tk = new Token();
      tk.setTokenType(TokenType.getType(body.getSubject()));
      tk.setTokenId((String) body.get("tokenId"));
      tk.setSubjectId((String) body.get("subjectId"));
      tk.setGenerateTime((Long) body.get("generateTime"));
      if (body.get("expiresIn") instanceof Integer) {
        tk.setExpiresIn(((Integer) body.get("expiresIn")).longValue());
      } else {
        tk.setExpiresIn((Long) body.get("expiresIn"));
      }
      tk.setScopes((List<Scope>) body.get("scopes"));

      return tk;

    } catch (JwtException | ClassCastException e) {
      return null;
    }
  }

  /**
   * generate token.
   *
   * @param tokenType : customer token, service token, anonymous token
   * @param subjectId subject id String
   * @param expires   expires time
   * @param scopes    scope
   * @return String token
   */
  public String generateToken(TokenType tokenType, String subjectId, long expires, List<Scope>
      scopes) {
    return JwtUtil.generateToken(tokenType, subjectId, expires, secret, scopes);
  }

  /**
   * generate token.
   *
   * @param tokenType : customer token, service token, anonymous token
   * @param subjectId subject id String
   * @param expires   expires time
   * @param scopes    scope
   * @return String token
   */
  public static String generateToken(TokenType tokenType, String subjectId, long expires, String
      secret, List<Scope> scopes) {
    Assert.isTrue(expires > 0, "expires should have positive value");
    Assert.notNull(secret);

    Claims claims = Jwts.claims().setSubject(tokenType.getValue());
    claims.put("tokenId", UUID.randomUUID().toString());
    claims.put("subjectId", subjectId);
    claims.put("generateTime", System.currentTimeMillis());
    claims.put("expiresIn", expires);
    claims.put("scopes", scopes);


    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  /**
   * generate service token for service.
   *
   * @param serviceName service name
   * @return String
   */
  public String generateServiceToken(String serviceName) {
    return this.generateToken(TokenType.SERVICE, serviceName, Integer.MAX_VALUE,
        new ArrayList<>());
  }

  /**
   * generate anonymous token.
   *
   * @return String anonymous token
   */
  public String generateAnonymousToken() {
    return this.generateToken(TokenType.ANONYMOUS, UUID.randomUUID().toString(), 0,
        new ArrayList<>());
  }
}

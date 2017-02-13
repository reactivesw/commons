package io.reactivesw.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
  @Value("${jwt.secret}")
  private String secret;

  /**
   * the default expires time for each token.
   */
  @Value("${jwt.expiresin:7200000}")
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
    try {
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();

      Token tk = new Token();
      tk.setTokenType(TokenType.getType(body.getSubject()));
      tk.setSubjectId((String) body.get("subjectId"));
      tk.setGenerateTime((Long) body.get("generateTime"));
      if (body.get("expiresIn") instanceof Integer) {
        tk.setExpiresIn(((Integer) body.get("expiresIn")).longValue());
      } else {
        tk.setExpiresIn((Long) body.get("expiresIn"));
      }
      tk.setScope((String) body.get("scope"));

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
   * @param scope     scope
   * @return String token
   */
  public String generateToken(TokenType tokenType, String subjectId, long expires, String scope) {
    Claims claims = Jwts.claims().setSubject(tokenType.getValue());
    claims.put("subjectId", subjectId);
    claims.put("generateTime", System.currentTimeMillis());
    if (expires > 0) {
      claims.put("expiresIn", expires);
    } else {
      claims.put("expiresIn", expiresIn);
    }
    claims.put("scope", scope);// TODO set role or scope


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
    return this.generateToken(TokenType.SERVICE, serviceName, Integer.MAX_VALUE, "");
  }

  /**
   * generate anonymous token.
   *
   * @return String anonymous token
   */
  public String generateAnonymousToken() {
    return this.generateToken(TokenType.ANONYMOUS, UUID.randomUUID().toString(), 0, "");
  }
}

package io.reactivesw.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 16/11/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Token implements Serializable {

  /**
   * customer id, service id, anonymous id.
   */
  @JsonProperty("subject_id")
  private String subjectId;

  @JsonProperty("token_type")
  private TokenType tokenType;

  @JsonProperty("expires_in")
  private long expiresIn;

  @JsonProperty("generate_time")
  private long generateTime;

  @JsonProperty("scope")
  private String scope;

}


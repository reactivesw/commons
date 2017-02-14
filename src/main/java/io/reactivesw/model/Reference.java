package io.reactivesw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by umasuo on 16/11/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reference {
  /**
   * type id.
   */
  private String typeId;

  /**
   * id.
   */
  private String id;
}

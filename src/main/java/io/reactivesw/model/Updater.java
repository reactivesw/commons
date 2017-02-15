package io.reactivesw.model;

/**
 * Created by umasuo on 17/2/15.
 */
public interface Updater<E, A> {

  /**
   * update action handler.
   *
   * @param entity entity
   * @param action action
   */
  void handle(E entity, A action);
}

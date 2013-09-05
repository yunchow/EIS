package com.eis.core.model.support;

import com.eis.core.model.AbstractSpecification;
import com.eis.core.model.Specification;
 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public class NotSpecification<T> extends AbstractSpecification<T> {

  private Specification<T> spec1;

  /**
   * Create a new NOT specification based on another spec.
   *
   * @param spec1 Specification instance to not.
   */
  public NotSpecification(final Specification<T> spec1) {
    this.spec1 = spec1;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSatisfiedBy(final T t) {
    return !spec1.isSatisfiedBy(t);
  }
}

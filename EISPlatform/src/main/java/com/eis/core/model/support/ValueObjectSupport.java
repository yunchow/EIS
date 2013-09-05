package com.eis.core.model.support;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.eis.core.model.ValueObject;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public abstract class ValueObjectSupport<T extends ValueObject<T>> implements ValueObject<T> {
	private static final long serialVersionUID = 1L;
	private transient int cachedHashCode = 0;
	private transient String cachedToString = null;

	/**
	 * @param other The other value object.
	 * @return True if all non-transient fields are equal.
	 */
	@Override
	public final boolean sameValueAs(final T other) {
		return other != null && EqualsBuilder.reflectionEquals(this, other, false);
	}

	/**
	 * @return Hash code built from all non-transient fields.
	 */
	@Override
	public final int hashCode() {
		int h = cachedHashCode;
		if (h == 0) {
			h = HashCodeBuilder.reflectionHashCode(this, false);
			cachedHashCode = h;
		}
		return h;
	}

	/**
	 * @param o other object
	 * @return True if other object has the same value as this value object.
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return EqualsBuilder.reflectionEquals(this, o, false);
	}

	@Override
	public String toString() {
		if (cachedToString == null) {
			cachedToString = ToStringBuilder.reflectionToString(this);
		}
		return cachedToString;
	}
}

package com.eis.core.model.support;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.eis.core.model.DataTransferObject;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 9, 2013
 */
public abstract class DataTransferObjectSupport implements DataTransferObject {
	private static final long serialVersionUID = 1L;
	private transient int cachedHashCode = 0;
	private transient String cachedToString = null;

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

/**
 * fileName: EISPlatform/com.eis.platform.model.support/EntitySupport.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.core.model.support;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eis.core.model.Entity;

/**
 * Title: EntitySupport.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public abstract class EntitySupport<T> implements Entity<T> {
	private static final long serialVersionUID = 1L;
	private transient int cachedHashCode = -1;
	private transient String cachedToString = null;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * identity
	 */
	protected String id;

	@Override
	public boolean sameIdentityAs(T other) {
		return equals(other);
	}

	@Override
	public final int hashCode() {
		if (id == null) {
			return -1;
		}
		if (cachedHashCode == -1) {
			cachedHashCode = id.hashCode();
		}
		return cachedHashCode;
	}

	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (id == null) {
			return false;
		}
		if (o instanceof EntitySupport) {
			EntitySupport<?> e = (EntitySupport<?>) o;
			return id.equals(e.id);
		}
		return false;
	}

	@Override
	public String toString() {
		if (cachedToString == null) {
			cachedToString = ToStringBuilder.reflectionToString(this);
		}
		return cachedToString;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

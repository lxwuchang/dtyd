/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.xwork.config.entities;

import com.opensymphony.xwork.interceptor.Interceptor;

import java.io.Serializable;

/**
 * <code>InterceptorMapping</code>
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id: InterceptorMapping.java 1268 2006-12-11 12:43:08Z tm_jee $
 */
public class InterceptorMapping implements Serializable {

	private static final long serialVersionUID = -6916794422111224317L;
	
	private String name;
    private Interceptor interceptor;

    public InterceptorMapping() {
    }

    public InterceptorMapping(String name, Interceptor interceptor) {
        this.name = name;
        this.interceptor = interceptor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final InterceptorMapping that = (InterceptorMapping) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        return result;
    }
}

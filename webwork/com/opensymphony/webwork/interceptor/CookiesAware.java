/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.Map;

/**
 * @author tmjee
 * @author Matthew Payne
 * @version $Date: 2007-01-09 18:04:09 +0100 (Tue, 09 Jan 2007) $ $Id: CookiesAware.java 2798 2007-01-09 17:04:09Z tmjee $
 */
public interface CookiesAware {
	void setCookiesMap(Map cookies);
}

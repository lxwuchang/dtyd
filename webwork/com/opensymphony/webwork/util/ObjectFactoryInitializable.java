package com.opensymphony.webwork.util;

import javax.servlet.ServletContext;

/**
 * Used to pass ServletContext init parameters to various
 * frameworks such as Spring, Plexus and Portlet.
 *
 * @author plightbo
 * @version $Date: 2006-03-16 15:44:02 +0100 (Thu, 16 Mar 2006) $ $Id: ObjectFactoryInitializable.java 2423 2006-03-16 14:44:02Z tmjee $
 */
public interface ObjectFactoryInitializable {

    void init(ServletContext servletContext);

}

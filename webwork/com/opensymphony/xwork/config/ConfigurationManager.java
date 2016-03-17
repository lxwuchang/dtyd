/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.config;

import com.opensymphony.util.FileManager;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.InterceptorMapping;
import com.opensymphony.xwork.config.impl.DefaultConfiguration;
import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork.interceptor.Interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * ConfigurationManager - central for XWork Configuration management, including 
 * its ConfigurationProvider.
 *
 * @author Jason Carreira
 * @author tm_jee
 * @version $Date: 2007-06-02 11:01:04 +0200 (Sat, 02 Jun 2007) $ $Id: ConfigurationManager.java 1533 2007-06-02 09:01:04Z tm_jee $
 */
public class ConfigurationManager {

    protected static final Log LOG = LogFactory.getLog(ConfigurationManager.class);
    protected static Configuration configurationInstance;
    private static List configurationProviders = new ArrayList();

    
    private ConfigurationManager() {
    }


    public static synchronized void setConfiguration(Configuration configuration) {
        configurationInstance = configuration;
    }

    /**
     * Get the current XWork configuration object.  By default an instance of DefaultConfiguration will be returned
     *
     * @see com.opensymphony.xwork.config.impl.DefaultConfiguration
     */
    public static synchronized Configuration getConfiguration() {
        if (configurationInstance == null) {
            configurationInstance = new DefaultConfiguration();
            try {
                configurationInstance.reload();
            } catch (ConfigurationException e) {
                configurationInstance = null;
                throw e;
            }
        } else {
            conditionalReload();
        }

        return configurationInstance;
    }

    /**
     * <p/>
     * get the current list of ConfigurationProviders.
     * </p>
     * <p/>
     * if no custom ConfigurationProviders have been added, this method
     * will return a list containing only the default ConfigurationProvider, XMLConfigurationProvider.  if a custom
     * ConfigurationProvider has been added, then the XmlConfigurationProvider must be added by hand.
     * </p>
     *
     * TODO: the lazy instantiation of XmlConfigurationProvider should be refactored to be elsewhere.  the behavior described above seems unintuitive.
     *
     * @return the list of registered ConfigurationProvider objects
     * @see com.opensymphony.xwork.config.ConfigurationProvider
     */
    public static List getConfigurationProviders() {
        synchronized (configurationProviders) {
            if (configurationProviders.size() == 0) {
                configurationProviders.add(new XmlConfigurationProvider());
            }

            return configurationProviders;
        }
    }

    /**
     * adds a configuration provider to the List of ConfigurationProviders.  a given ConfigurationProvider may be added
     * more than once
     *
     * @param provider the ConfigurationProvider to register
     */
    public static void addConfigurationProvider(ConfigurationProvider provider) {
        synchronized (configurationProviders) {
            if (!configurationProviders.contains(provider)) {
                configurationProviders.add(provider);
            }
        }
    }

    /**
     * clears the registered ConfigurationProviders.  this method will call destroy() on each of the registered
     * ConfigurationProviders
     *
     * @see com.opensymphony.xwork.config.ConfigurationProvider#destroy
     */
    public static void clearConfigurationProviders() {
        synchronized (configurationProviders) {
            for (Iterator iterator = configurationProviders.iterator();
                 iterator.hasNext();) {
                ConfigurationProvider provider = (ConfigurationProvider) iterator.next();
                provider.destroy();
            }

            configurationProviders.clear();
        }
    }

    public static synchronized void destroyConfiguration() {
        synchronized (configurationProviders) {
        	clearConfigurationProviders(); // let's destroy the ConfigurationProvider first
            configurationProviders = new ArrayList();
            
            if (configurationInstance != null) {
            	/*  let's destroy the interceptors used. */
            	
            	// keeps a copy of the interceptor that we've destroyed before, so we don't destroy them twice
            	List destroyedInterceptors = new ArrayList();  
            	Map namespacesMap = configurationInstance.getRuntimeConfiguration().getActionConfigs();
            	if (namespacesMap != null) {
            		for (Iterator i = namespacesMap.entrySet().iterator(); i.hasNext(); ) {
            			Map.Entry namespaceMapEntry = (Map.Entry) i.next();
            			String namespace = (String) namespaceMapEntry.getKey();
            			Map actionConfigsMap = (Map) namespaceMapEntry.getValue();
            		
            			for (Iterator ii = actionConfigsMap.entrySet().iterator(); ii.hasNext(); ) {
            				Map.Entry actionConfigMapEntry = (Map.Entry) ii.next();
            				String actionName = (String) actionConfigMapEntry.getKey();
            				ActionConfig actionConfig = (ActionConfig) actionConfigMapEntry.getValue();
            			
            				List interceptorMappings = actionConfig.getInterceptors();
            				for (Iterator iii = interceptorMappings.iterator(); iii.hasNext(); ) {
            					InterceptorMapping interceptorMapping = (InterceptorMapping) iii.next();
            					if (interceptorMapping != null && interceptorMapping.getInterceptor() != null) {
            						Interceptor interceptor = interceptorMapping.getInterceptor();
            						if (! destroyedInterceptors.contains(interceptor)) {
            							destroyedInterceptors.add(interceptor);  // keep a copy so we know we've destroy this interceptor before.
            							try {
            								if (LOG.isDebugEnabled())
            									LOG.debug("destroying interceptor ["+interceptor+"] registered under name ["+interceptorMapping.getName()+"] with namespace ["+namespace+"] in package ["+actionConfig.getPackageName()+"]");
            								interceptor.destroy();
            							}
            							catch(Exception e) {
            								// we don't want a bad interceptor to cause the whole destoy process to blow 
            								// everything up, if this happens, just inform the user by logging the error
            								LOG.error("an exception occurred while calling destroy() method on interceptor ["+interceptor+"] registered under name ["+interceptorMapping.getName()+"] with namespace ["+namespace+"] in package ["+actionConfig.getPackageName()+"]");
            							}
            						}
            					}
            				}
            			}
            		}
            	}
            	
            	configurationInstance.destroy(); // let's destroy it first, before nulling it.
            }
            configurationInstance = null;
        }
    }

    /**
     * reloads the Configuration files if the configuration files indicate that they need to be reloaded.
     *
     * <B>NOTE:</b> FileManager could be configured through webwork.properties through
     * webwork.configuration.xml.reload  property.
     */
    private static synchronized void conditionalReload() {
        if (FileManager.isReloadingConfigs()) {
            boolean reload;

            synchronized (configurationProviders) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Checking ConfigurationProviders for reload.");
                }

                reload = false;

                for (Iterator iterator = getConfigurationProviders().iterator();
                     iterator.hasNext();) {
                    ConfigurationProvider provider = (ConfigurationProvider) iterator.next();

                    if (provider.needsReload()) {
                        reload = true;

                        break;
                    }
                }
            }

            if (reload) {
                configurationInstance.reload();
            }
        }
    }
}

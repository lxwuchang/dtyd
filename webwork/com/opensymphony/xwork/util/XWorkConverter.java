/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import ognl.DefaultTypeConverter;
import ognl.OgnlRuntime;
import ognl.TypeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.util.FileManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.XWorkMessages;


/**
 * XWorkConverter is a singleton used by many of the WebWork's Ognl extention points,
 * such as InstantiatingNullHandler, XWorkListPropertyAccessor etc to do object 
 * conversion.
 * 
 * <!-- START SNIPPET: javadoc -->
 *
 * Type conversion is great for situations where you need to turn a String in to a more complex object. Because the web
 * is type-agnostic (everything is a string in HTTP), WebWork's type conversion features are very useful. For instance,
 * if you were prompting a user to enter in coordinates in the form of a string (such as "3, 22"), you could have
 * WebWork do the conversion both from String to Point and from Point to String.
 *
 * <p/> Using this "point" example, if your action (or another compound object in which you are setting properties on)
 * has a corresponding ClassName-conversion.properties file, WebWork will use the configured type converters for
 * conversion to and from strings. So turning "3, 22" in to new Point(3, 22) is done by merely adding the following
 * entry to <b>ClassName-conversion.properties</b> (Note that the PointConverter should impl the ognl.TypeConverter
 * interface):
 *
 * <p/><b>point = com.acme.PointConverter</b>
 *
 * <p/> Your type converter should be sure to check what class type it is being requested to convert. Because it is used
 * for both to and from strings, you will need to split the conversion method in to two parts: one that turns Strings in
 * to Points, and one that turns Points in to Strings.
 *
 * <p/> After this is done, you can now reference your point (using &lt;ww:property value="post"/&gt; in JSP or ${point}
 * in FreeMarker) and it will be printed as "3, 22" again. As such, if you submit this back to an action, it will be
 * converted back to a Point once again.
 *
 * <p/> In some situations you may wish to apply a type converter globally. This can be done by editing the file
 * <b>xwork-conversion.properties</b> in the root of your class path (typically WEB-INF/classes) and providing a
 * property in the form of the class name of the object you wish to convert on the left hand side and the class name of
 * the type converter on the right hand side. For example, providing a type converter for all Point objects would mean
 * adding the following entry:
 *
 * <p/><b>com.acme.Point = com.acme.PointConverter</b>
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/>
 *
 * <!-- START SNIPPET: i18n-note -->
 *
 * Type conversion should not be used as a substitute for i18n. It is not recommended to use this feature to print out
 * properly formatted dates. Rather, you should use the i18n features of WebWork (and consult the JavaDocs for JDK's
 * MessageFormat object) to see how a properly formatted date should be displayed.
 *
 * <!-- END SNIPPET: i18n-note -->
 *
 * <p/>
 *
 * <!-- START SNIPPET: error-reporting -->
 *
 * Any error that occurs during type conversion may or may not wish to be reported. For example, reporting that the
 * input "abc" could not be converted to a number might be important. On the other hand, reporting that an empty string,
 * "", cannot be converted to a number might not be important - especially in a web environment where it is hard to
 * distinguish between a user not entering a value vs. entering a blank value.
 *
 * <p/> By default, all conversion errors are reported using the generic i18n key <b>xwork.default.invalid.fieldvalue</b>,
 * which you can override (the default text is <i>Invalid field value for field "xxx"</i>, where xxx is the field name)
 * in your global i18n resource bundle.
 *
 * <p/>However, sometimes you may wish to override this message on a per-field basis. You can do this by adding an i18n
 * key associated with just your action (Action.properties) using the pattern <b>invalid.fieldvalue.xxx</b>, where xxx
 * is the field name.
 *
 * <p/>It is important to know that none of these errors are actually reported directly. Rather, they are added to a map
 * called <i>conversionErrors</i> in the ActionContext. There are several ways this map can then be accessed and the
 * errors can be reported accordingly.
 *
 * <!-- END SNIPPET: error-reporting -->
 *
 * @author <a href="mailto:plightbo@gmail.com">Pat Lightbody</a>
 * @author Rainer Hermanns
 * @author <a href='mailto:the_mindstorm[at]evolva[dot]ro'>Alexandru Popescu</a>
 * @see XWorkBasicConverter
 */
public class XWorkConverter extends DefaultTypeConverter {
    private static XWorkConverter instance;
    protected static final Log LOG = LogFactory.getLog(XWorkConverter.class);
    public static final String REPORT_CONVERSION_ERRORS = "report.conversion.errors";
    public static final String CONVERSION_PROPERTY_FULLNAME = "conversion.property.fullName";
    public static final String CONVERSION_ERROR_PROPERTY_PREFIX = "invalid.fieldvalue.";
    public static final String CONVERSION_COLLECTION_PREFIX = "Collection_";

    public static final String LAST_BEAN_CLASS_ACCESSED = "last.bean.accessed";
    public static final String LAST_BEAN_PROPERTY_ACCESSED = "last.property.accessed";

    HashMap defaultMappings = new HashMap();
    HashMap mappings = new HashMap();
    HashSet noMapping = new HashSet();
    HashSet unknownMappings = new HashSet();
    TypeConverter defaultTypeConverter = new XWorkBasicConverter();
    ObjectTypeDeterminer objectTypeDeterminer = ObjectTypeDeterminerFactory.getInstance();


    protected XWorkConverter() {
        try {
            // note: this file is deprecated
            loadConversionProperties("xwork-default-conversion.properties");
        } catch (Exception e) {
        }

        try {
            loadConversionProperties("xwork-conversion.properties");
        } catch (Exception e) {
        }
    }

    public static String getConversionErrorMessage(String propertyName, OgnlValueStack stack) {
        String defaultMessage = LocalizedTextUtil.findDefaultText(XWorkMessages.DEFAULT_INVALID_FIELDVALUE,
                ActionContext.getContext().getLocale(),
                new Object[]{
                        propertyName
                });
        String getTextExpression = "getText('" + CONVERSION_ERROR_PROPERTY_PREFIX + propertyName + "','" + defaultMessage + "')";
        String message = (String) stack.findValue(getTextExpression);

        if (message == null) {
            message = defaultMessage;
        }

        return message;
    }

    public static XWorkConverter getInstance() {
        if (instance == null) {
            try {
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass("com.opensymphony.xwork.util.AnnotationXWorkConverter");
                instance = (XWorkConverter) clazz.newInstance();
                LOG.info("Detected AnnotationXWorkConverter, initializing it...");
            } catch (ClassNotFoundException e) {
                // this is fine, just fall back to the default object type determiner
            } catch (Exception e) {
                LOG.error("Exception when trying to create new AnnotationXWorkConverter", e);
            }
            if ( instance == null ) {
                instance = new XWorkConverter();
            }
        }

        return instance;
    }

    public static void setInstance(XWorkConverter instance) {
        XWorkConverter.instance = instance;
    }

    public static String buildConverterFilename(Class clazz) {
        String className = clazz.getName();
        String resource = className.replace('.', '/') + "-conversion.properties";

        return resource;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void setDefaultConverter(TypeConverter defaultTypeConverter) {
        this.defaultTypeConverter = defaultTypeConverter;
    }

    public Object convertValue(Map map, Object o, Class aClass) {
        return convertValue(map, null, null, null, o, aClass);
    }

    /**
     * Convert value from one form to another.
     * Minimum requirement of arguments:
     * <ul>
     * 		<li>supplying context, toClass and value</li>
     * 		<li>supplying context, target and value.</li>
     * </ul>
     * 
     * @see ognl.TypeConverter#convertValue(java.util.Map, java.lang.Object, java.lang.reflect.Member, java.lang.String, java.lang.Object, java.lang.Class)
     */
    public Object convertValue(Map context, Object target, Member member, String property, Object value, Class toClass) {
        //
        // Process the conversion using the default mappings, if one exists
        //
        TypeConverter tc = null;

        if ((value != null) && (toClass == value.getClass())) {
            return value;
        }

        // allow this method to be called without any context
        // i.e. it can be called with as little as "Object value" and "Class toClass"
        if (target != null) {
            Class clazz = target.getClass();

            Object[] classProp = null;

            // this is to handle weird issues with setValue with a different type
            if ((target instanceof CompoundRoot) && (context != null)) {
                classProp = getClassProperty(context);
            }

            if (classProp != null) {
                clazz = (Class) classProp[0];
                property = (String) classProp[1];
            }

            tc = (TypeConverter) getConverter(clazz, property);
        }

        if (tc == null && context != null) {
            // ok, let's see if we can look it up by path as requested in XW-297
            Object lastPropertyPath = context.get(OgnlContextState.CURRENT_PROPERTY_PATH);
            Class clazz = (Class) context.get(XWorkConverter.LAST_BEAN_CLASS_ACCESSED);
            if (lastPropertyPath != null && clazz != null) {
                String path = lastPropertyPath + "." + property;
                tc = (TypeConverter) getConverter(clazz, path);
            }
        }

        if (tc == null) {
            if (toClass.equals(String.class) && (value != null) && !(value.getClass().equals(String.class) || value.getClass().equals(String[].class)))
            {
                // when converting to a string, use the source target's class's converter
                tc = lookup(value.getClass());
            } else {
                // when converting from a string, use the toClass's converter
                tc = lookup(toClass);
            }
        }

        if (tc != null) {
            try {
                return tc.convertValue(context, target, member, property, value, toClass);
            } catch (Exception e) {
                handleConversionException(context, property, value, target);

                return OgnlRuntime.NoConversionPossible;
            }
        }

        if (defaultTypeConverter != null) {
            try {
                return defaultTypeConverter.convertValue(context, target, member, property, value, toClass);
            } catch (Exception e) {
                handleConversionException(context, property, value, target);

                return OgnlRuntime.NoConversionPossible;
            }
        } else {
            try {
                return super.convertValue(context, target, member, property, value, toClass);
            } catch (Exception e) {
                handleConversionException(context, property, value, target);

                return OgnlRuntime.NoConversionPossible;
            }
        }
    }

    /**
     * Looks for a TypeConverter in the default mappings.
     *
     * @param className name of the class the TypeConverter must handle
     * @return a TypeConverter to handle the specified class or null if none can be found
     */
    public TypeConverter lookup(String className) {
        if (unknownMappings.contains(className)) {
            return null;
        }

        TypeConverter result = (TypeConverter) defaultMappings.get(className);

        //Looks for super classes
        if (result == null) {
            Class clazz = null;

            try {
                clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            } catch (ClassNotFoundException cnfe) {
            }

            result = lookupSuper(clazz);

            if (result != null) {
                //Register now, the next lookup will be faster
                registerConverter(className, result);
            } else {
                // if it isn't found, never look again (also faster)
                registerConverterNotFound(className);
            }
        }

        return result;
    }

    /**
     * Looks for a TypeConverter in the default mappings.
     *
     * @param clazz the class the TypeConverter must handle
     * @return a TypeConverter to handle the specified class or null if none can be found
     */
    public TypeConverter lookup(Class clazz) {
        return lookup(clazz.getName());
    }

    protected Object getConverter(Class clazz, String property) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Property: " + property);
            LOG.debug("Class: " + clazz.getName());
        }
        synchronized (clazz) {
            if ((property != null) && !noMapping.contains(clazz)) {
                try {
                    Map mapping = (Map) mappings.get(clazz);

                    if (mapping == null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Map is null.");
                        }
                        mapping = buildConverterMapping(clazz);
                    } else {
                        mapping = conditionalReload(clazz, mapping);
                    }

                    Object converter = mapping.get(property);
                    if (LOG.isDebugEnabled() && converter == null) {
                        LOG.debug("converter is null for property " + property + ". Mapping size: " + mapping.size());
                        Iterator iter = mapping.keySet().iterator();
                        while (iter.hasNext()) {
                            Object next = iter.next();
                            LOG.debug(next + ":" + mapping.get(next));
                        }
                    }
                    return converter;
                } catch (Throwable t) {
                    noMapping.add(clazz);
                }
            }
        }

        return null;
    }

    protected void handleConversionException(Map context, String property, Object value, Object object) {
        if ((Boolean.TRUE.equals(context.get(REPORT_CONVERSION_ERRORS)))) {

            String realProperty = property;
            String fullName = (String) context.get(CONVERSION_PROPERTY_FULLNAME);

            if (fullName != null) {
                realProperty = fullName;
            }

            Map conversionErrors = (Map) context.get(ActionContext.CONVERSION_ERRORS);

            if (conversionErrors == null) {
                conversionErrors = new HashMap();
                context.put(ActionContext.CONVERSION_ERRORS, conversionErrors);
            }

            conversionErrors.put(realProperty, value);
        }
    }

    public synchronized void registerConverter(String className, TypeConverter converter) {
        defaultMappings.put(className, converter);
    }

    public synchronized void registerConverterNotFound(String className) {
        unknownMappings.add(className);
    }

    private Object[] getClassProperty(Map context) {
        return (Object[]) context.get("__link");
    }

    /**
     * not used
     */
    private Object acceptableErrorValue(Class toClass) {
        if (!toClass.isPrimitive()) {
            return null;
        }
        
        if (toClass == int.class) {
            return new Integer(0);
        } else if (toClass == double.class) {
            return new Double(0);
        } else if (toClass == long.class) {
            return new Long(0);
        } else if (toClass == boolean.class) {
            return Boolean.FALSE;
        } else if (toClass == short.class) {
            return new Short((short) 0);
        } else if (toClass == float.class) {
            return new Float(0);
        } else if (toClass == byte.class) {
            return new Byte((byte) 0);
        } else if (toClass == char.class) {
            return new Character((char) 0);
        }
        
        return null;
    }

    /**
     * Looks for converter mappings for the specified class and adds it to an existing map.  Only new converters are
     * added.  If a converter is defined on a key that already exists, the converter is ignored.
     *
     * @param mapping an existing map to add new converter mappings to
     * @param clazz   class to look for converter mappings for
     */
    void addConverterMapping(Map mapping, Class clazz) {
        try {
            InputStream is = FileManager.loadFile(buildConverterFilename(clazz), clazz);

            if (is != null) {
                Properties prop = new Properties();
                prop.load(is);

                Iterator it = prop.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();

                    if (mapping.containsKey(key)) {
                        break;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(key + ":" + entry.getValue());
                    }

                    if (key.startsWith(DefaultObjectTypeDeterminer.KEY_PROPERTY_PREFIX)
                            || key.startsWith(DefaultObjectTypeDeterminer.CREATE_IF_NULL_PREFIX)) {
                        mapping.put(key, entry.getValue());
                    }
                    //for properties of classes
                    else if (!(key.startsWith(DefaultObjectTypeDeterminer.ELEMENT_PREFIX) ||
                            key.startsWith(DefaultObjectTypeDeterminer.KEY_PREFIX) ||
                            key.startsWith(DefaultObjectTypeDeterminer.DEPRECATED_ELEMENT_PREFIX))
                            ) {
                        mapping.put(key, createTypeConverter((String) entry.getValue()));
                    }
                    //for keys of Maps
                    else if (key.startsWith(DefaultObjectTypeDeterminer.KEY_PREFIX)) {

                        Class converterClass = Thread.currentThread().getContextClassLoader().loadClass((String) entry.getValue());
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Converter class: " + converterClass);
                        }
                        //check if the converter is a type converter if it is one
                        //then just put it in the map as is. Otherwise
                        //put a value in for the type converter of the class
                        if (converterClass.isAssignableFrom(TypeConverter.class)) {

                            mapping.put(key, createTypeConverter((String) entry.getValue()));


                        } else {

                            mapping.put(key, converterClass);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Object placed in mapping for key "
                                        + key
                                        + " is "
                                        + mapping.get(key));
                            }

                        }


                    }
                    //elements(values) of maps / lists
                    else {
                        mapping.put(key, Thread.currentThread().getContextClassLoader().loadClass((String) entry.getValue()));
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Problem loading properties for " + clazz.getName(), ex);
        }
    }

    /**
     * Looks for converter mappings for the specified class, traversing up its class hierarchy and interfaces and adding
     * any additional mappings it may find.  Mappings lower in the hierarchy have priority over those higher in the
     * hierarcy.
     *
     * @param clazz the class to look for converter mappings for
     * @return the converter mappings
     */
    private Map buildConverterMapping(Class clazz) throws Exception {
        Map mapping = new HashMap();

        // check for conversion mapping associated with super classes and any implemented interfaces
        Class curClazz = clazz;

        while (!curClazz.equals(Object.class)) {
            // add current class' mappings
            addConverterMapping(mapping, curClazz);

            // check interfaces' mappings
            Class[] interfaces = curClazz.getInterfaces();

            for (int x = 0; x < interfaces.length; x++) {
                addConverterMapping(mapping, interfaces[x]);
            }

            curClazz = curClazz.getSuperclass();
        }

        if (mapping.size() > 0) {
            mappings.put(clazz, mapping);
        } else {
            noMapping.add(clazz);
        }

        return mapping;
    }

    private Map conditionalReload(Class clazz, Map oldValues) throws Exception {
        Map mapping = oldValues;

        if (FileManager.isReloadingConfigs()) {
            if (FileManager.fileNeedsReloading(buildConverterFilename(clazz))) {
                mapping = buildConverterMapping(clazz);
            }
        }

        return mapping;
    }

    TypeConverter createTypeConverter(String className) throws Exception {
        // type converters are used across users
        return (TypeConverter) ObjectFactory.getObjectFactory().buildBean(className, null);
    }

    public void loadConversionProperties(String propsName) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propsName);
        Properties props = new Properties();
        props.load(is);

        for (Iterator iterator = props.entrySet().iterator();
             iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();

            try {
                defaultMappings.put(key, createTypeConverter((String) entry.getValue()));
            } catch (Exception e) {
                LOG.error("Conversion registration error", e);
            }
        }
    }

    /**
     * Recurses through a class' interfaces and class hierarchy looking for a TypeConverter in the default mapping that
     * can handle the specified class.
     *
     * @param clazz the class the TypeConverter must handle
     * @return a TypeConverter to handle the specified class or null if none can be found
     */
    TypeConverter lookupSuper(Class clazz) {
        TypeConverter result = null;

        if (clazz != null) {
            result = (TypeConverter) defaultMappings.get(clazz.getName());

            if (result == null) {
                // Looks for direct interfaces (depth = 1 )
                Class[] interfaces = clazz.getInterfaces();

                for (int i = 0; i < interfaces.length; i++) {
                    if (defaultMappings.containsKey(interfaces[i].getName())) {
                        result = (TypeConverter) defaultMappings.get(interfaces[i].getName());
                        break;
                    }
                }

                if (result == null) {
                    // Looks for the superclass
                    // If 'clazz' is the Object class, an interface, a primitive type or void then clazz.getSuperClass() returns null
                    result = lookupSuper(clazz.getSuperclass());
                }
            }
        }

        return result;
    }

    public ObjectTypeDeterminer getObjectTypeDeterminer() {
        return objectTypeDeterminer;
    }

    /**
     * @param determiner
     */
    public void setObjectTypeDeterminer(ObjectTypeDeterminer determiner) {
        objectTypeDeterminer = determiner;
    }

}

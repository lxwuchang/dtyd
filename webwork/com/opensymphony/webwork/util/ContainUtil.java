/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


/**
 * <code>ContainUtil</code> will check if object 1 contains object 2.
 * Object 1 may be an Object, array, Collection, or a Map
 *
 * @author Matt Baldree (matt@smallleap.com)
 * @version $Revision: 2737 $
 */
public class ContainUtil {

	/**
     * Determine if <code>obj2</code> exists in <code>obj1</code>.
     *
     * <table borer="1">
     *  <tr>
     *      <td>Type Of obj1</td>
     *      <td>Comparison type</td>
     *  </tr>
     *  <tr>
     *      <td>null<td>
     *      <td>always return false</td>
     *  </tr>
     *  <tr>
     *      <td>Map</td>
     *      <td>Map containsKey(obj2)</td>
     *  </tr>
     *  <tr>
     *      <td>Collection</td>
     *      <td>Collection contains(obj2)</td>
     *  </tr>
     *  <tr>
     *      <td>Array</td>
     *      <td>there's an array element (e) where e.equals(obj2)</td>
     *  </tr>
     *  <tr>
     *      <td>Object</td>
     *      <td>obj1.equals(obj2)</td>
     *  </tr>
     * </table>
     *
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean contains(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) {
            //log.debug("obj1 or obj2 are null.");
            return false;
        }

        if (obj1 instanceof Map) {
            if (((Map) obj1).containsKey(obj2)) {
                //log.debug("obj1 is a map and contains obj2");
                return true;
            }
        } else if (obj1 instanceof Collection) {
            if (((Collection) obj1).contains(obj2)) {
                //log.debug("obj1 is a collection and contains obj2");
                return true;
            }
        } else if (obj1.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(obj1); i++) {
                Object value = null;
                value = Array.get(obj1, i);

                if (value.equals(obj2)) {
                    //log.debug("obj1 is an array and contains obj2");
                    return true;
                }
            }
        } else if (obj1.equals(obj2)) {
            //log.debug("obj1 is an object and equals obj2");
            return true;
        }

        //log.debug("obj1 does not contain obj2: " + obj1 + ", " + obj2);
        return false;
    }
}

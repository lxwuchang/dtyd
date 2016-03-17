/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.validator.validators;


/**
 * <!-- START SNIPPET: javadoc -->
 * Field Validator that checks if the integer specified is within a certain range.
 * <!-- END SNIPPET: javadoc -->
 * 
 * 
 * <!-- START SNIPPET: parameters -->
 * <ul>
 * 		<li>fieldName - The field name this validator is validating. Required if using Plain-Validator Syntax otherwise not required</li>
 * 		<li>min - the minimum value (if none is specified, it will not be checked) </li>
 * 		<li>max - the maximum value (if none is specified, it will not be checked) </li>
 * </ul>
 * <!-- END SNIPPET: parameters -->
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: examples -->
 * 		&lt;validators>
 *           &lt;!-- Plain Validator Syntax --&gt;
 *           &lt;validator type="int">
 *               &lt;param name="fieldName"&gt;age&lt;/param&gt;
 *               &lt;param name="min"&gt;20&lt;/param&gt;
 *               &lt;param name="max"&gt;50&lt;/param&gt;
 *               &lt;message&gt;Age needs to be between ${min} and ${max}&lt;/message&gt;
 *           &lt;/validator&gt;
 *           
 *           &lt;!-- Field Validator Syntax --&gt;
 *           &lt;field name="age"&gt;
 *               &lt;field-validator type="int"&gt;
 *                   &lt;param name="min"&gt;20&lt;/param&gt;
 *                   &lt;param name="max"&gt;50&lt;/param&gt;
 *                   &lt;message&gt;Age needs to be between ${min} and ${max}&lt;/message&gt;
 *               &lt;/field-validator&gt;
 *           &lt;/field&gt;
 *      &lt;/validators&gt;
 * <!-- END SNIPPET: examples -->
 * </pre>
 * 
 * 
 * 
 * @author Jason Carreira
 * @version $Date: 2006-03-03 16:24:08 +0100 (Fr, 03 Mrz 2006) $ $Id: IntRangeFieldValidator.java 861 2006-03-03 15:24:08Z rainerh $
 */
public class IntRangeFieldValidator extends AbstractRangeValidator {

    Integer max = null;
    Integer min = null;


    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMax() {
        return max;
    }

    public Comparable getMaxComparatorValue() {
        return max;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMin() {
        return min;
    }

    public Comparable getMinComparatorValue() {
        return min;
    }
}

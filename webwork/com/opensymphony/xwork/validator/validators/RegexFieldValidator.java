/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.xwork.validator.validators;

import com.opensymphony.xwork.validator.ValidationException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * <!-- START SNIPPET: javadoc -->
 * Validates a string field using a regular expression.
 * <!-- END SNIPPET: javadoc -->
 * <p/>
 * 
 * 
 * <!-- START SNIPPET: parameters -->
 * <ul>
 * 	  <li>fieldName - The field name this validator is validating. Required if using Plain-Validator Syntax otherwise not required</li>
 *    <li>expression - The RegExp expression  REQUIRED</li>
 *    <li>caseSensitive - Boolean (Optional). Sets whether the expression should be matched against in a case-sensitive way. Default is <code>true</code>.</li>
 * </ul>
 * <!-- END SNIPPET: parameters -->
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 *    &lt;validators&gt;
 *        &lt;!-- Plain Validator Syntax --&gt;
 *        &lt;validator type="regex"&gt;
 *            &lt;param name="fieldName"&gt;myStrangePostcode&lt;/param&gt;
 *            &lt;param name="expression"&gt;&lt;![CDATA[([aAbBcCdD][123][eEfFgG][456])]]&lt;&gt;/param&gt;
 *        &lt;/validator&gt;
 *    
 *        &lt;!-- Field Validator Syntax --&gt;
 *        &lt;field name="myStrangePostcode"&gt;
 *            &lt;field-validator type="regex"&gt;
 *               &lt;param name="expression"&gt;&lt;![CDATA[([aAbBcCdD][123][eEfFgG][456])]]&gt;&lt;/param&gt;
 *            &lt;/field-validator&gt;
 *        &lt;/field&gt;
 *    &lt;/validators&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Quake Wang
 * @version $Date: 2006-04-16 11:23:32 +0200 (So, 16 Apr 2006) $ $Revision: 1000 $
 */
public class RegexFieldValidator extends FieldValidatorSupport {

    private String expression;
    private boolean caseSensitive = true;

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);
        // if there is no value - don't do comparison
        // if a value is required, a required validator should be added to the field
        if (value == null || expression == null) {
            return;
        }

        // XW-375 - must be a string
        if (!(value instanceof String)) {
            return;
        }

        // string must not be empty
        String str = ((String) value).trim();
        if (str.length() == 0) {
            return;
        }

        // match against expression
        Pattern pattern;
        if (isCaseSensitive()) {
            pattern = Pattern.compile(expression);
        } else {
            pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        }

        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            addFieldError(fieldName, object);
        }
    }

    /**
     * @return Returns the regular expression to be matched.
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the regular expression to be matched.
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return Returns whether the expression should be matched against in
     *         a case-sensitive way.  Default is <code>true</code>.
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Sets whether the expression should be matched against in
     * a case-sensitive way.  Default is <code>true</code>.
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

}

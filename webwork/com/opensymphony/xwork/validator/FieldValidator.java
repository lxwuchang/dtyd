/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.validator;


/**
 * The FieldValidator interface defines the methods to be implemented by FieldValidators,
 * which are used by the XWork validation framework to validate Action properties before
 * executing the Action.
 *
 * @author $author$
 * @version $Revision: 861 $
 */
public interface FieldValidator extends Validator {

    /**
     * Sets the field name to validate with this FieldValidator
     *
     * @param fieldName
     */
    void setFieldName(String fieldName);

    /**
     * @return the field name to be validated
     */
    String getFieldName();
}

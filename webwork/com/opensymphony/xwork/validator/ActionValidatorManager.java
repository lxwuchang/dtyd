/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.xwork.validator;

import java.util.List;

/**
 * <code>ActionValidatorManager</code>
 *
 * @author Rainer Hermanns
 * @version $Id: ActionValidatorManager.java 1281 2006-12-25 04:23:44Z tm_jee $
 */
public interface ActionValidatorManager {

    /**
     * Returns a list of validators for the given class and context. This is the primary
     * lookup method for validators.
     *
     * @param clazz the class to lookup.
     * @param context the context of the action class - can be <tt>null</tt>.
     * @return a list of all validators for the given class and context.
     */
    List getValidators(Class clazz, String context);

    /**
     * Validates the given object using action and its context.
     *
     * @param object the action to validate.
     * @param context the action's context.
     * @throws ValidationException if an error happens when validating the action.
     */
    void validate(Object object, String context) throws ValidationException;

    /**
     * Validates an action give its context and a validation context.
     *
     * @param object the action to validate.
     * @param context the action's context.
     * @param validatorContext
     * @throws ValidationException if an error happens when validating the action.
     */
    void validate(Object object, String context, ValidatorContext validatorContext) throws ValidationException;
    
    
    /**
     * Validates an action through a series of <code>validators</code> with 
     * the given <code>validatorContext</code>.
     * 
     * @param object
     * @param validators
     * @param validatorContext
     * @throws ValidationException
     */
    void validate(Object object, List validators, ValidatorContext validatorContext) throws ValidationException;
}

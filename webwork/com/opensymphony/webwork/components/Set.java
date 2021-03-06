package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 * <p>The set tag assigns a value to a variable in a specified scope. It is useful when you wish to assign a variable to a
 * complex expression and then simply reference that variable each time rather than the complex expression. This is
 * useful in both cases: when the complex expression takes time (performance improvement) or is hard to read (code
 * readability improvement).</P>
 * 
 * <p>The set tag accepts body. However the followings need to be take note when using set tag with body</p>
 * <ul>
 *    <li>body are treated as String and will not be parsed by OGNL</li>
 *    <li>body could be scriptlet or JSP tags, the String representation of scriptlet or JSP tags will be used</li>
 *    <li>A non-empty will take precedence if there's also a value attribute present, if the body is empty, then the value attribute will be used</li>
 * </ul>
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Parameters</b>
 *
 * <!-- START SNIPPET: params -->
 *
 * <ul>
 *
 * <li>name* (String): The name of the new variable that is assigned the value of <i>value</i></li>
 *
 * <li>value (Object): The value that is assigned to the variable named <i>name</i></li>
 *
 * <li>scope (String): The scope in which to assign the variable. Can be <b>application</b>, <b>session</b>,
 * <b>request</b>, <b>page</b>, or <b>action</b>. By default it is <b>action</b>.</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: params -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:set name="personName" value="person.name"/&gt;
 * Hello, &lt;ww:property value="%{#personName}"/&gt;. How are you?
 * 
 * &lt;ww:set name="personName"&gt;
 *   &lt;ww:property value="%{'some string'}" /&gt;
 * &lt;/ww:set&gt;
 * Hello, &lt;ww:property value="%{#personName}"/&gt;. How are you?
 * 
 * &lt;ww:set name="personName"&gt;
 *    &lt;c:set value="${person.name}" /&gt;
 * &lt;/ww:set&gt;
 * Hello, &lt;ww:property value="%{#personName}"/&gt;. How are you?
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision: 2871 $
 * @since 2.2
 *
 * @ww.tag name="set" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.SetTag"
 * description="Assigns a value to a variable in a specified scope"
 */
public class Set extends Component {
    protected String name;
    protected String scope;
    protected String value;

    public Set(OgnlValueStack stack) {
        super(stack);
    }
    
    /**
     * Returns true, so that we use EVAL_BODY_BUFFERED instead of EVAL_BODY_INCLUDE
     * @see com.opensymphony.webwork.components.Component#usesBody()
     */
    public boolean usesBody() {
    	return true;
    }

    public boolean end(Writer writer, String body) {
        OgnlValueStack stack = getStack();

        Object o = null;
        if (body != null && body.trim().length() > 0) {
        	o = body;
        	body=""; // empty the body, we don't want it to be writen out.
        }
        else {
        	if (value == null) {
        		value = "top";
        	}
        	o = findValue(value);
        }
        

        String name;
        if (altSyntax()) {
            name = findString(this.name, "name", "Name is required");
        } else {
            name = this.name;

            if (this.name == null) {
                throw fieldError("name", "Name is required", null);
            }
        }

        if ("application".equalsIgnoreCase(scope)) {
            stack.setValue("#application['" + name + "']", o);
        } else if ("session".equalsIgnoreCase(scope)) {
            stack.setValue("#session['" + name + "']", o);
        } else if ("request".equalsIgnoreCase(scope)) {
            stack.setValue("#request['" + name + "']", o);
        } else if ("page".equalsIgnoreCase(scope)) {
            stack.setValue("#attr['" + name + "']", o, false);
        }
        //WW-796: SetTag "scope" attribute do not put the object to stack if the scope is given.
        stack.getContext().put(name, o);


        return super.end(writer, body);
    }

    /**
     * The name of the new variable that is assigned the value of <i>value</i>
     * @ww.tagattribute required="true" type="String"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The scope in which to assign the variable. Can be <b>application</b>, <b>session</b>, <b>request</b>, <b>page</b>, or <b>action</b>.
     * @ww.tagattribute required="false" type="String" default="action"
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * The value that is assigned to the variable named <i>name</i>
     * @ww.tagattribute required="false"
     */
    public void setValue(String value) {
        this.value = value;
    }
}

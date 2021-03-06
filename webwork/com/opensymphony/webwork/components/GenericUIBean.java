package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.util.ContainUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * Renders an custom UI widget using the specified templates. Additional objects can be passed in to the template
 * using the param tags.<p/>
 * 
 * <b>Freemarker:</b><p/>
 * Objects provided can be retrieve from within the template via $parameters._paramname_.<p/>
 * 
 * <b>Jsp:</b><p/>
 * Objects provided can be retrieve from within the template via &lt;ww:property value="%{parameters._paramname_}" /&gt;<p/>
 *
 *
 * In the bottom JSP and Velocity samples, two parameters are being passed in to the component. From within the
 * component, they can be accessed as:- <p/>
 * 
 * <b>Freemarker:</b><p/>
 * $parameters.get('key1') and $parameters.get('key2') or $parameters.key1 and $parameters.key2<p/>
 * 
 * <b>Jsp:</b><p/>
 * &lt;ww:property value="%{parameters.key1}" /&gt; and &lt;ww:property value="%{'parameters.key2'}" /&gt; or
 * &lt;ww:property value="%{parameters.get('key1')}" /&gt; and &lt;ww:property value="%{parameters.get('key2')}" /&gt;<p/>
 *
 * Currently, your custom UI components can be written in Velocity, JSP, or Freemarker, and the correct rendering
 * engine will be found based on file extension.<p/>
 *
 * <b>Remember:</b> the value params will always be resolved against the OgnlValueStack so if you mean to pass a
 * string literal to your component, make sure to wrap it in quotes i.e. value="'value1'" otherwise, the the value
 * stack will search for an Object on the stack with a method of getValue1(). (now that i've written this, i'm not
 * entirely sure this is the case. i should verify this manana)<p/>
 * 
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * JSP
 *     &lt;ww:component template="/my/custom/component.vm"/&gt;
 *     
 *       or
 *
 *     &lt;ww:component template="/my/custom/component.vm"&gt;
 *       &lt;ww:param name="key1" value="value1"/&gt;
 *       &lt;ww:param name="key2" value="value2"/&gt;
 *     &lt;/ww:component&gt;
 *
 * Velocity
 *     #wwcomponent( "template=/my/custom/component.vm" )
 *
 *       or
 *
 *     #wwcomponent( "template=/my/custom/component.vm" )
 *       #wwparam( "name=key1" "value=value1" )
 *       #wwparam( "name=key2" "value=value2" )
 *     #end
 *     
 * Freemarker
 *    &lt;@ww.component template="/my/custom/component.ftl" />
 *    
 *      or
 *      
 *    &lt;@ww.component template="/my/custom/component.ftl"&gt;
 *       &lt;@ww.param name="key1" value="%{'value1'}" /&gt;
 *       &lt;@ww.param name="key2" value="%{'value2'}" /&gt;
 *    &lt;/@ww.component&gt;
 *    
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * <p/>
 * 
 * <b>NOTE:</b>
 * <!-- START SNIPPET: note -->
 * 
 * If Jsp is used as the template, the jsp template itself must lie within the 
 * webapp itself and not the classpath. Unlike Freemarker or Velocity, JSP template
 * could not be picked up from the classpath.
 * 
 * <!-- END SNIPPET: note -->
 * 
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @author tm_jee
 * @version $Date: 2006-06-16 09:55:54 +0200 (Fri, 16 Jun 2006) $ $Revision: 2608 $ 
 * @since 2.2
 *
 * @ww.tag name="component" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.ComponentTag"
 * description="Render a custom ui widget"
 */
public class GenericUIBean extends UIBean {
    private final static String TEMPLATE = "empty";

    public GenericUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public boolean contains(Object obj1, Object obj2) {
        return ContainUtil.contains(obj1, obj2);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

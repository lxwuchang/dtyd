/**
* $Id: EncodeUtil.java,v 1.1 2012/05/17 22:50:07 wuchang Exp $
*
*/
package com.yundastat.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.JavaScriptUtils;






/**
 * 
 * @author jacky.chenb
 *
 */
public class EncodeUtil {
	
	public static final String appEncoding = "GBK";
	public static final String dbEncoding = "GBK";
	
	/**
	 * �����ݿ���ַ��������ַ�Ĵ洢�ֽ���
	 */
	public static int getDBLength(String str) {
		if (str == null) {
			return 0;
		}
		
		try {
			return str.getBytes(dbEncoding).length;
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * ���Ӧ�õ��ַ��ַ�ת�����ֽ�����
	 */
	public static byte[] toBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(appEncoding);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException(e);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * ���Ӧ�õ��ַ��ֽ�����ת�����ַ�
	 */
	public static String toString(byte[] bytes) {
		if (bytes != null) {
			try {
				return new String(bytes, appEncoding);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException(e);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * ���Ӧ�õ��ַ���ַ����URL����
	 */
	public static String url(Object obj) {
		if (obj == null) {
			return "";
		}
		
		try {
			return URLEncoder.encode(obj.toString(), appEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * �Ե���˫����е������ַ����Ԥ���?��ֹjavascript�������?
	 * ������ű�����ʱ������Ԥ���?���磺
	 * <script>
	 * 	 var s = "<%= EncodeUtil.script(value) %>";
	 * </script>
	 */
	public static String javascript(Object obj) {
		if (obj == null) {
			return "";
		}
		
        return JavaScriptUtils.javaScriptEscape(obj.toString());
	}
	
	/**
	 * �������ַ�<>"&�Ƚ���Ԥ���?��ֹHTML�������?
	 * �����input��area��Ԫ�ص�value����ʱ������Ԥ���?���磺
	 * <input name="foo" value="<%= EncodeUtil.html(value) %>">
	 */
	public static String html(Object obj) {
		if (obj == null) {
			return "";
		}
		
		return escapeHtml(obj.toString());
	}
	
	/**
	 * �������ַ�<>"&�Ƚ���Ԥ���?��ֹXML�������?
	 * �����XML������ֵʱ������Ԥ���?���磺
	 * <node attribute="<%= EncodeUtil.xml(value) %>" />
	 */
	public static String xml(Object obj) {
		if (obj == null) {
			return "";
		}
		
		return escapeXml(obj.toString());
	}
	
	/**
     * Escapes the characters in a <code>String</code> to be suitable to pass to an SQL query.
     * <br />
     * Delegates the process to {@link StringEscapeUtils#escapeSql(String)}.
     *
     * @param string the string to escape, may be null
     * @return a new String, escaped for SQL, <code>null</code> if null string input
     *
     * @see StringEscapeUtils#escapeSql(String)
     */
    public String sql(Object string)
    {
        if (string == null)
        {
            return null;
        }
        return escapeSql(String.valueOf(string));
    }
    
    
    /**
     * ת�廻��Ϊ\r\nΪ<br>
     * @param content
     * @return 
     */
    public String traslateRT(String content){
    	if(StringUtil.isEmpty(content)){
    		return null;
    	}
       return content.replaceAll("\r\n", "  ");
    }

    /* ============================================================================ */
    /* SQL��䡣 */
    /* ============================================================================ */

    /**
     * ��SQL���Ĺ�����ַ����ת�塣
     * <p>
     * ���磺
     * 
     * <pre>
     * statement.executeQuery(&quot;SELECT * FROM MOVIES WHERE TITLE='&quot; + StringEscapeUtil.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
     * </pre>
     * 
     * </p>
     * <p>
     * Ŀǰ���˷���ֻ�������ת������������ţ�<code>"McHale's Navy"</code>ת����<code>"McHale''s
     * Navy"</code>���������ַ��а��<code>%</code>��<code>_</code>�ַ�
     * </p>
     * 
     * @param str Ҫת����ַ�
     * @return ת�����ַ����ԭ�ַ�Ϊ<code>null</code>���򷵻�<code>null</code>
     * @see <a href="http://www.jguru.com/faq/view.jsp?EID=8881">faq</a>
     */
    public static String escapeSql(String str) {
        return StringUtil.replace(str, "'", "''");
    }

    /* ============================================================================ */
    /* HTML��XML�� */
    /* ============================================================================ */

    /**
     * ���HTML�Ĺ��򣬽��ַ��еĲ����ַ�ת����ʵ����롣
     * <p>
     * ���磺<code>"bread" & "butter"</code>����ת����<tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * ֧������HTML 4.0 entities��
     * </p>
     * 
     * @param str Ҫת����ַ�
     * @return ��ʵ�����ת����ַ����ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
     * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
     * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
     */
    public static String escapeHtml(String str) {
        return escapeEntities(Entities.HTML40, str);
    }
    
    /**
     * ���ָ���Ĺ��򣬽��ַ��еĲ����ַ�ת����ʵ����롣
     * 
     * @param entities ʵ�弯��
     * @param str Ҫת����ַ�
     * @return ��ʵ�����ת����ַ����ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeEntities(Entities entities, String str) {
        if (str == null) {
            return null;
        }

        try {
            StringWriter out = new StringWriter(str.length());

            if (escapeEntitiesInternal(entities, str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringWriter�����ܷ�������쳣
        }
    }
    
    /**
     * ���XML�Ĺ��򣬽��ַ��еĲ����ַ�ת����ʵ����롣
     * <p>
     * ���磺<code>"bread" & "butter"</code>����ת����<tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * ֻת��4�ֻ��XMLʵ�壺<code>gt</code>��<code>lt</code>��<code>quot</code>��<code>amp</code>�� ��֧��DTD���ⲿʵ�塣
     * </p>
     * 
     * @param str Ҫת����ַ�
     * @return ��ʵ�����ת����ַ����ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static String escapeXml(String str) {
        return escapeEntities(Entities.XML, str);
    }
    /**
     * ���ַ��еĲ����ַ�ת����ʵ����롣
     * 
     * @param entities ʵ�弯��
     * @param str Ҫת����ַ�
     * @param out �ַ������������Ϊ<code>null</code>
     * @return ����ַ�û�б仯���򷵻�<code>false</code>
     * @throws IllegalArgumentException ���<code>entities</code>�������Ϊ<code>null</code>
     * @throws IOException ������ʧ��
     */
    private static boolean escapeEntitiesInternal(Entities entities, String str, Writer out) throws IOException {
        boolean needToChange = false;

        if (entities == null) {
            throw new IllegalArgumentException("The Entities must not be null");
        }

        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            String entityName = entities.getEntityName(ch);

            if (entityName == null) {
                out.write(ch);
            } else {
                out.write('&');
                out.write(entityName);
                out.write(';');

                // ���øı��־
                needToChange = true;
            }
        }

        return needToChange;
    }



}
/**
 * 
 */
package com.yundastat.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.ObjectFindingVisitor;
import org.htmlparser.visitors.TextExtractingVisitor;

/**
 * @author Administrator
 * 
 */
public class ParseLocalHtml {

	public final static String baseurl = "http://nbsw.yundasys.com/nbsw/go.php?wen=";
	public final static String baseurl2 = "http://qz.yundasys.com:18090/ws/kjcx/cxend.jsp?wen=";
    public final static String tmfpurl="http://kjcx.yundasys.com/kjcx/ajax.php";//获取面单分配
	/**
	 * @param args
	 * @throws ParserException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParserException, IOException {
		// TODO Auto-generated method stub

		BufferedReader reader = new BufferedReader(
				new FileReader("D:/mail.cfg"));

		String mailid = "";

		while ((mailid = reader.readLine()) != null) {

			String url1 = baseurl + mailid;
			String mailidencode = "";
			Parser parser = new Parser(url1);

			ObjectFindingVisitor visitor = new ObjectFindingVisitor(
					org.htmlparser.tags.FormTag.class);
			parser.visitAllNodesWith(visitor);
			Node[] links = visitor.getTags();
			for (int i = 0; i < links.length; i++) {
				FormTag linkTag = (FormTag) links[i];
				String link = linkTag.getAttribute("action");

				mailidencode = link.substring(link.indexOf("wen=") + 4, link
						.indexOf("&jmm"));
			}

			//System.out.println(mailidencode);
			String url2 = baseurl2+ mailidencode;
			Parser parser2 = new Parser(url2);

			//System.out.println(url2);
			ObjectFindingVisitor visitor2 = new ObjectFindingVisitor(
					org.htmlparser.tags.ScriptTag.class);
			parser2.visitAllNodesWith(visitor2);
			Node[] scriptTag = visitor2.getTags();
			for (int i = 0; i < scriptTag.length; i++) {
				ScriptTag linkTag = (ScriptTag) scriptTag[i];
				String str = linkTag.getScriptCode();

				if (str.indexOf("binTable") != -1) {

					String st = str.substring(str.indexOf("b4.de(") + 7, str
							.indexOf("\"));"));

					//System.out.println(st);

					System.out.println(ParseHTML.transformToNormalText(st)
							+ "r>");

					break;

				}
			}

		}

	}

}

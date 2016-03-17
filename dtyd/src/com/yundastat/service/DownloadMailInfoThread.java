/**
 * 
 */
package com.yundastat.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.ObjectFindingVisitor;

/**
 * @author Administrator
 *
 */
public class DownloadMailInfoThread implements Runnable {

	private List<String> maillist=null;
	private String filePath=null;
	private String loginid="";
	public DownloadMailInfoThread(List maillist,String loginid,String filepath){
		this.maillist=maillist;
		this.filePath=filepath;
		this.loginid=loginid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		 OutputStream output;
			
		
		String htmlstrs="";
		int page=0;
		for(int k=0;k<maillist.size();k++){
		
	 		String htmlstr="";
	 		String mailid= maillist.get(k);
			 String mailidencode = "";
		 	
			//获取面单的分配记录
			 DefaultHttpClient httpclient = new DefaultHttpClient();
			String url0=ParseLocalHtml.tmfpurl;

	        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	        formparams.add(new BasicNameValuePair("lb", "tmfp"));
	        formparams.add(new BasicNameValuePair("txm", mailid));
	        UrlEncodedFormEntity entity=null;
			try {
				entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        HttpPost httppost = new HttpPost(url0);
	        httppost.setEntity(entity);
	     

	        HttpResponse response1;
			try {
				response1 = httpclient.execute(httppost);
			
	        String wd="";
	        String fb="";
	        
	            
	            HttpEntity entity1 = response1.getEntity();
	            // do something useful with the response body
	            // and ensure it is fully consumed           
	            InputStream input=   entity1.getContent();  

	            if(input.available()==0){
	            	
	            }else{
	            byte [] bt=new byte[2048];        
	            input.read(bt);
	            input.close();           
	          
	           JSONObject object = JSONObject.fromObject(new String(bt));
	           wd=object.getString("wd");
	           fb=object.getString("fb");
	          
	            }
	            htmlstr=" <div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>		  <tr>		   <th width='20%'>快件单号</th>  <th width='30%'>分配网点</th>  <th width='50%'>分配业务员</th></tr> <tr><td> "+mailid+"</td>"+"<td>"+wd+"</td><td>"+fb+"</td></tr></table></div></div></div>";
	            
//	         System.out.println(   object.getString("fb"));
//	           
//	         System.out.println( object.getString("wd"));
//	           
	             
	            EntityUtils.consume(entity1);
	        } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            httppost.releaseConnection();
	        }
	        
			
			//获取快件的收发详情
			
			try {
			//获取快件跟踪记录
			//1.获取mailcode
			String url9 = ParseLocalHtml.baseurl+ mailid;
			 
			Parser parser;
			
				parser = new Parser(url9);
			
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
			} catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String url2="";
			String maildetail="";
			try {
			  url2 = ParseLocalHtml.baseurl2+ mailidencode;
			Parser parser2;
			
				parser2 = new Parser(url2);
			
			
			//3.请求并且组装快件跟踪记录
			//System.out.println(url2);
			
			ObjectFindingVisitor visitor2 = new ObjectFindingVisitor(
					org.htmlparser.tags.ScriptTag.class);
			parser2.visitAllNodesWith(visitor2);
			Node[] scriptTag = visitor2.getTags();
			for (int i = 0; i < scriptTag.length; i++) {
				ScriptTag linkTag = (ScriptTag) scriptTag[i];
				String str = linkTag.getScriptCode();

				if (str!=null&&str.indexOf("binTable") != -1) {

					String st = str.substring(str.indexOf("b4.de(") + 7, str
							.indexOf("\"));"));

					//System.out.println(st);

					maildetail="  <div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>		  <tr>		   <th width='15%'>扫描时间</th>  <th width='15%'>入库时间</th>  <th width='10%'>扫描类型</th> <th width='30%'>跟踪记录</th> <th width='10%'>扫描单号</th> <th width='10%'>业务员</th> <th width='10%'>重量(千克)</th>   </tr>"+ParseHTML.transformToNormalText(st)
							+ "r>	</table>      </div></div></div>";

					break;

				}
			}
			} catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			String querymailidurl="";
			try {
			//2.查找快件详情的url
			//System.out.println(url2);
			
			Parser parser4 = new Parser(url2);
			
			ObjectFindingVisitor visitor1 = new ObjectFindingVisitor(
					org.htmlparser.tags.Div.class);
			
				parser4.visitAllNodesWith(visitor1);
		
			Node[] divTag = visitor1.getTags();
			for (int i = 0; i < divTag.length; i++) {
				Div linkTag = (Div) divTag[i];
				String str = linkTag.getAttribute("id");

				if (str!=null&&str.equals("div_ld")) {

					String html=linkTag.toHtml();
					
					querymailidurl=html.substring(html.indexOf("src")+5, html.indexOf("'></iframe>"));
				
					break;

				}
			}
			
			} catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			
			try {
			String url3 = querymailidurl;
			Parser parser3;
			
				parser3 = new Parser(url3);
			
			ObjectFindingVisitor visitor3 = new ObjectFindingVisitor(
					org.htmlparser.tags.TableTag.class);
			parser3.visitAllNodesWith(visitor3);
			Node[] tableTags = visitor3.getTags();
			for (int i = 0; i < tableTags.length; i++) {
				TableTag linkTag = (TableTag) tableTags[i];
				if(linkTag.getAttribute("border").equals("1"))
				  htmlstr=htmlstr+"<div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>	" +linkTag.getChildrenHTML()+"</table> </div></div></div>";
	              
			}
		
			
			} catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			htmlstr=htmlstr+maildetail;
			
			htmlstrs=htmlstrs+htmlstr;
			
			if(k%50==0&&k!=0){
				
				
				try {
					output = new FileOutputStream(filePath+"downloadmailInfolist_"+loginid+"_"+page+".vm");
					try {
						output.write(htmlstrs.getBytes());
						output.flush();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							output.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				page++;
				htmlstrs="";
			 try {
	                Thread.sleep(500);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
			}
			
		}
		
		
		try {
			output = new FileOutputStream(filePath+"downloadmailInfolist_"+loginid+"_"+page+".vm");
			try {
				output.write(htmlstrs.getBytes());
				output.flush();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	}

}

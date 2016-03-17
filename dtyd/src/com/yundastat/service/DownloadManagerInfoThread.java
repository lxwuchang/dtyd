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
public class DownloadManagerInfoThread implements Runnable {

	private List<String> maillist=null;
	private String filePath=null;
	private String loginid="";
	public DownloadManagerInfoThread(List maillist,String loginid,String filepath){
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
		    String fenpeimanager="";
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
		           
		           fenpeimanager=object.getString("fb");
		          
		            }
		             
		             EntityUtils.consume(entity1);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
		            httppost.releaseConnection();
		        }
		        
				
				//获取快件的收发详情
				
				
				//获取快件跟踪记录
				//1.获取mailcode
				String url1 = ParseLocalHtml.baseurl+ mailid;
			 	Parser parser;
				try {
					parser = new Parser(url1);
			

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
				} catch (ParserException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.out.println(mailidencode);
				
				String url2 = ParseLocalHtml.baseurl2+ mailidencode;
				Parser parser2;
				try {
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
						
						String html=ParseHTML.transformToNormalText(st);
						if(html==null ||html.equals("")){
							html="";
							
						}else {
							
							html=html+"r>";
						   String [] htmlarray=  html.split("</td>");
						
						if(htmlarray.length>6)
							html=htmlarray[5].substring(4);
			 			
						}
						
						if(html.equals("")||fenpeimanager.indexOf(html)==-1){
							  htmlstr="<tr> <td style='background-color:red;color:white'>"+mailid+"</td><td style='background-color:red;color:white'>"+html+"</td><td style='background-color:red;color:white'>"+fenpeimanager+"</td><tr>";
							
							}else{
								htmlstr="<tr> <td>"+mailid+"</td><td>"+html+"</td><td>"+fenpeimanager+"</td><tr>";
									
								
							}
						break;

					}
				}	
				} catch (ParserException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 	
			htmlstrs=htmlstrs+htmlstr;
			
			if((k%50==0&&k!=0)||k==(maillist.size()-1)){
				
				
				try {
					output = new FileOutputStream(filePath+"downloadmanagerInfolist_"+loginid+"_"+page+".vm");
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
		
		
		 
		 
	}

}

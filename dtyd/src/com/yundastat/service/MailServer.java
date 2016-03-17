/**
 * 
 */
package com.yundastat.service;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
 * @author Simon
 *
 */
public class MailServer {
	
	
 public static final String baseurl ="C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/YunDaStat/conf/";
 
 
 /***
  * 
  * 该函数的功能是当滚动条接近底部时，数据会自动加载，这里的数据是真正揽件人和面单分配
 * @throws FileNotFoundException 
* @throws IOException 
  * */

	public String loadMoreManagerInfoByThread(int count,int mailnum, String loginid) throws FileNotFoundException{
	
		//返回0表示已经结束显示，返回1表示未加载完
		String filename=baseurl+"downloadmanagerInfolist_"+loginid+"_"+(count-1)+".vm";
		String htmlstr="0";
	
		int total=mailnum/50;		
		if(total>=(count-1)){
		File f=new File(filename);
		 if(!f.exists()){
			htmlstr= "1";
		 }else{
				BufferedReader reader =new BufferedReader(new FileReader(filename));
				String s="";
				try {
					while((s=reader.readLine())!=null){
						htmlstr=htmlstr+s;
					}
					
				 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					   f.delete();
				}
			 
		 }
		}
		
		
	 return htmlstr;
	}

 /***
  * 
  * 该函数的功能是当滚动条接近底部时，数据会自动加载，这里的数据是真正揽件人和面单分配
 * @throws FileNotFoundException 
* @throws IOException 
  * */

	public String loadMoreMailInfoByThread(int count,int mailnum, String loginid) throws FileNotFoundException{
	
		//返回0表示已经结束显示，返回1表示未加载完
		String filename=baseurl+"downloadmailInfolist_"+loginid+"_"+(count-1)+".vm";
		String htmlstr="0";
	
		int total=mailnum/50;		
		if(total>=(count-1)){
		File f=new File(filename);
		 if(!f.exists()){
			htmlstr= "1";
		 }else{
			 

				BufferedReader reader =new BufferedReader(new FileReader(filename));
				String s="";
				try {
					while((s=reader.readLine())!=null){
						htmlstr=htmlstr+s;
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  f.delete();
				}
			 
		 }
		}
		
		
	 return htmlstr;
	}
   /***
    * 
    * 该函数的功能是当滚动条接近底部时，数据会自动加载，这里的数据是真正揽件人和面单分配
 * @throws IOException 
    * */
 
	public String loadMoreManagerInfo(int count,int mailnum, String loginid) throws IOException{
		
		
		//默认是20个显示一次，
		//当拉动一次时,往后加20
		   String htmlstr="";
		   String filepath=baseurl+"maillist_"+loginid+".vm";
			
			if((count-1)*50<=mailnum){
		    try {
			
			String tmp="";
			int total=mailnum-(count-1)*50;
				total=50>total?total:50;
				BufferedReader reader =new BufferedReader(new FileReader(filepath));
				
				
			    int flag=0;//flag=1 表示该数据已经没有了。		
			 
				//先读取掉N条mailid
				 for(int i=0; i< ((count-1)*50);i++){
				    	tmp= reader.readLine();
				    if(tmp==null){
				    	flag=1;
				    	break;
				    }
				}
				
				 
				 ArrayList list=new ArrayList();
				 for(int s=0;s<total;s++){
					 String t=reader.readLine();
					
					 list.add(t);
				   
				 }
					
				  
					reader.close();
			 
				if(flag==1){
					
				}else{
					//还有数据
				      
				     for(int k=0;k<total;k++){
				    	
				    	 String tmpStr=(String) list.get(k);
				    	 
				    	 if(tmpStr!=null){
				    		 try {
								htmlstr+=createOnlineManagerInfo(tmpStr);
							} catch (ParserException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    		 
				    	 }
				    	 
				    	 
				     }
				     
					
				}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
			}else{
				
				htmlstr="";
				
			}
		   
		 return htmlstr;
		
	}
	
	/***
	 * 
	 * 该函数的功能是当滚动条接近底部时，会自动加载数据，现在默认是加载20
	 * @throws IOException 
	 * */
	public String loadMoreMailInfo(int count,int mailnum,String loginid) throws IOException{
		
		

		//默认是20个显示一次，
		//当拉动一次时,往后加20
		   String htmlstr="";
		   String filepath=baseurl+"maillist_"+loginid+".vm";
			
			if((count-1)*20<=mailnum){
		    try {
			
			String tmp="";
			int total=mailnum-(count-1)*20;
				total=20>total?total:20;
				BufferedReader reader =new BufferedReader(new FileReader(filepath));
				
				
			    int flag=0;//flag=1 表示该数据已经没有了。		
			 
				//先读取掉N条mailid
				 for(int i=0; i< ((count-1)*20);i++){
				    	tmp= reader.readLine();
				    if(tmp==null){
				    	flag=1;
				    	break;
				    }
				}
				
				 
				 ArrayList list=new ArrayList();
				 for(int s=0;s<total;s++){
					 String t=reader.readLine();
					
					 list.add(t);
				   
				 }
					
				  
					reader.close();
			 
				if(flag==1){
					
				}else{
					//还有数据
				      
				     for(int k=0;k<total;k++){
				    	
				    	 String tmpStr=(String) list.get(k);
				    	 
				    	 if(tmpStr!=null){
				    		 try {
								htmlstr+=createOnlineMailInfo(tmpStr);
							} catch (ParserException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    		 
				    	 }
				    	 
				    	 
				     }
				     
					
				}
				 
			 
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
			}else{
				
				htmlstr="";
				
			}
		   
		 return htmlstr;
		
	}
	 

	private String createOnlineMailInfo(String mailid) throws ParserException, IOException{
		
		String htmlstr="";
		
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
     

        HttpResponse response1 = httpclient.execute(httppost);
        String wd="";
        String fb="";
        try {
            
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
//         System.out.println(   object.getString("fb"));
//           
//         System.out.println( object.getString("wd"));
//           
             
            EntityUtils.consume(entity1);
        } finally {
            httppost.releaseConnection();
        }
        
		
		//获取快件的收发详情
		
		
		//获取快件跟踪记录
		//1.获取mailcode
		String url1 = ParseLocalHtml.baseurl+ mailid;
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
		
		String url2 = ParseLocalHtml.baseurl2+ mailidencode;
		Parser parser2 = new Parser(url2);
		
		//3.请求并且组装快件跟踪记录
		//System.out.println(url2);
		String maildetail="";
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
		
		
		//2.查找快件详情的url
		//System.out.println(url2);
		String querymailidurl="";
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
		
		
		String url3 = querymailidurl;
		Parser parser3 = new Parser(url3);
		
		ObjectFindingVisitor visitor3 = new ObjectFindingVisitor(
				org.htmlparser.tags.TableTag.class);
		parser3.visitAllNodesWith(visitor3);
		Node[] tableTags = visitor3.getTags();
		for (int i = 0; i < tableTags.length; i++) {
			TableTag linkTag = (TableTag) tableTags[i];
			if(linkTag.getAttribute("border").equals("1"))
			  htmlstr=htmlstr+"<div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>	" +linkTag.getChildrenHTML()+"</table> </div></div></div>";
              
		}
	
		htmlstr=htmlstr+maildetail;
		
		return htmlstr;
	}


	/**
	 *  该函数实现的功能是：
	 *  1.对面单进行排序--先不做
	 *  2.对获取面单分配记录
	 *  3.获取快件的详细跟踪记录
	 * 
	 * */
	
	private String createOnlineManagerInfo(String mailid) throws ParserException, IOException{
		 System.out.println(mailid);
		String htmlstr="";
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
     

        HttpResponse response1 = httpclient.execute(httppost);
        try {
            
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
           
       //  htmlstr=" <div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>		  <tr>		   <th width='20%'>快件单号</th>  <th width='30%'>分配网点</th>  <th width='50%'>分配业务员</th></tr> <tr><td> "+mailid+"</td>"+"<td>"+object.getString("wd")+"</td><td>"+object.getString("fb")+"</td></tr></table></div></div></div>";
//         System.out.println(   object.getString("fb"));
//           
//         System.out.println( object.getString("wd"));
//           
             
            EntityUtils.consume(entity1);
        } finally {
            httppost.releaseConnection();
        }
        
		
		//获取快件的收发详情
		
		
		//获取快件跟踪记录
		//1.获取mailcode
		String url1 = ParseLocalHtml.baseurl+ mailid;
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
		
		String url2 = ParseLocalHtml.baseurl2+ mailidencode;
		Parser parser2 = new Parser(url2);
		
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
				//System.out.println(st);
// <div class='imp_mod_inner'><div class='cc_para imp_intro_para'><div class='cc_link_sign_box2'> <table id='mytable'><tr><th width='30%'>快件单号</th>  <th width='35%'>揽件人</th>  <th width='35%'>面单分配</th></tr>"+html
		//		+ "r>	</table>      </div></div></div>
				
				
				}
				
				if(html.equals("")||fenpeimanager.indexOf(html)==-1){
					  htmlstr="<tr> <td style='background-color:red;color:white'>"+mailid+"</td><td style='background-color:red;color:white'>"+html+"</td><td style='background-color:red;color:white'>"+fenpeimanager+"</td><tr>";
					
					}else{
						htmlstr="<tr> <td>"+mailid+"</td><td>"+html+"</td><td>"+fenpeimanager+"</td><tr>";
							
						
					}
				break;

			}
		}	
	 
		
		return htmlstr;
	}

}

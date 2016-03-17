/**
 * 
 */
package com.yundastat.util;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author hbj
 * @date 15 May 2009
 */
/**
 * 
 */

public class FileUploadUtil  {
	
	public static boolean uploadFile(File file, String type, String name, String savePath,String appendix) {
		if(!savePath.endsWith("/")) 
			savePath += "/";
		String fullpath = savePath + appendix+ name+"."+type;
		try {
			FileUtils.copyFile(file, new File(fullpath));//ֻ�ǽ�ͼƬ��������Ӧ��Ŀ¼��
		} catch(IOException e) {
			e.printStackTrace();
		
		}		
		return true;
	}
	

	public static  boolean validateSize(File file,String uploadSize) {
		if(file.length() > Long.valueOf(uploadSize)) {
			return false;
		}
		return true;
	}
	
	/**
	 * ����ļ�������
	 * 
	 * **/
	public static boolean validateFormat(String type) {
		int index=-2;
		if(type != null && ((index=type.indexOf("."))!=-1)&&type.substring(index).length()!=0) {
			return true;
		} else {
			System.out.println(" format error!");
			return false;
		}
	}

}

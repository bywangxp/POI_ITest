package com.bywangxp.util;

import java.io.File;

public class PPTUtils {
	//根据路径获取文件名
	public static String getFilename(String filepath){
		String[] split = filepath.split("/");
		//获取到带后缀的全文件名
		String fileFullname = split[split.length-1];
		int lastIndexOf = fileFullname.lastIndexOf(".");
		return fileFullname.substring(0, lastIndexOf);
	}
/*	public static String getShortname(String filepath) {
		String filename = getFilename(filepath);
		System.out.println(filename);
		int indexOf = filename.lastIndexOf(".");
		System.out.println(indexOf);
		String name = filename.substring(0,2);
		System.out.println(name);
		return name;
		
	}*/
	//获取后缀
	public static String getSuffixname(String filepath){
		String[] split = filepath.split("/");
		String fileFullname = split[split.length-1];
		int lastIndexOf = fileFullname.lastIndexOf(".");
		return fileFullname.substring(lastIndexOf+1,fileFullname.length());
	}
	//判断是否是ppt 方法有待完善
	public static boolean isPPT(String filepath) throws Exception{
		String suffixname = getSuffixname(filepath);
        String name = getFilename(filepath);
        if("ppt".equals(suffixname)){
        	return true;
        }
		return false;
	}
	public static boolean createDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (dir.exists()) {  
	            return false;  
	        }  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	        //创建目录  
	        if (dir.mkdirs()) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	     
	}
}

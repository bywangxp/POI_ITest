package com.bywangxp.poi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;

import com.bywangxp.util.PPTUtils;
public class UploadTest {
	public static void main(String[] args) throws Exception {
		  String file1="File/1.Why-Software-Engineering.ppt";
		  String file2="File/Chapter-4_Capturing_the_Requirements.ppt";
		  String file3="File/Chapter08.ppt";
		  String file4="File/Design Patterns.ppt";
		  String file5="File/pptx测试.pptx";
		  long start=System.currentTimeMillis();
		  getFile(file1);
	      long end=System.currentTimeMillis();
		  System.out.println("file1整个解析流程用时:"+(end-start)/1000+"s");
		  long start2=System.currentTimeMillis();
		  getFile(file2);
		  long end2=System.currentTimeMillis();
		  System.out.println("file2整个解析流程用时:"+(end2-start2)/1000+"s");
		  long start3=System.currentTimeMillis();
		  getFile(file3);
		  long end3=System.currentTimeMillis();
		  System.out.println("file3整个解析流程用时:"+(end3-start3)/1000+"s");
		  long start4=System.currentTimeMillis();
		  getFile(file4);
		  long end4=System.currentTimeMillis();
		  System.out.println("file4整个解析流程用时:"+(end4-start4)/1000+"s");
		  long start5=System.currentTimeMillis();
		  getFile(file5);
		  long end5=System.currentTimeMillis();
		  System.out.println("start5整个解析流程用时:"+(end5-start5)/1000+"s");
		 
		
		  
		
	    
	}
	public static void test(String file) throws Exception{
		long start=System.currentTimeMillis();
		 getFile(file);
		  long end=System.currentTimeMillis();
	      System.out.println("整个解析流程用时:"+(end-start)/1000+"s");
	}
	//读取File目录下文件到PPTFiles目录下 重命名文件名  time_PPT_soucename
	public static void getFile(String filepath) throws Exception{
	    Long currentTime=System.currentTimeMillis();
		String sourceName = PPTUtils.getFilename(filepath);
		FileInputStream inputStream=new FileInputStream(filepath);
		String name_with_suffix=null;
		if(PPTUtils.isPPT(filepath)){
			name_with_suffix=currentTime+"_"+"PPT_"+sourceName+".ppt";
		}else{
			name_with_suffix=currentTime+"_"+"PPTX_"+sourceName+".pptx";
		}
		FileOutputStream out=new FileOutputStream("PPTFiles/"+name_with_suffix);
		//文件写
		byte[] buffer = new byte[1024]; 
        int length=0;
		while((length=inputStream.read(buffer))!=-1){
			 out.write(buffer, 0, length);
		}
        out.close();
        //生成该文件的目录结构
        String name_no_suffix=PPTUtils.getFilename(name_with_suffix);
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/images");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/individual_ppts");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/pictures/");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/texts/");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/pdf/");
        System.out.println("文件录入，创建文件夹成功");
        //用同一个输入流操作
        int size=0;
        if(PPTUtils.isPPT(filepath)){
        	System.out.println("ppt操作。。。");
//        	HSLFSlideShowImpl hslfSlideShowImpl = new HSLFSlideShowImpl(inputStream);
        	GetInfoPPT.get_Texts_PPT(filepath, name_no_suffix);
        	GetInfoPPT.get_Pictures_PPT(filepath, name_no_suffix);
        	size = GetInfoPPT.get_Images_PPT(filepath, name_no_suffix);
        }else{
        	System.out.println("pptx操作。。。");
         	GetInfoPPT.get_Texts_PPTX(filepath, name_no_suffix, inputStream);
        	GetInfoPPT.get_Pictures_PPTX(filepath, name_no_suffix, inputStream);
            size = GetInfoPPT.get_Images_PPTX(filepath, name_no_suffix, inputStream);
        }
        //转pdf
    	ItextPdf ptf=new ItextPdf();
		ptf.t(name_no_suffix,size);
//    	ptf.t("1477383417932_PPTX_test1",50);
		System.out.println("缩列图转pdf成功");
	}

}

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
		  String file5="File/pptx����.pptx";
		  long start=System.currentTimeMillis();
		  getFile(file1);
	      long end=System.currentTimeMillis();
		  System.out.println("file1��������������ʱ:"+(end-start)/1000+"s");
		  long start2=System.currentTimeMillis();
		  getFile(file2);
		  long end2=System.currentTimeMillis();
		  System.out.println("file2��������������ʱ:"+(end2-start2)/1000+"s");
		  long start3=System.currentTimeMillis();
		  getFile(file3);
		  long end3=System.currentTimeMillis();
		  System.out.println("file3��������������ʱ:"+(end3-start3)/1000+"s");
		  long start4=System.currentTimeMillis();
		  getFile(file4);
		  long end4=System.currentTimeMillis();
		  System.out.println("file4��������������ʱ:"+(end4-start4)/1000+"s");
		  long start5=System.currentTimeMillis();
		  getFile(file5);
		  long end5=System.currentTimeMillis();
		  System.out.println("start5��������������ʱ:"+(end5-start5)/1000+"s");
		 
		
		  
		
	    
	}
	public static void test(String file) throws Exception{
		long start=System.currentTimeMillis();
		 getFile(file);
		  long end=System.currentTimeMillis();
	      System.out.println("��������������ʱ:"+(end-start)/1000+"s");
	}
	//��ȡFileĿ¼���ļ���PPTFilesĿ¼�� �������ļ���  time_PPT_soucename
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
		//�ļ�д
		byte[] buffer = new byte[1024]; 
        int length=0;
		while((length=inputStream.read(buffer))!=-1){
			 out.write(buffer, 0, length);
		}
        out.close();
        //���ɸ��ļ���Ŀ¼�ṹ
        String name_no_suffix=PPTUtils.getFilename(name_with_suffix);
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/images");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/individual_ppts");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/pictures/");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/texts/");
        PPTUtils.createDir("PPTFiles/"+name_no_suffix+"/pdf/");
        System.out.println("�ļ�¼�룬�����ļ��гɹ�");
        //��ͬһ������������
        int size=0;
        if(PPTUtils.isPPT(filepath)){
        	System.out.println("ppt����������");
//        	HSLFSlideShowImpl hslfSlideShowImpl = new HSLFSlideShowImpl(inputStream);
        	GetInfoPPT.get_Texts_PPT(filepath, name_no_suffix);
        	GetInfoPPT.get_Pictures_PPT(filepath, name_no_suffix);
        	size = GetInfoPPT.get_Images_PPT(filepath, name_no_suffix);
        }else{
        	System.out.println("pptx����������");
         	GetInfoPPT.get_Texts_PPTX(filepath, name_no_suffix, inputStream);
        	GetInfoPPT.get_Pictures_PPTX(filepath, name_no_suffix, inputStream);
            size = GetInfoPPT.get_Images_PPTX(filepath, name_no_suffix, inputStream);
        }
        //תpdf
    	ItextPdf ptf=new ItextPdf();
		ptf.t(name_no_suffix,size);
//    	ptf.t("1477383417932_PPTX_test1",50);
		System.out.println("����ͼתpdf�ɹ�");
	}

}

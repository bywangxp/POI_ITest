package com.bywangxp.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class ItextPdf {
	public static void main(String[] args) {
	   	  ItextPdf itextPdf = new ItextPdf();
	      itextPdf.t("1477416462847_PPT_1.Why-Software-Engineering",30);
	      System.out.println("结束了");
	}
	
	public void t(String name_no_suffix,int size)
	{
		//创建一个文档对象 
	    Document doc = new Document(PageSize.A4, 50, 50,80, 0);
	    try {   
	        //定义输出文件的位置   
	        PdfWriter.getInstance(doc, new FileOutputStream("PPTFiles/"+name_no_suffix+"/pdf/"+name_no_suffix+".pdf"));
	        
	        //开启文档   
	        doc.open();   
	        //设定字体 为的是支持中文   
	        //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);   
	        // Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);   
	        //向文档中加入图片  
	        Image jpg1=null;
	        System.out.println("ppt个数"+size);
	        for(int i=1;i<=size;i++)
	        {
	        	//取得图片~~~图片格式：
	        	jpg1= Image.getInstance("PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+i+".png"); //原来的图片的路径
	        	//获得图片的高度
	        	float heigth=jpg1.height();
	        	float width=jpg1.width();
	        	//合理压缩，h>w，按w压缩，否则按w压缩
//	        	int percent=getPercent(heigth, width);
	        	//统一按照宽度压缩
	        	int percent=getPercent2(heigth, width);
	        	//设置图片居中显示
	        	jpg1.setAlignment(Image.ALIGN_CENTER);
	        /*	//按百分比显示图片的比例
	        	
*/	        	//可设置图像高和宽的比例
	       /* 	jpg1.scalePercent(50, 100);*/
	        	jpg1.scalePercent(percent);//表示是原来图像的比例;
	            doc.add(jpg1);
	          /*  System.out.println(i+"转成pdf");*/
	            doc.newPage();
	        }
	        //在此处多插入任意一个图片，此图片不展示，作用是使得最后一张图片可以正常转换。 后期修复此功能点
	       /* jpg1 = Image.getInstance("PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+1+".png"); 
	        doc.add(jpg1);*/
	        //关闭文档并释放资源   
	        doc.close();   
    } catch (FileNotFoundException e) {   
        e.printStackTrace();   
    } catch (DocumentException e) {   
        e.printStackTrace();   
    } catch (IOException e) {   
        e.printStackTrace();   
    }  
	}
	/**
	 * 第一种解决方案
	 * 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
	 * @param h
	 * @param w
	 * @return
	 */
	
	public int getPercent(float h,float w)
	{
		int p=0;
		float p2=0.0f;
		if(h>w)
		{
			p2=297/h*100;
		}
		else
		{
			p2=210/w*100;
		}
		p=Math.round(p2);
		return p;
	}
	/**
	 * 第二种解决方案，统一按照宽度压缩
	 * 这样来的效果是，所有图片的宽度是相等的
	 * @param args
	 */
	public int getPercent2(float h,float w)
	{
		int p=0;
		float p2=0.0f;
		p2=530/w*100;
		p=Math.round(p2);
		return p;
	}
	
	
}


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
	      System.out.println("������");
	}
	
	public void t(String name_no_suffix,int size)
	{
		//����һ���ĵ����� 
	    Document doc = new Document(PageSize.A4, 50, 50,80, 0);
	    try {   
	        //��������ļ���λ��   
	        PdfWriter.getInstance(doc, new FileOutputStream("PPTFiles/"+name_no_suffix+"/pdf/"+name_no_suffix+".pdf"));
	        
	        //�����ĵ�   
	        doc.open();   
	        //�趨���� Ϊ����֧������   
	        //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);   
	        // Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);   
	        //���ĵ��м���ͼƬ  
	        Image jpg1=null;
	        System.out.println("ppt����"+size);
	        for(int i=1;i<=size;i++)
	        {
	        	//ȡ��ͼƬ~~~ͼƬ��ʽ��
	        	jpg1= Image.getInstance("PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+i+".png"); //ԭ����ͼƬ��·��
	        	//���ͼƬ�ĸ߶�
	        	float heigth=jpg1.height();
	        	float width=jpg1.width();
	        	//����ѹ����h>w����wѹ��������wѹ��
//	        	int percent=getPercent(heigth, width);
	        	//ͳһ���տ��ѹ��
	        	int percent=getPercent2(heigth, width);
	        	//����ͼƬ������ʾ
	        	jpg1.setAlignment(Image.ALIGN_CENTER);
	        /*	//���ٷֱ���ʾͼƬ�ı���
	        	
*/	        	//������ͼ��ߺͿ�ı���
	       /* 	jpg1.scalePercent(50, 100);*/
	        	jpg1.scalePercent(percent);//��ʾ��ԭ��ͼ��ı���;
	            doc.add(jpg1);
	          /*  System.out.println(i+"ת��pdf");*/
	            doc.newPage();
	        }
	        //�ڴ˴����������һ��ͼƬ����ͼƬ��չʾ��������ʹ�����һ��ͼƬ��������ת���� �����޸��˹��ܵ�
	       /* jpg1 = Image.getInstance("PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+1+".png"); 
	        doc.add(jpg1);*/
	        //�ر��ĵ����ͷ���Դ   
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
	 * ��һ�ֽ������
	 * �ڲ��ı�ͼƬ��״��ͬʱ���жϣ����h>w����hѹ����������w>h��w=h������£������ѹ��
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
	 * �ڶ��ֽ��������ͳһ���տ��ѹ��
	 * ��������Ч���ǣ�����ͼƬ�Ŀ������ȵ�
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


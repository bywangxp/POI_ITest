package com.bywangxp.poi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextFont;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

import com.bywangxp.util.PPTUtils;


public class GetInfoPPT {
	public static int get_Images_PPT(String filepath,String name_no_suffix) throws IOException{
		//关于ppt的提取
			HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(filepath));
	            Dimension pgsize = ppt.getPageSize();
	            int size = ppt.getSlides().size();
	            System.out.println("ppt:"+size);
	            for (int i = 0; i < size; i++) {  
	                //防止中文乱码  
	                for(HSLFShape shape : ppt.getSlides().get(i).getShapes()){  
	                    if(shape instanceof HSLFTextShape) {  
	                        HSLFTextShape tsh = (HSLFTextShape)shape;  
	                        for(HSLFTextParagraph p : tsh){  
	                            for(HSLFTextRun r : p){  
	                                r.setFontFamily("宋体");  
	                            }  
	                        }  
	                    }  
	                }  
	                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);  
	                Graphics2D graphics = img.createGraphics();  
	                // clear the drawing area  
	                graphics.setPaint(Color.white);  
	                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));  
	                // render  
	                ppt.getSlides().get(i).draw(graphics);  
	                // save the output  
	           	    FileOutputStream out=new FileOutputStream("PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+(i+1)+".png");
	                javax.imageio.ImageIO.write(img, "png", out);  
	                out.close();  
//	                resizeImage(filename, filename, width, height);  
	            }
	            System.out.println("ppt转缩列图结束");	
	            return size;
	        
	}
	//获取缩列图
	public static int get_Images_PPTX(String filepath,String name_no_suffix,InputStream inputStream) throws Exception {
		
	    	   /*下面的XML配置文件定义转换后的图片内的文字字体，否则将会出现转换后 的图片内的中文为乱码 */
	    	   String xmlFontFormat1="<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"; 
	    	   String xmlFontFormat2= "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\">"; 
	    	   String xmlFontFormat3="<a:latin typeface=\"微软雅黑\" pitchFamily=\"34\" charset=\"-122\"/>";
	    	   String xmlFontFormat31 = "<a:ea typeface=\"微软雅黑\" pitchFamily=\"34\" charset=\"-122\"/>"; 
	    	   String xmlFontFormat4= "</a:rPr>"; 
	    	   String xmlFontFormat5="</xml-fragment>";
	    	   StringBuffer xmlFontFormatStringBuffer=new StringBuffer();
	    	   xmlFontFormatStringBuffer.append(xmlFontFormat1);
	    	   xmlFontFormatStringBuffer.append(xmlFontFormat2);
	    	   xmlFontFormatStringBuffer.append(xmlFontFormat3);
	    	   xmlFontFormatStringBuffer.append(xmlFontFormat31);
	    	   xmlFontFormatStringBuffer.append(xmlFontFormat4);
	    	   xmlFontFormatStringBuffer.append(xmlFontFormat5);
	    	 //关于pptx的提取
	    	   inputStream = new FileInputStream(filepath);  
	    	   XMLSlideShow ppt = new XMLSlideShow(inputStream); 
	           Dimension pgsize = ppt.getPageSize();  
	           List<XSLFSlide> slides = ppt.getSlides();
	           int size=ppt.getSlides().size();
	           for (int i = 0; i < slides.size(); i++) {  
	            	/*设置字体为宋体，解决中文乱码问题*/
	            	CTSlide oneCTSlide = ppt.getSlides().get(i).getXmlObject(); 
	            	CTGroupShape oneCTGroupShape = oneCTSlide.getCSld().getSpTree();
	            	CTShape [] oneCTShapeArray = oneCTGroupShape.getSpArray();
	            	BufferedImage img =null;
	            	int length = oneCTShapeArray.length;
	            	if(length==0){
	            	  	XSLFSlide slide=slides.get(i);
	        		        img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
	        		        Graphics2D graphics = img.createGraphics();
	        		        // clear the drawing area
	        		        graphics.setPaint(Color.WHITE);
	        		        
//	        			        graphics.setPaint();
	        		        graphics.setBackground(slide.getBackground().getFillColor());
	        		        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
	        		        

	        		        // render
	        		        slide.draw(graphics);

	        		        // save the output
//	        		        FileOutputStream out = new FileOutputStream("test/slide" + i + ".jpg");
//	        		        javax.imageio.ImageIO.write(img, "png", out);
//	        		        out.close();

	        		       
	        	    }
	            	else{
		            	 for(CTShape oneCTShape : oneCTShapeArray){ 
			            	CTTextBody oneCTTextBody = oneCTShape.getTxBody();
			            	if(null == oneCTTextBody){ 
			            		continue;
			            	}
			            	CTTextParagraph [] oneCTTextParagraph = oneCTTextBody.getPArray(); 
			            	CTTextFont oneCTTextFont = null;
			            	oneCTTextFont =CTTextFont.Factory.parse(xmlFontFormatStringBuffer.toString()); 
			            	for(CTTextParagraph textParagraph : oneCTTextParagraph){ 
			            		CTRegularTextRun [] oneCTRegularTextRunArray = textParagraph.getRArray(); 
			            	    for(CTRegularTextRun oneCTRegularTextRun : oneCTRegularTextRunArray){ 
				            		CTTextCharacterProperties oneCTTextCharacterProperties = oneCTRegularTextRun.getRPr();
				            		oneCTTextCharacterProperties.setLatin(oneCTTextFont);
			            	    }
				            	img= new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);  
				            	Graphics2D graphics = img.createGraphics();  
				                // clear the drawing area  
				                graphics.setPaint(Color.white);  
				                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));  
				                // render  
				                ppt.getSlides().get(i).draw(graphics);  
			            	}
			            	
		                 } 
	            	}
	            	 String filename1 = "PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+(i+1)+".png";
		             FileOutputStream out = new FileOutputStream(filename1);  
		             javax.imageio.ImageIO.write(img, "png", out);
//	            	 System.out.println("抽取成图片:"+(i+1));
	    }
	           System.out.println("pptx转缩列图结束");
	           return size;
	}
	public static void get_Pictures_PPT(String filepath,String name_no_suffix) throws Exception{
		HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(filepath));
	    // now retrieve pictures containes in the all slide and save them on disk
		int page=1;//记录页码
		for (HSLFSlide slide : ppt.getSlides()) {
		    int idx = 1;//每页中的图片计数器
	    for (HSLFShape sh : slide.getShapes()) {
	        if (sh instanceof HSLFPictureShape) {
	            HSLFPictureShape pict = (HSLFPictureShape) sh;
	            HSLFPictureData pictData = pict.getPictureData();
	            byte[] data = pictData.getData();
	            PictureType type = pictData.getType();
	            //此处获取的图片有多种类型    后期需要处理"PPTFiles/"+name_no_suffix+"/images/"+name_no_suffix+
	            FileOutputStream out = new FileOutputStream("PPTFiles/"+name_no_suffix+"/pictures/"+name_no_suffix+""+page+"_" + idx + type.extension);
	            idx++;
	            out.write(data);
	            out.close();
	        }
	    }
	    page++;
	 }
     System.out.println("pictures 读取结束");
	}
	//获取全部图片，图片序号格式X_Y,X为第几张PPT，Y为同一张PPT的第几个图片
		public  static void get_Pictures_PPTX(String filepath,String name_no_suffix,InputStream inputStream) throws Exception{
		
			   XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(filepath));
			   int page=1;//记录页码
				for (XSLFSlide slide : ppt.getSlides()) {
				    int idx = 1;//每页中的图片计数器
			    for (XSLFShape sh : slide.getShapes()) {
			        if (sh instanceof XSLFPictureShape) {
			            XSLFPictureShape pict = (XSLFPictureShape) sh;
			            XSLFPictureData pictData = pict.getPictureData();
			            byte[] data = pictData.getData();
			            PictureType type = pictData.getType();
			            //此处获取的图片有多种类型    后期需要处理
			            FileOutputStream out = new FileOutputStream("PPTFiles/"+name_no_suffix+"/pictures/"+name_no_suffix+page+"_" + idx + type.extension);
			            idx++;
			            out.write(data);
			            out.close();
			        }
			    }
			    page++;
			 }
		        System.out.println("pictures 读取结束");
        }
		//获取到PPT的文字
		public static void get_Texts_PPTX(String filepath,String name_no_suffix,InputStream inputStream)throws Exception, IOException{
		    inputStream = new FileInputStream(filepath);  
	         XMLSlideShow ppt = new XMLSlideShow(inputStream);  
	         Dimension pgsize = ppt.getPageSize();  
	         for (int i = 0; i < ppt.getSlides().size(); i++) {
	        	StringBuffer sb=new StringBuffer();
	         	CTSlide oneCTSlide = ppt.getSlides().get(i).getXmlObject(); 
	         	CTGroupShape oneCTGroupShape = oneCTSlide.getCSld().getSpTree();
	         	CTShape [] oneCTShapeArray = oneCTGroupShape.getSpArray();
	         	for(CTShape oneCTShape : oneCTShapeArray){ 
		            CTTextBody oneCTTextBody = oneCTShape.getTxBody();
		            	if(null == oneCTTextBody){ 
		            		continue;
		            	}
		            	 CTTextParagraph[] paras = oneCTTextBody.getPArray();
		                    for (CTTextParagraph textParagraph : paras) {
		                        CTRegularTextRun[] textRuns = textParagraph.getRArray();
		                        for (CTRegularTextRun textRun : textRuns) {
		                            sb.append(textRun.getT());
		                        }
		                    }
             }
	         	 String string = sb.toString();
	       	     FileOutputStream out=new FileOutputStream("PPTFiles/"+name_no_suffix+"/texts/"+name_no_suffix+(i+1)+".txt");
				 out.write(string.getBytes());
				 out.close();
	         }
	            System.out.println("提取文本信息完成");
		}
		//获取到PPT的文字
		public static void get_Texts_PPT(String filepath,String name_no_suffix) throws Exception, IOException{
			String filename=PPTUtils.getFilename(filepath);
			//关于ppt的提取
				HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(filepath));
				int page=1;//记录页码
				for (HSLFSlide slide : ppt.getSlides()){
					 List<List<HSLFTextParagraph>> textParagraphs = slide.getTextParagraphs();
					 String string = textParagraphs.toString();
					 FileOutputStream out=new FileOutputStream("PPTFiles/"+name_no_suffix+"/texts/"+name_no_suffix+""+page+""+".txt");
					 out.write(string.getBytes());
					 out.close();
					 ++page;
				}
				 System.out.println("提取文本信息完成");
		    
		}
	
		public static void get_Individual_PPTs(String filepath) throws Exception {
			String filename=PPTUtils.getFilename(filepath);
			//关于ppt的提取
			if(PPTUtils.isPPT(filepath)){
				HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(filepath));
				int size=ppt.getSlides().size();//获取ppt总页数
				int index=0;
				for(int i=0;i<size;++i){
					HSLFSlideShow tempPpt=new HSLFSlideShow(new HSLFSlideShowImpl(filepath));
					for(int j=0;j<size;++j){
						if(j<i){
							tempPpt.removeSlide(0);
						}else if(j>i){
							tempPpt.removeSlide(1);
						}else{
							index=j+1;
						}
					}
					FileOutputStream out=new FileOutputStream("File/ppt/filename1/individual/"+filename+""+index+".ppt");
					tempPpt.write(out);
				    out.close();
				    System.out.println("拆分中");
				}
				 System.out.println("拆分完成");
			}
			else{
				XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(filepath));
				List<XSLFSlide> slides = ppt.getSlides();
				int size=ppt.getSlides().size();//获取ppt总页数
				int index=0;
				for(int i=0;i<size;++i){
					XMLSlideShow tempPpt = new XMLSlideShow(new FileInputStream(filepath));
					for(int j=0;j<size;++j){
						if(j<i){
							tempPpt.removeSlide(0);
						}else if(j>i){
							tempPpt.removeSlide(1);
						}else{
							index=j+1;
						}
					}

					FileOutputStream out=new FileOutputStream("File/pptx/filename1/individual/"+filename+""+index+".pptx");
					tempPpt.write(out);
				    out.close();
				    System.out.println("拆分中");
				}
				System.out.println("拆分完成");
			}
		}
		
}

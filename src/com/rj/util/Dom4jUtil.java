package com.rj.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import com.rj.bean.AirData;
import com.rj.bean.PointInfo;

/**
 * @author Alexia
 * 
 * Dom4j 解析XML文档
 */
public class Dom4jUtil implements XmlDocument {

    public List parserXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        List list = new ArrayList();
		List<PointInfo> list1 = new ArrayList<PointInfo>();
		List<AirData> list2 = new ArrayList<AirData>();
        try {
            Document document = saxReader.read(inputXml);
            Element users = document.getRootElement();
            for (Iterator i = users.elementIterator(); i.hasNext();) {
                Element user = (Element) i.next();

            	int jj = 0;
                if("datas".equals(user.getQualifiedName())){
	                for (Iterator j = user.elementIterator(); j.hasNext();) {
	                    Element node = (Element) j.next();
	                    jj++;
		            	AirData ad = new AirData();
		            	PointInfo pi = new PointInfo();
	                 
	                    int kk=0;
	                    String str = "";
	                    for(Iterator k = node.elementIterator(); k.hasNext();){
	                    	 Element item = (Element) k.next();
	                    	 kk++;
//	 	                    System.out.print(item.attributeValue("name"));
//	 	                    System.out.print(item.attributeValue("code"));
	                    	str = item.attributeValue("value");
	                    	if(str.equals("-1"))str="";
	                    	
		                    if(kk==1)
		                    	pi.setCityname(str);
		                    else if(kk==2)
		                    	pi.setCitycode(str);
		                    else if(kk==3)
		                    	pi.setStationname(str);
		                    else if(kk==4)
		                    	pi.setStationcode(str);
		                    else if(kk==8)
		                    	ad.setYear(Integer.parseInt(str.trim()));
		                    else if(kk==9)
		                    	ad.setMonth(Integer.parseInt(str.trim()));
		                    else if(kk==10)
		                    	ad.setDay(Integer.parseInt(str.trim()));
		                    else if(kk==11)
		                    	ad.setHour(Integer.parseInt(str.trim()));
		                    else if(kk==12)
		                    	ad.setAqi(str);
		                    else if(kk==13)
		                    	ad.setA21026(str);//二氧化硫
		                    else if(kk==14)
		                    	ad.setA21004(str);//二氧化氮
		                    else if(kk==15)
		                    	ad.setA34002(str);//pm10
		                    else if(kk==16)
		                    	ad.setCO(str);//一氧化碳
		                    else if(kk==17)
		                    	ad.setO3(str);//o3
		                    else if(kk==18)
		                    	ad.setO3T8(str);//o3t8
		                    else if(kk==19)
		                    	ad.setA34004(str);//pm2.5
		                    else if(kk==20)
		                    	ad.setA01007(str);//风速
		                    else if(kk==21)
		                    	ad.setA01008(str);//风向
		                    else if(kk==22)
		                    	ad.setA01001(str);//温度
		                    else if(kk==23)
		                    	ad.setA01004(str);//相对湿度
		                    else if(kk==24)
		                    	ad.setA01006(str);//气压
		                    else if(kk==25)
		                    	ad.setA01020(str);//能见度 
	                    }
	               		list1.add(pi);
               			list2.add(ad);	
	                    
	                }
               	}
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
        list.add(list1);
        list.add(list2);
		return list;
    }
    
    public List parserXml1(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        List list = new ArrayList();
		List<PointInfo> list1 = new ArrayList<PointInfo>();
		List<AirData> list2 = new ArrayList<AirData>();
        try {
            Document document = saxReader.read(inputXml);
            Element users = document.getRootElement();
            for (Iterator i = users.elementIterator(); i.hasNext();) {
                Element user = (Element) i.next();

            	int jj = 0;
                if("datas".equals(user.getQualifiedName())){
	                for (Iterator j = user.elementIterator(); j.hasNext();) {
	                    Element node = (Element) j.next();
	                    jj++;
		            	AirData ad = new AirData();
		            	PointInfo pi = new PointInfo();
	                 
	                    int kk=0;
	                    String str = "";
	                    for(Iterator k = node.elementIterator(); k.hasNext();){
	                    	 Element item = (Element) k.next();
	                    	 kk++;
//	 	                    System.out.print(item.attributeValue("name"));
//	 	                    System.out.print(item.attributeValue("code"));
	                    	str = item.attributeValue("value");
	                    	
		                    if(kk==1)
		                    	pi.setCityname(str);
		                    else if(kk==2)
		                    	pi.setCitycode(str);
		                    else if(kk==3)
		                    	pi.setStationname(str);
		                    else if(kk==4)
		                    	pi.setStationcode(str);
		                    else if(kk==6)
		                    	pi.setLongitude(str.trim().replace("．", "."));
		                    else if(kk==7)
		                    	pi.setLatitude(str.trim().replace("．", "."));
		                    else if(kk==9)
		                    	ad.setYear(Integer.parseInt(str.trim()));
		                    else if(kk==10)
		                    	ad.setMonth(Integer.parseInt(str.trim()));
		                    else if(kk==11)
		                    	ad.setDay(Integer.parseInt(str.trim()));
		                    else if(kk==12)
		                    	ad.setHour(Integer.parseInt(str.trim()));
		                    else if(kk==13)
		                    	ad.setA21026(str);//二氧化硫
		                    else if(kk==14)
		                    	ad.setNO(str);//一氧化氮
		                    else if(kk==15)
		                    	ad.setA21004(str);//二氧化氮
		                    else if(kk==16)
		                    	ad.setNOx(str);//氮氧化物
		                    else if(kk==17)
		                    	ad.setCO(str);//一氧化碳
		                    else if(kk==18)
		                    	ad.setO3(str);//o3
		                    else if(kk==19)
		                    	ad.setA34002(str);//pm10
		                    else if(kk==20)
		                    	ad.setA34004(str);//pm2.5
		                    else if(kk==21)
		                    	ad.setA01007(str);//风速
		                    else if(kk==22)
		                    	ad.setA01008(str);//风向
		                    else if(kk==23)
		                    	ad.setA01001(str);//气温
		                    else if(kk==24)
		                    	ad.setA01006(str);//气压
		                    else if(kk==25)
		                    	ad.setA01004(str);//相对湿度
		                    else if(kk==26)
		                    	ad.setPrecipitation(str);//雨量 
	                    }
	               		list1.add(pi);
               			list2.add(ad);	
	                    
	                }
               	}
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
        list.add(list1);
        list.add(list2);
		return list;
    }
    
    @Test
    public void jTest(){
    	parserXml("E:/datashare/120000/AQ_2015122816_120000_0001.xml");
    }
}
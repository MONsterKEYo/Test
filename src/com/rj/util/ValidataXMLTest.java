package com.rj.util;

import org.dom4j.Document; 
import org.dom4j.io.OutputFormat; 
import org.dom4j.io.SAXReader; 
import org.dom4j.io.SAXValidator; 
import org.dom4j.io.XMLWriter; 
import org.dom4j.util.XMLErrorHandler; 

import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** 
* Java XMLУ����� 
* 
* @author leizhimin��2008-9-4 14:42:35 
*/ 
public class ValidataXMLTest {

    /** 
     * ͨ��XSD��XML Schema��У��XML 
     * @throws FileNotFoundException 
     */ 
    public boolean validateXMLByXSD(String testXSD,String xmlFileName) throws FileNotFoundException { 
//        String xmlFileName = "Q:\\_dev_stu\\xsdtest\\src\\note.xml"; 
        String xsdFileName = "E:/datashare/"+testXSD;
        FileInputStream fls = new FileInputStream( new File(xmlFileName));
        try { 

            
            //����Ĭ�ϵ�XML�������� 
            XMLErrorHandler errorHandler = new XMLErrorHandler(); 
            //��ȡ���� SAX �Ľ�������ʵ�� 
            SAXParserFactory factory = SAXParserFactory.newInstance(); 
            //�������ڽ���ʱ��֤ XML ���ݡ� 
            factory.setValidating(true); 
            //ָ���ɴ˴������ɵĽ��������ṩ�� XML ���ƿռ��֧�֡� 
            factory.setNamespaceAware(true); 
            //ʹ�õ�ǰ���õĹ����������� SAXParser ��һ����ʵ���� 
            SAXParser parser = factory.newSAXParser(); 
            //����һ����ȡ���� 
            SAXReader xmlReader = new SAXReader(); 
            //��ȡҪУ��xml�ĵ�ʵ�� 
            Document xmlDocument = (Document) xmlReader.read(fls); 
            //���� XMLReader �Ļ���ʵ���е��ض����ԡ����Ĺ��ܺ������б������ [url]http://sax.sourceforge.net/?selected=get-set[/url] ���ҵ��� 
            parser.setProperty( 
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
                    "http://www.w3.org/2001/XMLSchema"); 
            parser.setProperty( 
                    "http://java.sun.com/xml/jaxp/properties/schemaSource", 
                    "file:" + xsdFileName); 
            //����һ��SAXValidatorУ�鹤�ߣ�������У�鹤�ߵ����� 
            SAXValidator validator = new SAXValidator(parser.getXMLReader()); 
            //����У�鹤�ߵĴ�������������������ʱ�����ԴӴ����������еõ�������Ϣ�� 
            validator.setErrorHandler(errorHandler); 
            //У�� 
            validator.validate(xmlDocument); 

            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint()); 
            //���������Ϣ��Ϊ�գ�˵��У��ʧ�ܣ���ӡ������Ϣ 
            if (errorHandler.getErrors().hasContent()) { 
                System.out.println("XML�ļ�У��ʧ�ܣ�"); 
                writer.write(errorHandler.getErrors()); 
                return false;
            } else { 
                System.out.println("XML�ļ�У��ɹ���"); 
                return true;
            } 
        } catch (Exception ex) { 
            System.out.println("XML�ļ�: " + xmlFileName + " ͨ��XSD�ļ�:" + xsdFileName + "����ʧ�ܡ�\nԭ�� " + ex.getMessage()); 
            //ex.printStackTrace(); 
            return false;
        }finally{
        	try {
				fls.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    } 

    /** 
     * ͨ��DTDУ��XML 
     */ 
    public static void validateXMLByDTD() { 
        //todo����ʱ���ã��Ժ���˵�� 
    } 
   
}
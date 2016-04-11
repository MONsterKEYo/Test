package com.rj.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.lang.Integer;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rj.bean.AirData;
import com.rj.bean.PointInfo;
import stationQualityData.WSEnvCityData;
import com.rj.util.PointUtil;

public class StationsData {

	private static Logger log = Logger.getLogger(StationsData.class);
	public String stationsHourData(){
		//���Բ���
//		 try {
//			String filePath = "E:\\datashare\\AAA.txt";
//	        String encoding="UTF-8";
//	        File file=new File(filePath);
//	        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
//	        BufferedReader bufferedReader = new BufferedReader(read);
//	        String s = null;
//       
//			s = bufferedReader.readLine();
//	        return s;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 System.out.println(" return null");
//			return null;
		//����������������������������������������������������
			
		 
		WSEnvCityData dao = new WSEnvCityData();
		String s = dao.getWSEnvCityDataSoap().stationQualityData("ZongHeQueryUser", "dG245%uisWR34n97tY");
		System.out.println(s.substring(0,500));
		return s;
	}
	
	public void stationsDataParsing() throws ParseException{
		String s = stationsHourData();//����
    	DataParsingThread dpt = new DataParsingThread();
		PointUtil pu = new PointUtil();
		List<PointInfo> piList = pu.getStations();
		List<AirData> adList = new ArrayList<AirData>();
		if (s != null) {
        	//<date>6λ��201601020304 10λ
        	String date = s.substring(6, 16);
        	System.out.println("��������:date"+date);
        	DateFormat format1= new SimpleDateFormat("yyyyMMddHH");
			Date date1 = format1.parse(date);
        	int year = Integer.parseInt(date.substring(0,4));
        	int month = Integer.parseInt(date.substring(4,6));
        	int day = Integer.parseInt(date.substring(6,8));
        	int hour = Integer.parseInt(date.substring(8,10));
        	
        	//���ಿ�� split("")
        	s=s.substring(s.indexOf("StationCode"), s.indexOf("</value></record></records>"));
        	
        	//record[n]��n����λ
        	String[] record = s.split("</value></record><record><column>");
        	System.out.println("��λ����"+record.length+"\n-------------");
        //	log.info("Сʱ��������"+record.length+"\n-------------");
        	
        	
        	for(int k=0;k<record.length;k++){//k������
        		PointInfo pi = piList.get(k);
        		AirData ad = new AirData();
        		//cvs[n]���������еĵ�n������
            	String[] cvs = record[k].split("</value><column>");
            	String[] i = new String[cvs.length];
            	for(int j=0;j<cvs.length;j++){
            		String[] cv = cvs[j].split("</column><value>");
            		if("��".equals(cv[1]))
            			cv[1]="";
            		i[j]=cv[1];//cv[0]Ϊ�������ƣ�cv[1]Ϊ����ֵ
            	}
            	//sql+="(null,null,'"+i[2] +"','"+i[0] +"','"+i[1]+"',1,"+i[6]+","+i[7]+","+"'str'),";
            	if(i[0].equals(pi.getStationcode())){
					ad.setNewCode(pi.getNewcode());
					ad.setSamplingTime(date1);

					ad.setYear(year);
					ad.setMonth(month);
					ad.setDay(day);
					ad.setHour(hour);

					ad.setAqi(i[3]);

					ad.setA21026(i[8]);
					ad.setSO2IAQI(i[10]);

					ad.setA21004(i[11]);
					ad.setNO2IAQI(i[13]);

					ad.setO3(i[14]);
					ad.setO3IAQI(i[15]);
					ad.setO3T8(i[16]);

					ad.setCO(i[18]);
					ad.setCOIAQI(i[20]);

					ad.setA34002(i[21]);
					ad.setPm10t24(i[22]);
					ad.setPM10IAQI(i[23]);

					ad.setA34004(i[24]);
					ad.setPm25t24(i[25]);
					ad.setPM25IAQI(i[26]);
            		
            		
            	}else{
            		Iterator ii = piList.iterator();
            		while(ii.hasNext()){
            			PointInfo p = (PointInfo) ii.next();
            			if(i[0].equals(p.getStationcode())){
            				ad.setNewCode(pi.getNewcode());
        					ad.setSamplingTime(date1);
                    		
                    		ad.setYear(year);
                    		ad.setMonth(month);
                    		ad.setDay(day);
                    		ad.setHour(hour);
                    		
                    		ad.setAqi(i[3]);
                    		
                    		ad.setA21026(i[8]);//so2
                    		ad.setSO2IAQI(i[10]);
                    		
                    		ad.setA21004(i[11]);//no2
                    		ad.setNO2IAQI(i[13]);
                    		
                    		ad.setO3(i[14]);
                    		ad.setO3IAQI(i[15]);
                    		ad.setO3T8(i[16]);
                    		
                    		ad.setCO(i[18]);
                    		ad.setCOIAQI(i[20]);
                    		
                    		ad.setA34002(i[21]);
                    		ad.setPm10t24(i[22]);
                    		ad.setPM10IAQI(i[23]);
                    		
                    		ad.setA34004(i[24]);
                    		ad.setPm25t24(i[25]);
                    		ad.setPM25IAQI(i[26]);
                    		
            				
                    	}
            		}
            	}
            	adList.add(ad);
            	
            	
        	}
        	//System.out.println("insert into datashare.point_info values"+sql);
        	boolean flag = false;
			try {
				flag = dpt.ifStationsDataExist(date1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(!flag){
	        	boolean f = dpt.testAdd(adList);
	        	if(!f) System.out.println(date+"���ص�λ��������쳣!");
	        	else System.out.println(date+"���ص�λ�������ɹ�");
        	}
        }
		
	}
	@Test
	public void test() throws IOException, ParseException{
		  stationsDataParsing();
	}
	
	
}

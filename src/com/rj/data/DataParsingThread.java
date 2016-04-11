package com.rj.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;


import com.rj.bean.AirData;
import com.rj.bean.PointInfo;
import com.rj.util.Dom4jUtil;
import com.rj.util.ExcelUtil;
import com.rj.util.JdbcUtil;
import com.rj.util.PointUtil;
import com.rj.util.ValidataXMLTest;
import com.rj.util.ComputeUtil;

public class DataParsingThread {
	public volatile static boolean stop = true;
	private String filePath = "E:/datashare/130000";
	private String filePath12 = "E:/datashare/120000";
	private String filePath11 = "E:/datashare/110000";
	
	private String provinces[] = {"110000","130000","150000","370000","140000"};
	

	public void dataParsing() throws Exception{
		//1.获取目录下的文件夹
/*		File f = null;  
		f = new File(filePath);  
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。  
//		List<File> list = ReadAllFile(filePath,new ArrayList<File>());  
		
		for(File file : files) {  
		    System.out.println(file.getAbsolutePath());  
		}  
		
		for (File file : files) {
			boolean ifWrongFile = true;
			//2.读取文件
			System.out.println("read");
			if (file.getAbsolutePath().endsWith(".xls")
					|| file.getAbsolutePath().endsWith(".xlsx")) {
				try {
					// 对读取Excel表格标题测试
					InputStream is = new FileInputStream(file.getAbsolutePath());
					ExcelUtil excelReader = new ExcelUtil();
					String[] title = excelReader.readExcelTitle(is);
					System.out.println("获得Excel表格的标题:");
					for (String s : title) {
						System.out.print(s + " ");
					}

					// 对读取Excel表格内容测试
					InputStream is2 = new FileInputStream(file.getAbsolutePath());
					Map<Integer, String> map = excelReader
							.readExcelContent(is2);
					System.out.println("/n获得Excel表格的内容:");
					List<AirData> list = new ArrayList<AirData>();
					for (int i = 1; i <= map.size(); i++) {
						String[] cell = map.get(i).split("@!#");
			//3.获取行内容
						
						//String newcode = "130000_"+new DecimalFormat("0000").format(i);
						PointInfo pSearch = new PointInfo();
						pSearch.setCityname(cell[0]);
						pSearch.setStationname(cell[1]);
						PointInfo pResult = findPoint(pSearch);
						AirData airData = new AirData();
						airData.setNewCode(pResult.getNewcode());
						
						String[] t = cell[4].split("-");
						airData.setYear( Integer.parseInt(t[0]));
						airData.setMonth(Integer.parseInt(t[1]));
						airData.setDay(Integer.parseInt(t[2]));
						airData.setHour(Integer.parseInt(cell[5]));
						airData.setAqi(cell[6]);
						airData.setA34004(cell[7]);//pm2.5 A34004
						airData.setA34002(cell[8]);//pm10 A34002
						airData.setA21026(cell[9]);//so2 A21026
						airData.setA21004(cell[10]);//no2 A21004
						airData.setCO(cell[11]);//co CO_014
						airData.setO3(cell[12]);//o3 O3
						
						DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");        
						DateFormat format2= new SimpleDateFormat("yyyy-MM-dd HH");  
						// String转Date         
						try {   
							Date date1 = format1.parse(cell[4]);
						} catch (ParseException e) {   
							System.out.println("日期有误");
						}   
						try {   
					           Date date2 = format2.parse(cell[4]+" "+cell[5]);
								airData.setSamplingTime(date2);
						} catch (ParseException e) {   
							System.out.println("日期+小时 无法拼接出有效时间");
							ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/130000/");
							System.out.println("moveErr");
							break;
					           
						}  
						list.add(airData);
						if(i==map.size()){
							
							ComputeUtil cu = new ComputeUtil();
							List<AirData> listToAddR = cu.resultWithAQI(list,false);	
							
							if(! testAdd(listToAddR)) ifWrongFile = false;//数据存储，如果存储失败，false
							
							
						}
					}
					
					
				} catch (FileNotFoundException e) {
					System.out.println("未找到指定路径的文件!");
					e.printStackTrace();
				}
			}else{
				ifWrongFile = false;
			}
			
			
			//4.复制文件到备份目录或错误目录，删除原文件
			if (file.getAbsolutePath().indexOf(".") > -1) {

				
				if (ifWrongFile){
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/130000/");
					System.out.println("move");
				}else{
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/130000/");
					System.out.println("moveErr");
				}
			}
		}*/
		
		
		dataParsing12();
		for(int i=0;i<provinces.length;i++){
			dataParsing11(provinces[i]);
		}
	}
	
	public void dataParsing12() throws Exception{
		//1.获取目录下的文件夹
		File f = null;  
		f = new File(filePath12);  
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。  
//		List<File> list = ReadAllFile(filePath,new ArrayList<File>());  
		
		for(File file : files) {  
		    System.out.println(file.getAbsolutePath());  
			boolean ifWrongFile = true;
			//XSD验证
			ValidataXMLTest v = new ValidataXMLTest();
			if(!file.getName().substring(file.getName().length()-3,file.getName().length()).equalsIgnoreCase("XML")){
				ifWrongFile = false;
				ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/120000/");
				System.out.println("moveErr");
				continue;
			}else if( v.validateXMLByXSD("test.xsd",file.getAbsolutePath()) ){
				Dom4jUtil d4j = new Dom4jUtil();
				List list = d4j.parserXml(file.getAbsolutePath());
				if(null==list){//判断是否能成功解析
					ifWrongFile = false;
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/120000/");
					System.out.println("moveErr");
					continue;
				}else{
					List<PointInfo> list1 = (List<PointInfo>) list.get(0);
					List<AirData> list2 = (List<AirData>) list.get(1);
					List<AirData> listToAdd = new ArrayList();
					for(int ii=0;ii<list1.size();ii++){
						PointInfo piSearch = list1.get(ii);
						AirData ad = list2.get(ii);
						PointUtil pu = new PointUtil();
						PointInfo pi = pu.findPoint(piSearch,false);
						if(null==pi)
							ifWrongFile = false;
						else
							ad.setNewCode(pi.getNewcode());
						
						DateFormat format1= new SimpleDateFormat("yyyyMMddHH");  
						// String转Date         
						try {   
							Date date1 = format1.parse(file.getName().substring(3, 13));
							ad.setSamplingTime(date1);
						} catch (ParseException e) {   
							System.out.println("日期有误");
							ifWrongFile = false;
						}  
						listToAdd.add(ad);System.out.println(ad.getNewCode());
					}
					ifAirDataExist(listToAdd);//如果数据库中已经有此数据,则删除
						
					ComputeUtil cu = new ComputeUtil();
					List<AirData> listToAddR = cu.resultWithAQI(listToAdd,false);	
					if(!testAdd(listToAddR)) ifWrongFile = false;
				}
			}else{
				ifWrongFile = false;
			}
			
			//复制文件到备份目录或错误目录，删除原文件
			if (file.getAbsolutePath().indexOf(".") > -1) {

				
				if (ifWrongFile){
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/120000/");
					System.out.println("move");
				}else{
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/120000/");
					System.out.println("moveErr");
				}
			}
			
		}  
		
	}
	
	public void dataParsing11(String provinceCode) throws Exception{
		//1.获取目录下的文件夹
		File f = null;  
		f = new File(filePath11.replace("110000", provinceCode));  
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。  
//		List<File> list = ReadAllFile(filePath,new ArrayList<File>());  
		
		for(File file : files) {  
		    System.out.println(file.getAbsolutePath());  
			boolean ifWrongFile = true;
			
			
			//XSD验证
			ValidataXMLTest v = new ValidataXMLTest();
			if(!file.getName().substring(file.getName().length()-3,file.getName().length()).equalsIgnoreCase("XML")){
				ifWrongFile = false;
				ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/110000/".replace("110000", provinceCode));
				System.out.println("moveErr");
				continue;
			}else if( v.validateXMLByXSD("test1.xsd",file.getAbsolutePath()) ){
				Dom4jUtil d4j = new Dom4jUtil();
				List list = d4j.parserXml1(file.getAbsolutePath());//北京版xml解析
				if(null==list){//判断是否能成功解析
					ifWrongFile = false;
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/110000/".replace("110000", provinceCode));
					System.out.println("moveErr");
					continue;
				}else{
					List<PointInfo> list1 = (List<PointInfo>) list.get(0);
					
					
					
					//生成点位信息sql语句,使用中最好注释掉此段内容
//					String sqlInsert = "INSERT INTO `datashare`.`point_info` VALUES ";
//					for(int ii=0;ii<list1.size();ii++){
//						PointInfo p = list1.get(ii);
//						java.text.DecimalFormat df = new java.text.DecimalFormat("0000");
//						int pID=ii+0;
//						String s = "(null,'"+p.getCitycode()+"','"+p.getCityname()+"','"+p.getStationcode()+"','"+p.getStationname()+"',2,'"+p.getLongitude()+"','"+p.getLatitude()+"','"+provinceCode.substring(0,2)+"0000_"+df.format(pID)+"'),";
//						sqlInsert += s;
//					}
//					System.out.println(sqlInsert);
//					System.out.println();
					
					List<AirData> list2 = (List<AirData>) list.get(1);
					List<AirData> listToAdd = new ArrayList();
					for(int ii=0;ii<list1.size();ii++){
						PointInfo piSearch = list1.get(ii);
						AirData ad = list2.get(ii);
						PointUtil pu = new PointUtil();
						PointInfo pi = pu.findPoint(piSearch,false);
						if(null==pi){
							//插入此点位，重新执行
							
							//1.先查询该省所有点位
							PointInfo pnewcode = new PointInfo();
							pnewcode.setNewcode(provinceCode);
							List<PointInfo> provincePoints = pu.findPoints(pnewcode);
							
							//2.使用经纬度判断是否存在点位改名的情况
							try{
								//当前数据的点位经纬度
								double longitude = Double.parseDouble(piSearch.getLongitude());
								double latitude = Double.parseDouble(piSearch.getLatitude());
								//与该省全部点位经纬度作比较±0.0001
								boolean isNew = true;
								for(PointInfo p:provincePoints){
									double plong = Double.parseDouble(p.getLongitude().trim());
									double plat = Double.parseDouble(p.getLatitude().trim());
									//3.如果是点位改名-update，如果是新点位-insert
									if( longitude-0.0001<=plong && longitude+0.001>=plong
											&& latitude-0.0001<=plat && latitude+0.0001>=plat)
									{
										//可以确定为同一个点
										String sqlupdate = "update `datashare`.`point_info` set ";
										sqlupdate += " citycode='"+piSearch.getCitycode()+"',cityname='"+piSearch.getCityname()+"',stationcode='"+piSearch.getStationcode()+"',stationname='"+piSearch.getStationname()+"', longitude='"+piSearch.getLongitude()+"',latitude='"+piSearch.getLatitude()+"'";
										sqlupdate += " where citycode='"+p.getCitycode()+"' and cityname='"+p.getCityname()+"' and stationcode='"+p.getStationcode()+"' and stationname='"+p.getStationname()+"' and  longitude='"+p.getLongitude()+"' and latitude='"+p.getLatitude()+"'";
										System.out.println(sqlupdate);
										sqlexecute(sqlupdate);
										isNew = false;
										break;
									}
								}
								//新增点位
								if(isNew){
									java.text.DecimalFormat df = new java.text.DecimalFormat("0000");
									PointInfo plast = provincePoints.get(0);
									int num = Integer.parseInt(plast.getNewcode().substring(7))+1;
									String sqlInsert = "INSERT INTO `datashare`.`point_info` VALUES ";
									sqlInsert += "(null,'"+piSearch.getCitycode()+"','"+piSearch.getCityname()+"','"+piSearch.getStationcode()+"','"+piSearch.getStationname()+"',2,'"+piSearch.getLongitude()+"','"+piSearch.getLatitude()+"','"
												+provinceCode.substring(0,2)+"0000_"+df.format(num)+"')";
									System.out.println(sqlInsert);
									sqlexecute(sqlInsert);
								}
								
							}catch(NumberFormatException e){
								System.out.println(e);
								ifWrongFile = false;
								
							}
							
							ii--;
							continue;
							//ifWrongFile = false;
						}else
							ad.setNewCode(pi.getNewcode());
						
						DateFormat format1= new SimpleDateFormat("yyyyMMddHH");  
						// String转Date         
						try {   
							Date date1 = format1.parse(file.getName().substring(3, 13));
							ad.setSamplingTime(date1);
						} catch (ParseException e) {   
							System.out.println("日期有误");
							ifWrongFile = false;
						}  
						listToAdd.add(ad);
					}
					ifAirDataExist(listToAdd);//如果数据库中已经有此数据,则删除
					ComputeUtil cu = new ComputeUtil();
					List<AirData> listToAddR = cu.resultWithAQI(listToAdd,true);	
					
					if(!testAdd(listToAddR)) ifWrongFile = false;
				}
			}else{
				ifWrongFile = false;
			}
			
			//复制文件到备份目录或错误目录，删除原文件
			if (file.getAbsolutePath().indexOf(".") > -1) {

				
				if (ifWrongFile){
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/110000/".replace("110000", provinceCode));
					System.out.println("move");
				}else{
					ExcelUtil.FileMove(file.getAbsolutePath(), "E:/datashare_bak/errorfile/110000/".replace("110000", provinceCode));
					System.out.println("moveErr");
				}
			}
			
		}  
		
	}
	
	public boolean testAdd(List<AirData> list){
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				AirData ad = list.get(i);
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timestr=format1.format(ad.getSamplingTime());
				
				if(i>0) sb.append(",");
				sb.append("(null,")//id
				.append(ad.getYear())//year
				.append(",")
				.append(ad.getMonth())//month
				.append(",")
				.append(ad.getDay())//day
				.append(",")
				.append(ad.getHour())//hour
				.append(" ");
				//aqi
				if(null==ad.getAqi()||"".equals(ad.getAqi()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getAqi()+"'");
				//so2
				if(null==ad.getA21026()||"".equals(ad.getA21026()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA21026()+"'");
				//so2IAQI
				if(null==ad.getSO2IAQI()||"".equals(ad.getSO2IAQI()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getSO2IAQI()+"'");
				//no
				if(null==ad.getNO()||"".equals(ad.getNO()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getNO()+"'");
				//no2
				if(null==ad.getA21004()||"".equals(ad.getA21004()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA21004()+"'");
				//no2IAQI
				if(null==ad.getNO2IAQI()||"".equals(ad.getNO2IAQI()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getNO2IAQI()+"'");
				//nox
				if(null==ad.getNOx()||"".equals(ad.getNOx()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getNOx()+"'");
				//pm10
				if(null==ad.getA34002()||"".equals(ad.getA34002()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA34002()+"'");
				//pm10IAQI
				if(null==ad.getPM10IAQI()||"".equals(ad.getPM10IAQI()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getPM10IAQI()+"'");
				//pm10t24
				if(null==ad.getPm10t24()||"".equals(ad.getPm10t24()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getPm10t24()+"'");
				//co
				if(null==ad.getCO()||"".equals(ad.getCO()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getCO()+"'");
				//coIAQI
				if(null==ad.getCOIAQI()||"".equals(ad.getCOIAQI()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getCOIAQI()+"'");
				//o3
				if(null==ad.getO3()||"".equals(ad.getO3()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getO3()+"'");
				//o3IAQI
				if(null==ad.getO3IAQI()||"".equals(ad.getO3IAQI()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getO3IAQI()+"'");
				//o3t8
				if(null==ad.getO3T8()||"".equals(ad.getO3T8()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getO3T8()+"'");
				//pm2.5
				if(null==ad.getA34004()||"".equals(ad.getA34004()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA34004()+"'");
				//pm2.5IAQI
				if(null==ad.getPM25IAQI()||"".equals(ad.getPM25IAQI()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getPM25IAQI()+"'");
				//pm2.5t24
				if(null==ad.getPm25t24()||"".equals(ad.getPm25t24()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getPm25t24()+"'");
				//风速
				if(null==ad.getA01007()||"".equals(ad.getA01007()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA01007()+"'");
				//风向
				if(null==ad.getA01008()||"".equals(ad.getA01008()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA01008()+"'");
				//温度
				if(null==ad.getA01001()||"".equals(ad.getA01001()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA01001()+"'");
				//相对湿度
				if(null==ad.getA01004()||"".equals(ad.getA01004()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA01004()+"'");
				////气压
				if(null==ad.getA01006()||"".equals(ad.getA01006()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA01006()+"'");
				////能见度
				if(null==ad.getA01020()||"".equals(ad.getA01020()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getA01020()+"'");
				//雨量
				if(null==ad.getPrecipitation()||"".equals(ad.getPrecipitation()))
					sb.append(",null");
				else
					sb.append(",'"+ad.getPrecipitation()+"'");
				sb.append(",'")
				.append(timestr)//samplingtime
				.append("','")
				.append(ad.getNewCode())//newcode
				.append("') ");
				if(null==ad.getNewCode()){ 
					return false;
				}	
			}
			String sql = "insert into datashare.air_data values "+sb.toString();
			stmt.executeUpdate(sql);
			
			return true;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}finally{
			JdbcUtil.release(null, stmt, conn);
		}
	}
	
	
	public void ifAirDataExist(List<AirData> list) throws Exception{
		for(int i = 0;i<list.size();i++){
			AirData ad = new AirData();
			ad = list.get(i);
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestr=format1.format(ad.getSamplingTime());
			String newcode = ad.getNewCode();
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try{
				conn = JdbcUtil.getConnection();
				String sql = "select * from datashare.air_data where 1=1 and sampling_time='"+timestr+"' and newcode='"+newcode+"'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				if(rs.next()){
					String delsql = "delete from datashare.air_data where sampling_time='"+timestr+"' and newcode='"+newcode+"'";
//					stmt = conn.prepareStatement(delsql);
//					stmt.executeQuery();
					   try {
				            stmt.executeUpdate(delsql);
				        }catch (Exception e){
				        	System.out.println("sql delete 出错");
				            e.printStackTrace();
				        }
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}finally{
				JdbcUtil.release(rs, stmt, conn);
			}
		}
	}
	
	public boolean ifStationsDataExist(Date d) throws Exception{
		

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestr=format1.format(d);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select * from datashare.air_data where 1=1 and sampling_time='"+timestr+"' and newcode='110000_1001'";
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
		return false;
	}
	
	
	
	public void sqlexecute(String sql) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println(e);
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	@Test
	public void jTest(){
		try {
			dataParsing11("110000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.rj.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.rj.bean.AirData;
import com.rj.bean.PointInfo;

public class ComputeUtil {
	/**
	 * 计算AQI
	 * 参考文件:
	 * 环境空气质量标准GB3095-2012
	 * 环境空气质量指数(AQI)技术规定HJ633-2013
	 * 总站气字2013第240号文件-调整AQI发布
	 */
	public int pieceIAQI[]={0,50,100,150,200,300,400,500};
	public int[] pieceSO2t24={0,50,150,475,800,1600,2100,2620};//实时报的计算不用，24小时均值需要有至少20个有效数据
	public int[] pieceSO2={0,150,500,650,800};
	public int[] pieceNO2t24={0,40,80,180,280,565,750,940};//实时报的计算不用
	public int[] pieceNO2={0,100,200,700,1200,2340,2090,3840};
	public int[] piecePM10t24={0,50,150,250,350,420,500,600};//24小时滑动平均值需要有至少20个有效数据（小时值不计算IAQI）
	public int[] pieceCOt24={0,2,4,14,24,36,48,60};//实时报的计算不用
	public int[] pieceCO={0,5,10,35,60,90,120,150};
	public int[] pieceO3={0,160,200,300,400,800,1000,1200};
	public int[] pieceO3t8={0,100,160,215,265,800};//8小时滑动平均值至少有6个有效数据
	public int[] piecePM25t24={0,35,75,115,150,250,350,500};//24小时滑动平均值需要有至少20个有效数据（小时值不计算IAQI）
	
	public List<AirData> resultWithAQI(List<AirData> airDatas,boolean calculate) {
		List<AirData> resultWithAQI = new ArrayList<AirData>();
		for (int i = 0; i < airDatas.size(); i++) {
			AirData ad = airDatas.get(i);
			Calendar cal = Calendar.getInstance();
			cal.setTime(ad.getSamplingTime());
			cal.add(Calendar.HOUR, -24);
			Date tStart = cal.getTime();
		/*	// 获取该点位之前23个小时的AirData
			List<AirData> periodData = findAirData(ad.getNewCode(), tStart, ad.getSamplingTime());
			if(null==periodData||periodData.size()==0) {
				periodData=new ArrayList<AirData>();
			}
			periodData.add(ad);*/
			int so2IAQI = 0;
			int no2IAQI = 0;
			int coIAQI = 0;
			int o3IAQI = 0;
			int pm10t24IAQI = 0;
			int pm25t24IAQI = 0;
			int o3t8IAQI = 0;
			int AQI = 0;
			// 按时间递增排列，即将录入的数据也add到list
	/*		double pm10t24Sum = 0;
			int pm10t24Count = 0;
			double pm25t24Sum = 0;
			int pm25t24Count = 0;
			double o3t8Sum = 0;
			int o3t8Count = 0;
			
			//根据总站气字2013第240号文件-调整AQI发布 文件，不再使用滑动平均值
			for (int j = periodData.size() - 1; j >=0 && periodData.size() >= 8; j--) {// 倒着取，o3只取8次
				AirData rad = periodData.get(j);
				if (null != rad.getA34002() && !"".equals(rad.getA34002().trim())) {// pm10
					pm10t24Count++;
					pm10t24Sum += Double.parseDouble(rad.getA34002().trim());
				}
				if (null != rad.getA34004() && !"".equals(rad.getA34004().trim())) {// pm25
					pm25t24Count++;
					pm25t24Sum += Double.parseDouble(rad.getA34004().trim());
				}
				
				if (null != rad.getO3() && !"".equals(rad.getO3().trim()) && j > periodData.size() - 9) {// o3 取8次
					o3t8Count++;
					o3t8Sum += Double.parseDouble(rad.getO3().trim());
				}
				
			}*/
			// so2IAQI
			if (null != ad.getA21026() && !"".equals(ad.getA21026().trim())) {// so2IAQI
				double v = Double.parseDouble(ad.getA21026().trim());
				for (int p = 0; p < pieceSO2.length - 1; p++) {
					if (v > pieceSO2[p] && v <= pieceSO2[p + 1]) {
						double iaqid = pieceIAQI[p] + (v - pieceSO2[p]) / (pieceSO2[p + 1] - pieceSO2[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
						so2IAQI = (int) (iaqid );//取整
					} else if (v > pieceSO2[pieceSO2.length - 1]) {
						so2IAQI = pieceIAQI[pieceSO2.length - 1]+1;
					}
				}
			}
			// no2IAQI
			if (null != ad.getA21004() && !"".equals(ad.getA21004().trim())) {
				int[] piece = pieceNO2;
				double v = Double.parseDouble(ad.getA21004().trim());
				for (int p = 0; p < pieceNO2.length - 1; p++) {
					if (v > pieceNO2[p] && v <= pieceNO2[p + 1]) {
						double iaqid = pieceIAQI[p] + (v - piece[p]) / (pieceNO2[p + 1] - pieceNO2[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
						no2IAQI = (int) (iaqid );//取整
					} else if (v > pieceNO2[pieceNO2.length - 1]) {
						no2IAQI = pieceIAQI[pieceNO2.length - 1]+1;
					}
				}
			}
			// coIAQI
			if (null != ad.getCO() && !"".equals(ad.getCO().trim())) {
				int[] piece = pieceCO;
				double v = Double.parseDouble(ad.getCO().trim());
				for (int p = 0; p < piece.length - 1; p++) {
					if (v > piece[p] && v <= piece[p + 1]) {
						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
						coIAQI = (int) (iaqid );//取整
					} else if (v > piece[piece.length - 1]) {
						coIAQI = pieceIAQI[piece.length - 1]+1;
					}
				}
			}
			// o3IAQI
			if (null != ad.getO3() && !"".equals(ad.getO3().trim())) {
				int[] piece = pieceO3;
				double v = Double.parseDouble(ad.getO3().trim());
				for (int p = 0; p < piece.length - 1; p++) {
					if (v > piece[p] && v <= piece[p + 1]) {
						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
						o3IAQI = (int) (iaqid );//取整
					} else if (v > piece[piece.length - 1]) {
						o3IAQI = pieceIAQI[piece.length - 1]+1;
					}
				}
			}
			// pm10t24IAQI
			if (null != ad.getA34002() && !"".equals(ad.getA34002().trim())) {
				int[] piece = piecePM10t24;
				double v = Double.parseDouble(ad.getA34002().trim());
				for (int p = 0; p < piece.length - 1; p++) {
					if (v > piece[p] && v <= piece[p + 1]) {
						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
						pm10t24IAQI = (int) (iaqid );//取整
					} else if (v > piece[piece.length - 1]) {
						pm10t24IAQI = pieceIAQI[piece.length - 1]+1;
					}
				}
			}
//			if (null != ad.getA34002() && !"".equals(ad.getA34002().trim())&&pm10t24Count >= 20) {
//				int[] piece = piecePM10t24;
//				double v = pm10t24Sum / pm10t24Count;
//				ad.setPm10t24(Double.toString(v));
//				for (int p = 0; p < piece.length - 1; p++) {
//					if (v > piece[p] && v <= piece[p + 1]) {
//						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
//						pm10t24IAQI = (int) (iaqid );//取整
//					} else if (v > piece[piece.length - 1]) {
//						pm10t24IAQI = pieceIAQI[piece.length - 1];
//					}
//				}
//			}
			// pm25t24IAQI
			if (null != ad.getA34004() && !"".equals(ad.getA34004().trim())) {
				int[] piece = piecePM25t24;
				double v = Double.parseDouble(ad.getA34004().trim());
				for (int p = 0; p < piece.length - 1; p++) {
					if (v > piece[p] && v <= piece[p + 1]) {
						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
						pm25t24IAQI = (int) (iaqid );//取整
					} else if (v > piece[piece.length - 1]) {
						pm25t24IAQI = pieceIAQI[piece.length - 1]+1;
					}
				}
			}
//			if (null != ad.getA34004() && !"".equals(ad.getA34004().trim())&&pm25t24Count >= 20) {
//				int[] piece = piecePM25t24;
//				double v = pm25t24Sum / pm25t24Count;
//				ad.setPm25t24(Double.toString(v));
//				for (int p = 0; p < piece.length - 1; p++) {
//					if (v > piece[p] && v <= piece[p + 1]) {
//						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
//						pm25t24IAQI = (int) (iaqid );//取整
//					} else if (v > piece[piece.length - 1]) {
//						pm25t24IAQI = pieceIAQI[piece.length - 1];
//					}
//				}
//			}
			
			// o3t8IAQI
//			if (null != ad.getO3() && !"".equals(ad.getO3().trim())&&o3t8Count >= 6) {
//				int[] piece = pieceO3t8;
//				double v = o3t8Sum / o3t8Count;
//				ad.setO3T8(Double.toString(v));
//				for (int p = 0; p < piece.length - 1; p++) {
//					if (v > piece[p] && v <= piece[p + 1]) {
//						double iaqid = pieceIAQI[p] + (v - piece[p]) / (piece[p + 1] - piece[p]) * (pieceIAQI[p + 1] - pieceIAQI[p]);
//						o3t8IAQI = (int) (iaqid );//取整
//					} else if (v > piece[piece.length - 1]) {
//						o3t8IAQI = pieceIAQI[piece.length - 1];
//					}
//				}
//			}
			List<Integer> IAQIs = new ArrayList<Integer>();
			if(so2IAQI>0){
				IAQIs.add(so2IAQI);
				ad.setSO2IAQI(Integer.toString(so2IAQI));
			}
			if(no2IAQI>0){
				IAQIs.add(no2IAQI);
				ad.setNO2IAQI(Integer.toString(no2IAQI));
			}
			if(coIAQI>0){
				IAQIs.add(coIAQI);
				ad.setCOIAQI(Integer.toString(coIAQI));
			}
			if(o3IAQI>0){
				IAQIs.add(o3IAQI);
				ad.setO3IAQI(Integer.toString(o3IAQI));
			}
			if(pm10t24IAQI>0){
				IAQIs.add(pm10t24IAQI);
				ad.setPM10IAQI(Integer.toString(pm10t24IAQI));
			}
			if(pm25t24IAQI>0){
				IAQIs.add(pm25t24IAQI);
				ad.setPM25IAQI(Integer.toString(pm25t24IAQI));
			}
			
		//	IAQIs.add(o3t8IAQI);
			if(IAQIs.size()>0 && calculate){
				AQI = Collections.max(IAQIs);
			}
			if(AQI>0) ad.setAqi(Integer.toString(AQI));
			resultWithAQI.add(ad);
			
		}

		return resultWithAQI;
	}
	//查询时间段内的数据用以计算滑动平均值
	//有SQL注入问题
	public List<AirData> findAirData(String newcode,Date startTime,Date endTime) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strStart = format1.format(startTime);
		String strEnd = format1.format(endTime);
		
		List<AirData> airDatas = new ArrayList<AirData>();
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select * from datashare.air_data where 1=1 and newcode='"+newcode+"' and sampling_time>'"+strStart+"' and sampling_time<'"+strEnd+"' order by sampling_time asc";
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				AirData ad = new AirData();
				ad.setO3(rs.getString("o3"));
				ad.setA34002(rs.getString("a34002"));
				ad.setA34004(rs.getString("a34004"));
				ad.setSamplingTime(rs.getDate("sampling_time"));
				airDatas.add(ad);
			}
			if(null==airDatas||airDatas.size()==0)
				return null;
			else
				return airDatas;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	@Test
	public void testMethod(){
		Date d1 = new Date();
		Date d2 = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		c1.add(Calendar.DATE, -40);
		c2.add(Calendar.DATE, -2);
		
		List<AirData> periodData = findAirData("120000_0001",c1.getTime(),c2.getTime());
		for(int i=0;i<periodData.size();i++){
			System.out.println((periodData.get(i)).getO3());
		}
	}
}

package com.rj.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.rj.bean.AirData;
import com.rj.bean.PointInfo;

public class PointUtil {
	
	
	/**专用于国控点位查询
	 * 返回值PointInfo中只有stationCode,newCodes
	 */
	public List<PointInfo> getStations() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		List<PointInfo> stations = new ArrayList<PointInfo>();
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select stationcode,newcode from datashare.point_info where 1=1 and stationattribute=1 order by id asc";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				PointInfo pi = new PointInfo();
				pi.setStationcode(rs.getString("stationcode"));
				pi.setNewcode(rs.getString("newcode"));
				stations.add(pi);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}

		if(null==stations||stations.size()==0)
			return null;
		else
			return stations;
	}
	
	//有SQL注入问题
	public PointInfo findPoint(PointInfo pointInfo,boolean position) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select * from datashare.point_info where 1=1 ";
			if(null!=pointInfo.getCitycode()&&!"".equals(pointInfo.getCitycode()))
				sql += " and citycode='"+pointInfo.getCitycode()+"'";
			if(null!=pointInfo.getCityname()&&!"".equals(pointInfo.getCityname()))
				sql +=" and cityname='"+pointInfo.getCityname()+"'";	
			if(null!=pointInfo.getStationcode()&&!"".equals(pointInfo.getStationcode()))
				sql +=" and stationcode='"+pointInfo.getStationcode()+"'";
			if(null!=pointInfo.getStationname()&&!"".equals(pointInfo.getStationname()))
				sql +=" and stationname='"+pointInfo.getStationname()+"'";
			if(position&&null!=pointInfo.getLongitude()&&!"".equals(pointInfo.getLongitude()))
				sql +=" and longitude like '"+pointInfo.getLongitude()+"'";
			if(position&&null!=pointInfo.getLatitude()&&!"".equals(pointInfo.getLatitude()))
				sql +=" and latitude like '"+pointInfo.getLatitude()+"'";
			
			stmt = conn.prepareStatement(sql+" order by newcode desc");
			rs = stmt.executeQuery();
			if(rs.next()){
				PointInfo pi = new PointInfo();
				pi.setCitycode(rs.getString("citycode"));
				pi.setCityname(rs.getString("cityname"));
				pi.setStationcode(rs.getString("stationcode"));
				pi.setStationname(rs.getString("stationname"));
				pi.setStationattribute(rs.getInt("stationattribute"));
				pi.setLongitude(rs.getString("longitude"));
				pi.setLatitude(rs.getString("latitude"));
				pi.setNewcode(rs.getString("newcode"));
				return pi;
			}
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	public List<PointInfo> findPoints(PointInfo pointInfo) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		List<PointInfo> points = new ArrayList<PointInfo>();
		try{
			conn = JdbcUtil.getConnection();
			String sql = "select * from datashare.point_info where stationattribute=2 ";
			if(null!=pointInfo.getCitycode()&&!"".equals(pointInfo.getCitycode()))
				sql += " and citycode like '"+pointInfo.getCitycode()+"'";
			if(null!=pointInfo.getCityname()&&!"".equals(pointInfo.getCityname()))
				sql +=" and cityname like '"+pointInfo.getCityname()+"'";	
			if(null!=pointInfo.getStationcode()&&!"".equals(pointInfo.getStationcode()))
				sql +=" and stationcode like '"+pointInfo.getStationcode()+"'";
			if(null!=pointInfo.getStationname()&&!"".equals(pointInfo.getStationname()))
				sql +=" and stationname like '"+pointInfo.getStationname()+"'";
			if(null!=pointInfo.getLongitude()&&!"".equals(pointInfo.getLongitude()))
				sql +=" and longitude like '"+pointInfo.getLongitude()+"'";
			if(null!=pointInfo.getLatitude()&&!"".equals(pointInfo.getLatitude()))
				sql +=" and latitude like '"+pointInfo.getLatitude()+"'";
			if(null!=pointInfo.getNewcode()&&!"".equals(pointInfo.getNewcode()))
				sql +=" and newcode like '"+pointInfo.getNewcode()+"%'";
			
			stmt = conn.prepareStatement(sql+" order by newcode desc");
			rs = stmt.executeQuery();
			while(rs.next()){
				PointInfo pi = new PointInfo();
				pi.setCitycode(rs.getString("citycode"));
				pi.setCityname(rs.getString("cityname"));
				pi.setStationcode(rs.getString("stationcode"));
				pi.setStationname(rs.getString("stationname"));
				pi.setStationattribute(rs.getInt("stationattribute"));
				pi.setLongitude(rs.getString("longitude"));
				pi.setLatitude(rs.getString("latitude"));
				pi.setNewcode(rs.getString("newcode"));
				points.add(pi);
			}
			if(points.size()>0)
				return points;
			else
				return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	@Test
	public void test(){
		 List<PointInfo> sl = getStations();
		 Iterator ii = sl.iterator();
		 int n=1;
		 while(ii.hasNext()){
			 PointInfo pi = (PointInfo) ii.next();
			 System.out.println(n+" | "+pi.getStationcode()+"|"+pi.getNewcode());
			 n++;
		 }
		 System.out.println("end");
	}
}

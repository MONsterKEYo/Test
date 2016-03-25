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

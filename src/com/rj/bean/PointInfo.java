package com.rj.bean;



public class PointInfo implements java.io.Serializable {
	private int id;
	private String citycode;
	private String cityname;
	private String stationcode;
	private String stationname;
	private int stationattribute;
	private String longitude;
	private String latitude;
	private String admincode;
	private String newcode;
	
	public PointInfo(){
		
	}
	
	public PointInfo(int id, String citycode, String cityname,
			String stationcode, String stationname, int stationattribute,
			String longitude, String latitude, String admincode, String newcode) {
		super();
		this.id = id;
		this.citycode = citycode;
		this.cityname = cityname;
		this.stationcode = stationcode;
		this.stationname = stationname;
		this.stationattribute = stationattribute;
		this.longitude = longitude;
		this.latitude = latitude;
		this.admincode = admincode;
		this.newcode = newcode;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getStationcode() {
		return stationcode;
	}
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	public String getStationname() {
		return stationname;
	}
	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	public int getStationattribute() {
		return stationattribute;
	}
	public void setStationattribute(int stationattribute) {
		this.stationattribute = stationattribute;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAdmincode() {
		return admincode;
	}

	public void setAdmincode(String admincode) {
		this.admincode = admincode;
	}

	public String getNewcode() {
		return newcode;
	}
	public void setNewcode(String newcode) {
		this.newcode = newcode;
	}
	
}
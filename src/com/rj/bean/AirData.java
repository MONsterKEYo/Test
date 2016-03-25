package com.rj.bean;

import java.util.Date;


public class AirData implements java.io.Serializable {
	private int id;
	private int year;
	private int month;
	private int day;
	private int hour;
	private String aqi;
	private String A21026;
	private String SO2IAQI;
	private String NO;//一氧化氮
	private String NOx;//氮氧化物
	private String A21004;
	private String NO2IAQI;
	private String A34002;
	private String PM10IAQI;
	private String pm10t24;
	private String CO;
	private String COIAQI;
	private String O3;
	private String O3IAQI;
	private String O3T8;
	private String A34004;
	private String PM25IAQI;
	private String pm25t24;
	private String A01007;
	private String A01008;
	private String A01001;
	private String A01004;
	private String A01006;
	private String A01020;
	private String precipitation;//雨量，降水量
	private Date samplingTime;
	private String newCode;
	
	
	
	public AirData() {
	}



	public AirData(int id, int year, int month, int day, int hour, String aqi,
			String a21026, String sO2IAQI, String nO, String nOx,
			String a21004, String nO2IAQI, String a34002, String pM10IAQI,
			String pm10t24, String cO, String cOIAQI, String o3, String o3iaqi,
			String o3t8, String a34004, String pM25IAQI, String pm25t24,
			String a01007, String a01008, String a01001, String a01004,
			String a01006, String a01020, String precipitation,
			Date samplingTime, String newCode) {
		super();
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.aqi = aqi;
		A21026 = a21026;
		SO2IAQI = sO2IAQI;
		NO = nO;
		NOx = nOx;
		A21004 = a21004;
		NO2IAQI = nO2IAQI;
		A34002 = a34002;
		PM10IAQI = pM10IAQI;
		this.pm10t24 = pm10t24;
		CO = cO;
		COIAQI = cOIAQI;
		O3 = o3;
		O3IAQI = o3iaqi;
		O3T8 = o3t8;
		A34004 = a34004;
		PM25IAQI = pM25IAQI;
		this.pm25t24 = pm25t24;
		A01007 = a01007;
		A01008 = a01008;
		A01001 = a01001;
		A01004 = a01004;
		A01006 = a01006;
		A01020 = a01020;
		this.precipitation = precipitation;
		this.samplingTime = samplingTime;
		this.newCode = newCode;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public int getMonth() {
		return month;
	}



	public void setMonth(int month) {
		this.month = month;
	}



	public int getDay() {
		return day;
	}



	public void setDay(int day) {
		this.day = day;
	}



	public int getHour() {
		return hour;
	}



	public void setHour(int hour) {
		this.hour = hour;
	}



	public String getAqi() {
		return aqi;
	}



	public void setAqi(String aqi) {
		this.aqi = aqi;
	}



	public String getA21026() {
		return A21026;
	}



	public void setA21026(String a21026) {
		A21026 = a21026;
	}



	public String getSO2IAQI() {
		return SO2IAQI;
	}



	public void setSO2IAQI(String sO2IAQI) {
		SO2IAQI = sO2IAQI;
	}



	public String getNO() {
		return NO;
	}



	public void setNO(String nO) {
		NO = nO;
	}



	public String getNOx() {
		return NOx;
	}



	public void setNOx(String nOx) {
		NOx = nOx;
	}



	public String getA21004() {
		return A21004;
	}



	public void setA21004(String a21004) {
		A21004 = a21004;
	}



	public String getNO2IAQI() {
		return NO2IAQI;
	}



	public void setNO2IAQI(String nO2IAQI) {
		NO2IAQI = nO2IAQI;
	}



	public String getA34002() {
		return A34002;
	}



	public void setA34002(String a34002) {
		A34002 = a34002;
	}



	public String getPM10IAQI() {
		return PM10IAQI;
	}



	public void setPM10IAQI(String pM10IAQI) {
		PM10IAQI = pM10IAQI;
	}



	public String getPm10t24() {
		return pm10t24;
	}



	public void setPm10t24(String pm10t24) {
		this.pm10t24 = pm10t24;
	}



	public String getCO() {
		return CO;
	}



	public void setCO(String cO) {
		CO = cO;
	}



	public String getCOIAQI() {
		return COIAQI;
	}



	public void setCOIAQI(String cOIAQI) {
		COIAQI = cOIAQI;
	}



	public String getO3() {
		return O3;
	}



	public void setO3(String o3) {
		O3 = o3;
	}



	public String getO3IAQI() {
		return O3IAQI;
	}



	public void setO3IAQI(String o3iaqi) {
		O3IAQI = o3iaqi;
	}



	public String getO3T8() {
		return O3T8;
	}



	public void setO3T8(String o3t8) {
		O3T8 = o3t8;
	}



	public String getA34004() {
		return A34004;
	}



	public void setA34004(String a34004) {
		A34004 = a34004;
	}



	public String getPM25IAQI() {
		return PM25IAQI;
	}



	public void setPM25IAQI(String pM25IAQI) {
		PM25IAQI = pM25IAQI;
	}



	public String getPm25t24() {
		return pm25t24;
	}



	public void setPm25t24(String pm25t24) {
		this.pm25t24 = pm25t24;
	}



	public String getA01007() {
		return A01007;
	}



	public void setA01007(String a01007) {
		A01007 = a01007;
	}



	public String getA01008() {
		return A01008;
	}



	public void setA01008(String a01008) {
		A01008 = a01008;
	}



	public String getA01001() {
		return A01001;
	}



	public void setA01001(String a01001) {
		A01001 = a01001;
	}



	public String getA01004() {
		return A01004;
	}



	public void setA01004(String a01004) {
		A01004 = a01004;
	}



	public String getA01006() {
		return A01006;
	}



	public void setA01006(String a01006) {
		A01006 = a01006;
	}



	public String getA01020() {
		return A01020;
	}



	public void setA01020(String a01020) {
		A01020 = a01020;
	}



	public String getPrecipitation() {
		return precipitation;
	}



	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}



	public Date getSamplingTime() {
		return samplingTime;
	}



	public void setSamplingTime(Date samplingTime) {
		this.samplingTime = samplingTime;
	}



	public String getNewCode() {
		return newCode;
	}



	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}


	
}

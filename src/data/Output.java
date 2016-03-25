package data;

import stationQualityData.WSEnvCityData;

public class Output {
	
	public void printdata(){
		WSEnvCityData dao = new WSEnvCityData();
		System.out.println(dao.getWSEnvCityDataSoap().stationQualityData("ZongHeQueryUser", "dG245%uisWR34n97tY"));
	}
}

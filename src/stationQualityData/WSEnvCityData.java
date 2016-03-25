package stationQualityData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * WSEnvCityData service = new WSEnvCityData();
 * WSEnvCityDataSoap portType = service.getWSEnvCityDataSoap();
 * portType.stationQualityData(...);
 * </pre>
 * 
 * </p>
 * 
 */
@WebServiceClient(name = "WSEnvCityData", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://172.16.0.190:8081/WSEnvCityData.asmx?wsdl")
public class WSEnvCityData extends Service {

	private final static URL WSENVCITYDATA_WSDL_LOCATION;
	private final static Logger logger = Logger
			.getLogger(stationQualityData.WSEnvCityData.class.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = stationQualityData.WSEnvCityData.class.getResource(".");
			url = new URL(baseUrl,
					"http://172.16.0.190:8081/WSEnvCityData.asmx?wsdl");
		} catch (MalformedURLException e) {
			logger.warning("Failed to create URL for the wsdl Location: 'http://172.16.0.190:8081/WSEnvCityData.asmx?wsdl', retrying as a local file");
			logger.warning(e.getMessage());
		}
		WSENVCITYDATA_WSDL_LOCATION = url;
	}

	public WSEnvCityData(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public WSEnvCityData() {
		super(WSENVCITYDATA_WSDL_LOCATION, new QName("http://tempuri.org/",
				"WSEnvCityData"));
	}

	/**
	 * 
	 * @return returns WSEnvCityDataSoap
	 */
	@WebEndpoint(name = "WSEnvCityDataSoap")
	public WSEnvCityDataSoap getWSEnvCityDataSoap() {
		return super.getPort(new QName("http://tempuri.org/",
				"WSEnvCityDataSoap"), WSEnvCityDataSoap.class);
	}

	/**
	 * 
	 * @return returns WSEnvCityDataSoap
	 */
	@WebEndpoint(name = "WSEnvCityDataSoap12")
	public WSEnvCityDataSoap getWSEnvCityDataSoap12() {
		return super.getPort(new QName("http://tempuri.org/",
				"WSEnvCityDataSoap12"), WSEnvCityDataSoap.class);
	}

}
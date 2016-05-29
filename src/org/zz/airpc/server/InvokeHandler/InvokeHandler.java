package org.zz.airpc.server.InvokeHandler;


import java.io.InputStream;
import java.util.Map;

public interface InvokeHandler {

	Object invoke(String realInterfaceName);
	
	void closeSource(InputStream in);
	
	void transferResult(Object result);
	
	String InterfaceAdapter(Map<String, String> initMap);
	
}

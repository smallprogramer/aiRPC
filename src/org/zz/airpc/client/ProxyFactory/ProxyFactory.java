package org.zz.airpc.client.ProxyFactory;

import java.net.MalformedURLException;

public interface ProxyFactory {
	
	Object send(Class<?> api, String url)throws MalformedURLException;
	
}

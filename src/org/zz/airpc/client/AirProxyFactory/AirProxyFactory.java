package org.zz.airpc.client.AirProxyFactory;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

import org.zz.airpc.client.AirProxyHandler.AirProxyHandler;
import org.zz.airpc.client.ProxyFactory.ProxyFactory;
import org.zz.airpc.client.Utils.AirInterface;



public class AirProxyFactory implements ProxyFactory{
	
	private ClassLoader loader;
	
	public AirProxyFactory(){
		this(Thread.currentThread().getContextClassLoader());
	}
	
	public AirProxyFactory(ClassLoader loader){
		this.loader = loader;
	}
	
	

	@Override
	public Object send(Class<?> api, String urlName) throws MalformedURLException {
		URL url = new URL(urlName);
		return send(api, url, loader);
	}
	
	
	public Object send(Class<?> api, URL url, ClassLoader loader){
		if(api == null){
			throw new NullPointerException("api is null!");
		}
		
		AirProxyHandler airHandler = null;
		airHandler = new AirProxyHandler(api, url);
		return Proxy.newProxyInstance(loader, new Class[]{api, AirInterface.class}, airHandler);
			
	}
	
}

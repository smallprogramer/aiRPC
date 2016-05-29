package org.zz.airpc.client.AirProxyHandler;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class AirProxyHandler implements InvocationHandler,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<?> api;
	private URL url;

	public AirProxyHandler(Class<?> api, URL url){
		this.api = api;
		this.url = url;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		String interfacename = api.toString();
	
		//����׼��
		HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
		httpUrlConnection.setDoOutput(true);
	    httpUrlConnection.setDoInput(true);
		httpUrlConnection.setUseCaches(false);
		httpUrlConnection.setRequestProperty("Content-type","application/x-java-serialized-object");
	    httpUrlConnection.setRequestMethod("POST");	  
		httpUrlConnection.connect();
		OutputStream outStrm = httpUrlConnection.getOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(outStrm);

		//��������
		output.writeUTF(interfacename);
		output.writeUTF(method.getName());
		output.writeObject(method.getParameterTypes());
		output.writeObject(args);
		output.flush();
		output.close();

		//����
		InputStream input = httpUrlConnection.getInputStream();
		
		//��ȡԶ�̵���ִ�н��
		ObjectInputStream in = new ObjectInputStream(input);
		Object re =  in.readObject();

		
		return re;
	}

	
	
}

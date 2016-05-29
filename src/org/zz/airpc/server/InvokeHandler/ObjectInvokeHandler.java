package org.zz.airpc.server.InvokeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ObjectInvokeHandler implements InvokeHandler{

	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;

	public ObjectInvokeHandler(InputStream in, OutputStream out) {
		// TODO Auto-generated constructor stub
		try {			
			input = new ObjectInputStream(in);
			output = new ObjectOutputStream(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(String realInterfaceName) {
		try {				
			try {
				String methodName = input.readUTF();
				Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
				Object[] arguments = (Object[]) input.readObject();
				
				@SuppressWarnings("rawtypes")
				Class serviceinterfaceclass = Class.forName(realInterfaceName);
				
				Object obj;
				try {
					obj = serviceinterfaceclass.newInstance();
					Method method;
					try {						
						try {
							method = serviceinterfaceclass.getMethod(methodName, parameterTypes);
							Object result = method.invoke(obj, arguments);
							if(result != null){
								return result;
							}							
							return null;
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeSource(input);
		}
		
		return null;
	}

	


	@Override
	public String InterfaceAdapter(Map<String, String> initMap) {
		// TODO Auto-generated method stub
		try {
			
			String Interfacename = input.readUTF();
			String[] str = Interfacename.split("\\.");
			String doAfterInterfaceName = str[str.length - 1];			
			String realName = initMap.get(doAfterInterfaceName);			
			return realName;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void closeSource(InputStream in) {
		// TODO Auto-generated method stub	
			try {
				if(in != null){
				   in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	@Override
	public void transferResult(Object result) {
		// TODO Auto-generated method stub
		try {
			output.writeObject(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(output != null){
					output.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

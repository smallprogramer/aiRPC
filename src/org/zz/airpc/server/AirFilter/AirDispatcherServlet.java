package org.zz.airpc.server.AirFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zz.airpc.server.InvokeHandler.InvokeHandler;
import org.zz.airpc.server.InvokeHandler.ObjectInvokeHandler;


public class AirDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Map<String, String> initMap = new ConcurrentHashMap<>();
    public AirDispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletInputStream in = null;
		ServletOutputStream out = null;
		try{
			in =request.getInputStream();
			out = response.getOutputStream();
	
			InvokeHandler invoke = new ObjectInvokeHandler(in,out);
			String realInterfaceName = invoke.InterfaceAdapter(initMap);
			Object result = invoke.invoke(realInterfaceName);
			
			
			/*·µ»Ø½á¹û*/
			invoke.transferResult(result);
						
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in != null){
				in.close();
			}
			
			if(out != null){
				out.close();
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		ServletConfig fConfig = getServletConfig();
		
		@SuppressWarnings("unchecked")
		Enumeration<String> e = fConfig.getInitParameterNames();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			String value = fConfig.getInitParameter(key);
			initMap.put(key, value);
		}
	}
}

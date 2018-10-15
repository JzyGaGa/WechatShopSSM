package com.imooc.o2o.util;




import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 * @author 59842
 *
 */
public class HttpServletRequestUtil {
	
	public static int getInt(HttpServletRequest request,String key) {

		try {
			return  Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getLong(HttpServletRequest request,String key) {

		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return  -1;
		}
	}
	public static Double getDouble(HttpServletRequest request,String key) {
		
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return  -1d;
		}
	}
	
	public static Boolean getBoolean (HttpServletRequest request,String key) {
		
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return  false;
		}
	}
	
	public static String getString (HttpServletRequest request,String key) {
		try {
			String string = request.getParameter(key);
			if(string!=null)
				string=string.trim();
			if("".equals(string)) 
				return null;
			return string;
		} catch (Exception e) {
			return null;
		}
	}
}

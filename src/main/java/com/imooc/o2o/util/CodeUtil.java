package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;
/**
 * 验证码的校对
 * @author 59842
 *
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		//
		String verifyCodeExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//从传过来的请求中获取验证码
		String verifyCodeActual = HttpServletRequestUtil.getString(request,
				"verifyCodeActual");
		if (verifyCodeActual == null
				|| !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}

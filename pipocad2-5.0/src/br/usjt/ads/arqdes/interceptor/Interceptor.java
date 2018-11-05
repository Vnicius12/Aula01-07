package br.usjt.ads.arqdes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws Exception {
		
		String uri = request.getRequestURI();
		
		if (uri.endsWith("/") || uri.endsWith("inicio") || uri.endsWith("fazer_login") || uri.endsWith("tela_login") || uri.contains("css") || uri.contains("js") ||uri.contains("img")) {
			return true;
		}
		
		if (request.getSession().getAttribute("usuario") != null) {
			return true;
		}
		response.sendRedirect("tela_login");
		return false;
	}
}

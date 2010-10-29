package br.org.direto.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Utils {

	private static HttpSession session;
	
	public static Integer getIdCarteiraFromSession(HttpServletRequest request){
		Utils.session = request.getSession(true);
		return new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
	}
	
}

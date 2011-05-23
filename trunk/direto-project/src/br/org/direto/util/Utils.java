package br.org.direto.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.ged.direto.model.entity.Usuario;

public class Utils {

	private static HttpSession session;
	
	public static Integer getIdCarteiraFromSession(HttpServletRequest request){
		Utils.session = request.getSession(true);
		return new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
	}
	
	public static String formatNUD(String protocolo){
		
		if (protocolo.length() != 17) return protocolo;
		
		String nup = protocolo.substring(0, 5);
			nup +=  "."+protocolo.substring(5, 11);
			nup +=  "/"+protocolo.substring(11, 15);
			nup +=  "-"+protocolo.substring(15, 17);
		
		return nup;
	}
	
	public static String formatHexa(String hexa){
		String hexaFormated = "";
		
		char[] c = hexa.toCharArray();
		int i;
		
		for(i=0;i<c.length;i++){
			hexaFormated+=c[i];
			if(i%2==1)
				hexaFormated+=" ";
		}
		
		return hexaFormated;
	}
	
	public static Usuario userLogon(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Usuario)auth.getPrincipal();
	}
}

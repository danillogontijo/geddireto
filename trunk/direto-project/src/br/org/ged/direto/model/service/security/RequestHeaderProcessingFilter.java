package br.org.ged.direto.model.service.security;

import java.io.IOException;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.Assert;

import br.org.ged.direto.model.entity.Usuario;


public class RequestHeaderProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
    public static final String SPRING_SECURITY_FORM_USUARIO_CONTA_KEY = "j_usuario_conta";
    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String usuarioContaParameter = SPRING_SECURITY_FORM_USUARIO_CONTA_KEY;
    
    private boolean postOnly = true;

	protected RequestHeaderProcessingFilter() {
        super("/j_spring_security_filter");
	}
	
	@Override
	 public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       
		if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
		
		 String username = obtainUsername(request);
	     String password = obtainPassword(request);
	     String usuarioConta = obtainUsuarioConta(request);
	     
	        if (username == null) {
	            username = "";
	        }

	        if (password == null) {
	            password = "";
	        }
	        
	        if (usuarioConta == null) {
	        	usuarioConta = "";
	        }

	        username = username.trim();
	        
	        System.out.println("Autenticando RequestHeaderProcessingFilter:" + usuarioConta);

	        UsuarioContaAuthenticationToken authRequest = new UsuarioContaAuthenticationToken(username, password, usuarioConta);
	               
	        // Place the last username attempted into HttpSession for views
	        HttpSession session = request.getSession(true);
	        
	        System.out.println(session.getAttributeNames().toString());

	        if (session != null || getAllowSessionCreation()) {
	            request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
	            request.getSession().setAttribute(SPRING_SECURITY_FORM_USUARIO_CONTA_KEY, TextEscapeUtils.escapeEntities(usuarioConta));
	        }
	        
	        System.out.println(session.getAttribute(SPRING_SECURITY_LAST_USERNAME_KEY));

	        // Allow subclasses to set the "details" property
	        setDetails(request, authRequest);
	        
	        //Authentication a = this.getAuthenticationManager().authenticate(authRequest);
	        //UserDetails u = a.getPrincipal();

	        return this.getAuthenticationManager().authenticate(authRequest);

		 
       // request.getHeader(usernameHeader);
        
      /*  //response.
        
        System.out.println("Autenticando RequestHeaderProcessingFilter:" + usuarioConta);

       
        
        //AbstractAuthenticationToken token = authRequest;
        WebAuthenticationDetails wad = new WebAuthenticationDetails(request);
        
       // wad.
        
        authRequest.setDetails(wad);
        
        //SecurityContextHolder.getContext().setAuthentication(authRequest);
        //response.addHeader(usuarioConta, getUsuarioContaHeader());
        //authRequest.
        
        System.out.println("RETORNANDO Authentication");
        //return authRequest;
        
       return this.getAuthenticationManager().authenticate(authRequest);*/		
	}

	protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }
    
    protected String obtainUsuarioConta(HttpServletRequest request) {
        return request.getParameter(usuarioContaParameter);
    }
    
    protected void setDetails(HttpServletRequest request, UsuarioContaAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }
    
    public void setusuarioContaParameter(String usuarioContaParameter) {
        Assert.hasText(usuarioContaParameter, "usuarioConta parameter must not be empty or null");
        this.usuarioContaParameter = usuarioContaParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }

    public final String getPasswordParameter() {
        return passwordParameter;
    }


	
	
}

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% //response.sendRedirect("principal.html"); %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="br.org.ged.direto.model.entity.*" %>
<%@ page import="br.org.ged.direto.model.service.security.*" %>
<%@ page import="org.springframework.security.core.userdetails.*" %>
<%@ page import="org.springframework.security.web.authentication.WebAuthenticationDetails" %>


<a href="user/add.htm" >Add</a> <br>
<a href="user/remove.htm" >Remove</a>

<br>
<br>
<a href="usuarioTeste.html?id=1" >usuarioTeste.html?id=1</a><br>
<a href="login.html" >Login.html</a><br>
<a href="exitUser.jsp" >exit</a><br>
<a href="principal.html?box=1" >principal</a><br>
<a href="logout.jsp" >logout</a><br>
<a href="login.jsp" >login</a><br>
<a href="changePwd.html" >Change Pwd</a><br>



<%
	
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
	//UsuarioContaAuthenticationToken token = (UsuarioContaAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    
	//SecurityContextHolder.
	
	//UsuarioContaAuthentication.class
	
	//UsuarioContaAuthenticationToken authRequest = (UsuarioContaAuthenticationToken) auth;
		//Usuario o = (Usuario) principal;
		
		//o.
		
		//Object o = (org.springframework.security.web.authentication.WebAuthenticationDetails) auth.getDetails();
		
		if (auth != null) { 
        
			WebAuthenticationDetails wad = (WebAuthenticationDetails)auth.getDetails();
			
			out.println(wad.toString());
			
			//(Details)obj;
			
			//Usuario u = (Usuario) auth.getPrincipal();
        //(UsuarioContaAuthenticationToken) auth.
        
        //UsuarioContaAuthenticationToken contaUsuarioToken =	(UsuarioContaAuthenticationToken) auth.getPrincipal();
        
        //contaUsuarioToken.getRequestUsuarioConta();
        
        //out.println(auth.getDetails().toString());
        
        //out.println("<br>Conta Atual: "+u.getContaAtual());
        
        //auth.
        //u.setContaAtual("4");
        
        //out.println("<br>Conta Atual: "+u.getContaAtual());
        
        
        %>
<p>
            Authentication object is of type: <em><%= auth.getClass().getName() %></em>
</p>
<p>
            Authentication object as a String: <br/><br/><%= auth.toString() %>
</p>

            Authentication object holds the following granted authorities:<br /><br />
<%
            for (GrantedAuthority authority : auth.getAuthorities()) { %>
                <%= authority %> (<em>getAuthority()</em>: <%= authority.getAuthority() %>)<br />
<%			}
%>

                <p><b>Success! Your web filters appear to be properly configured!</b></p>
<%
        } else {
%>
            Authentication object is null.<br />
            This is an error and your Spring Security application will not operate properly until corrected.<br /><br />
<%		}
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% //response.sendRedirect("principal.html"); %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="br.org.ged.direto.model.entity.*" %>
<%@ page import="br.org.ged.direto.model.service.security.*" %>

<a href="user/add.htm" >Add</a> <br>
<a href="user/remove.htm" >Remove</a>

<br>
<br>
<a href="usuarioTeste.html?id=1" >usuarioTeste.html?id=1</a><br>
<a href="login.html" >Login.html</a><br>
<a href="exitUser.jsp" >exit</a><br>
<a href="principal.html" >principal</a><br>
<a href="logout.jsp" >logout</a><br>
<a href="login.jsp" >login</a><br>
<a href="changePwd.html" >Change Pwd</a><br>



<%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) { 
        //Usuario o = (Usuario) auth.getPrincipal();
        //auth.
        
        	//UsuarioContaAuthenticationToken contaUsuarioToken =	(UsuarioContaAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        
       // out.println(o.getUsername());
        
        //out.println(auth.);
        
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
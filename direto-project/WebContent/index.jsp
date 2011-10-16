<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="br.org.ged.direto.model.entity.*" %>
<%@ page import="br.org.ged.direto.model.service.security.*" %>
<%@ page import="org.springframework.security.core.userdetails.*" %>
<%@ page import="org.springframework.security.web.authentication.WebAuthenticationDetails" %>

<%@ page import="java.util.*" %>


<% 
response.sendRedirect("https://diretov3.bdaopesp.eb.mil.br/feed.html");
%>

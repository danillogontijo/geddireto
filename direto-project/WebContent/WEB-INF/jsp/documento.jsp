<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>

<div style="width:100%; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
				
			<a href=""  style="margin-left: 5px;" class="menu2">Encaminhar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Responder</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Despachar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anotar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anexar</a> |
			
		
</div>



<div style="width:100%; text-align:center; float: left; position: static; width: 822px; vertical-align: middle;">
		
		
	${documento.assunto}<br>
		
		
		<c:forEach var="userMap" items="${allDocuments}">
		    	<c:set var="user" value="${userMap.key}"/>
		    	<c:set var="texto" value="${userMap.value}" />
		    	<c:set var="texto_split" value="${fn:split(texto,',')}" ></c:set>
		    	
		    	<c:choose> 
  						<c:when test="${texto_split[0] == 0}" > 
  							<font color="red" title="${texto_split[1]}">${user.usuLogin}</font> 
  						</c:when> 
  						<c:otherwise> 
  							<font title="${texto_split[1]}">${user.usuLogin}</font> 
  						</c:otherwise> 
					</c:choose> 
		    	
		    	<font></font>
		    	
		    	
		    	
		    	
		    	
		    	<br>
		    	
		 </c:forEach>
		    
	
		
</div>	
	
<%@ include file="include_foot.jsp" %>
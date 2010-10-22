<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<script type="text/javascript">

//alert("<c:out value="${pstgradList}"/>");

</script>

<!--

 <c:forEach var="p" items="${pstgradList}">
 	<c:set var="pList" value="${p.pstgradDesc}" />
      <c:out value="${pList}"/>
		<c:if test="${p.idPstGrad==usuario.pstGrad.idPstGrad}">selected</c:if>
      <br>
 </c:forEach>

-->


<div style="vertical-align: middle; line-height:30px; text-align: left; float: left; width: 100%; height: 30px; border-bottom: 1px solid gray;">
		    		<input type="checkbox" class="chkbox" value="${dwr.id}" id="chk${i}"
						onClick="javascript:seleciona('${i}');" />
		    		
		    		<c:set var="texto" value="${dwr.texto}" />
		    		
					<c:set var="texto_split" value="${fn:split(texto,';')}" ></c:set>
								
					<c:choose> 
  						<c:when test="${texto_split[1] == 0}" > 
  							<img src="imagens/outras/cartaFec.gif" style="vertical-align: middle; line-height:30px;" /> 
    						<c:out value="<b>${texto_split[0]}</b>" escapeXml="false"></c:out> 
  						</c:when> 
  						<c:otherwise> 
  							<img src="imagens/outras/cartaAbr.gif" style="vertical-align: middle; line-height:30px;"/> 
    						${texto_split[0]} 
  						</c:otherwise> 
					</c:choose> 
		    		
		    		<font style="background-color: aqua; padding: 5px 5px 5px 5px "></font>
		    		
					
		    		
		    		
		    		
		    	</div>


<c:forEach var="conta" items="${usuario.contas}">
			 
			  <c:forEach var="docs" items="${conta.carteira.documentos}">
		      
		        <c:out value="${conta.carteira.cartAbr}" /> -
		      	<c:out value="${docs.documentoDetalhes.assunto}" /> <br>
		      
		      </c:forEach>
		      
		    </c:forEach>
		    
		    <br><br>
		    
		    <c:forEach var="d" items="${documentos}">
		    	<c:set var="ass" value="${d.documentoDetalhes.assunto}" />
		    	<c:out value="${d.idDocumento}" />
		    	<c:out value="${ass}" /><br>
		    
		    </c:forEach>
		    
		    <br>DocDWR<br>


<sec:authorize ifAnyGranted="ROLE_ADMIN">
				<a href="/admin/" class="menu_titulo">Admin</a> |	
			</sec:authorize>
			
			<sec:authorize ifAnyGranted="ROLE_PROTOCOLO">
				<a href="/admin/" class="menu_titulo">Admin</a> |	
			</sec:authorize>
			
			<!--<a href="passar_conta.jsp" class="menu_titulo">Passar Conta</a> |
			
			<a href="dados_cadastro.jsp?modo=ver" class="menu_titulo">Dados Cadastrais</a> |
			<a href="configuracao.jsp" class="menu_titulo">Configurações</a> |  
			<a href="logout.jsp" class="menu_titulo">Sair</a>
			
			
			-->


<form:form commandName="usuario" action="editProduct.html">
    <table>
        <tr>
            <td>Usu name:</td>
            <td><form:input path="usuLogin" /></td>
        </tr>
         <tr>
            <td>PstGrad type:</td>
            <td><form:select path="pstGrad">
            <form:option value="-" label="--Please Select"/>
            <form:options items="${pstgradList}" itemValue="idPstGrad" itemLabel="pstgradDesc" />
            
            
           
        
            
            <!--<form:options items="${pstgradList}"/>
        --></form:select></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Save Changes" />
            </td>
        </tr>
    </table>
</form:form>



</body>
</html>
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


<c:forEach var="a" items="${anotacoes}">
 	
    ${a.anotacao} - ${a.dataHoraAnotacao} - ${a.usuario.usuLogin}  
      <br>
 </c:forEach>


	${documento.assunto}<br>
		
		
		<c:forEach var="doc_cart" items="${allDocuments}">
		    	
		    	<c:set var="status" value="${doc_cart.status}" />
		    	
		    	<c:set var="s" value="${'0'}" />
		    	
		    	${status}<br><br>
		    	
		    	
		    	
		    	
		    	<c:forEach var="conta" items="${doc_cart.carteira.contas}">
		    					    			
		    		<c:choose> 
  						<c:when test="${doc_cart.status == '0'}" > 
  							<font color="red" title="${conta.carteira.idCarteira}">${conta.usuario.usuLogin}</font> 
  						</c:when> 
  						<c:otherwise> 
  							<font title="${conta.carteira.idCarteira}">${conta.usuario.usuLogin}</font> 
  						</c:otherwise> 
					</c:choose>
		    			
		    				
		    	 </c:forEach>
		    	 </c:forEach>
		    	 
		    	 
		    	 
		    	 
		    	 
		    	 
		    	 
		    	 
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
		    	
	<input type="date" min="-20" max="2013-03-10" /><br>
		
		<button id="blind">Blind Toggle</button>
<div id="box">Blind me up. Blind me up. Blind me up.</div>
		
		<p>Date: <input type="text" id="datepicker"></p>
		<a name="teste" id="teste" class="menu2" style="margin-left: 5px;" href="#wgrupos">CLICK</a>
		
<div id="placeholder">Hover over me to show the menu here</div>

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



<table border="1" width="539">
      <tr>
        <td colspan="2" width="529" bgcolor="#0000FF">
          <b>
            <font color="#FFFFFF" size="4">HTTP
            Request(pageContext.request.)</font>
          </b>
        </td>
      </tr>

      <tr>
        <td width="210">Access Method</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.method}" />
        </td>
      </tr>

      <tr>
        <td width="210">Authentication Type</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.authType}" />
        </td>
      </tr>

      <tr>
        <td width="210">Context Path</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.contextPath}" />
        </td>
      </tr>

      <tr>
        <td width="210">Path Information</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.pathInfo}" />
        </td>
      </tr>

      <tr>
        <td width="210">Path Translated</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.pathTranslated}" />
        </td>
      </tr>

      <tr>
        <td width="210">Query String</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.queryString}" />
        </td>
      </tr>

      <tr>
        <td width="210">Request URI</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.requestURI}" />
        </td>
      </tr>
    </table>

    <table border="1" width="539">
      <tr>
        <td colspan="2" width="529" bgcolor="#0000FF">
          <b>
            <font color="#FFFFFF" size="4">HTTP
            Session(pageContext.session.)</font>
          </b>
        </td>
      </tr>

      <tr>
        <td width="210">Creation Time</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.session.creationTime}" />
        </td>
      </tr>

      <tr>
        <td width="210">Session ID</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.session.id}" />
        </td>
      </tr>

      <tr>
        <td width="210">Last Accessed Time</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.session.lastAccessedTime}" />
        </td>
      </tr>

      <tr>
        <td width="210">Max Inactive Interval</td>

        <td width="313">&#160; 
        <c:out
        value="${pageContext.session.maxInactiveInterval}" />

        seconds</td>
      </tr>

      <tr>
        <td width="210">You have been on-line for</td>

        <td width="313">&#160; 
        <c:out
        value="${(pageContext.session.lastAccessedTime-pageContext.session.creationTime)/1000}" />

        seconds</td>
      </tr>
    </table>
    
    <c:set var="page" value="${pageContext.request.requestURI}"></c:set>
  
    <c:set var="length_page" value="2"></c:set>
    
    ${fn:split(page,"/")[(fn:length(fn:split(page,"/"))-1)]}
	${fn:length(fn:split(page,"/"))}

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>

</head>

<body>

<div id="table" style="position: absolute; width: 1002px; top: 0; left: 0;">  

<div id="table" style="position: relative; width: 1002px; top: 0; left: 0;">

	<div id="line" style="width: 1002px;float: left; height: 43px;">
		<div style="float: left;"><img name="direto_r1_c1" src="imagens/direto_r1_c1.jpg" width="189" height="43" border="0" id="direto_r1_c1" usemap="#m_direto_r1_c1" alt="" /></div>
		<div style="float: left; line-height:43px; background-image: url('imagens/direto_r1_c2.jpg'); width: 813px; height:43px; text-align: center;" class="menu_titulo">
			
			<c:forEach var="mt" items="${menuTopo}">
 				<c:set value="${mt.name}" var="name"/>
 				<c:set value="${mt.value}" var="value"/>
 				
 				<c:url value="name" var="mtURL">
				  <c:param name="box" value="${box}" />
				</c:url>
				
				<c:if test="${name != 'Admin'}">| </c:if>
				
				<a href="<c:out value="${mtURL}" />" class="menu_titulo">${name}</a> 
			</c:forEach>
			
 			
 			</div>
   		<div style="float: left;"><img src="imagens/spacer.gif" width="1" height="43" border="0" alt="" /></div>
	</div>


	<div id="line" style="width: 1002px; float: left; height: 29px;">
   		<div style="float: left;"><img name="direto_r2_c1" src="imagens/head_complemento.jpg" width="117" height="29" border="0" id="direto_r2_c1" usemap="#m_direto_r2_c1" alt="" /></div>
   		<div style="float: left;"><img name="direto_r2_c2" src="imagens/direto_r2_c2.jpg" width="813" height="29" border="0" id="direto_r2_c2" alt="" /></div>
   		<div style="float: left; background-image: url('imagens/head_complemento.jpg');width: 72px;height:29px;"></div>
	</div>

</div>

    
<div id="table" style="position: relative; width: 1002px; text-align: center; top: 75px; left: 0px; height: 1000px; background-color: white;">
	<div id="line" style="width: 167px; float: left; position: absolute; text-align: left; background-color: #ffffff; top: -27px; left: 5px" class="menuLado">
		<div style="height: 30px; font-size: 14px; font-weight: bold;">
			<spring:message code="welcome"/>
		</div>
		
		<c:set var="divStyle" value="menuLado"/>
		
		<div style="height: 30px; vertical-align: middle;">
			${usuario.pstGrad.pstgradNome}
			${usuario.usuNGuerra}
		</div>
		
		<c:forEach var="p" items="${pastas}">
 			<c:set var="pasta" value="${p.nomePasta}" />
      		
	      	<div id="divMenuLateral" style="left: 5px; position: relative;">
				<a href="principal.html?box=<c:out value="${p.idPasta}"/>" class="<c:out value="${divStyle}"/><c:if test="${box == p.idPasta}">Sel</c:if>">
					<c:out value="${pasta}"/> ()
				</a>
			</div>
      	
 		</c:forEach>
		
				
		
		<br><br><br> 
 
		  <c:forEach var="conta" items="${usuario.contas}">
		      <c:out value="${conta.carteira.cartAbr}" />
		
		      <br />
		    </c:forEach>
		    
		    <br>
		    Conta Atual: <c:out value="${contaAtual}"></c:out>
		    
		 
		  
		   <br>${numUsers} user(s) are logged in!
	</div>
	
	<div id="line" style="width: 1px; position: absolute; margin: 0; background-color: gray; left: 177px; height: 1000px;" class="menuLado">
		
	</div>
	
	
	<div id="line" style="width: 822px; position: absolute; margin: 0 0 0 2px; text-align: center; left: 178px">
		
		<div style="width:100%; text-align:left; background-color: #1E90FF; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu1">
	
			<a href="escrever.jsp" style="margin-left: 5px;" class="menu1">Novo</a> | 
			<a href="javascript:Arquivar(2);" class="menu1">Arquivar</a> | 
			<a href="javascript:Arquivar(3);" class="menu1" onClick="">Pender</a> |
			<a href="pesquisa.jsp" class="menu1">Pesquisar</a> |
			
			<c:if test="${box == 4 || box == 5}">
				<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
			</c:if>		
		
			<c:if test="${box == 1}">
				<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
			</c:if>	
			
			<c:if test="${box == 2}">
				<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
			</c:if>	
			
			<a href="javascript: history.go(-1);" class="menu1">Voltar</a>			
		</div>
		
		<c:url value="principal.jsp" var="mostrarURL">
		  <c:param name="box"   value="${box}" />
		  <c:param name="filtro" value="" />
		</c:url>
		
		
		<div style="width:100%; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
			<input name="checkbox" type="checkbox" id="sel_tudo"
				onClick="selTudo('15')" value="15" style="position: relative; top: 2px;" /> 
			
			Mostrar: 
			
			<a href="<c:out value="${mostrarURL}todas" />" style="margin-left: 5px;" class="menu2">Todas</a> |
			
			<a href="<c:out value="${mostrarURL}naolidas" />" style="margin-left: 5px;" class="menu2">Não lidas</a> |
			
			<a href="<c:out value="${mostrarURL}urgente" />" style="margin-left: 5px;" class="menu2">Urgentes</a> |
				
			<a href="<c:out value="${mostrarURL}encaminhar" />" style="margin-left: 5px;" class="menu2">Encaminhar selecionadas</a>  	
			
			<c:if test="${box == 2}">
				<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
			</c:if>
			
			<div style="position: relative; float: right">
				Ordernação:
				<select	name=slOrdenacao id="slOrdenacao" style="width: 100pt; font-style: normal; font-size: 12px; color: black;" onchange="ordenacao()">
					<option  style="background-color: #FFFFFF;" value=0>Prioridade</option>
					<option  style="background-color: #FFFFFF;" value=1>Data decrescente</option>
					<option  style="background-color: #FFFFFF;" value=2>Data crescente</option>
				</select>
			</div>
		
		</div>
		
		<div style="width:100%; text-align:left; float: left; position: static; width: 822px; vertical-align: middle;">
				
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
		    
		    <c:forEach var="dwr" items="${DocDWR}">
		    	<c:out value="${dwr.id}" />
		    	<c:out value="${dwr.texto}" /><br>
		    
		    </c:forEach>
		    
		   
		
		</div>
	
		
	</div>
	
	
</div>
    


	
<div id="table" style="position: relative; width: 1002px; top: 400px; left: 0; margin-top: 0px; text-align: center;">
	<font class="rodape">
		© 2010 - Direto - Gerenciador Eletrônico de Documentos<br>
					Ver. 3.0
	</font>
</div>


</div>
  </body>
</html>
    
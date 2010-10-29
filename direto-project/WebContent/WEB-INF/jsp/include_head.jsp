<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="title"/></title>
	
<!-- Inicio Folha de Estilos -->
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<!-- Fim Folha de Estilos -->
    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/loginValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/documentosJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/direto.js"></script>
</head>


<body onload="">

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

    
<div id="table" style="position: relative; width: 1002px; text-align: center; top: 75px; left: 0px; height: 400px; background-color: white;">
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
 			
 			<c:url value="principal.html" var="pastaURL">
				  <c:param name="box" value="${p.idPasta}" />
				  <c:param name="pr" value="0" />
				  <c:param name="filtro" value="todas" />
				  <c:param name="idCarteira" value="${contaAtual}" />
			</c:url>
      		
	      	<div id="divMenuLateral" style="left: 5px; position: relative;">
				<a href="<c:out value="${pastaURL}" />" class="<c:out value="${divStyle}"/><c:if test="${box == p.idPasta}">Sel</c:if>">
					<c:out value="${pasta}"/>
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
	
	<div id="line" style="width: 1px; position: absolute; margin: 0; background-color: gray; left: 177px; height: 700px;" class="menuLado">
		
	</div>
	
	<div id="line" style="width: 822px; position: absolute; margin: 0 0 0 2px; text-align: center; left: 178px">
		
		<div style="width:100%; text-align:left; background-color: #1E90FF; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu1">
	
			<a href="escrever.jsp" style="margin-left: 5px;" class="menu1">Novo</a> | 
			 
			<c:if test="${box == 1}">
				<a href="javascript:Arquivar(2);" class="menu1">Arquivar</a> |
				<a href="javascript:Arquivar(3);" class="menu1" onClick="">Pender</a> |
			</c:if>
			
			<c:if test="${box == 2}">
				<a href="javascript:Arquivar(3);" class="menu1" onClick="">Pender</a> |
				<a href="javascript:Arquivar(3);" class="menu1" onClick="">Retornar para caixa de entrada </a> |
			</c:if>
			
			<c:if test="${box == 3}">
				
			</c:if>
			
			<c:if test="${box == 4}">
				<a href="javascript:Arquivar(3);" class="menu1" onClick="">Arquivar</a> |
				<a href="javascript:Arquivar(3);" class="menu1" onClick="">Retornar para caixa de entrada </a> |
			</c:if>		
		
			<c:if test="${box == 5}">
				<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
			</c:if>		
			
			<a href="pesquisa.jsp" class="menu1">Pesquisar</a> |
			<a href="javascript: history.go(-1);" class="menu1">Voltar</a>			
		</div>
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
		<div style="float: left; line-height:43px; background-image: url('imagens/direto_r1_c2.jpg'); width: 813px; height:43px; text-align: center;" class="menu_titulo"><!--
		 	
		 	 <c:forEach var="mt" items="${menuTopo}">
      			<c:url var="url" value="/catalog" >
      				<c:param name="Add" value="${mt.menuList}" />
				</c:url>
				 
				
    		</c:forEach>
    		 <c:out value="${command.principal}" />
		 	
		 	
		 	
		 	
		 	--><a href="passar_conta.jsp" class="menu_titulo">Passar Conta</a> |
			<a href="dados_cadastro.jsp?modo=ver" class="menu_titulo">Dados Cadastrais</a> |
			<a href="configuracao.jsp" class="menu_titulo">Configurações</a> |  
			<a href="logout.jsp" class="menu_titulo">Sair</a>
			 
   		</div>
   		<div style="float: left;"><img src="imagens/spacer.gif" width="1" height="43" border="0" alt="" /></div>
	</div>


	<div id="line" style="width: 1002px; float: left; height: 29px;">
   		<div style="float: left;"><img name="direto_r2_c1" src="imagens/head_complemento.jpg" width="117" height="29" border="0" id="direto_r2_c1" usemap="#m_direto_r2_c1" alt="" /></div>
   		<div style="float: left;"><img name="direto_r2_c2" src="imagens/direto_r2_c2.jpg" width="813" height="29" border="0" id="direto_r2_c2" alt="" /></div>
   		<div style="float: left; background-image: url('imagens/head_complemento.jpg');width: 72px;height:29px;"></div>
	</div>

</div>

    
<div id="table" style="position: relative; width: 189px; text-align: center; left: 100px; top: 300px; height: 1000px;">
${usuario.usuLogin}<br>


 ${usuario.pstGrad.pstgradDesc}<br> 
 
  <c:forEach var="conta" items="${usuario.contas}">
      <c:out value="${conta.carteira.cartAbr}" />

      <br />
    </c:forEach>
    
 
  
       
 
    
    

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
    
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>

<style type="text/css" media="all">
/* simple css-based tooltip */
.tooltip {
	background-color:#000;
	border:1px solid #fff;
	padding:10px 15px;
	width:200px;
	display:none;
	color:#fff;
	text-align:left;
	font-size:12px;
	position: absolute;
	z-index: 9999;

	/* outline radius for mozilla/firefox only */
	-moz-box-shadow:0 0 10px #000;
	-webkit-box-shadow:0 0 10px #000;
}

/*@import "css/dateinput.css";*/
</style>



		
		<c:url value="principal.html" var="mostrarURL">
		  <c:param name="box"   value="${box}" />
		  <c:param name="pr" value="${(page*limiteByPage) - limiteByPage}" />
		  <c:param name="filtro" value="" />
		</c:url>
		
		
		<div style="width:100%; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
			<input name="checkbox" type="checkbox" id="sel_tudo"
				onClick="js.direto.sel_chkbox_doc('16')" value="16" style="position: relative; top: 2px;" /> 
			
			Mostrar: 
			
			<a href="<c:out value="${mostrarURL}todas" />" style="margin-left: 5px;" class="menu2">Todas</a> |
			
			<a href="<c:out value="${mostrarURL}naolidas" />" style="margin-left: 5px;" class="menu2">Não lidas</a> |
			
			<a href="<c:out value="${mostrarURL}urgentes" />" style="margin-left: 5px;" class="menu2">Urgentes</a> |
				
			<a href="#grupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar selecionadas</a>  |	
			
			<a href="javascript: mostrarGrupos();" style="margin-left: 5px;" class="menu2">Acompanhar</a>
			
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
		
		
		<!-- INICIO CORPO DE LISTA DOCUMENTOS -->
		<div style="width:100%; text-align:center; float: left; position: static; width: 822px; vertical-align: middle;">
			
			<c:set var="i" value="1"></c:set>
			<c:forEach var="dwr" items="${DocDWR}">
		    	<c:out value="${dwr.texto}" escapeXml="false"></c:out>
		    	<c:set var="i" value="${i+1}"></c:set>
		    </c:forEach>
		    
			<c:if test="${totalRegs > 0}">
			    <div style="text-align: center; float: left; width: 100%; height: 40px; vertical-align: middle; line-height:40px;">
			    	Total de registros ${totalRegs}
				</div>
			
				<div style="text-align: center; float: left; width: 100%; height: 30px;">			    
				    <div style="float: left">
				    	Página <c:out value="${page} de ${pages}" />
				    </div>
				    
				    <div style="float: right">
				    	
					    <c:set var="iCountPages" value="1" />
					    <c:set var="fCountPages" value="${pages}" />
					    
					    <c:if test="${pages > 3}">
					    	<c:set var="fCountPages" value="3" />
					    	<c:if test="${page % 3 == 0}">
					    		<c:set var="iCountPages" value="${page+1}" />
					    		<c:set var="fCountPages" value="${page+3}" />
					    		<c:if test="${fCountPages > pages}">
					    			<c:set var="iCountPages" value="${page-1}" />
					    			<c:set var="fCountPages" value="${pages}" />
					    		</c:if>
					    	</c:if>
					    	
					    </c:if>
					    
					    <c:url value="principal.html" var="previousPageURL">
							  <c:param name="box" value="${box}" />
							  <c:param name="pr" value="${previousPage - limiteByPage}" />
							  <c:param name="filtro" value="${filtro}" />
							  <c:param name="idCarteira" value="${contaAtual}" />
						</c:url>
					    
					    <c:if test="${previousPage != 0}">
					    	<a href="<c:out value="${previousPageURL}" />" class="pages">&lt;&lt;</a>
					    </c:if>
					    
					    <c:forEach var="i" begin="${iCountPages}" end="${fCountPages}">
			   				<c:url value="principal.html" var="pagesURL">
							  <c:param name="box" value="${box}" />
							  <c:param name="pr" value="${(i*limiteByPage) - limiteByPage}" />
							  <c:param name="filtro" value="${filtro}" />
							  <c:param name="idCarteira" value="${contaAtual}" />
							</c:url>
							
							<a href="<c:out value="${pagesURL}" />" class="pages"> ${i}</a> 
						</c:forEach>
						
					    <c:url value="principal.html" var="nextPageURL">
							  <c:param name="box" value="${box}" />
							  <c:param name="pr" value="${nextPage}" />
							  <c:param name="filtro" value="${filtro}" />
							  <c:param name="idCarteira" value="${contaAtual}" />
						</c:url>
					    
					    <c:if test="${pages > 1 && page != pages}">
					    	<a href="<c:out value="${nextPageURL}" />" class="pages">&gt;&gt;</a>
					    </c:if>
		  	 		</div>
				</div>
				 
		   </c:if>
		   
		   <c:if test="${totalRegs == 0}">
		   	Nenhum registro encontrado
		   </c:if>
		
		</div>
		<!-- FIM CORPO DE LISTA DOCUMENTOS -->
		
	</div>
	
</div>
    


	
<div id="table" style="position: relative; width: 1002px; top: 400px; left: 167px; margin-top: 0px; text-align: center;">
		
		
<input type="date" />


	
	<font class="rodape" style="left: 167px;">
			© 2010 - Direto - Gerenciador Eletrônico de Documentos<br>
						Ver. 3.0
	</font>
	
	
	
	
</div>


</div>
  </body>
</html>
    
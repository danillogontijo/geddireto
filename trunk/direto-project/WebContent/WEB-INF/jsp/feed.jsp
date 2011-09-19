<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ include file="include_head.jsp" %>
			
			<!-- INICIO SUBMENU 2 DO CORPO PRINCIPAL -->
			<div style="background-color: #B8C9DD;" class="ui_main_body ui_subMenu _width_main_body _font_normal">
				<input name="checkbox" type="checkbox" id="sel_tudo"
				onClick="js.direto.sel_chkbox_doc('16')" value="16" style="position: relative; top: 2px;" /> 
			
				Mostrar: 
				
				<a href="<c:out value="${mostrarURL}todas" />" style="margin-left: 5px;" class="menu2">Todas</a> |
				
				<c:if test="${box == 1}">
					<a href="<c:out value="${mostrarURL}naolidas" />" style="margin-left: 5px;" class="menu2">Não lidas</a> |
				</c:if>
				
				<a href="<c:out value="${mostrarURL}urgentes" />" style="margin-left: 5px;" class="menu2">Urgentes</a> |
					
				<a href="#wgrupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar selecionadas</a>  |	
				
				<a href="#acompanhar" style="margin-left: 5px;" class="menu2" name="acompanhar">Acompanhar</a>
				
				<div style="position: relative; float: right">
					Ordernação:
					<select	name=slOrdenacao id="slOrdenacao" style="width: 100pt; font-style: normal; font-size: 12px; color: black; margin-right: 3px;" onchange="ordenacao()">
						<option value=0 <c:if test="${ordenacao == 0}">selected</c:if>>Prioridade</option>
						<option value=1 <c:if test="${ordenacao == 1}">selected</c:if>>Data decrescente</option>
						<option value=2 <c:if test="${ordenacao == 2}">selected</c:if>>Data crescente</option>
					</select>
				</div>
			
			</div>
			
			<!-- INICIO CORPO PRINCIPAL -->
			<div class="ui_main_body _width_main_body" style="background-color: #fff; min-height: 640px;">
				
				<!-- INICIO CORPO DE LISTA DOCUMENTOS -->
				<div style="text-align:center; float: left; position: static; vertical-align: middle;" class="_width_main_body">
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
							    <c:set var="sizeViewPages" value="20" />
							    
							    <c:if test="${pages > sizeViewPages}">
							    	
							    	<c:set var="fCountPages" value="20" />
							    	
							    	<c:if test="${page > (sizeViewPages-1)}">
							    	 	<c:set var="temp" value="${(page%10)}" />
							    	 	<c:set var="temp" value="${(page-temp)/10}" />
							    	 	
							    	 	<c:forEach var="c" begin="0" end="${temp}">
							    			<c:set var="fCountPages" value="${(fCountPages+10)}" />					    		
							    		</c:forEach>
							    		<c:set var="fCountPages" value="${(fCountPages-sizeViewPages)}" />
							    		<c:set var="iCountPages" value="${fCountPages-sizeViewPages}" />
							    							    	
								    	<c:if test="${page % sizeViewPages == 0}">
								    		<c:set var="iCountPages" value="${page}" />
								    		<c:set var="fCountPages" value="${page+sizeViewPages}" />
								    		<c:if test="${fCountPages > pages}">
								    			<c:set var="iCountPages" value="${page-1}" />
								    			<c:set var="fCountPages" value="${pages}" />
								    		</c:if>
								    	</c:if>
								    	
								    	<c:if test="${page == pages}">
								    		<c:set var="iCountPages" value="${page-(sizeViewPages-1)}" />
								    		<c:set var="fCountPages" value="${pages}" />
								    	</c:if>
							    		
							    	</c:if>
							    </c:if>
							    
							    <c:url value="principal.html" var="previousPageURL">
									  <c:param name="box" value="${box}" />
									  <c:param name="pr" value="${previousPage - limiteByPage}" />
									  <c:param name="filtro" value="${filtro}" />
									  <c:param name="idCarteira" value="${contaAtual}" />
									  <c:param name="ordenacao" value="${ordenacao}" />
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
									  <c:param name="ordenacao" value="${ordenacao}" />
									</c:url>
									
									<a href="<c:out value="${pagesURL}" />" class="pages" <c:if test="${page==i}">style="font-weight: bold"</c:if>> ${i}</a> 
								</c:forEach>
								
							    <c:url value="principal.html" var="nextPageURL">
									  <c:param name="box" value="${box}" />
									  <c:param name="pr" value="${nextPage}" />
									  <c:param name="filtro" value="${filtro}" />
									  <c:param name="idCarteira" value="${contaAtual}" />
									  <c:param name="ordenacao" value="${ordenacao}" />
								</c:url>
							    
							    <c:if test="${pages > 1 && page != pages}">
							    	<a href="<c:out value="${nextPageURL}" />" class="pages">&gt;&gt;</a>
							    </c:if>
				  	 		</div>
						</div>
						 
				   </c:if>
				   
				   <c:if test="${totalRegs == 0}">
				   	<p>Nenhum registro encontrado</p>
				   </c:if>
				
				</div>
				<!-- FIM CORPO DE LISTA DOCUMENTOS -->
				
			</div>
		</div>
	</div>
	
	<%@ include file="include_foot.jsp" %>
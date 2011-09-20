<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ include file="include_head.jsp" %>
			
			<!-- INICIO SUBMENU 2 DO CORPO PRINCIPAL -->
			<div style="background-color: #B8C9DD;" class="ui_main_body ui_subMenu _width_main_body _font_normal">
				
				<div style="position: relative; float: right">
					Filtro:
					<select	name=slOrdenacao id="slOrdenacao" style="width: 100pt; font-style: normal; font-size: 12px; color: black; margin-right: 3px;" onchange="ordenacao()">
						<option value=0 <c:if test="${ordenacao == 0}">selected</c:if>>Por Usuário</option>
						<option value=1 <c:if test="${ordenacao == 1}">selected</c:if>>Por Carteira</option>
						<option value=2 <c:if test="${ordenacao == 2}">selected</c:if>>Por Usuário e Carteira</option>
					</select>
				</div>
			
			</div>
			
			<!-- INICIO CORPO PRINCIPAL -->
			<div class="ui_main_body _width_main_body" style="background-color: #fff; min-height: 640px;">
				
				<!-- INICIO CORPO DE LISTA FEED -->
				<div style="text-align:center; float: left; position: static; vertical-align: middle;" class="_width_main_body">
					
					<c:forEach var="feedsByDocs" items="${feeds}" >
					
					<div style="width: 100%; border-bottom: 1px solid gray; float: left; margin-top: 5px; padding-bottom: 5px;">
						<div style="width: 162px; float: left;">
							
							<c:choose> 
			  					<c:when test="${feedsByDocs.key.prioridade == '0'}" > 
			  						<div style="background-color: #fff; height: 60px; line-height: 60px; width: 60px; margin: 0 auto;">N 
			  					</c:when>
			  					<c:when test="${feedsByDocs.key.prioridade == '1'}" > 
			  						<div style="background-color: yellow; height: 60px; line-height: 60px; width: 60px; margin: 0 auto;">U 
			  					</c:when> 
			  					<c:otherwise> 
			  						<div style="background-color: red; height: 60px; line-height: 60px; width: 60px; margin: 0 auto;">UU
			  					</c:otherwise> 
							</c:choose>
					    	
							</div>
						</div>
						
						<div style="width: 654px; float: left; margin: 0 auto;">
							<div style="width: 100%; float: left; padding-bottom: 10px;">
								<p style="margin: 0 0 0 0;"><b>Você foi mencionado em:</b><br> [${feedsByDocs.key.tipoDocumento.tipoDocumentoAbr}] ${feedsByDocs.key.assunto}</p> 
								<p><a href="view.html?id=${feedsByDocs.key.idDocumentoDetalhes}" style="color: #708090;">Visualizar documento</a></p>
							</div>
							
							<c:forEach var="feed" items="${feedsByDocs.value}">
							 <div style="width: 600px; background-color: #B8C9DD; float: left; margin: 0 27px 3px 27px; padding: 10px 0 10px 0; color: #000;">${feed.acao}</div>
							</c:forEach>
							
						</div>
					</div>
					
					</c:forEach>
					
				</div>
				<!-- FIM CORPO DE LISTA FEED -->
				
			</div>
			<!-- FIM CORPO PRINCIPAL -->
		</div>
	</div>
	
	<%@ include file="include_foot.jsp" %>
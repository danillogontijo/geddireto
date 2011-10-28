<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/feedJS.js"></script>

<script type="text/javascript">
function filtro(){
	var listaFilter = $("slFilter");
	for (var i=0; i < listaFilter.options.length; i++){
		if (listaFilter.options[i].selected){
			window.location.href = "feed.html?filter="+listaFilter.options[i].value;
		}
	}
}

function removeFeed(e,idDocumento){
	e.preventDefault();

	$j("#feed_"+idDocumento).fadeOut("slow");
	
	feedJS.deleteAllFeedsFromDocument(idDocumento,{
		callback:function(ok) {
			if(ok)
				$j("#feed_"+idDocumento).fadeOut("slow");
		}
  	});

	
	
}
</script>
			
			<!-- INICIO SUBMENU 2 DO CORPO PRINCIPAL -->
			<div style="background-color: #B8C9DD;" class="ui_main_body ui_subMenu _width_main_body _font_normal">
				
				<div style="position: relative; float: right">
					Filtro:
					<select	name=slFilter id="slFilter" style="width: 170px; font-style: normal; font-size: 12px; color: black; margin-right: 3px;" onchange="filtro()">
						<option value=1 <c:if test="${filter == 1}">selected</c:if>>Por Usuário</option>
						<option value=2 <c:if test="${filter == 2}">selected</c:if>>Por Carteira</option>
						<option value=3 <c:if test="${filter == 3}">selected</c:if>>Por Usuário e Carteira</option>
					</select>
				</div>
			
			</div>
			
			<!-- INICIO CORPO PRINCIPAL -->
			<div class="ui_main_body _width_main_body" style="background-color: #fff; min-height: 640px;">
				
				<!-- INICIO CORPO DE LISTA FEED -->
				<div style="text-align:center; float: left; position: static; vertical-align: middle;" class="_width_main_body">
					
					<c:if test="${fn:length(feeds) == 0}">
						<p>Alimentador vazio</p>
					</c:if>
					
					<c:forEach var="feedsByDocs" items="${feeds}" >
					 
					<div style="width: 100%; border-bottom: 1px solid #ccc; float: left; margin-top: 5px; padding-bottom: 5px;" id="feed_${feedsByDocs.key.idDocumentoDetalhes}">
						<c:if test="${filter == 1 || filter == ''}">
							<div class="ui_feed_remove"><a href="#" title="Remover citações no documento" onclick="javascript:removeFeed(event,${feedsByDocs.key.idDocumentoDetalhes})" style="_font_black_bold">X</a></div>
						</c:if>
						<div style="width: 132px; float: left; margin-top: 10px;">
							
							<c:choose> 
			  					<c:when test="${feedsByDocs.key.prioridade == '0'}" > 
			  						<div class="border_radius ui_border_shadow" style="background-color: #fff; height: 60px; line-height: 60px; width: 60px; margin: 0 auto;">N 
			  					</c:when>
			  					<c:when test="${feedsByDocs.key.prioridade == '1'}" > 
			  						<div class="border_radius ui_border_shadow" style="background-color: yellow; height: 60px; line-height: 60px; width: 60px; margin: 0 auto;">U 
			  					</c:when> 
			  					<c:otherwise> 
			  						<div class="border_radius ui_border_shadow" style="background-color: red; height: 60px; line-height: 60px; width: 60px; margin: 0 auto;">UU
			  					</c:otherwise> 
							</c:choose>
					    	
							</div>
						</div>
						
						<div style="width: 684px; float: left; margin: 0 auto;">
						
							<div style="width: 100%; float: left; padding-bottom: 10px;">
								<p style="margin: 0;" class="_font_feed_titulo"><b>Você foi citado em:</b><br> [${feedsByDocs.key.tipoDocumento.tipoDocumentoAbr}] ${feedsByDocs.key.assunto}</p> 
								<p class="_font_feed_titulo"><a href="view.html?id=${feedsByDocs.key.idDocumentoDetalhes}" style="color: #1E90FF;">Visualizar documento</a></p>
							</div>
							
							<c:forEach var="feed" items="${feedsByDocs.value}">
							 <div class="ui_feed _font_feed">
							 	<div style="float: left; width: 515px; text-align: left; margin-left: 5px;"><b>[${feed.usuarioRem.pstGrad.pstgradNome} ${feed.usuarioRem.usuNGuerra} - ${feed.carteiraRem.cartAbr}]</b> ${feed.acao}</div>
							 	<div style="float: left; width: 110px; color: #708090;"><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${feed.dataHora}" /></div>
							 </div>
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
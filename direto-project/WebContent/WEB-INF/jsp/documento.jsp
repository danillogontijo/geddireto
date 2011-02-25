<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>

<script type="text/javascript">

/*Eventos MouseOver de atualiza��o hist�rico,anota�oes e despachos */
$j(function(){

	$j('#despachos').mouseenter(function(e) {
	
		var tipo = $j(this).attr('id');
		//setTimeout("show_updates(${idDocumento},tipo)",100);
	
	 });
	
	$j('#anotacoes').mouseenter(function(e) {
	
		var tipo = $j(this).attr('id');
		//show_updates(${idDocumento},tipo);
	
	 });
	
	$j('#despachos').mouseenter(function(e) {
	
		var tipo = $j(this).attr('id');
		//show_updates(${idDocumento},tipo);
	
	 });
	
	$j('#historico').mouseenter(function(e) {
	
		var tipo = $j(this).attr('id');
		//show_updates(${idDocumento},tipo);
	
	 });


	/*Efeitos hist�rico,anota�oes e despachos */		
	$j('a[name=link_historico]').toggle(function() {
		$j(this).parent().next('#historico').slideDown(100);
					
	}, function() {
		$j(this).parent().next('#historico').slideUp(100);
	});


	$j('a[name=link_anotacoes]').toggle(function() {
		$j(this).parent().next('#anotacoes').fadeOut('slow');
					
	}, function() {
		$j(this).parent().next('#anotacoes').fadeIn(1000);
	});

	$j('a[name=link_despachos]').click(function(e) {
		e.preventDefault();
		//alert('');
		$j('#despachos').toggle("slow");
	});


	/*Evento do bot�o de confirma��o da edi��o do documento*/
	$j('input[type=button]').click(function(e) {
		var bt_name = $j(this).attr('name');
		var nome_anexo = $j('#hn_nome_anexo').val();
		if(bt_name == 'bt_editar_nao'){
			confirma_edicao(0,nome_anexo);
			alert("fecha esse trem");
			js.direto.close_mask();
		}	
	});

	/*Fun��o Update hist�rico,anota�oes e despachos */
	function show_updates(id,type){
		var singular = type;

		if (type == "anotacoes") {
			singular = "anotacao";
		} else if (type == "despachos") {
			singular = "despacho";
		} else singular = "unknow";	

		var txt_date = $j('#'+type+' #data_'+singular).last().text();
		var last_date = js.direto.parseDate(txt_date);
		var retorno = 0;
			$j.getJSON(type+".html?id="+id, function(data) {
				
		    $j.each(data.dados, function(i,d){
				//alert(i);
		        //$j("#anotacoes div:last").after('div');
		        var date_json_return = js.direto.parseDate(d.dataHora);
				retorno = js.direto.compareDate(date_json_return,last_date);
		        if( retorno == 1){
					var txt = "";
					txt = txt + "<strong>["+d.carteira+"] ["+d.usuNGuerra+"]</strong> - ";
					txt = txt + d.acao;
					txt = txt + " - <span id='data_despacho'>"+d.dataHora+"</span>";
			        
			        $j('#'+type+' div:last').after("<div id='div_despachos'></div>");
			        //$j('#'+type+' div').last().hide();
			        $j('#'+type+' div').last().html(txt);
			        $j('#'+type+' div').last().addClass('celula '+singular);
			        //$j('#'+type+' div').last().remove();
			       // $j('#'+type+' div').last().fadeIn("slow");
		       }
				//$j('<div>teste</div>').insertAfter($('#div_anotacoes div:last'));
		      });
		    });
	}
		


});




/*Fun��o de confirma��o da edi��o do documento*/
function confirma_edicao(resposta,nome_anexo){
	if (resposta == 0) {
		alert(nome_anexo+" - Apaga arquivo temp servidor e cancela opera��o");
	} else {
		alert(nome_anexo+" - Substitui arquivo servidor, apaga arquivo temp e armazena hist�rico.");
	}
}

</script>

<div style="width: 822px; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
				
			<a href="#grupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Responder</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Despachar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anotar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anexar</a> |
			
		
</div>



<div id="corpo_documento" style="float: left; position: static; width: 822px; vertical-align: middle; display: none;">
	
	<div style="float: left; text-align: left; margin-top: 10px; margin-left: 7px">
	
		<font size="+2">[${documento.tipoDocumento}] ${documento.assunto}</font>		
			
		<br><br>
		
		<font color="#666666">Nr. Prototocolo: </font><b>${documento.nrProtocolo}</b><br>
		<font color="#666666">Nr. Documento: </font><b>${documento.nrDocumento}</b><br>	
		<font color="#666666">Documento elaborado por: </font><b>${usuarioElaborador.pstGrad.pstgradNome}
		${usuarioElaborador.usuNGuerra}</b><br>
		
		
		<font color="#666666">Documento enviado para... (negrito) e acessado por...(azul): </font>
		
		 <c:forEach var="doc_cart" items="${allDocuments}">
			   <c:forEach var="conta" items="${doc_cart.carteira.contas}">
			    					    			
			    	<c:choose> 
	  					<c:when test="${doc_cart.status == '0'}" > 
	  						<font color="red" style="font-size: 10px;" title="${conta.carteira.cartAbr}"><b>${conta.usuario.pstGrad.pstgradNome} ${conta.usuario.usuNGuerra};</b></font> 
	  					</c:when> 
	  					<c:otherwise> 
	  						<font title="${conta.carteira.cartAbr}" style="font-size: 10px;"><b>${conta.usuario.pstGrad.pstgradNome} ${conta.usuario.usuNGuerra};</b></font> 
	  					</c:otherwise> 
					</c:choose>
			    			
			    				
			    </c:forEach>
		 </c:forEach>
		<br>
		
		<font color="#666666">Remetente: </font><b>${documento.remetente}</b><br>	
		<font color="#666666">Destinatario: </font><b>${documento.destinatario}</b><br>
		<font color="#666666">Data de entrada no sistema: </font><b><fmt:formatDate pattern="EEEE, d MMMM yyyy HH:mm" value="${documento.dataEntSistema}" /></b><br>
		<font color="#666666">Refer�ncia: </font><b>${documento.referencia}</b><br>
	
		<font color="#666666">Documento: </font><b>${documento_principal}</b> 
		
   <c:if test="${documento_principal != 'Sem documento'}">			
		
		>>
		
		<c:choose>
			<c:when test="${documento.assinatura == 0}">
				<c:if test="${documento_principal.assinado == 0}">
				 <span id="s_editar"><a href="#weditar" id="${documento_principal.anexoCaminho}" name="modal" class="l_edicao_vis">Editar</a></span> |
				</c:if>
			</c:when>
			<c:otherwise>
				Documento assinado.
			</c:otherwise>
		</c:choose>
		
		<span id="s_visualizar"><a href="" class="l_edicao_vis">Visualizar</a></span>
		
		<br>
		<font color="#666666">SHA-1: </font><b>${sha1}</b><br>
		
	</c:if>
		
		<div id="line" style="margin-top: 10px; background-color: #B8C9DD; position: relative; width: 822px; height: 30px; text-align: center; line-height:30px;">
			<a href="" id="link_titulo">Anexos</a>
		</div>
		
		
		
		<c:forEach var="anexo" items="${anexos}">
			<div id="div_anexos" style="border-bottom: 1px solid gray; background-color: #FFFFCC; position: relative; width: 822px; height: 25px; text-align: center; line-height: 25px;">
				${anexo.anexoNome}
				(
				<c:choose>
					<c:when test="${documento.assinatura == 0}">
						<c:if test="${anexo.assinado == 0}">
							<a href="#editar" name="modal" id="${anexo.anexoCaminho}" class="l_edicao_vis">Editar</a> |
						</c:if>
					</c:when>
				</c:choose>
				<a href="" class="l_edicao_vis">Visualizar</a>
				)
			</div>
		</c:forEach>
	
		<div id="line" style="margin-top: 10px; background-color: #B8C9DD; position: relative; width: 822px; height: 30px; text-align: center; line-height:30px;">
			<a href="javascript:show_updates(${idDocumento},'despachos')" id="link_titulo" name="link_despachos">Despachos</a> [<a href="#wacao" name="modal">Despachar</a>]
		</div>
		<div style="position: relative" id="despachos">
			<c:forEach var="d" items="${despachos}">
				<div id="div_despachos" class="celula despacho">
					<strong>[${d.carteira.cartAbr }] [${d.usuario.pstGrad.pstgradNome} ${d.usuario.usuNGuerra}]</strong> - ${d.despacho} - 
					<span id="data_despacho"><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${d.dataHoraDespacho}" /></span> 
				</div>
			 </c:forEach>
		 </div>
		
		<div id="line" class="div_title_anotacoes" style="">
			<a href="javascript:show_updates(${idDocumento},'anotacoes')" id="link_titulo" name="link_anotacoes">Anota��es</a> [<a href="#wacao" name="modal">Anotar</a>]
		</div>
		<div style="position: relative" id="anotacoes">
			<c:forEach var="a" items="${anotacoes}">
				<div id="div_anotacoes" class="celula anotacao">
					<strong>[${a.carteira.cartAbr }] [${a.usuario.pstGrad.pstgradNome} ${a.usuario.usuNGuerra}]</strong> - ${a.anotacao} - 
					<span id="data_anotacao"><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${a.dataHoraAnotacao}" /></span> 
				</div>
			 </c:forEach>
		 </div>	
		 
		<div id="line" style="margin-top: 10px; background-color: #B8C9DD; position: relative; width: 822px; height: 30px; text-align: center; line-height:30px;">
			<a href="#" id="link_titulo" name="link_historico">Hist�rico</a>
		</div>
		<div style="position: relative" id="historico">
			<c:forEach var="h" items="${historico}">
				<div id="div_historico" class="celula historico">
					<strong>[${h.carteira.cartAbr }] [${h.usuario.pstGrad.pstgradNome} ${h.usuario.usuNGuerra}]</strong> - ${h.historico} - 
					<span id="data_historico"><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${h.dataHoraHistorico}" /></span> 
				</div>
			 </c:forEach>
		</div>
	
	</div>	
		
		
		
		    
	
		
</div>	
	
<%@ include file="include_foot.jsp" %>
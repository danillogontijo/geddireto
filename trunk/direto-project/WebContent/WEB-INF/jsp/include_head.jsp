<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ include file="include_taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="title"/></title>

<link rel="shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" type="image/ico" />
<link rel="icon" href="favicon.ico" type="image/ico" />


<!-- Inicio Folha de Estilos -->
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/modals.css" rel="stylesheet" type="text/css" />

<link href="css/custom-theme-jquery/jquery-ui-1.8.10.custom.css" rel="stylesheet" type="text/css" />
<link href="css/custom-theme-jquery/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<!-- Fim Folha de Estilos -->

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>
    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/gruposJS.js"></script>    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/loginValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/documentosJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/comentarioJS.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/chatJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/chatDireto.js" charset="utf-8""></script>

<script src="<%=request.getContextPath() %>/js/direto.js" charset="utf-8"" type="text/javascript"></script>

<script src="<%=request.getContextPath() %>/js/custom/jquery-1.4.4.min.js"></script>

 <script src="<%=request.getContextPath() %>/js/custom/jquery-ui-1.8.10.custom.min.js"></script> 

<script src="<%=request.getContextPath() %>/js/flowplayer/jquery.tools.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/external/validator.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/external/jquery.alerts.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/jquery.ui.datepicker-pt-BR.js"></script>

<style type="text/css">
		
			.tela_apresentacao {
				background: #fff url(imagens/direto_r4_c2.jpg) center no-repeat;
				/*background-color: #ff0000;*/
				background-position: center center;
				 z-index: 9999;
				 position: absolute;
				 width: 822px;
				 height: 700px;;
				 /*vertical-align: middle;*/
				 /*line-height: 700px;*/
				 top: 133px;
				 left: 180px;
				 display: none;
			}
			
			.tooltip {
				display:none;
				background-color: #696969;
				
				padding:5px;
				color:#fff;
				font-weight: bold;
				font-size: 10px;
				
				border: 2px solid #fff;
				
				-moz-box-shadow: 0 0 5px 5px #888;
				-webkit-box-shadow: 0 0 5px 5px #888;	
				
				-moz-border-radius:4px;
				-webkit-border-radius:4px;
			}
		
		</style>

<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.pack.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/jtip.js" charset="utf-8""></script> -->

<script type="text/javascript" charset="utf-8"> 

/**
 * HTML5 Notifications
 * - dependencies: jQuery only if you want to use callForPermission() and checkForPermission() methods.
 *
 * @djalmaaraujo
 * @wilkerlucio
 */
var Notifications = {
    apiAvailable: function() {
        if(window.webkitNotifications) {
            return true;
        } else {
            return false;
        }
    },
 
    isAuthorized: function() {
        if (!this.apiAvailable()) return false;
 
        return window.webkitNotifications.checkPermission() > 0 ? false : true;
    },
 
    authorize: function(callback) {
        var self = this;
        if (!this.apiAvailable()) return false;
        
        setTimeout(function(){alertMessage('Notificações do Chat','Clique em permitir localizado na parte superior do site para ativar a visualização de mensagens do chat em sua área de trabalho.',false);},2800);
        
        window.webkitNotifications.requestPermission(function() {
            if (self.isAuthorized()) {
                callback();
            }
        });
    },
 
    show: function(url, title, body) {
        if (!this.apiAvailable()) return false;
 
        var self = this;
 
        if (this.isAuthorized()) {
            var popup = window.webkitNotifications.createNotification(url, title, body);
            popup.show();
            setTimeout(function(){
                popup.cancel();
            }, 5000);
        } else {
            this.authorize(function() {
                //console.log(arguments);
                self.show(url, title, body);
            });
        }
    },
 
    checkForPermission: function() {
    	if (!this.isAuthorized()) {
    		this.callForPermission();
        }
    },
 
    callForPermission: function() {
       Notifications.authorize();
    }
};


var $j = jQuery.noConflict();
var DESTINATARIOS = new Array();
var PAGE = '${pageName}';

function init(page){
	PAGE = page;

	if(page=="documento"){
		//$j('#corpo_documento').fadeIn('slow');
        $j('#corpo_documento').show("clip");
 	}

	if(page=="documento"){
		$j('.tela_apresentacao').hide();
 	}

	if(page=="pesquisar"){
		$j('.tela_apresentacao').hide();
 	}

	if(page=="criarDocumento"){
		
 	}

	if(page=="principal"){
	    setTimeout("$j('.tela_apresentacao').hide('explode')",100);
	    //errorAlert("Sistema em teste. Qualquer problema encontrado utilize o link sugestões ou ligue para o ramal 4470.<br><br>O G.E.D. voltará ao normal a partir das 10:30 hs.");
  	}

	$j('.tela_apresentacao').hide();
}

jQuery(document).ready(function($) {	

	/*$.tools.dateinput.localize("pt-br",  {
		   months:        'Janeiro,Fevereiro,Março,Abril,Maio,Junho,Julho,Agosto,Setembro,' +
		                   	'Outubro,Novembro,Dezembro',
		   shortMonths:   'jan,fev,mar,abr,mai,jun,jul,ago,set,out,nov,dez',
		   days:          'domingo,segunda-feira,terça-feira,quarta-feira,quinta-feira,sexta-feira,s&eacute;bado',
		   shortDays:     'dom,seg,ter,qua,qui,sex,sáb'
		});

	$(":date").dateinput({ 
		lang: 'pt-br', 
		format: 'dd/mm/yyyy',
		//offset: [30, 0]
	});*/


	<%
		if(request.getParameter("aviso") != null){
	%>
	
	var alertaInicial = "Devido a grande quantidade de solicitações/dúvidas "+
	"com relação a nova versão do GED, solicito-vos que utilizem APENAS "+ 
	"o link SUGESTÕES localizado "+
	"na parte superior ao lado de SAIR, com todas as informações da carteira, tais como: OM, seção, grupos o qual faz parte e abreviatura "+
	"de como queira que apareça aos outros usuários. Não se esquecendo também de "+
	"nos informar o nr ramal para que possamos, posteriormente, entrarmos em contato.<br>Att. Eqp Des Seç Infor.";
	
	alertMessage('ATENÇÃO',alertaInicial,false);

	<%
		}
	%>
	
	ChatDiretoAPI = new ChatDiretoAPI('${usuario.pstGrad.pstgradNome} ${usuario.usuNGuerra}',${usuario.idUsuario});
	ChatDiretoAPI.start(null);
	
	$( "#data" ).datepicker();
	$( "#data" ).datepicker( "option", "dateFormat", 'yy-mm-dd' );
	
	/**
	*DRAGGABLES
	*
	**/
	$( "#wacao" ).draggable();
	$( "#weditar" ).draggable();
	$( "#wgrupos" ).draggable();
	
	//DRAGGABLE DO CHAT
	$( "#chat" ).draggable();
	$( "#chat" ).draggable( "option", "disabled", true );
	$( "#chat" ).removeClass( 'ui-state-disabled' );
	
	/**
	*Funções para habilitar e desabilitar o draggable do chat
	*Porém quando habilitado o scroll deve desaparecer
	**/
	function disableChatDrag(){
		$( "#chat" ).draggable( "option", "disabled", true );
		$( "#chat" ).removeClass( 'ui-state-disabled' );
		$( "#console_chat" ).css('overflow','auto');
		$( "#console_chat" ).css('cursor', 'text');
	};
	
	function enableChatDrag(){
		$( "#chat" ).draggable( "option", "disabled", false );
		$( "#console_chat" ).css('overflow','hidden');
		$( "#console_chat" ).css('cursor', 'move');
	};
	
	$('#welcome').click(function() {
		var disabled = $( "#chat" ).draggable( "option", "disabled" );
		if	(disabled)
			enableChatDrag();
		else
			disableChatDrag();
	});
	
	$( "#console_chat" ).dblclick(function() {
		var disabled = $( "#chat" ).draggable( "option", "disabled" );
		if	(disabled)
			enableChatDrag();
		else
			disableChatDrag();
	});
	
	/**
	*MODAL
	*Seleciona todos os <a href> com name=modal
	**/
	$('a[name=modal]').click(function(e) {

		//alert($(this).attr('id'));
		//var winH = e.pageX;
		//var winW = 1002;
	
		e.preventDefault();
		
		var id = $(this).attr('href');
		//alert(id);

		if (id == '#wgrupos'){
			getGrupos();
		}else if(id == '#wacao'){
			//var winH = $(id).position();
			$('#div_criptografar').css('display','none');
			var acao = $(this).attr('id').split('_');
			
			$j(id+' #bt_acao_salvar').unbind('click');
			
			if(acao[1] == 'Despachar')
				$('#div_criptografar').css('display','block');
			
			$('#chk_criptografar').attr('checked', false);
			$(id+' .titulo_confirmacao').html(acao[1]);
			$(id+' #bt_acao_salvar').bind('click', function() {
				salvarAcao(acao[1],parseInt(acao[0]),$(this));
			});
			
			
		}else if(id=='#weditar'){	
			//var anexoCaminho = $(this).attr('id');
			//$('#hn_nome_anexo').val(anexoCaminho);
			//alert("abrir documento: "+anexoCaminho);
			ID_ANEXO = $(this).attr('anexo');
			NOME_ANEXO = $(this).attr('nomeanexo');
			caminho = "verdocumentoFisico.html?id="+ID_ANEXO;
			//alert(caminho);
			window.open(caminho, '', ''); 
			
		}else if (id=='#wchecar'){
			ID_ANEXO = $(this).attr('anexo');
		}
		
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(100);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		var winH = $(window).height();
	    var winW = $(window).width();
              
		//Set the popup window to center
		//$(id).css('top',  winH/2-$(id).height()/2);
		//alert(winH);
		//$(id).css('top',  winH-$(id).height()-100);
		var pos = $(this).offset();
		$(id).css('top',  pos.top-20);
		$(id).css('left', winW/2-$(id).width()/2);
		//$(id).css('left', winW/2);
	
		if(id=='#wcomentar')
			$(id).css('top',  pos.top+20);

		if(id=='#wacao'){
			if($(this).attr('direction') == 'bottom')
				$(id).css('top',  winH-$(id).height()-100);
			else
		   		$(id).css('top',  pos.top-$(id).height());
		}	
		
		
		//transition effect
		$(id).fadeIn(100); 
	
	});

	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();

		js.direto.close_mask();
	});

	$('.window .send').click(function (e) {
		e.preventDefault();
		js.direto.enviarPara();
		js.direto.atualiza(PAGE); //grava destinarios no array
		js.direto.close_mask();
	});		
	
	//Se mask for clicado, desaparece o  modal
	/*$('#mask').click(function () {
		/$(this).hide();
		$('.window').hide();
	});*/

	$.tools.tooltip.addEffect("explode",
			// abrindo animação
			function(done) {
				this.getTip().show('slide');
			},
			// fechando animação
			function(done) {
				this.getTip().fadeOut('slow');
			}
		);

	$(".ahref_docs").tooltip(
			{ 
				position: "bottom right", 
				opacity: 1,
				effect: 'explode'
			}
	).dynamic({ bottom: { direction: 'top', bounce: true } });
		
	//$('a[name=tooltip]').live('click', function(clickEvent){clickEvent.preventDefault();});
	/*$('#testeTooltip').tooltip({
			 
		//events: {def: "click,blur"},
		// each trashcan image works as a trigger
		tip: '#notificacoes',

		effect: "fade",
		predelay: 40,
		// custom positioning
		position: 'bottom center',

		// move tooltip a little bit to the right
		offset: [10, 0],

		// there is no delay when the mouse is moved away from the trigger
		delay: 0
	});*/

	$('a[name=notificacoes]').click(function(e) {
		e.preventDefault();
		var id = $(this).attr('id');
		getNotificacoes(id,$(this));
		var pos = $(this).offset();
		var width = $(this).width();
	  	$("#notificacoes").css( { "left": (pos.left) + "px", "top":(pos.top+20) + "px" } );
	  	$("#notificacoes").toggle("fast");
	  });	

	$('#bt_conf_comentario').click(function (e) {
		e.preventDefault();
		comentarioJS.save($('#comentario').val(),{
			callback:function() {
				//dialogMessage('Sugestão/crítica','Sugestão/crítica enviado com sucesso!',false);
				alert("Sugestão/crítica enviado com sucesso!");
				js.direto.close_mask();
			}
		});
	});

	$('a[name=acompanhar]').click(function(e) {
		//e.preventDefault();
		$(".div_docs input[type=checkbox]").each(function () {
			var id = $(this).attr('pk');
			var isChecked = $(this).is(':checked');
			var existeNotificacao = $(".div_docs a[id="+id+"]").text();
			
			if (existeNotificacao == "" && isChecked) 
				documentosJS.acompanhar(id,true);

			if (existeNotificacao != "" && isChecked){
				documentosJS.acompanhar(id,false);
			}
			
		});	
		setTimeout("document.location.reload(true)",1000);
	});
		
});

</script>

<script type="text/javascript">

var first_click_notification = true;
var no_notifications;
var id_notificacao_elemento = 0;

function getNotificacoes(id,ele){

	if(id_notificacao_elemento == 0 || id_notificacao_elemento != $j(ele).attr('href')){
		first_click_notification = true;
	}else{
		first_click_notification = false;
	}

	$j.getJSON("notificacoes.html?id="+id, function(json){
	        //$('#tipoUdt').val(json.tipo);
	        //$('#tituloUdt').val(json.titulo);
	        //$('#descricaoUdt').val(json.descricao);
			//alert(json.id_categoria);
			//$('#notificacoes')innerHTML = "teste: ";
			//divN.innerHTML = "teste: "+id;
		
		if (first_click_notification){

			$j("#p_notificacoes").text('');

			id_notificacao_elemento = $(ele).attr('href');
			//alert(first_click_notification);
			//alert($j(notificacao_elemento).text());

			$j("#s_sem_notificacoes").hide();

			var arNotificacoes = (json.notificacoes).split('<br>');

			var total = arNotificacoes.length-1;

			no_notifications = (total == 0 ? true : false);

			total = "("+total+") ";
			$j(ele).html(total);
			
			(no_notifications == true ? $j("#s_sem_notificacoes").show() : $j("#p_notificacoes").append(json.notificacoes)); 
		 	
		 	first_click_notification = false;
	
		}else{

			$j("#s_sem_notificacoes").hide()	
			
			var qtde_notificacoes = $j(ele).text();
			//alert(qtde_notificacoes);
			qtde_notificacoes = qtde_notificacoes.replace("(","");
			qtde_notificacoes = qtde_notificacoes.replace(")","");
			qtde_notificacoes = parseInt(qtde_notificacoes);
			//qtde_notificacoes = qtde_notificacoes.match(/[0-9]/g);
			//alert(qtde_notificacoes);
			//$j("#notificacoes").html(str);
			
			var arNotificacoes = (json.notificacoes).split('<br>');

			var total = arNotificacoes.length-1;
			total = qtde_notificacoes+total;

			no_notifications = (total == 0 ? true : false);
			
			total = "("+total+") ";

			//var element = $j(ele).attr('id');
			//element = "#"+element;

			$j(ele).html(total);

			(no_notifications == true ? $j("#s_sem_notificacoes").show() : $j("#p_notificacoes").append(json.notificacoes));

		 	//$j("#notificacoes").append(json.notificacoes)
		}

			
	        } // fim do callback
		); // fim do .getJSON()
	
}


	
function getGrupos()
{
	var idCarteira = $('idCarteira').value;
	gruposJS.listGroups(idCarteira,montaGrupos);
}

function montaGrupos(listBeans){
	var slGrupos = $("slGrupo");
	var qtd = slGrupos.options.length;
	var val = slGrupos.options[0].value;

	if (qtd == 1 && val == 0){
		if (listBeans != null){
			//dwr.util.removeAllOptions('slGrupo');
			dwr.util.addOptions('slGrupo', listBeans, "id", "texto");
		}
	}
}

function carregaGrupo()
{
	var idNomeGrupo = $("slGrupo").value;
	gruposJS.usersByGroup(idNomeGrupo,montaUsersByGrupos);
	
}

function montaUsersByGrupos(listBeans){
	if (listBeans != null){
		dwr.util.removeAllOptions("ListaDE");
		dwr.util.addOptions("ListaDE", listBeans, "id", "texto");
	}
	
	var total = $j('#ListaDE option').length;
	var cur;
	for ( var i = 0; i < total; i++ )
	{
		cur = $j('#ListaDE option:eq(' + i + ')');
		cur.attr( 'title', listBeans[i].titulo );
	}
	
	/*var pos = $j("#wgrupos").offset();
	var x = 0, y = 0;
	
	$j(document).mousemove(function(e){
		x=e.pageX;
		y=e.pagey;
	}); 
	
	$j('#ListaDE option').tooltip(
			{ 
				position: 'bottom center', 
				offset: [pos.top+100, pos.left+100],
				opacity: 1,
				effect: 'explode',
				
				onBeforeHide: function() {
					$j(".tooltip").each(function(i){
						//$j(this).css('top',y);
						//$j(this).css('left',x);
						
					});
					
				}
			}
			
			
	).dynamic({ top: {offset: [400, -300]} });*/
	
}

</script>  

<style>
.tooltip {
z-index: 9999;
position: relative;
}
</style>

</head>

<c:set var="pagePath" value="${pageContext.request.requestURI}"></c:set>
<c:set var="pageName" value="${fn:split((fn:split(pagePath,'/')[(fn:length(fn:split(pagePath,'/'))-1)]),'.')[0]}"></c:set>

<body onload="init('${pageName}')">

<audio src="sound81.wav" id="beepchat" type="application/x-mplayer2"></audio>

<div id="error-message" title="Error" class="ui-state-error ui-corner-all" style="display: none;">
</div>

<div id="dialog-message" title="Caixa de Diálogo" style="display: none;">
</div>

<!-- MODALS -->
<div id="boxes">

	<%@ include file="includes/principalModals.jsp" %>
	
	<c:if test="${pageName == 'documento'}">
		<%@ include file="includes/documentoModals.jsp" %>
	</c:if>
	
	<c:if test="${pageName == 'criarDocumento'}">
		<%@ include file="includes/criarDocumentoModals.jsp" %>
	</c:if>	

  <!-- Mask para bloquear tela -->
  <div id="mask"></div>
</div>

<div id="#tela_apresentacao" class="tela_apresentacao"></div> 

<div style="width: 100%; margin: 0 auto;" id="bloco">
	<!-- CABEÇALHO -->
	<div style="position: relative; background-image: url('imagens/fundo.jpg'); height:72px;">
		<div style="width: 1002px; margin: 0 auto;">
			<div style="width: 189px; float: left;">
				<img name="direto_r1_c1" src="imagens/direto_r1_c1.jpg" width="189" height="43" border="0" id="direto_r1_c1" usemap="#m_direto_r1_c1" alt="" />
			</div>
			<div style="line-height:43px; width: 813px; height:43px; background-image: url('imagens/direto_r1_c2.jpg'); float: right;">
				<div style="text-align: center; position: relative; width: 100%;">
					<c:forEach var="mt" items="${menuTopo}">
		 				<c:set value="${mt.name}" var="name"/>
		 				<c:set value="${mt.value}" var="value"/>
		 				
		 				<c:url value="${value}" var="mtURL">
						  <c:if test="${name != 'Sugestões'}">
						    <c:param name="box" value="${box}" />
						  </c:if>
						</c:url>
						
						<a href="<c:out value="${mtURL}" />" class="menu_titulo" <c:if test="${name == 'Sugestões'}">name="modal"</c:if> >${name}</a> 
						
						<c:if test="${name != 'Sair'}"> | </c:if>
					</c:forEach>
				</div>
			</div>
		</div>
		<div style="line-height: 29px; height: 29px; background-color: #fff; position: relative; top: 43px;"> 
			<div style="width: 1002px; margin: 0 auto;">
				<div style="width: 184px; font-size:14px; position: absolute;">
					<span style="margin-left: 5px;"><spring:message code="welcome"/></span>
				</div>
				<div style="width: 817px; font-size:18px; margin: 0 189px; position: absolute; text-align: center;">
					Brigada de Operações Especiais 
				</div>
			</div>	
		</div>
	</div>
	
	<!-- CORPO e MENU ESQUERDA-->
	<div style="display: table; margin: 0 auto; width: 1002px; position: relative; min-height: 700px; border-bottom: 1px solid #ccc;">
		<div style="width: 167px; height: 700px; background-color: #fff; float: left; margin: 3px 0 0 5px;">
		
			<c:set var="divStyle" value="menuLado" />
			
			<div style="height: 30px; vertical-align: middle;">
				${usuario.pstGrad.pstgradNome}
				${usuario.usuNGuerra}
			</div>
			
			<div id="divMenuLateral" style="left: 5px; position: relative;">
				<span style="color: red;">*</span><a href="feed.html" class="<c:out value="${divStyle}"/><c:if test="${box == 7}">Sel</c:if>">
					Citações Doc Tramitados
				</a>
			</div>
			
			<c:forEach var="p" items="${pastas}">
	 			<c:set var="pasta" value="${p.nomePasta}" />
	 			<c:url value="principal.html" var="pastaURL">
					  <c:param name="box" value="${p.idPasta}" />
					  <c:param name="pr" value="0" />
					  <c:param name="filtro" value="todas" />
					  <c:param name="idCarteira" value="${contaAtual}" />
					  <c:choose>
					      <c:when test="${p.idPasta == 1}">
					     	<c:param name="ordenacao" value="0" /> 
					      </c:when>
					      <c:otherwise>
					      	<c:param name="ordenacao" value="1" />
					      </c:otherwise>
				    </c:choose>
				</c:url>
	      		<div id="divMenuLateral" style="left: 5px; position: relative;">
					<a href="<c:out value="${pastaURL}" />" class="<c:out value="${divStyle}"/><c:if test="${box == p.idPasta}">Sel</c:if>">
						<c:out value="${pasta}"/>
					</a> 
				</div>
	      </c:forEach>
			
			<div style="margin-top: 15px; height: 25px; font-weight: bold; font-size: 13px;">
			
				Alternar entre contas:
			
			</div>		
			
			<ul style="margin: 0;">
		 		<c:forEach var="conta" items="${usuario.contas}">
				     
				     <c:choose>
				      <c:when test="${conta.carteira.idCarteira == contaAtual}">
				     	<li style="margin-left: -5px; font-weight: bold; font-style: italic;"><a href="alternar.html?id=${conta.carteira.idCarteira}" style="color: gray;">${conta.carteira.cartAbr}</a></li> 
				      </c:when>
				
				      <c:otherwise>
				      	<li style="margin-left: -5px;"><a href="alternar.html?id=${conta.carteira.idCarteira}">${conta.carteira.cartAbr}</a></li>
				      </c:otherwise>
				    </c:choose>
				    <c:if test="${conta.contaPrincipal == 1}"><br></c:if>
				     
				     
				</c:forEach>
			</ul>
			
			<br />${numUsers} usuário(s) no sistema!
			
			<script type="text/javascript">
		
					  
				$j(function(){
					//start(null);
					
				 
				Notifications.checkForPermission();
				 
				//$j('#chat').hide();
				
				  $j('#stayOn').click(function(e) {
						e.preventDefault();
						ChatDiretoAPI.start(e);
				  });
				  
				  setTimeout(function(){ChatDiretoAPI.changeStatusInChat(null,1);},2900);
					
				 
					$j('#topo div[name=minimize]').toggle(function(){
						ChatDiretoAPI.showMinimized();
						$j(this).text('+');						
						},
						function(){
							ChatDiretoAPI.showMaximized();
							$j(this).text('-');
							}
						);

					$j('#topo div[name=search]').toggle(
							function(){
								ChatDiretoAPI.searchUser();
								$j('#search').focus(function(){$j(this).val('');$j(this).css('font-style', 'normal');});
							},

							function(){
									$j('#new').show();
									$j('#search').remove();
									ChatDiretoAPI.activeTimer();
							}
					);

					$j('#new').focus(function(){

						Notifications.checkForPermission();

						if ($j(this).val() == 'Digite aqui'){
							$j(this).val('');
							$j(this).css('font-style', 'normal');
						}
						
						});
					$j('#new').blur(function(){

						if (($j(this).val() == 'Digite aqui') || ($j(this).val() == '')){
							$j(this).val('Digite aqui');
							$j(this).css('font-style', 'italic');
						}
						
						});
						
								
				});

				

</script>

		<div id="chat" class="ui_border_shadow border_radius">
			<div id="topo"><div class="left border_radius" name="minimize" title="Minimizar"> - </div><div id="welcome"></div><div class="right border_radius" name="search" title="Clique aqui para pesquisar por usuários"> p </div></div>
			<div id="console_chat" class="border_radius">
			
			</div>
			<div id="div_new">
				<input type="text" id="new" value='Digite aqui' onkeypress="ChatDiretoAPI.teclaEnter(event)" onfocus="ChatDiretoAPI.checkToUser()"></input>
			</div>
			<div id="div_usuarios">
				<select id="usuariosON" name="usuariosON" onchange="ChatDiretoAPI.mudaTo(this)"></select>
			</div>
			<div id="div_status"><span id="status"><a href="#" id="stayOn">Entrar no chat</a></span></div>
		</div>	
			
		</div>
		
		<!-- INICIO SUBMENU 1 DO CORPO PRINCIPAL -->
		<div style="border-left:1px solid #ccc; width: 822px; float: right; height: auto;">
			<div style="background-color: #1E90FF;" class="ui_main_body ui_subMenu _width_main_body _font_white_bold">
				<a href="criarDocumento.html" style="margin-left: 5px;" class="menu1">Novo</a> | 
			
				<c:set var="isPagePrincipal" value="true"></c:set>
			
				<c:if test="${pageName == 'principal' || (pageName == 'documento' && pkDocumento !=0)}">
					
					<script type="text/javascript">
						function changeStatus(status,isMultiplos){
							var resposta = "";
							dialogMessage('Aguarde...','<p style="text-align: center"><img src="imagens/ajax-loader.gif" /></p>',true);
							$j( "#dialog-message" ).dialog( "option", "buttons", { "Ok": function() { 

								<c:choose> 
				  					<c:when test="${pageName == 'principal'}" > 
					  					setTimeout("document.location.reload(true)",100);
									</c:when> 
				  					<c:otherwise> 
					  					setTimeout(function(){
											document.location = 'principal.html?box=1&pr=0&filtro=todas';
										},100); 
				  					</c:otherwise> 
								</c:choose>

								 
						    	} 
							});
						
							if(isMultiplos){
								$j(".chkbox").each(function(i){
									var chkbox = $j(this);
									if(chkbox.is(':checked')){
										documentosJS.changeStatus(chkbox.attr('pk'),status,{
											callback:function(sRetorno) {
												resposta += sRetorno+'<br>';
												dialogMessage('Aguarde...',resposta,false);
											}
										});
									}
								});
							}else{
								documentosJS.changeStatus(PK_DOCUMENTO,status,{
									callback:function(sRetorno) {
										resposta += sRetorno+'<br>';
										dialogMessage('Aguarde...',resposta,false);
									}
								});
							}
						}
					</script>
				
					<c:if test="${ pageName == 'documento'}">
						<c:set var="isPagePrincipal" value="false"></c:set>
					</c:if>
					
					<c:choose>
				      <c:when test="${box == 1}">
				      	<a href="javascript:changeStatus(2,${isPagePrincipal});" class="menu1">Arquivar</a> |
						<a href="javascript:changeStatus(4,${isPagePrincipal});" class="menu1" id="testeTooltip" title="Teste">Pender</a> |
						<a href="javascript:changeStatus(0,${isPagePrincipal});" class="menu1" id="testeTooltip" title="Teste">Marcar como doc não lido</a> |
				      </c:when>
				
				      <c:when test="${box == 2}">
				      	<a href="javascript:changeStatus(4,${isPagePrincipal});" class="menu1" onClick="">Pender</a> |
						<a href="javascript:changeStatus(0,${isPagePrincipal});" class="menu1" onClick="">Retornar para caixa de entrada </a> |
				      </c:when>
						
					  <c:when test="${box == 3}">
				      	
				      </c:when>
				      
				      <c:when test="${box == 4}">
				      	<a href="javascript:changeStatus(2,${isPagePrincipal});" class="menu1" onClick="">Arquivar</a> |
						<a href="javascript:changeStatus(0,${isPagePrincipal});" class="menu1" onClick="">Retornar para caixa de entrada </a> |
				      </c:when>
				      
				      <c:when test="${box == 5}">
				      	<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
				      </c:when>
				      
				      <c:otherwise>
				        
				      </c:otherwise>
		    		</c:choose>
	    		</c:if>	
				<a href="pesquisar.html" class="menu1">Pesquisar</a> |
				<a href="javascript: history.go(-1);" class="menu1">Voltar</a>
			</div>
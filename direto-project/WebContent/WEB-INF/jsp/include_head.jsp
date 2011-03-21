<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="include_taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="title"/></title>
	
<!-- Inicio Folha de Estilos -->
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/modals.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/dateinput.css" rel="stylesheet" type="text/css" /> -->

<link href="css/custom-theme-jquery/jquery-ui-1.8.10.custom.css" rel="stylesheet" type="text/css" />
<link href="css/custom-theme-jquery/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />

<!-- Fim Folha de Estilos -->

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>
    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/gruposJS.js"></script>    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/loginValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/documentoValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/documentosJS.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/chatJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/chatDireto.js" charset="utf-8""></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/direto.js" charset="utf-8""></script>

<!-- <script src="<%=request.getContextPath() %>/js/jquery.tools.min.js"></script> -->

<script src="<%=request.getContextPath() %>/js/custom/jquery-1.4.4.min.js"></script>

 <script src="<%=request.getContextPath() %>/js/custom/jquery-ui-1.8.10.custom.min.js"></script> 

<script src="<%=request.getContextPath() %>/js/custom/external/tooltip.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/external/validator.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/external/jquery.alerts.js"></script>

<!--
<script src="<%=request.getContextPath() %>/js/custom/external/jquery.bgiframe-2.1.2.js"></script>

<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.core.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.widget.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.datepicker.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/jquery.ui.datepicker-pt-BR.js"></script>

<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.effects.core.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.effects.clip.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.effects.explode.min.js"></script>

<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.mouse.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.button.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.draggable.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.position.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.resizable.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/ui/minified/jquery.ui.dialog.min.js"></script>
 -->
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
		
		</style>

<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.pack.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/jtip.js" charset="utf-8""></script> -->

<script type="text/javascript">
var $j = jQuery.noConflict();
var DESTINATARIOS = new Array();
var PAGE = '';

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
	    setTimeout("$j('.tela_apresentacao').hide('explode')",1100);
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

	ChatDiretoAPI = new ChatDiretoAPI('${usuario.usuNGuerra}',${usuario.idUsuario});
	ChatDiretoAPI.start(null);
	

	$( "#data" ).datepicker();
	$( "#data" ).datepicker( "option", "dateFormat", 'yy-mm-dd' );
	
	
		
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {

		//var winH = e.pageX;
		var winW = 1002;
	
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
		//alert(id);

		if (id == '#wgrupos'){
			getGrupos();
		}else if(id == '#wacao'){
			//var winH = $(id).position();
			var txt = $(this).text();
			$(id+' .titulo_confirmacao').html(txt);
		}else{	
			var anexoCaminho = $(this).attr('id');
			$('#hn_nome_anexo').val(anexoCaminho);
			alert("abrir documento: "+anexoCaminho);
		}
		
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		//var winH = $(window).height();
		//var winH = $(id).position();
		//var winW = '1002';//$(window).width();
              
		//Set the popup window to center
		//$(id).css('top',  winH/2-$(id).height()/2);
		//alert(winH);
		//$(id).css('top',  winH-$(id).height()-100);
		var pos = $(this).offset();
		$(id).css('top',  pos.top-20);
		$(id).css('left', winW/2-$(id).width()/2);
	
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
	
	//if mask is clicked
	/*$('#mask').click(function () {
		/$(this).hide();
		$('.window').hide();
	});*/

	$.tools.tooltip.addEffect("explode",

			// opening animation
			function(done) {
				this.getTip().show('explode');
			},

			// closing animation
			function(done) {
				this.getTip().fadeOut('slow');
			}
		);

	//$("#testeTooltip a[title]").tooltip();
	$("#testeTooltip").tooltip(
			{ 
				position: "center right", 
				opacity: 0.7,
				effect: 'explode'
			}
	);
	
	
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

	
	//$(document).ready(function() {
		 // var $box = $('#box')
		 //.wrap('<div id="box-outer"></div>');
		  //$('#blind').click(function() {
		 //$box.blindToggle('slow');
		  //});
	//});

	$('a[name=notificacoes]').click(function(e) {
		e.preventDefault();

		var id = $(this).attr('href');
		
		//javascript:getNotificacoes("+doc.getIdDocumentoDetalhes()+",this);
		
		getNotificacoes(id,$(this));
		//alert("teste");
		
	  //get the position of the placeholder element
	  var pos = $(this).offset();  
	  var width = $(this).width();

//	alert(pos.left);
//	alert(pos.top);
	  
	  //show the menu directly over the placeholder
	  $("#notificacoes").css( { "left": (pos.left) + "px", "top":(pos.top+20) + "px" } );
	  $("#notificacoes").toggle("fast");


	  });	

	

	$('a[name=acompanhar]').click(function(e) {
		//e.preventDefault();

		$(".div_docs input[type=checkbox]").each(function () {

			var id = $(this).val();
			var isChecked = $(this).is(':checked');
			var existeNotificacao = $(".div_docs a[id="+id+"]").text();
			
			if (existeNotificacao == "" && isChecked) 
				documentosJS.acompanhar(id,true);

			if (existeNotificacao != "" && isChecked){
				documentosJS.acompanhar(id,false);
			}
			
		});	

		//document.location.reload(true);
		setTimeout("document.location.reload(true)",1000);

	});
	
	

	
	//var $not = $('#notificacoes').wrap('<div id="box-outer"></div>');		

	//$('a[name=tooltip]').click(function() {
		//$not.blindToggle('slow');
		  //});	

	
		  
		
});

</script>

<script type="text/javascript">

var first_click_notification = true;
var no_notifications;

function getNotificacoes(id,ele){

	//alert(first_click_notification);
	//ele.style.backgroundColor="#dddddd"; 

	$j.getJSON("notificacoes.html?id="+id, function(json){
	        //$('#tipoUdt').val(json.tipo);
	        //$('#tituloUdt').val(json.titulo);
	        //$('#descricaoUdt').val(json.descricao);
			//alert(json.id_categoria);
			//$('#notificacoes')innerHTML = "teste: ";
			//divN.innerHTML = "teste: "+id;
		
		if (first_click_notification){

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
	
}



/*function mostrarGrupos(){

	var disabledZone = document.createElement('div');
	disabledZone.setAttribute('id', 'disabledZone');
	disabledZone.style.position = "absolute";
	disabledZone.style.zIndex = "1000";
	disabledZone.style.left = "0px";
	disabledZone.style.top = "0px";
	disabledZone.style.width = "100%";
	disabledZone.style.height = "100%";
	document.body.appendChild(disabledZone);

	var messageZone = document.createElement('div');
	messageZone.setAttribute('id', 'div_grupos');
	messageZone.style.position = "absolute";
	messageZone.style.top = "500px";
	messageZone.style.right = "500px";
	messageZone.style.background = "blue";
	messageZone.style.color = "white";
	messageZone.style.fontWeight = "bold";
	messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
	messageZone.style.padding = "4px";
	disabledZone.style.zIndex = "1001";
	disabledZone.appendChild(messageZone);


	//var myLink = document.createElement('a');

	//var href = document.createAttribute('href');

	
	var fechar = document.createElement('a'); 
	fechar.setAttribute('href', 'javascript:fechar();');
	//var href = document.createAttribute('href');
	//fechar.setAttribute(href,'fecharGrupos()');
	fechar.innerText = "fechar";

	
	//var text = document.createTextNode('Grupos');
	//messageZone.appendChild(text);
	
	messageZone.appendChild(fechar);

	


	
		

	
	$('disabledZone').style.visibility = 'visible';
	
}*/

</script>  





</head>

<c:set var="pagePath" value="${pageContext.request.requestURI}"></c:set>
<c:set var="pageName" value="${fn:split((fn:split(pagePath,'/')[(fn:length(fn:split(pagePath,'/'))-1)]),'.')[0]}"></c:set>

<body onload="init('${pageName}')">




<!-- MODALS -->
<div id="boxes">

	<c:if test="${pageName == 'principal'}">
		<%@ include file="includes/principalModals.jsp" %>
	</c:if>
	
	<c:if test="${pageName == 'documento'}">
		<%@ include file="includes/principalModals.jsp" %>
		<%@ include file="includes/documentoModals.jsp" %>
	</c:if>
	
	<c:if test="${pageName == 'criarDocumento'}">
		<%@ include file="includes/principalModals.jsp" %>
		<%@ include file="includes/criarDocumentoModals.jsp" %>
	</c:if>	

  <!-- Mask para bloquear tela -->
  <div id="mask"></div>
</div>


<div id="#tela_apresentacao" class="tela_apresentacao"></div> 

<div id="table" style="position: absolute; width: 1002px; top: 0; left: 0;">
		 

<div id="table" style="position: relative; width: 1002px; top: 0; left: 0;">

	<div id="line" style="width: 1002px;float: left; height: 43px;">
		<div style="float: left;"><img name="direto_r1_c1" src="imagens/direto_r1_c1.jpg" width="189" height="43" border="0" id="direto_r1_c1" usemap="#m_direto_r1_c1" alt="" /></div>
		<div style="float: left; line-height:43px; background-image: url('imagens/direto_r1_c2.jpg'); width: 813px; height:43px; text-align: center;" class="menu_titulo">
			
			<c:forEach var="mt" items="${menuTopo}">
 				<c:set value="${mt.name}" var="name"/>
 				<c:set value="${mt.value}" var="value"/>
 				
 				<c:url value="${value}" var="mtURL">
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
   		<div style="float: left; background-image: url('imagens/head_complemento.jpg');width: 72px;height:29px;"></div>
   		<div style="float: left;"><img name="direto_r2_c2" src="imagens/direto_r2_c2.jpg" width="813" height="29" border="0" id="direto_r2_c2" alt=""  /></div>
   		<!--<div style="float: left; background-image: url('imagens/head_complemento.jpg');width: 72px;height:29px;"></div>-->
   		</div>

</div>

    
<div id="table" style="position: relative; width: 1002px; text-align: center; top: 75px; left: 0px; height: 700px; background-color: white; border-bottom: 1px solid #ccc">
	<div id="line" style="width: 167px; height: 715px; position: absolute; text-align: left; background-color: #ffffff; top: -27px; left: 5px" class="menuLado">
		
		<div style="height: 30px; font-size: 14px; font-weight: bold;">
			<spring:message code="welcome"/>
		</div>
		
		<c:set var="divStyle" value="menuLado" />
		
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
		
		<div style="margin-top: 30px; height: 25px; font-weight: bold; font-size: 13px;">
		
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
		
		    
		    <br>
		    Conta Atual: <c:out value="${contaAtual}"></c:out>
		    
		 
		  
		   <br>${numUsers} usuário(s) no sistema!
		
		<script type="text/javascript">
		   
			
			
				$j(function(){
					//start(null);
					
				  $j('#stayOn').click(function(e) {
						e.preventDefault();
						ChatDiretoAPI.start(e);
					});

				  $j('#welcome').click(function(e){ChatDiretoAPI.searchUser();});
				
					//hideOffline();
				
					$j('#console_chat').click(function(){ChatDiretoAPI.showMinimized();});	
					//$j('#div_status').click(function(){ChatDiretoAPI.showMaximized();});
								
				});


</script>

		<div id="chat" class="border_radius">
			<div id="welcome"></div>
			<div id="console_chat" class="border_radius">
			
			</div>
			<div id="div_new">
				<input type="text" id="new" onkeypress="ChatDiretoAPI.teclaEnter(event)" onfocus="ChatDiretoAPI.checkToUser()"></input>
			</div>
			<div id="div_usuarios">
				<select id="usuariosON" name="usuariosON" onchange="ChatDiretoAPI.mudaTo(this)"></select>
			</div>
			<div id="div_status"><span id="status"><a href="#" id="stayOn">Entrar no chat</a></span></div>
		</div>	
				   
	</div>
	
	<div id="line" style="width: 1px; position: absolute; margin: 0; background-color: #ccc; left: 177px; height: 700px;" class="menuLado">
		
	</div>
	
	<div id="line" style="width: 822px; position: absolute; margin: 0 0 0 2px; text-align: center; left: 178px">
		
		<div style="width:100%; text-align:left; background-color: #1E90FF; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu1">
	
			<a href="criarDocumento.html" style="margin-left: 5px;" class="menu1">Novo</a> | 
			
			<c:if test="${pageName == 'principal'}">
			 
				<c:choose>
			      <c:when test="${box == 1}">
			      	<a href="javascript:Arquivar(2);" class="menu1">Arquivar</a> |
					<a href="#" class="menu1" id="testeTooltip" title="Teste">Pender</a> |
			      </c:when>
			
			      <c:when test="${box == 2}">
			      	<a href="javascript:Arquivar(3);" class="menu1" onClick="">Pender</a> |
					<a href="javascript:Arquivar(3);" class="menu1" onClick="">Retornar para caixa de entrada </a> |
			      </c:when>
					
				  <c:when test="${box == 3}">
			      	
			      </c:when>
			      
			      <c:when test="${box == 4}">
			      	<a href="javascript:Arquivar(3);" class="menu1" onClick="">Arquivar</a> |
					<a href="javascript:Arquivar(3);" class="menu1" onClick="">Retornar para caixa de entrada </a> |
					<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
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
		
		
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="title"/></title>
	
<!-- Inicio Folha de Estilos -->
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/dateinput.css" rel="stylesheet" type="text/css" />
<!-- Fim Folha de Estilos -->
    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/gruposJS.js"></script>    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/loginValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/documentosJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/direto.js" charset="utf-8""></script>

<script src="<%=request.getContextPath() %>/js/jquery.tools.min.js"></script>


<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.pack.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/jtip.js" charset="utf-8""></script> -->

<script type="text/javascript">

var $j = jQuery.noConflict();



jQuery(document).ready(function($) {	


	$.tools.dateinput.localize("pt-br",  {
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
	});



	$('input[type=button]').click(function(e) {
		var bt_name = $(this).attr('name');
		var nome_anexo = $('#hn_nome_anexo').val();
		
		if(bt_name == 'bt_editar_nao'){

			confirma_edicao(0,nome_anexo);

			
			alert("fecha esse trem");

			js.direto.close_mask();
			
		}	

		
	});
	
		
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {

	
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');

		if (id == '#grupos'){
			getGrupos();
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
		var winH = $(window).height();
		var winW = '1002';//$(window).width();
              
		//Set the popup window to center
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
	
		//transition effect
		$(id).fadeIn(2000); 
	
	});

	
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		js.direto.close_mask();
	});		
	
	//if mask is clicked
	/*$('#mask').click(function () {
		/$(this).hide();
		$('.window').hide();
	});*/

	//$('a[name=tooltip]').live('click', function(clickEvent){clickEvent.preventDefault();});
	/*$('a[name=tooltip]').tooltip({
			 
		events: {def: "click,blur"},
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

	$('a[name=tooltip]').click(function(e) {
		e.preventDefault();

		var id = $(this).attr('href');
		
		//javascript:getNotificacoes("+doc.getIdDocumentoDetalhes()+",this);
		
		getNotificacoes(id,$(this));
		//alert("teste");
		
	  //get the position of the placeholder element
	  var pos = $('a[name=tooltip]').offset();  
	  var width = $('a[name=tooltip]').width();

//	alert(pos.left);
//	alert(pos.top);
	  
	  //show the menu directly over the placeholder
	  $("#notificacoes").css( { "left": (pos.left) + "px", "top":(pos.top+20) + "px" } );
	  $("#notificacoes").toggle("fast");


	  });	

	
	
	
			

	//var $not = $('#notificacoes').wrap('<div id="box-outer"></div>');		

	//$('a[name=tooltip]').click(function() {
		//$not.blindToggle('slow');
		  //});	


		  
		
});

</script>

<script type="text/javascript">

jQuery.fn.blindToggle = function(speed, easing, callback) {
	  var h = this.height() + parseInt(this.css('paddingTop')) + parseInt(this.css('paddingBottom'));
	  return this.animate({marginTop: parseInt(this.css('marginTop')) <0 ? 0 : -h}, speed, easing, callback);
	};



function confirma_edicao(resposta,nome_anexo){

	if (resposta == 0){
		alert(nome_anexo+" - Apaga arquivo temp servidor e cancela operação");
	}else{
		alert(nome_anexo+" - Substitui arquivo servidor, apaga arquivo temp e armazena histórico.");
	}
	
}

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

			$j("#s_sem_notificacoes").hide()

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

function show_updates(id,type){

	var singular = type;

	if(type == "anotacoes"){
		singular = "anotacao";
	}else if (type == "despachos"){
		singular = "despacho";
	}else singular = "unknow";	

	var txt_date = $j('#'+type+' #data_'+singular).last().text();
	var last_date = js.direto.parseDate(txt_date);
	
		$j.getJSON(type+".html?id="+id, function(data) {

			
	    $j.each(data.dados, function(i,d){
			//alert(a.anotacao);
	        //$j("#anotacoes div:last").after('div');
	        
	        
	        var date_json_return = js.direto.parseDate(d.dataHora);

			
	        
	       // if(js.direto.compareDate(date_json_return,last_date) == 1){
	        
		        $j('#'+type+' div:last').after("<div></div>");
		        $j('#'+type+' div').last().hide();
		        $j('#'+type+' div').last().html(d.acao);
		        $j('#'+type+' div').last().addClass('celula '+singular);
		        $j('#'+type+' div').last().fadeIn("slow");

	       // }
	        
			//$j('<div>teste</div>').insertAfter($('#div_anotacoes div:last'));

	        
	      });
	    });

	
    
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

<style>
#box {
  padding: 10px;
  height: 100px;
  width: 100px;
  background: #e459e9;
}
#box-outer {
  overflow: hidden;
  height: 120px;
  margin: 20px;
  z-index: 99999;
}




#mask {
  position:absolute;
  left:0;
  top:0;
  z-index:9000;
  background-color:#000;
  display:none;
}
  
#boxes .window {
  position:absolute;
  left:85px;
  top:0;
  width:1002px;
  height:500px;
  display:none;
  z-index:9999;
  background-color:#FFF;
  text-align: center;
  
}

#boxes #grupos {
  width:840px; 
  height:400px;
}

#boxes #editar {
  width:300px; 
  height:70px;
  /* outline radius for mozilla/firefox only */
	-moz-box-shadow:0 0 10px #fff;
	-webkit-box-shadow:0 0 10px #fff;
}

#boxes #notificacoes {
  width:140px; 
  height:100px;
}

#bt_conf_edicao {
	border: 1px solid #006;
    background: #ccf
}
#bt_conf_edicao:hover {
	 border: 1px solid #f00;
    background: #eef;
}

</style>

<style type="text/css" media="all">
/* simple css-based tooltip */
.tooltip {
	/*background-color:#000;
	border:1px solid #fff;
	padding:10px 15px;
	width:200px;
	display:none;
	color:#fff;
	text-align:left;*/
	font-size:12px;
	position: absolute;
	z-index: 9999;
	text-align: center;
	width: 200px;
	display: none;
	border:1px solid #000;
	background-color: white;

	/* outline radius for mozilla/firefox only */
	-moz-box-shadow:0 0 10px #000;
	-webkit-box-shadow:0 0 10px #000;
}

/*@import "css/dateinput.css";*/
</style>

</head>


<body onload="">


<div id="notificacoes" class="tooltip">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Notificações</td>
		</tr>
	</table>
	<p id="p_notificacoes"><span id="s_sem_notificacoes" style="display: none;">Sem notificacoes.</span></p>
</div>

<div id="boxes">

<!-- Confirmação da edição documento -->
<div id="editar" class="window">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="#1E90FF" class="titulo_confirmacao" height="20" valign="middle">Confirmar edição do documento?</td>
		</tr>
		<tr>
			<td height="35" valign="bottom"><input type="button" id="bt_conf_edicao" value="Sim" name="bt_editar_sim"></td>
			<td valign="bottom"><input type="button" id="bt_conf_edicao" value="Não" name="bt_editar_nao"></td>
		</tr>
		
	</table>
	
	<input type="hidden" id="hn_nome_anexo">
</div>
  
<!-- Start of Login Dialog -->  
<div id="grupos" class="window">
    <table align=center cellPadding=10 cellSpacing=10 width=80%>
		<tr>
			<td>
			<TABLE border=0 cellpadding=3 cellspacing=0 width=90% class=table>
				<TBODY>
					<TR>
						<TD colspan="3" align="center">
						<b>Grupo:</b> <SELECT
							name=slGrupo id="slGrupo" style="width: 200pt" LANGUAGE=javascript
							onchange="carregaGrupo()" onfocus="getGrupos()">
							<option id="0" value="0" selected>Selecione o grupo</option>
							
						</SELECT></TD>
					</TR>
					<TR>
						<TD style="width: 40%">
						<FIELDSET><LEGEND>&nbsp;<b>Usuários</b>&nbsp;</LEGEND> <SELECT
							name=ListaDE id=ListaDE multiple size=11 style="width: 310px;"
							ondblClick="mover($('ListaDE'), $('ListaPARA')); return false;">
	
	
						</SELECT></FIELDSET>
						</TD>
						<TD style="text-align: center" style="width:20%"><INPUT
							type="button" value="Incluir >>" style="width: 90pt"
							id=btParaDireita
							onClick="mover($('ListaDE'), $('ListaPARA')); return false;">
						<INPUT type="button" value="<< Remover" style="width: 90pt"
							name=btExclui id=btParaEsquerda
							onClick="remover($('ListaPARA'), $('ListaDE')); return false;">
						</TD>
	
						<TD style="text-align: center" style="width:40%">
						<FIELDSET><LEGEND>&nbsp;<b>Destinatário</b>&nbsp;</LEGEND>
						<SELECT name=ListaPARA id=ListaPARA size=11 style="width: 310px;"
							multiple
							ondblClick="remover($('ListaPARA'), $('ListaDE')); return false;">
	
						</SELECT></FIELDSET>
						</TD>
					</TR>
					
				</TBODY>
			</TABLE>
			</td>
		</tr>
	</table>   
	<a href="#" class="close"/>Fechar [Enviar]</a>
	<input type="hidden" id="idCarteira" value="${contaAtual}" />
</div>
<!-- End of Login Dialog -->  


<!-- Mask to cover the whole screen -->
  <div id="mask"></div>
</div>


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

    
<div id="table" style="position: relative; width: 1002px; text-align: center; top: 75px; left: 0px; height: 400px; background-color: white;">
	<div id="line" style="width: 167px; float: left; position: absolute; text-align: left; background-color: #ffffff; top: -27px; left: 5px" class="menuLado">
		
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
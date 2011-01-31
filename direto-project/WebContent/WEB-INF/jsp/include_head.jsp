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

jQuery.noConflict();



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

	
	
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {
		getGrupos();

		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
	
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
		var winW = '1002px';//$(window).width();
              
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
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});	

	
		
	$('a[name=tooltip]').tooltip({

		events: {def: "click,blur"},
		// each trashcan image works as a trigger
		tip: '#notificacoes',

		// custom positioning
		position: 'bottom center',

		// move tooltip a little bit to the right
		offset: [0, 0],

		// there is no delay when the mouse is moved away from the trigger
		delay: 0

		
	});		
	
});

</script>

<script type="text/javascript">

function getNotificacoes(id){

	

		jQuery.getJSON("notificacoes.html?id="+id, function(json){
	        //$('#tipoUdt').val(json.tipo);
	        //$('#tituloUdt').val(json.titulo);
	        //$('#descricaoUdt').val(json.descricao);
			//alert(json.id_categoria);
			//$('#notificacoes')innerHTML = "teste: ";
			//divN.innerHTML = "teste: "+id;
			jQuery("#notificacoes").html(json.notificacoes);
			
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

<style>
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

#boxes #notificacoes {
  width:140px; 
  height:100px;
}

</style>

</head>


<body onload="">


<div id="notificacoes" class="tooltip">
	Remove this row.
</div>

<div id="boxes">
  
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

<div id="notificacoes" class="window">
	<table>
		<tr><td>Teste Notificacao</td></tr>	
	</table>

</div>



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
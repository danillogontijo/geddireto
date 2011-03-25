<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include_taglibs.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administração - Direto</title>
<link href="<%=request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/css/modals.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath() %>/css/custom-theme-jquery/jquery-ui-1.8.10.custom.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/css/custom-theme-jquery/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />

<script src="<%=request.getContextPath() %>/js/custom/jquery-1.4.4.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/jquery-ui-1.8.10.custom.min.js"></script> 

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/pstgradJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/carteiraJS.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/direto.js" charset="utf-8""></script>


<style type="text/css">
#palco {width: 1024px;}

#menu {	width: 200px; position: absolute;}

#conteudo{
	width: 824px; 
	
	position: absolute;
	left: 200px;
	}
	
.table_users {
	text-align: center;
}	

.table_users tr td:first{
	width: 20%;
	background-color: aqua;
}

.table_titulo {
	background-color: #1E90FF;
	color: #fff;
	font-weight: bold;
}

.link_red{
	color: red;
}

.left_{
	
	width: 390px;
	float: left;
	
}

.right_{
	width: 390px;
	float: left;
	
}

fieldset p input {
	width: 350px;
}

fieldset p select {
	width: 350px;
}

fieldset p {
	padding-bottom: 30px;
}

</style>

</head>
<body>

<script>
var $j = jQuery.noConflict();

var S_USERS = "";
var TABLE_USERS = null;


$j(function() {

	$j('a[name=modal]').live('click',function(e) {
		e.preventDefault();
		js.direto.modal(this);
		//alert('');
			
	});

	$j('.window .close').click(function (e) {
		e.preventDefault();
		js.direto.close_mask();
	});
 
	
	showAllUsers();
	
	$j( "#accordion" ).accordion({ active: 0 });
	$j('a[name=users]').click(function(e){
		e.preventDefault();
		if(S_USERS != ""){
			removeAllUsers(true);
			setTimeout(function(){showAllUsers();},2000);
		}else{
			showAllUsers();
		}
	});

	$j('a[name=edit_user]').live('click',function(e){
		e.preventDefault();
		var id = $j(this).attr('href');
		editUser(id);
	});

	function editUser(id){

		removeTable();
		
		table = '<table id="table_users" width="100%" class="table_users">'+
		'<tr><td colspan="10">'+
		'</td></tr><tr><td>';

		$j('#conteudo').append(table);
		$j("#table_users tr:first").addClass('table_titulo');
		$j('#table_users tr').eq(1).find('td').prepend('<fieldset>');
		//$j('#table_users tr').eq(1).find('td').find('fieldset').append('<h3>Preencha os campos abaixo</h3>');	
		
		$j.getJSON("consultaUsuario.html?id="+id, function(data){
			//removeTable();
			//alert(data.usuNGuerra);
			$j("#table_users tr:first").first().text('Edição do usuário: '+data.usuLogin);
			
			userForm('text','Login usuario',data.usuLogin,0);
			userForm('text','Nome de Guerra',data.usuNGuerra,0);

			userForm('text','Nome Completo',data.usuNome,1);
			userForm('text','Identidade',data.usuIdt,1);

			userForm('select','Pst Grad',data.idPstGrad,2);
			userForm('select','Papel',[data.usuNGuerra],2);

			userForm('text','Senha','',3);

			$j.each(data.contas, function(i,conta){
				userForm('contas','Contas',conta,4);
				
			});
		});

		setTimeout(function(){$j('#table_users tr').eq(1).first().find('fieldset').append('<p><button> Salvar modificações</button></p>');
		$j( "button", "#table_users" ).button();},3000);
	};

	function userForm(campo,nome,valor,linha){
		var fieldset = $j('#table_users tr').eq(1).find('td').find('fieldset');
		var field_name = (nome.replace(/ {1}/gi,'_')).toLowerCase();
		var isSelect = false;
		var isCheckbox = false;

		if (campo == 'text')
			campo = ' <input type="text" name="'+(nome.replace(/ {1}/gi,'_')).toLowerCase()+'" value="'+valor+'">';

		if (campo == 'select'){
			campo = ' <select id="'+(nome.replace(/ {1}/gi,'_')).toLowerCase()+'">';
			isSelect = true;
		}

		if (campo == 'contas'){
			campo = '<input type="checkbox" id="'+valor.idCarteira+'" value="'+valor.idConta+'" checked '+(valor.isPrincipal == true ? 'disabled' : '')+'/>'+valor.cartAbr;
			isCheckbox = true;
		}	

		if (!isCheckbox){	
			if (fieldset.find('p').eq(linha).text() == ""){			
				fieldset.append('<p><label class="left_">'+nome+'*'+campo+'</label></p>');
			}else{
				fieldset.find('p').eq(linha).append('<label class="right_">'+nome+'*'+campo+'</label>');
			}
		}else{
			var principal = "";
			if (valor.isPrincipal == true)
				campo += ' (Principal)<br>';
			
			if (fieldset.find('p').eq(linha).text() == ""){
				fieldset.append('<p><b>Contas</b> [<a href="#wcontas" name="modal">adicionar</a>]<br>'+campo+'</p>');
				fieldset.find('p').eq(linha).css('margin-top', '30px');
				fieldset.find('p').eq(linha).css('background-color', '#E2E4FF');
			}else{
				fieldset.find('p').eq(linha).append(campo);
			}
		}
		
		
		
		if ((isSelect) && (field_name == 'pst_grad') ){
			pstgradJS.getAll({
				callback:function(pstgradList) {
				dwr.util.addOptions(field_name, pstgradList, "idPstGrad", "pstgradNome");

					setTimeout(function(){
						fieldset.find('p').eq(linha).first().first().find('option').each(function(){
							if ($j(this).val() == valor)
								$j(this).attr('selected','selected');
						});
					},1000);
		  		}
		  	});
		} else if ((isSelect)) {
			dwr.util.addOptions(field_name, valor);	
			dwr.util.addOptions(field_name, ['ADMIN', 'USER', 'PROTOCOLISTA']);
		} 
	}

	function removeAllUsers(all){
		if (all){
			S_USERS = "";
			TABLE_USERS = null;
			$j('#table_users').remove();
			return;
		}
		
		$j('#table_users tr').each(function(i){
			if ( (i != 0) )
				$j(this).remove();
		});
	};

	function removeTable(){
		$j('#conteudo').find('table').remove();
	}

	function showAllUsers(){

		table = '<table id="table_users" width="100%" class="table_users">'+
				'<tr><td colspan="10">Pesquisar: '+
				'<input type="text" id="pesquisar"></input></td>'+
				'</tr>';

		$j('#conteudo').append(table);
		$j("#table_users tr:first").addClass('table_titulo');	

		$j("#pesquisar").bind('keyup',function(){
			setTimeout(function(){search();},10);
		});
		
		$j.getJSON("buscaUsuarios.html", function(data){
			 $j.each(data.users, function(i,user){
				 $j('#table_users').append('<tr id="'+user[0]+'"><td width="40%" name="name_user">'+
							user[1]+'</td><td width="40%" name="login_user">'+user[2]+
							'</td><td width="20%">(<a href="'+user[0]+
							'" name="edit_user" class="link_red">Editar</a>)</td></tr>');
					
					S_USERS += user[0]+' | '+user[1]+' | '+user[2]+';';
					
					if( (i%2 == 0) && (i != 0))
						$j('#table_users tr:eq('+i+')').css("background-color", "#E2E4FF");
					 
					
			 });

		});

		//$j('#conteudo').append('</table>');	
		//$j("#table_users tr:odd").css("background-color", "#E2E4FF");
		
		
	};

	function search(){

		removeAllUsers(false);
		
		var search = $j('#pesquisar').val();
		var arUsers = S_USERS.split(';');
		 
		for (i=0; i<arUsers.length-1; i++){
			var sUser = arUsers[i];
			var user = sUser.split('|');

			if ( ((sUser.toLowerCase()).indexOf(search) != -1) )
				$j('#table_users').append('<tr id="'+user[0]+'"><td width="40%" name="name_user">'+
						user[1]+'</td><td width="40%" name="login_user">'+user[2]+
						'</td><td width="20%">(<a href="'+user[0]+
						'" name="edit_user" class="link_red">Editar</a>)</td></tr>');
		 }

		$j("#table_users tr:odd").css("background-color", "#E2E4FF");
		$j("#table_users tr:first").css("background-color", "#1E90FF");	
		

	};

	
});


function carregaCarteiras(){

	carteiraJS.getAll({
		callback:function(carteirasList) {
			dwr.util.addOptions('slContas', carteirasList, "idCarteira", "cartAbr");
			dwr.util.addOptions('ListaDE', carteirasList, "idCarteira", "cartAbr");
		}
	});
	
};


function atualizaContas(){
	
	js.direto.close_mask();
	var pContas = $j('#table_users tr').eq(1).first().find('fieldset').find('p').eq(4);
	
	//pContas.html('');

	pContas.find('input:checkbox').each(function(i){
		var id = $j(this).attr('id');
		if (i != 0){
			alert(id);
			$j('#ListaPARA option').each(function(){	
				var value = $j(this).val();
				alert(value);
				if(value != id){
					pContas.append('<br><br><input type="checkbox" value="'+value+'">'+$j(this).text());		
				}
				
				$j(this).remove();
			});
		}
	});
	
	var slPrincipal = $j('#slContas option:selected');

	pContas.append('<br><b>'+slPrincipal.text());

	
	
}








</script>

<!-- MODALS -->
<div id="boxes">

	<div id="wcontas" class="window">
		<table width="100%">
			<tr>
				<td colspan="3" style="width: 720px; text-align: center;" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Adicionar nova conta</td>
				<td style="width: 30px;"><a href="#" class="close" style="font-weight: bold">X</a></td>
			</tr>
			
			<tr>
				<td colspan="3" align="center">
					<b>Conta principal:</b> <select
						name="slContas" id="slContas" style="width: 200px; margin: 20px 0 20px 0;">
					</select>
				</td>
			</tr>
			
			<tr>
				<td style="width: 260px;">
					<fieldset><legend>&nbsp;<b>Todas as carteiras</b>&nbsp;</legend>
					 <select name=ListaDE id=ListaDE multiple size=11 style="width: 250px;" disabled
							ondblClick="mover(document.getElementById('ListaDE'), document.getElementById('ListaPARA')); return false;">
					 </select></fieldset>
				</td>
				
				<td style="text-align: center; width:50px;">
					<input
						type="button" value="Incluir >>" style="width: 90pt"
						id=btParaDireita
						onClick="mover(document.getElementById('ListaDE'), document.getElementById('ListaPARA')); return false;">
					<input type="button" value="<< Remover" style="width: 90pt"
						name=btExclui id=btParaEsquerda
						onClick="remover(document.getElementById('ListaPARA'), document.getElementById('ListaDE')); return false;">
				</td>
	
				<td style="width: 260px; text-align: center;">
						<fieldset><legend>&nbsp;<b>Carteiras adicionadas</b>&nbsp;</legend>
						<select name=ListaPARA id=ListaPARA size=11 style="width: 250px;"
							multiple disabled
							ondblClick="remover(document.getElementById('ListaPARA'), document.getElementById('ListaDE')); return false;">
					</select></fieldset>
				</td>
			</tr>
		</table>
		
		<p><button onclick="atualizaContas()">Salvar</button></p>
		</div>

  <!-- Mask para bloquear tela -->
  <div id="mask"></div>
</div>



<div id="palco">
	<div id="menu">
			<div id="accordion">
				<h3><a href="#">Usuários</a></h3>
				<div>
					<ul>
						<li><a href="usuarios.html" name="users" target="palco">Listar usuários</a></li>
						<li><a href="index.html" target="palco">Cadastro</a></li>
						<li>Edição</li>
						<li>Vincular carteira</li>
					</ul>
				</div>
				<h3><a href="#">Documentos</a></h3>
				<div>
					
				</div>
				<h3><a href="#">Carteiras</a></h3>
				<div>
					<ul>
						<li>Cadastro</li>
						<li>Edição</li>
						<li>Grupos</li>
					</ul>
				</div>
				<h3><a href="#">Outros</a></h3>
				<div>
					<ul>
						<li>Postro/Graduação</li>
						<li>Seção</li>
						<li>OM</li>
						<li>Função</li>
					</ul>
				</div>
			</div>
		</div>
	<div id="conteudo"></div>
</div>


</body>
</html>
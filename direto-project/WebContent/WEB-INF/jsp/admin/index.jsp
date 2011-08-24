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
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/contasJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/funcaoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/omJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/secaoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/gruposJS.js"></script>

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

fieldset p input[type="checkbox"] {
	max-width: 15px;
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

	/**
	*DRAGGABLES
	*/
	$j( "#waddcarteira" ).draggable();

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
	
	$j('a[name=showDocument]').click(function(e){
		e.preventDefault();
		showDocument();
		}
	);
	
	$j('a[name=addUser]').click(function(e){
		e.preventDefault();
		addUser();
		}
	);
	
	$j('a[name=logUsers]').click(function(e){
		e.preventDefault();
		showLogUsers();
		}
	);

	$j('a[name=edit_user]').live('click',function(e){
		e.preventDefault();
		var id = $j(this).attr('href');
		editUser(id);
	});
	
	function showLogUsers(){

		removeTable();
		
		table = '<table id="table_users" width="100%" class="table_users">'+
		'<tr><td colspan="10">'+
		'</td></tr>';

		$j('#conteudo').append(table);
		$j("#table_users tr:first").addClass('table_titulo');
				
		$j("#table_users tr:first").first().html('Usuários Logados no Sistema');
		
		<c:forEach items="${activeUsers}" var="uinfo">
			$j('#table_users').append('<tr><td><strong>${uinfo.key.usuNome} - ${uinfo.key.usuLogin}</strong> / Last Active: <strong>${uinfo.value}</strong></td></tr>');
		</c:forEach>
		
		$j("#table_users tr:odd").css("background-color", "#E2E4FF");
		$j('#table_users').append('<tr class="table_titulo"><td>Total de sessões abertas: ${fn:length(allUsersLogged)}</td></tr>');
		$j("#table_users tr:last td").css("border-bottom", "1px solid #000");
		$j("#table_users tr:last td").css("border-top", "1px solid #000");
		
	}
	
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
			$j("#table_users tr:first").first().html('Edição do usuário: '+data.usuLogin+' - <span id="idUser">'+id+'</span>');
			
			userForm('text','Login usuario',data.usuLogin,0);
			userForm('text','Nome de Guerra',data.usuNGuerra,0);

			userForm('text','Nome Completo',data.usuNome,1);
			userForm('text','Identidade',data.usuIdt,1);

			userForm('select','Pst Grad',data.idPstGrad,2);
			userForm('select','Papel',[data.usuPapel],2);

			userForm('text','Senha','',3);

			$j.each(data.contas, function(i,conta){
				userForm('contas','Contas',conta,4);
				
			});

			if (data.contas.length == 0){
				var fieldset = $j('#table_users tr').eq(1).find('td').find('fieldset');
				fieldset.append('<p><b>Contas</b> [<a href="#wcontas" name="modal">Adicionar</a>] | [<a href="#waddcarteira" name="modal">Cadastrar Carteira</a>]<br></p>');
				fieldset.find('p').eq(4).append('<div id="contas_atuais">Sem Conta Cadastrada!</div>');
				fieldset.find('p').eq(4).css('margin-top', '30px');
				fieldset.find('p').eq(4).css('background-color', '#E2E4FF');
			}	
		});

		setTimeout(function(){$j('#table_users tr').eq(1).first().find('fieldset').append('<p><button id="save_user"> Salvar modificações</button></p>');
		$j( "button", "#table_users" ).button();
		$j('#save_user').click(saveEditUser);

		},3000);
	};

	function addUser(){

		removeTable();
		
		table = '<table id="table_users" width="100%" class="table_users">'+
		'<tr><td colspan="10">'+
		'</td></tr><tr><td>';

		$j('#conteudo').append(table);
		$j("#table_users tr:first").addClass('table_titulo');
		$j('#table_users tr').eq(1).find('td').prepend('<fieldset>');
		
		$j("#table_users tr:first").first().html('Cadastro de novo usuário <span id="idUser" style="display:none;">0</span>');
			
		userForm('text','Login usuario','',0);
		userForm('text','Nome de Guerra','',0);

		userForm('text','Nome Completo','',1);
		userForm('text','Identidade','',1);

		userForm('select','Pst Grad','',2);
		userForm('select','Papel','',2);

		userForm('text','Senha','',3);

			
		var fieldset = $j('#table_users tr').eq(1).find('td').find('fieldset');
		fieldset.append('<p><b>Contas</b> [<a href="#wcontas" name="modal">Adicionar</a>] | [<a href="#waddcarteira" name="modal">Cadastrar Carteira</a>]<br></p>');
		fieldset.find('p').eq(4).append('<div id="contas_atuais">Adicione a(s) conta(s)</div>');
		fieldset.find('p').eq(4).css('margin-top', '30px');
		fieldset.find('p').eq(4).css('background-color', '#E2E4FF');
				
		
		setTimeout(function(){$j('#table_users tr').eq(1).first().find('fieldset').append('<p><button id="save_user">Cadastrar</button></p>');
		$j( "button", "#table_users" ).button();
		$j('#save_user').click(saveAddUser);

		},3000);

	};

	function saveAddUser(){
		var usuLogin = $j('#login_usuario').val();
		var usuNome = $j('#nome_completo').val();
		var idPstGrad = parseInt($j('#pst_grad option:selected').val());
		var usuPapel =  $j('#papel option:selected').val();
		var usuNGuerra = $j('#nome_de_guerra').val();
		var usuIdt = $j('#identidade').val();
		var usuSenha = $j('#senha').val();

		if (usuLogin == ""){
			alert('Preencha o campo login do usuário');
			return;
		}else if (usuNGuerra == ""){
			alert('Preencha o campo nome de guerra');
			return;
		}else if (usuSenha == ""){
			alert('Digite uma senha para o usuário');
			return;
		}else if (usuNome == ""){
			usuNome = '-';
		}else if (usuIdt == ""){
			usuIdt = 0;
		}

		usuarioJS.validateUser(usuLogin,{
				callback:function(exists){
					if (exists){
						alert('Já existe um usuário cadastrado com este login.\nFavor escolha outro.');
						return;
					}else{
						
						//alert('Usuario gravado');

						var idUsuario = parseInt($j('#idUser').text());
						usuarioJS.editUser(usuLogin, usuNGuerra, usuNome, usuPapel, usuSenha, usuIdt, idPstGrad, idUsuario,{
							callback:function(resultado) {
								alert("cadastrado: "+resultado);
								$j('#idUser').text(resultado);
								setTimeout(function(){saveEditUser();},200);	
							}
						});
					}
				}
		});

		
		
	};

	function saveEditUser(){

		//var usuario = null;
		var idUsuario = parseInt($j('#idUser').text());
		var usuLogin = $j('#login_usuario').val();
		var usuNome = $j('#nome_completo').val();
		var idPstGrad = parseInt($j('#pst_grad option:selected').val());
		var usuPapel =  $j('#papel option:selected').val();
		var usuNGuerra = $j('#nome_de_guerra').val();
		var usuIdt = $j('#identidade').val();
		var usuSenha = $j('#senha').val();

		if (usuLogin == ""){
			alert('Preencha o campo login do usuário');
			return false;
		}else if (usuNGuerra == ""){
			alert('Preencha o campo nome de guerra');
			return false;
		}else if (usuNome == ""){
			usuNome = '-';
		}else if (usuIdt == ""){
			usuIdt = 0;
		}

		//var pContas = $j('#table_users tr').eq(1).first().find('fieldset').find('p').eq(4);
		
		usuarioJS.editUser(usuLogin, usuNGuerra, usuNome, usuPapel, usuSenha, usuIdt, idPstGrad, idUsuario,{
			callback:function(resultado) {
				alert(resultado);

				var count = 0;
				
				$j('#contas_atuais').find('input:checkbox').each(function(i){
					
					var pkConta = parseInt($j(this).val());
					
					if ( (i == 0) && ($j(this).attr('checked') == '') ){
						var idCarteira = parseInt($j('#novas_contas').find('input:checkbox').first().val());
						if (isNaN(idCarteira)){
							contasJS.deleteAccount(pkConta);
						}else{
							contasJS.updateAccount(pkConta, idCarteira, true);
						}
							
						
					}else if ($j(this).attr('checked') == ''){
						contasJS.deleteAccount(pkConta);
					}

					count++;
				});

				$j('#novas_contas').find('input:checkbox').each(function(i){
					var idCarteira = parseInt($j(this).val());
					var isPrincipal = $j(this).attr('principal');
					
					if ( (count == 0) && (isPrincipal == 'true') ){
						contasJS.add(idUsuario, 1, idCarteira,1);
					}else if (isPrincipal != 'true'){
						contasJS.add(idUsuario, 1, idCarteira,0);
					}
					
				});
				
				
				setTimeout(function(){editUser(idUsuario);},1000);	
				
			}
						
		});

		
			

	};

	function userForm(campo,nome,valor,linha){
		var fieldset = $j('#table_users tr').eq(1).find('td').find('fieldset');
		var field_name = (nome.replace(/ {1}/gi,'_')).toLowerCase();
		var isSelect = false;
		var isCheckbox = false;

		if (campo == 'text')
			campo = ' <input type="text" id="'+(nome.replace(/ {1}/gi,'_')).toLowerCase()+'" value="'+valor+'">';

		if (campo == 'select'){
			campo = ' <select id="'+(nome.replace(/ {1}/gi,'_')).toLowerCase()+'">';
			isSelect = true;
		}

		if (campo == 'contas'){
			campo = '<input type="checkbox" id="'+valor.idCarteira+'" value="'+valor.idConta+'" checked '+(valor.isPrincipal == true ? 'disabled' : '')+'/>';
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
				fieldset.append('<p><b>Contas</b> [<a href="#wcontas" name="modal">Adicionar</a>] | [<a href="#waddcarteira" name="modal">Cadastrar Carteira</a>]<br></p>');
				fieldset.find('p').eq(linha).append('<div id="contas_atuais"><label>'+valor.cartAbr+'</label>'+campo+'</div>');
				fieldset.find('p').eq(linha).css('margin-top', '30px');
				fieldset.find('p').eq(linha).css('background-color', '#E2E4FF');
				fieldset.find('p').eq(linha).css('text-align', 'center');
			}else{
				$j('#contas_atuais').append('(<label>'+valor.cartAbr+'</label>'+campo+')');
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
	
	function showDocument(){

		removeTable();
		
		table = '<table id="table_users" width="100%" class="table_users">'+
		'<tr><td colspan="10">Digite o nr  do Protocolo: '+
		'<input type="text" id="nrProtocolo"></input></td>'+
		'</tr>';
		
		$j('#conteudo').append(table);
		$j("#table_users tr:first").addClass('table_titulo');
		
		$j("#nrProtocolo").bind('keyup',function(e){
			//setTimeout(function(){search();},10);
			var evento = (window.event ? e.keyCode : e.which);

			if(evento == 13) {
				setTimeout(function(){alert('doc encontrado');},10);
			}
			
		});
		
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

function carregaGrupos(){
	gruposJS.allGroups({
		callback:function(grupos) {
			//alert(grupos.length);
			//dwr.util.removeAllOptions('slGrupos');
			dwr.util.removeAllOptions('CarteirasListaDE');
			dwr.util.removeAllOptions('CarteirasListaPARA');
			dwr.util.addOptions('slGrupos', grupos, "id", "texto");


			carteiraJS.getAll({
				callback:function(carteirasList) {
					dwr.util.addOptions('CarteirasListaDE', carteirasList, "idCarteira", "cartAbr");

					setTimeout(function(){

						$j('#CarteirasListaDE option').each(function(i){
							$j(this).attr('add','true');
						});
				
					},100);

					
				}
			});
			
		}
	});

	
};

var GRUPOS_PARA_EXCLUIR;
var GRUPO_SELECIONADO;

//Chamado quando é escolhido um grupo. Lista todas as carteiras pertencentes ao mesmo.
function carregaCarteirasPorGrupo(){
	GRUPOS_PARA_EXCLUIR = "";
	var idNomeGrupo = $j("#slGrupos").val();
	gruposJS.carteirasByGroup(idNomeGrupo,{
		callback:function(carteirasList) {
			dwr.util.removeAllOptions('CarteirasListaPARA');
			dwr.util.addOptions('CarteirasListaPARA', carteirasList, "id", "texto");
		}
	});
}


function carregaCarteiras(){
	carteiraJS.getAll({
		callback:function(carteirasList) {
			dwr.util.removeAllOptions('slContas');
			dwr.util.removeAllOptions('ListaDE');
			dwr.util.removeAllOptions('ListaPARA');
			dwr.util.addOptions('slContas', carteirasList, "idCarteira", "cartAbr");
			dwr.util.addOptions('ListaDE', carteirasList, "idCarteira", "cartAbr");
		}
	});
};

function removerCarteiraGrupo(){

	var obj = $j('#CarteirasListaPARA option:selected');
	
	if(obj.attr('add')!='true')
		GRUPOS_PARA_EXCLUIR += obj.val()+",";
	
	obj.remove();
}

function atualizaGrupo(){

	GRUPO_SELECIONADO = $j('#slGrupos option:selected').val();

	var arGruposParaExcluir = GRUPOS_PARA_EXCLUIR.split(',');
	for (i=0; i<arGruposParaExcluir.length-1; i++){
		//alert('excluindo... '+parseInt(arGruposParaExcluir[i]));
		gruposJS.deleteCarteiraFromGroup(parseInt(arGruposParaExcluir[i]));
	}

	setTimeout(function(){
	
		$j('#CarteirasListaPARA option').each(function(i){
	
			if($j(this).attr('add')=='true'){
				//alert($j(this).text());
				gruposJS.addCarteiraInGroup(parseInt(GRUPO_SELECIONADO),parseInt($j(this).val()));
			}
		});

	},100);
	
	alert("Grupo atualizado com sucesso!");

}

function adicionarCarteira(){
	var objToAdd = $j('#CarteirasListaDE option:selected');
	var exists = false;

	$j('#CarteirasListaPARA option').each(function(i){
		if($j(this).text()==objToAdd.text())
			exists = true;
	});

	setTimeout(function(){
		if(!exists)
			$j('#CarteirasListaPARA').append($j(objToAdd).clone().text(objToAdd.text()+'(novo)'));
		else
			alert('Carteira já existe neste grupo.');
	},100);
}

function carregaFuncoes(){
	funcaoJS.getAll({
		callback:function(funcoesList) {
			dwr.util.removeAllOptions('slFuncao');
			dwr.util.addOptions('slFuncao', funcoesList, "idFuncao", "funcAbr");
		}
	});
};

function carregaSecoes(){
	secaoJS.getAll({
		callback:function(secoesList) {
			dwr.util.removeAllOptions('slSecao');
			dwr.util.addOptions('slSecao', secoesList, "idSecao", "secaoAbr");
		}
	});
};

function carregaOm(){
	omJS.getAll({
		callback:function(omList) {
			dwr.util.removeAllOptions('slOm');
			dwr.util.addOptions('slOm', omList, "idOM", "omAbr");
		}
	});
};

function atualizaContas(){

	if (checkContasCadastradas()) 
		return;
	
	js.direto.close_mask();
	var pContas = $j('#table_users tr').eq(1).first().find('fieldset').find('p').eq(4);

	//if ($j('#novas_contas').text() != '')
		$j('#novas_contas').remove();
	//alert($j('#novas_contas').text());
	
	pContas.append('<div id="novas_contas" style="margin-top: 20px;"><b>NOVAS ATUALIZAÇÕES</b></div>');

	var value = '';

	var count = 0;
	pContas.find('input:checkbox').each(function(i){
		var id = $j(this).attr('id');
	
		if (i == 0){
			var slPrincipal = $j('#slContas option:selected');
			value = slPrincipal.val();
			if ( id != value ){			
				$j('#novas_contas').append('<br><input type="checkbox" principal="true" disabled checked value="'+value+'">'+slPrincipal.text()+' (Principal)');
				$j(this).attr('checked','');
				$j(this).attr('principal','true');
			}	
		}else{
			var exists = false;

			$j('#ListaPARA option').each(function(){	
				value = $j(this).val();
				
				if(id == value){
					exists = true;
					$j(this).remove();
					return;
				}

				
			});

			if (!exists){
				$j(this).attr('checked','');
			}	
		}
		count++;
		
	});
	

	if (count == 0){
		var slPrincipal = $j('#slContas option:selected');
		value = slPrincipal.val();
		$j('#novas_contas').append('<br><input type="checkbox" principal="true" disabled checked value="'+value+'">'+slPrincipal.text()+' (Principal)');
	}
	
	
	$j('#ListaPARA option').each(function(){
		value = $j(this).val();
		$j('#novas_contas').append('<br><input type="checkbox" checked value="'+value+'">'+$j(this).text());
		$j(this).remove();
	});
	
}


function checkContasCadastradas(){
	var exists = false;
	
	var principalValue = $j('#slContas option:selected').val();

	$j('#ListaPARA option').each(function(){
		var listaParaValue = $j(this).val();

		if (listaParaValue == principalValue){
			alert('A conta selecionada como principal está selecionada\n\tcomo conta secundária.\nFavor remova-a primeiro da box Carteiras Adicionadas.');
			exists = true;
		}
	});

	return exists;
	
};

function saveCarteira(){

	if(confirm("Todos os dados estão corretos?"))
	{
		var idFuncao = parseInt($j('#slFuncao').val());
		var idSecao = parseInt($j('#slSecao').val());
		var idOM = parseInt($j('#slOm').val());
		
		var descricao = $j('#cartDesc').val();
		var abreviatura = $j('#cartAbr').val();
		
		if (descricao == ""){
			alert('Preencha o campo Descrição');
			return;
		}else if (abreviatura == ""){
			alert('Preencha o campo Abreviatura');
			return;
		}

		carteiraJS.save(descricao, abreviatura, idFuncao,
				idSecao, idOM);

		alert('Carteira Cadastrada');
	}
	
};

</script>


		<style type="text/css">
			  #waddcarteira fieldset { border:1px solid #000 }
			
			  #waddcarteira input {
			  	margin-bottom: 10px;
			  	width: 300px;
			  }
			  
			  #waddcarteira select {
			  	margin-bottom: 10px;
			  	width: 300px;
			  }
			  
			  #waddgrupo fieldset { border:1px solid #000 }
			
			  #waddgrupo input {
			  	margin-bottom: 10px;
			  	width: 300px;
			  }
			  
			  #waddgrupo select {
			  	margin-bottom: 10px;
			  	width: 300px;
			  }
		</style>

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
						name="slContas" id="slContas" style="width: 200px; margin: 20px 0 20px 0;" onchange="checkContasCadastradas()">
						
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
		
		
		<div id="wgrupos" class="window">
		<table width="100%">
			<tr>
				<td colspan="3" style="width: 720px; text-align: center;" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Gerenciar Grupos</td>
				<td style="width: 30px;"><a href="#" class="close" style="font-weight: bold">X</a></td>
			</tr>
			
			<tr>
				<td colspan="3" align="center">
					<b>Grupos:</b> <select
						name="slGrupos" id="slGrupos" style="width: 200px; margin: 20px 0 20px 0;" onchange="carregaCarteirasPorGrupo()">
						<option value="0">--Selecione um Grupo--</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td style="width: 260px;">
					<fieldset><legend>&nbsp;<b>Todas as carteiras</b>&nbsp;</legend>
					 <select name=CarteirasListaDE id=CarteirasListaDE multiple size=11 style="width: 250px;" 
							ondblClick="adicionarCarteira()">
					 </select></fieldset>
				</td>
				
				<td style="text-align: center; width:50px;">
					<input type="button" value="Novo Grupo" style="width: 90pt"	id=btNovoGrupo>
					<input type="button" value="Editar Grupo" style="width: 90pt" id=btEditarGrupo>
				</td>
	
				<td style="width: 260px; text-align: center;">
						<fieldset><legend>&nbsp;<b>Carteiras do Grupo</b>&nbsp;</legend>
						<select name=CarteirasListaPARA id=CarteirasListaPARA size=11 style="width: 250px;"
							multiple ondblClick="removerCarteiraGrupo()">
					</select></fieldset>
				</td>
			</tr>
		</table>
		
		<p><button onclick="atualizaGrupo()">Atualizar Grupo</button></p>
		</div>
		
	<div id="waddcarteira" class="window">
	<table width="100%">
			<tr>
				<td colspan="3" style="width: 720px; text-align: center;" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Adicionar nova carteira</td>
				<td style="width: 30px;"><a href="#" class="close" style="font-weight: bold">X</a></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<fieldset>
  						
  							
    						<label for="cartDesc">Descrição:</label>
    							<input type="text" name="cartDesc" id="cartDesc" maxlength="60"/>
    						<br>
    						
    						<label for="cartAbr">Abreviatura:</label>
    							<input type="text" name="cartAbr" id="cartAbr" maxlength="30"/>
    						<br>
    						
    						<label for="slFuncao">Função:</label>
    							<select name="slFuncao" id="slFuncao">
    							</select>
    						<br>
    						
    						<label for="slSecao">Seção:</label>
    							<select name="slSecao" id="slSecao">
    							</select>
    						<br>
    						
    						<label for="slOm">OM:</label>
    							<select name="slOm" id="slOm">
    							</select>
    							
    						<input type="button" value="Salvar" style="width: 50px;" onclick="saveCarteira()" />		
    						
  					</fieldset>
				</td>
			</tr>
	</table>		
	</div>
	
	<div id="waddgrupo" class="window">
	<table width="100%">
			<tr>
				<td colspan="3" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Adicionar novo grupo</td>
				<td style="width: 30px;"><a href="#" class="close" style="font-weight: bold">X</a></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<fieldset>
  						
  							
    						<label for="grupoDesc">Descrição:</label>
    							<input type="text" name="grupoDesc" id="grupoDesc" maxlength="60"/>
    						<br>
    						
    						<label for="grupoAbr">Abreviatura:</label>
    							<input type="text" name="grupoAbr" id="grupoAbr" maxlength="30"/>
    						<br>
    						
    						<input type="button" value="Salvar" style="width: 50px;" onclick="saveGrupo()" />		
    						
  					</fieldset>
				</td>
			</tr>
	</table>		
	
	
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
						<li><a href="index.html" name="addUser" target="palco">Cadastro</a></li>
						<li>Edição</li>
						<li>Vincular carteira</li>
						<li><a href="index.html" name="logUsers" target="palco">Usuários Logados</a></li>
					</ul>
				</div>
				<h3><a href="#">Documentos</a></h3>
				<div>
					<ul>
						<li><a href="#" name="showDocument">Editar doc</a></li>
					</ul>
				</div>
				<h3><a href="#">Carteiras</a></h3>
				<div>
					<ul>
						<li><a href="#waddcarteira" name="modal">Cadastro</a></li>
						<li>Edição</li>
						<li>Grupos</li>
					</ul>
				</div>
				<h3><a href="#">Grupos</a></h3>
				<div>
					<ul>
						<li><a href="#wgrupos" name="modal">Gerenciar</a></li>
						<li><a href="#waddgrupo" name="modal">Cadastro</a></li>
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
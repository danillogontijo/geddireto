<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/chatJS.js"></script>

<style>

#chat {
	font-size: 80%;
	position: relative;
	margin-top: 30px;
	max-height: 230px; 
	width: 150px; 
	border: 1px solid #000; 
	left: 330px;
	
	-moz-box-shadow: 0 0 5px 5px #888;
	-webkit-box-shadow: 0 0 5px 5px #888;
	
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
}
#chat div {position: relative; padding-bottom: 2px;}
#div_new {border-bottom: 1px solid #000}
#div_new input {margin-top: 2px; max-width: 130px;}

#usuariosON {
	font-weight: normal; 
	font-size: 85%; 
	margin-top: 5px;
	max-width: 130px;
}

#console_chat {
	border-bottom: 1px solid #000;
	min-height: 100px;  
	overflow: auto; 
	position: relative; 
}

.user0 {background-color: #FFA07A;}
.user1 {background-color: #90EE90;}
.user2 {background-color: yellow;}

.offline {font-style: italic; color: #666;}
</style>

<script type="text/javascript">

var PARA = 0;
var USER = "${usuario.usuNGuerra}";
var ID_USER = ${usuario.idUsuario};
var USER_IS_TYPING = false;
var USER_IS_ACTIVE = true;
var TIMER;
var SIZE_MESSAGES = 0;

$j(function(){
	start();
});

function start(){
	
	startTimer();
	
	chatJS.start(this.ID_USER,this.USER,{
		callback:function() {
			usuariosON();
		}
	});
	
	
	$j('#console_chat').html("Entrou: "+this.USER);
	
	setTimeout("checkNewMessage();",3000);
	setTimeout("changeStatus(1);",5000);
	
}

function usuariosON(){
	chatJS.getAllUsersOn({
		callback:function(allUsers) {
			montaListaUsuarios(allUsers);	
			setTimeout("checkUsers();",3000);
		}
	});
}


function montaListaUsuarios(allUsers){
	
	dwr.util.removeAllOptions('usuariosON');
	$j('#usuariosON').append('<option>Selecione um usuário</option>');
	
	for (var i in allUsers){
		var user = allUsers[i];
		var userId = user.idUser;
		var userStatus = user.statusUser;
		
		if (this.ID_USER != userId){
			var selectedUser = '';
			var disabledUser = '';
			var status = ' - ON';

			if (this.PARA == userId)
				selectedUser = 'selected';
			if (userStatus == 0){
				disabledUser = 'disabled ';
				status = ' - OFF';
			}else if (userStatus == 2){
				status = ' - INATIVO';
			}
			
			var oOption = '<option value="'+userId+'" class="user'+user.statusUser
				+'" '+disabledUser+selectedUser+'>'
				+user.nameUser+status+'</option>';
			
			$j('#usuariosON').append(oOption)
			
		}	
	}
}

function checkUsers(){
	chatJS.checkUsers({
		callback:function(allUsers) {
			//dwr.util.removeAllOptions('usuariosON');
			//dwr.util.addOptions('usuariosON', allUsers, 'idUser', 'nameUser');
			if (allUsers != null){
				montaListaUsuarios(allUsers);
				setTimeout("checkUsers();",3000);
			}else{
				sessionExpired();	
			}	
		}
	});
}

function sessionExpired(){
	$j('#new').attr('readonly','readonly');
	$j('#status').html('Sua sessão expirou! <a href="reload()">Entre novamente</a>');
}

function checkNewMessage(){
	
	chatJS.check(this.ID_USER,{
		callback:function(listMsgCallback) {
			if(listMsgCallback != null){
				var isOffline = false;
				if (listMsgCallback.length > 1)
					isOffline = true;
				for (var i in listMsgCallback){
					var newMsgCallback = listMsgCallback[i];
					
					SIZE_MESSAGES++;
					
					var msgRec = newMsgCallback.HTMLCode;
					var from = newMsgCallback.from.nameUser;
					var to = newMsgCallback.to.nameUser;
	
					var classStatus = 'online';
					
					if (isOffline){
						from += ' (Offline)';
						classStatus = 'offline';
					}
					
					var msgHTML = '<p id="'+this.SIZE_MESSAGES+'" class="'+classStatus+'">'
							+from+' () diz:<br>'+msgRec+'</p>';
					
					showMessage(msgHTML);
						
					setTimeout("checkNewMessage();",300);
				}
			}else{
				alert("Sua sessão expirou!");
			}	
		}
	});	
		
}

function sendNewMessage(){
	if (this.PARA == 0){
		alert("Destinatario nao selecionado!");
		return;
	}	
	var msgTxt = $j('#new').val();
	chatJS.send(this.PARA,this.ID_USER,msgTxt);
			SIZE_MESSAGES++;
			
			var msgHTML = '<p id="'+SIZE_MESSAGES+'" style="display:none;"><b>Eu:<b><br>'
				+msgTxt+'</p>';
			
			showMessage(msgHTML);
}

function showMessage(msgHTML){
	var divConsole = $j('#console_chat');
	divConsole.append(msgHTML);
	
	var pMsg = $j('#console_chat p[id='+SIZE_MESSAGES+']');
	pMsg.fadeIn('slow');
	
	divConsole.scrollTop(divConsole.scrollTop()+pMsg.offset().top);
}

function doTimer(){
	this.TIMER = setTimeout("changeStatus(2)",(30*1000));
}

function clearTimer(){
	this.USER_IS_TYPING = true;
	clearTimeout(this.TIMER);
}

function startTimer(){
	this.USER_IS_TYPING = false;
	doTimer();
}

function isTyping(b){
	if (b)
		clearTimer();
	startTimer();
}

function teclaEnter(e){
	isTyping(true);
	
	if(!this.USER_IS_ACTIVE)
		changeStatus(1);
	
	var evento = (window.event ? e.keyCode : e.which);

	if(evento == 13) 
		sendNewMessage();
}

function mudaTo(obj){
	this.PARA = $j(obj).val();
}

function changeStatus(status){
	chatJS.changeUserStatus(status);
	USER_IS_ACTIVE = (status == 1 ? true : false);
	$j('#status').text((USER_IS_ACTIVE ? 'Você está ON' : 'Você está inativo'));
}

</script>

<div id="chat">
	<div id="console_chat" style="">
	
	
	</div>
	<div id="div_new">
		<input type="text" id="new" onkeypress="teclaEnter(event)"></input>
	</div>
	<div>
		<select id="usuariosON" name="usuariosON" onchange="mudaTo(this)"></select>
	</div>
	<div><span id="status">Você está ON</span></div>
</div>	

</div>
	
</div>
    
	
<div id="table" style="position: relative; width: 1002px; top: 120px; left: 0px; margin-top: 0px; text-align: center; z-index: -1;">
	<font class="rodape" style="left: 167px; font-weight: bold">
			© 2011 - Direto - Gerenciador Eletrônico de Documentos<br>
						Ver. 3.0
	</font>
</div>

</body>
</html>
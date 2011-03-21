function stop(e){
	if(e != null)
		e.preventDefault();
	changeStatus(0);
	showInactive();
}


function start(e){
	if(e != null)
		e.preventDefault();
	
	chatJS.start(this.ID_USER,this.USER,{
		callback:function(initialStatus) {
			if(initialStatus != -1){
				showOnline();
				usuariosON();
				
				if(initialStatus != 0 || e != null){
					$j('#div_status').html('Aguarde...');
					
					if(initialStatus == 1)					
						startTimer();
				
					setTimeout("messagesSession();",2800);
					setTimeout("checkNewMessage();",2900);
					
					
						setTimeout("changeStatus(1);",3000);
					
					setTimeout("checkUsers();",3100);
					$j('#console_chat').find('div').text('Entrou: '+USER);
					$j('#console_chat').find('div').addClass('welcome border_radius');
				
				}else{
					showInactive();
					setTimeout("messagesSession();",2800);
				}
			}	
		}
	});
		
	
}

function hideOffline(){
	$j('#console_chat').hide('blind',1000);
	$j('#div_usuarios').hide();
	$j('#div_new').hide();
}

function showOnline(){
	$j('#console_chat').show('blind',500);
	$j('#console_chat').css('background-color', '#fff');
	$j('#div_usuarios').fadeIn('slow');
	$j('#div_new').fadeIn('slow');
	$j('#new').removeAttr('disabled');
}

function showInactive(){
	$j('#console_chat').css('background-color', '#E8E8E8');
	$j('#new').attr('disabled','disabled');
}


function usuariosON(){
	chatJS.getAllUsersOn({
		callback:function(allUsers) {
			montaListaUsuarios(allUsers);	
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
				//disabledUser = 'disabled ';
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
	$j('#div_status').find('span').html('Sua sessão expirou! <a href="reload()">Entre novamente</a>');
}

function messagesSession(){
	chatJS.messagesSession({
		callback:function(listMsgCallback) {
			if(listMsgCallback != null)
				iteratorMessages(listMsgCallback,true);
		}
	});	
}

function iteratorMessages(listMsgCallback,isSession){
	var pClass = 'messageChat';
	var isOffline = false;
	
	if (isSession) {
		pClass += ' session';
	}else if (listMsgCallback.length > 1){
			isOffline = true;
			pClass += ' offline';
	}
	
	for (var i in listMsgCallback){
		var newMsgCallback = listMsgCallback[i];
		
		SIZE_MESSAGES++;
		
		var msgRec = newMsgCallback.HTMLCode;
		var from = newMsgCallback.from.nameUser;
		//var to = newMsgCallback.to.nameUser;
		var fromIdUser = newMsgCallback.from.idUser;
		from += ' diz:';
		if (fromIdUser == ID_USER)
			from = "Eu:";
			
		if (isOffline){
			from += ' (Offline)';
		}
		
		var msgHTML = '<p id="'+this.SIZE_MESSAGES+'" class="'+pClass+'">'
				+from+'<br>'+msgRec+'</p>';
		
		showMessage(msgHTML);
	}
}

function checkNewMessage(){
	
	chatJS.checkNewMessage(this.ID_USER,{
		callback:function(listMsgCallback) {
			if(listMsgCallback != null){
				iteratorMessages(listMsgCallback,false);			
				setTimeout("checkNewMessage();",300);
				
			}else{
				alert("\t\tVocê ficou offline!\nAs mensagens que os usuários lhe enviarem\nserão mostradas da próxima vez que entrar.");
			}	
		}
	});	
		
}

function sendNewMessage(){
	if (this.PARA == 0){
		alert("Nenhum destinatario selecionado!");
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
	this.TIMER = setTimeout("changeStatus(2)",(TIME_TO_INACTIVE*60*1000));
}

function clearTimer(isTyping,isUserActive){
	this.USER_IS_TYPING = isTyping;
	this.USER_IS_ACTIVE = isUserActive;
	clearTimeout(TIMER);
}

function startTimer(){
	this.USER_IS_TYPING = false;
	this.USER_IS_ACTIVE = true;
	doTimer();
}

function isTyping(b){
	if (b)
		clearTimer(b,true);
	startTimer();
}

function teclaEnter(e){
	if(!USER_IS_ACTIVE)
		changeStatus(1);
	
	isTyping(true);
	
	var evento = (window.event ? e.keyCode : e.which);

	if(evento == 13) 
		sendNewMessage();
}

function mudaTo(obj){
	this.PARA = $j(obj).val();
}

function checkToUser(){
	if (PARA != 0){
		chatJS.checkToUser(PARA,{
			callback:function(statusCallback) {
				if(statusCallback == 0){
					usuariosON();
					var statusHTML = $j('#div_status').html();
					$j('#div_status').html('<span>Atenção: destinatário offline!</span>');
					setTimeout(function(){$j('#div_status').html(statusHTML);},5000);
					$j('#div_status').find('span').addClass('alert');
				}	
			}
		});
	}	
}

function changeStatus(status){
	
	chatJS.changeUserStatus(status);
	
	if (status == 0){
		$j('#div_status').html('<span>OFF-LINE</></span> (<a href="#" id="stayOn" onclick="start(event)">Online</a>)');
		$j('#div_status').find('span').addClass('offline');
		$j('#console_chat').find('div').text('Você saiu!');
		$j('#console_chat').find('div').css('background-color', '#FF8C69');
		clearTimer(false,false);
		return;
	}
	
	(status == 2 ? clearTimer(false,false) : startTimer(false,true));
	
	var logoff = ' (<a href="#" id="stayOff" onclick="stop(event)">Offline</a>)';
	var status = 'Você está <span>'+(USER_IS_ACTIVE ? 'ON' : 'INATIVO')
					+'</span>'+logoff;
	$j('#div_status').html(status);
	$j('#div_status').find('span').addClass((USER_IS_ACTIVE ? 'online' : 'inactive'));
	
}

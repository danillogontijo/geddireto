function ChatDiretoAPI (userName, userID) {

	
	var PARA = 0,
		USER = userName,
		ID_USER = userID;
	
	var USER_IS_TYPING = false,
		USER_IS_ACTIVE = false,
		TIMER = null,
		SIZE_MESSAGES = 0,
		TIME_TO_INACTIVE = 5, //em minutos
		STATUS = 2,
		DISABLE_SOUND = true;
	
	var listUserSearch = new Array();
	
	
	this.stop = function(e){
		if(e != null)
			e.preventDefault();
		changeStatus(0);
		showInactive();
	};
	
	this.activeTimer = function(){
		doTimer();
	};
	
	this.search = function(){
		
		chatJS.getAllUsersOn({
			callback:function(allUsers) {
				montaListaUsuarios(allUsers);
				
				var search = $j('#search').val();
				search = search.replace(/^\s+/,'').replace(/\s+$/,''); // trim 
				var newList = new Array();
				
				$j('#usuariosON option').each(function(i){
					
					
					
					var id = $j(this).val();
					var name = ($j(this).text()).toLowerCase();
					
					
					
					if (name.indexOf(' - inativo') != -1){
						name = name.replace(' - inativo','');
					}else if (name.indexOf(' - off') != -1) {
						name = name.replace(' - off','');
					}else if (name.indexOf(' - on') != -1){
						name = name.replace(' - on','');
					}
					
					if (name.indexOf(search) == -1){
						$j(this).remove();
					}else{
						$j(this).attr('selected','selected');
					}
					
				});
			}
		});
		
	};	
	
	this.searchUser = function(){
		clearTimer(USER_IS_TYPING,USER_IS_ACTIVE);
		$j('#new').hide();
		$j('#div_new').append('<input type="text" id="search" value="Pesquisar" onkeypress="ChatDiretoAPI.search()" />');
		$j('#search').live('focus',function(){$j(this).val('');$j(this).css('font-style', 'normal');});
		$j('#search').blur(function(){
			PARA = $j('#usuariosON option:selected').val();
			//alert(PARA+' - blur');
			});
	};
	
	var welcomeMessage = function(status){
		//Mensagem que o usuario entrou
		/*$j('#console_chat').html('');
		$j('#console_chat').prepend('<div>Entrou: '+USER+'</div>');
		$j('#console_chat').find('div').addClass('welcome border_radius');*/
		
		var msgWelcome = 'Você entrou';
		
		if(status == 0)
			msgWelcome = 'Offline!';
		
		$j('#welcome').html(msgWelcome);
		$j('#welcome').addClass('welcome border_radius');
		
		if(status == 0){
			$j('#welcome').css('background-color', '#FF8C69');
		}else if (status == 1){
			$j('#welcome').css('background-color', '#90EE90');
		}else{
			$j('#welcome').css('background-color', 'yellow');
		}

	};

	var initial = function (status){
		showOnline();
		usuariosON();
		$j('#div_status').html('Aguarde...');
		setTimeout(function(){messagesSession();},2800); //Mensagens da sessao 
		messageStatus(status); //Mostra em qual estado o user se encontra no status
		setTimeout(function(){checkUsers();},3100); //Ativar o checador de users
		
		welcomeMessage(status);
		setTimeout(function(){DISABLE_SOUND=false;},5000);
		
		if(status == 0){
			showInactive();
			setTimeout(function(){messagesSession();},2800);
		}else if (status == 1){
			setTimeout(function(){checkNewMessage();},2900); //Ativa checador de msgs somente se o user != 0
			startTimer(); // So inicia o timer se user == 1
		}else{
			setTimeout(function(){checkNewMessage();},2900); //Ativa checador de msgs somente se o user != 0
		}
	};
	
	this.changeStatusInChat = function(e,status){
		var isRequest = (e == null ? true : false);
		if(!isRequest)
			e.preventDefault();
		changeStatus(status);
		if (status == 1){
			showOnline();
			setTimeout(function(){checkNewMessage();},2900); //Ativa checador de msgs somente se o user != 0
		}	 
		
	};
	
	var changeStatus = function (status){
		chatJS.changeUserStatus(status);
		messageStatus(status);
		welcomeMessage(status);
		//setTimeout(function(){messagesSession();},100); //Mensagens da sessao
	};
	
	var messageStatus = function (status){
		
		if (status == 0){
			$j('#div_status').html('<span>OFF-LINE</></span> (<a href="#" id="stayOn" onclick="ChatDiretoAPI.changeStatusInChat(event,1)">Online</a>)');
			$j('#div_status').find('span').addClass('offline');
			$j('#console_chat').find('div').text('Você saiu!');
			$j('#console_chat').find('div').css('background-color', '#FF8C69');
			clearTimer(false,false);
			return;
		}
		
		(status == 2 ? clearTimer(false,false) : startTimer(false,true));
			
		//função para colocar o usuario offline após o dobro de tempo de inatividade
		/*if(status == 2){
			startTimer();
			USER_IS_ACTIVE = false;
			STATUS = 0;
		}else if(status == 1) {
			STATUS = 2;
			startTimer();
		}*/
		
		var logoff = ' (<a href="#" id="stayOff" onclick="ChatDiretoAPI.changeStatusInChat(event,0)">Offline</a>)';
		var status = 'Você está <span>'+(USER_IS_ACTIVE ? 'ON' : 'INATIVO')
						+'</span>'+logoff;
		$j('#div_status').html(status);
		$j('#div_status').find('span').addClass((USER_IS_ACTIVE ? 'online' : 'inactive'));
		
		
	};

	this.start = function start(e){
		var isRequest = (e == null ? true : false);
		if(!isRequest)
			e.preventDefault();
		
		chatJS.start(ID_USER,USER,{
			callback:function(initialStatus) {
			
				if(initialStatus == -1){
					hideOffline(); //mostra minimizado se usuario nao existia na lista
					$j('#topo').hide(); 
				} else {
					$j('#topo').show();
					initial(initialStatus);			
				}
				
			}
		});
			
		
	};
	
	this.showMinimized = function(){
		hideOffline();
	};
	
	this.showMaximized = function(){
		showOnline();
	};

	var hideOffline = function(){
		$j('#console_chat').hide('blind',1000);
		$j('#div_usuarios').hide();
		$j('#div_new').hide();
	};
	
	var showOnline = function (){
		$j('#console_chat').show('blind',500);
		$j('#console_chat').css('background-color', '#fff');
		$j('#div_usuarios').fadeIn('slow');
		$j('#div_new').fadeIn('slow');
		$j('#new').removeAttr('disabled');
	};
	
	var showInactive = function (){
		$j('#console_chat').css('background-color', '#E8E8E8');
		$j('#new').attr('disabled','disabled');
	};
	
	
	var usuariosON = function (){
		chatJS.getAllUsersOn({
			callback:function(allUsers) {
				montaListaUsuarios(allUsers);	
			}
		});
	};
	
	
	var montaListaUsuarios = function(allUsers){
		
		dwr.util.removeAllOptions('usuariosON');
		$j('#usuariosON').append('<option>Selecione um usuário</option>');
		
		for (var i in allUsers){
			//alert(allUsers.length);
			var user = allUsers[i];
			var userId = user.idUser;
			var userStatus = user.statusUser;
			
			if (ID_USER != userId){
				var selectedUser = '';
				var disabledUser = '';
				var status = ' - ON';
	
				if (PARA == userId)
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
				
				$j('#usuariosON').append(oOption);
				
			}	
		}
	};
	
	var checkUsers = function (){
		chatJS.checkUsers({
			callback:function(allUsers) {
				//dwr.util.removeAllOptions('usuariosON');
				//dwr.util.addOptions('usuariosON', allUsers, 'idUser', 'nameUser');
				if (allUsers != null){
					montaListaUsuarios(allUsers);
					setTimeout(function(){checkUsers();},300);
				}else{
					sessionExpired();	
				}	
			}
		});
	};
	
	var sessionExpired = function (){
		$j('#new').attr('readonly','readonly');
		$j('#div_status').find('span').html('Sua sessão expirou! <a href="reload()">Entre novamente</a>');
	};
	
	var messagesSession = function (){
		chatJS.messagesSession({
			callback:function(listMsgCallback) {
				if(listMsgCallback != null)
					iteratorMessages(listMsgCallback,true);
			}
		});	
	};
	
	var iteratorMessages = function (listMsgCallback,isSession){
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
			var to = newMsgCallback.to.nameUser;
			var fromIdUser = newMsgCallback.from.idUser;
			
			if (fromIdUser == ID_USER)
				from = 'Eu para ('+to+')';
				
			if (isOffline){
				from += ' disse: (Offline)';
			}else if (isSession){
				from += ' disse:';	
			}else{
				from += ' diz:';
			}
			
			var msgHTML = '<p id="'+SIZE_MESSAGES+'" class="'+pClass+'">'
					+from+'<br>'+msgRec+'</p>';
			
			showMessage(msgHTML);
			if(!DISABLE_SOUND)
				alertBeepMessage('beepchat');
		}
	};
	
	var alertBeepMessage = function (soundobj) {
		if(!isIE()){
			var thissound=document.getElementById(soundobj);
			thissound.play();
		}else{
		// obj = document.embeds[soundobj];
		//if(obj.Play) obj.Play();
		// return true;
			 var thissound=document.getElementById(soundobj);
			 thissound.Play();
		}
	 };

	var isIE = function(){
		if(document.all)
			return true;
		return false;
	};
	
	var checkNewMessage = function (){
		
		chatJS.checkNewMessage(ID_USER,{
			callback:function(listMsgCallback) {
				if(listMsgCallback != null){
					iteratorMessages(listMsgCallback,false);			
					setTimeout(function(){checkNewMessage();},300);
				}else{
					alert("\t\tVocê ficou offline!\nAs mensagens que os usuários lhe enviarem\nserão mostradas da próxima vez que entrar.");
				}	
			}
		});	
			
	};
	
	var sendNewMessage = function (){
		if (PARA == 0){
			alert("Nenhum destinatario selecionado!");
			return;
		}	
		//alert(PARA);
		var msgTxt = $j('#new').val();
		chatJS.send(PARA,ID_USER,msgTxt);
				SIZE_MESSAGES++;
				
				var nameTo =$j('#usuariosON option:selected').text();
				
				if (nameTo.indexOf(' - INATIVO') != -1){
					nameTo = nameTo.replace(' - INATIVO','');
				}else if (nameTo.indexOf(' - OFF') != -1) {
					nameTo = nameTo.replace(' - OFF','');
				}else if (nameTo.indexOf(' - ON') != -1){
					nameTo = nameTo.replace(' - ON','');
				} 
				
				var msgHTML = '<p id="'+SIZE_MESSAGES+'" style="display:none;"><b>Eu para ('+nameTo+')<b><br>'
					+msgTxt+'</p>';
				
				showMessage(msgHTML);
				
				$j('#new').val('');
	};
	
	var showMessage = function (msgHTML){
		var divConsole = $j('#console_chat');
		divConsole.append(msgHTML);
		
		var pMsg = $j('#console_chat p[id='+SIZE_MESSAGES+']');
		pMsg.fadeIn('slow');
		
		divConsole.scrollTop(divConsole.scrollTop()+pMsg.offset().top);
	};
	
	var doTimer = function (){
		TIMER = setTimeout(function(){changeStatus(STATUS);},(TIME_TO_INACTIVE*60*1000));
	};
	
	var clearTimer = function (isTyping,isUserActive){
		USER_IS_TYPING = isTyping;
		USER_IS_ACTIVE = isUserActive;
		clearTimeout(TIMER);
	};
	
	var startTimer = function(){
		USER_IS_TYPING = false;
		USER_IS_ACTIVE = true;
		doTimer();
	};
	
	var isTyping = function (b){
		if (b)
			clearTimer(b,true);
		startTimer();
	};
	
	this.teclaEnter = function (e){
		if(!USER_IS_ACTIVE)
			changeStatus(1);
		
		isTyping(true);
		
		var evento = (window.event ? e.keyCode : e.which);
	
		if(evento == 13){ 
			sendNewMessage();
		}	
	};
	
	this.mudaTo = function (obj){
		PARA = $j(obj).val();
	};
	
	this.checkToUser = function (){
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
	};
	
	
}

dwr.engine.setErrorHandler(errh);
//dwr.engine.setWarningHandler(null);

function errh(msg, exc) {
	 alert("O erro é: " + msg + " - Error Details: " + dwr.util.toDescriptiveString(exc, 2));
	
	/*
	 * Tratamento de execção quando DWR request é enviado deopois que deu timeout na sessao
	 */
	try {
	    if (dwr && dwr.engine) {
	        dwr.engine.setTextHtmlHandler(function() {
					alert('session expirou');
		           // document.location = '/direto-project/login.html';
	        });
	    }   
	 } catch(err) {} 
		 
}


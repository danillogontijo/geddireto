<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/chatJS.js"></script>

<script type="text/javascript">

var isSendNewMessage = false;
var userChat = null;
var userChatTo = null;
var message = null;
var PARA = "DANILLO";

$j(function(){
	start();
});

function start(){

	chatJS.getUserChat("${usuario.usuNGuerra}",{
		callback:function(userChat) {
			this.userChat = userChat;
			$j('#console_chat').html("Entrou: "+userChat.nameUser);

			chatJS.getMessage(userChat,{
				callback:function(message) {
					this.message = message;
				}
			});
								
		}
	});

}


setTimeout("checkNewMessage();",3000);

function checkNewMessage(){
	
	chatJS.checkNewMessageFromUser(this.userChat,{
		callback:function(userChatWithNewMessage) {
			this.userChat = userChatWithNewMessage;

			if (this.userChat.messages != null){
				var list = this.userChat.messages;
				
				for (var prop in list){
					var propvalue = list[prop];
					var msgRec = propvalue.HTMLCode;
					var from = propvalue.from.nameUser;
					var to = propvalue.to.nameUser;

					var msgTxt = "<p>"+from+" diz:<br>"+msgRec+"</p>";
					
					$j('#console_chat').append(msgTxt);			
				}

				this.userChat.messages = null;

			}

			if (!this.isSendNewMessage)
				setTimeout("checkNewMessage();",10000);
		}
	});	
		
}

function sendNewMessage(){
	this.isSendNewMessage = true;
	var msgTxt = $j('#new').val();
	chatJS.getUserChat(this.PARA,{
		callback:function(userChat) {
			this.userChatTo = userChat;
			this.message.to = this.userChatTo;
			this.message.HTMLCode = msgTxt;
			//$j('#console_chat').text("enviando... "+msg.HTMLCode);
			chatJS.sendNewMessage(this.message);
			//alert(this.message.to.nameUser+this.message.HTMLCode);
			this.isSendNewMessage = false;

			var showTxt = "<p><b>Eu:<b> <br>"+msgTxt+"</p>";
			
			$j('#console_chat').append(showTxt);
			
			setTimeout("checkNewMessage();",300);
		}
	});
	
}

function teclaEnter(e){
	
	var evento = (window.event ? e.keyCode : e.which);

	if(evento == 13) 
		sendNewMessage();
}

function mudaTo(obj){
	this.PARA = obj.value;
	alert(this.PARA);
}

</script>

<div id="console_chat" style="max-height: 200px; min-height: 200px; max-width: 150px; border: 1px solid #000; overflow: auto; position: relative; left: 330px; font-size: 80%;">

</div>
	
<input type="text" id="new" onkeypress="teclaEnter(event)"></input>

<input type="text" id="para" onblur="mudaTo(this)"></input>

</body>
</html>
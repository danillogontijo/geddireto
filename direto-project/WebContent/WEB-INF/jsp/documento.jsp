<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/anotacaoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/despachoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/notificacaoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/anexoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/segurancaJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/historicoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/custom/jquery.limit-1.2.source.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/auto.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.fcbkcomplete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.elastic.source.js"></script>

<script type="text/javascript">

var IS_UPDATES_ACTIONS = false; //Para execucao de uma atualizacao por vez
var PROXIMO_ANEXO = ${proximoAnexo};
var ID_DOCUMENTO = ${idDocumento};
var USER_NAME = '${usuario.pstGrad.pstgradNome}	${usuario.usuNGuerra}';
var NAME,CAMINHO_NOME,ID_ANEXO,NOME_ANEXO;
var CRIPTOGRAFAR = false;
var USER_LOGIN_CRIPTO_DEST;
var ID_DESPACHO = 0;
var BACK = false; //Variavel para checar se esta tudo ok com a edicao.

//errorAlert("ATENÇÃO!<br>O link EDITAR não funcionará nessa versão teste do Direto para usuários do LINUX");

/*
 **** Eventos MouseOver de atualização histórico,anotaçoes e despachos **** 
 */
$j(function(){
	initURLTextarea();

	$j('#tar').elastic();
	//$j('#tar').trigger('update');

	$j("#tar").keyup(function() {
		var v = $j(this).val();
		//var lastChar = v.charAt(v.length-1);
		var hiText = v.replace(/\[/g,"<b>[").replace(/]/g,"]</b>");
		$j("#hiText").html(hiText);
	});
	
	var password = $j( "#password" ),
	    usulogin = $j("#usulogin");
		allFields = $j( [] ).add( password,usulogin ),
		tips = $j( ".validateTips" );

	function updateTips( t ) {
		tips
			.text( t )
			.addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	}

	function checkEmpty( o ) {
		if ( o.val().length < 1  ) {
			o.addClass( "ui-state-error" );
			updateTips( "Este campo não pode ser vazio." );
			return false;
		} else {
			return true;
		}
	}

	function checkExists( o , b ) {
		if ( !b  ) {
			o.addClass( "ui-state-error" );
			updateTips( "Login de usuário não encontrado." );
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass( "ui-state-error" );
			updateTips( n );
			return false;
		} else {
			return true;
		}
	}
	
	$j( "#form-sign" ).dialog({
		autoOpen: false,
		maxHeight: 200,
		width: 350,
		modal: true,
		buttons: {
			"Validar": function() {
				var bValid = true;
				allFields.removeClass( "ui-state-error" );
				bValid = bValid && checkEmpty(password);
				bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Campo senha permitido somente : a-z 0-9" );

				if ( bValid ) {

					if(ID_DESPACHO == 0){

						segurancaJS.signFile('${usuario.usuLogin}', password.val(),ID_ANEXO,{
								callback:function(retorno) { 
									updateTips(retorno);
									setTimeout(function(){
										$j( "#form-sign" ).dialog( "close" );
										window.location.reload();
										}
									,2000);
								}
						});

					}else{

						segurancaJS.decryptMessage(password.val(),ID_DESPACHO,{
							callback:function(dec) {
								//dec += " ADÇÇâÂÃo !@#%& ;: .()";
								//dialogMessage("Despacho descriptografado",dec.replace(/[^a-zA-Z0-9_\(\*\)\.\s:;,%$!@#&ãàáâäèéêëìíîïõòóôöùúûüçÃÀÁÂÄÈÉÊËÌÍÎÏÖÒÓÔÙÚÛÜÇ]+/g,'-'),false);
								dialogMessage("Despacho descriptografado",dec,false);
							}
						});
						
					}
				}
			},
			'Cancelar': function() {
				ID_DESPACHO = 0;
				$j( this ).dialog( "close" );
			}
		},
		close: function() {
			ID_DESPACHO = 0;
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});


	$j( "#user-cripto" ).dialog({
		autoOpen: false,
		maxHeight: 200,
		width: 350,
		modal: true,
		buttons: {
			"Validar": function() {
				var bValid = true;
				allFields.removeClass( "ui-state-error" );

				usuarioJS.validateUser(usulogin.val(),{
					callback:function(ok) {
						bValid = bValid && checkEmpty(usulogin);
						bValid = bValid && checkExists(usulogin,ok);
						
						if ( bValid ) {
							
							usuarioJS.userIdentity(usulogin.val(),{
								callback:function(usuIdt) { 
							
								segurancaJS.haveCertificate(usuIdt,{
									callback:function(ok) { 
									if(ok){
										USER_LOGIN_CRIPTO_DEST = usulogin.val();
										CRIPTOGRAFAR = true;
										$j( "#user-cripto" ).dialog( "close" );
										$j('#mask').show();
									}else{
										alertMessage('Usuário sem certificado digital','Este usuário não tem certifcado digital para ler despachos criptografados',false); 
									}
								
									}
								});
							 }
							});
						}
		  			}
				});
				
				
			},
			'Cancelar': function() {
				$j( this ).dialog( "close" );
				$j('#mask').show();
				$j('#chk_criptografar').attr('checked', false);
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
	
	$j("font[title]").tooltip(
			{ 
				position: "bottom right", 
				opacity: 1,
				effect: 'explode'
			}
	);
	
	/*Atualiza ajax histórico,anotaçoes e despachos */
	$j('#despachos').mouseenter(function(e) {
		var tipo = $j(this).attr('id');
		if (!IS_UPDATES_ACTIONS)
			js.direto.show_updates(${idDocumento},tipo);
	 });
	
	$j('#anotacoes').mouseenter(function(e) {
		var tipo = $j(this).attr('id');
		if (!IS_UPDATES_ACTIONS)
			js.direto.show_updates(${idDocumento},tipo);
	 });
	
	
	$j('#historico').mouseenter(function(e) {
		var tipo = $j(this).attr('id');
		if (!IS_UPDATES_ACTIONS)
			js.direto.show_updates(${idDocumento},tipo);
	
	 });

	/*Efeitos histórico,anotaçoes e despachos */		
	$j('a[name=link_historico]').toggle(function() {
		$j(this).parent().next('#historico').slideDown(100);
					
	}, function() {
		$j(this).parent().next('#historico').slideUp(100);
	});


	$j('a[name=link_anotacoes]').toggle(function() {
		$j(this).parent().next('#anotacoes').fadeOut('slow');
					
	}, function() {
		$j(this).parent().next('#anotacoes').fadeIn(1000);
	});

	$j('a[name=link_despachos]').click(function(e) {
		e.preventDefault();
		$j('#despachos').toggle("slow");
	});

	$j('#chk_criptografar').click(function() {
		if($j(this).is(':checked')){
			$j( "#user-cripto" ).dialog( "open" );
			$j("#wacao").css('z-index','1000');
			$j("#mask").css('z-index','999');
			$j('#mask').hide();
		}else{
			CRIPTOGRAFAR = false;
		}
	});

	$j('a[name=decrypto]').click(function(e) {
		e.preventDefault();
		ID_DESPACHO = $j(this).attr('id');
		$j( "#form-sign" ).dialog( "option", "title", 'Descriptografar despacho' );
		$j( "#form-sign" ).dialog( "open" );
		
	});
	
	$j('a[name=cryptofile]').click(function(e) {
		e.preventDefault();
		idAnexo = $j(this).attr('id');
		//segurancaJS.decrypt(idAnexo);
				
	});
	

	$j('a[name=liberar_edicao]').click(function(e) {
		e.preventDefault();

		segurancaJS.releaseDocumentEdition($j(this).attr('anexo'),{
			callback:function(retorno) { 
				//retorno = eval(retorno);
				//(retorno == -1) ? errorAlert('Documento já está assinado.') : alertMessage('Bloquear edição do documento',retorno,false);
				alertMessage('Liberar edição do documento',retorno,false); 

				window.location.reload();	
			}						
		});
		
	});

	/*Evento do botão de confirmação da edição do documento*/
	$j('input[type=button]').click(function(e) {
		var bt_name = $j(this).attr('name');
		if(bt_name == 'bt_editar_nao'){
			confirma_edicao(0);
			js.direto.close_mask();
		}	

		if(bt_name == 'bt_editar_sim_assinar'){
			confirma_edicao(2);
			js.direto.close_mask();
			
			setTimeout(function(){
				
				if(BACK){
					
					textoHistorico = "(Assinado) "+NOME_ANEXO+"-${usuario.usuLogin}";
					
					historicoJS.save(${idDocumento},textoHistorico);
					
					segurancaJS.haveCertificate(${usuario.usuIdt},{
						callback:function(ok) { 
							if (ok){
								$j( "#form-sign" ).dialog( "open" );
							}else{
								segurancaJS.blockEditDocument(ID_ANEXO,{
									callback:function(retorno) {
										(retorno != '1') ? errorAlert(retorno) : alertMessage('Bloquear edição do documento','Documento bloqueado para edição!',false); 
										if (retorno == '1')
											window.location.reload();	
									}						
								});
							}
						}
		
					});
				
				}
				
			
			},300);	
			
		}

		if(bt_name == 'bt_editar_sim'){
			confirma_edicao(1);
			js.direto.close_mask();
		}

		
	});

	$j('#close_attach').click(function(e) {
		e.preventDefault();
		js.direto.close_mask();
	});

	
	$j('#texto_acao').limit('500','#charsLeft');
	

	$j("#div_anexos span[name=ui_anexo]").toggle(

		function(){
			var id = $j(this).attr('id');
			$j('#hash_'+id).fadeIn(1000);
			$j('#hash_'+id).css('display','block');
			var div_parent = $j(this).parent();
			div_parent.css('height','50px');
			div_parent.css('line-height','');
			},

		function(){
			var id = $j(this).attr('id');
			$j('#hash_'+id).fadeOut('slow');
			var div_parent = $j(this).parent();
			setTimeout(function(){
				div_parent.css('height','25px');
				div_parent.css('line-height','25px');
			},500);
		}

	);
		
});

/*Função de confirmação da edição do documento*/
function confirma_edicao(resposta){
	
	if (resposta == 0) {
		//alert(nome_anexo+" - Apaga arquivo temp servidor e cancela operação");
		anexoJS.deleteAnexoFromTemp(ID_ANEXO,{
			callback:function(ok) { 
				if (!ok)
					errorAlert('O arquivo não pode ser deletado da pasta temporária.');
				BACK = eval(ok);
			}
		});
		
	} else if (resposta == 1) {
		//alert(NOME_ANEXO+" - Substitui arquivo servidor, apaga arquivo temp e armazena histórico.-"+ID_ANEXO);
		anexoJS.copy(ID_ANEXO,{
			callback:function(ok) { 
				if (!ok)
					errorAlert('1-Você não tem permissão para editar o documento. Verifique se o seu nome consta na lista de destinatários, caso encontrado, por favor, contate o administrador do sistema.');
				BACK = eval(ok);
				notifyAndDelete(BACK,resposta);
			}
		});
	} else {
		anexoJS.copy(ID_ANEXO,{
			callback:function(ok) { 
				if (!ok)
					errorAlert('Você não tem permissão para editar o documento. Verifique se o seu nome consta na lista de destinatários, caso encontrado, por favor, contate o administrador do sistema.');
				BACK = eval(ok);
				notifyAndDelete(BACK,resposta);
			}
		});
	}
}

function notifyAndDelete(b,resposta){
	if(b){
		if(resposta != 0){
			var textoParaNotificacao = "Edição("+NOME_ANEXO+") - ${usuario.pstGrad.pstgradNome} ${usuario.usuNGuerra}";
			notificacaoJS.save(${idDocumento},textoParaNotificacao);
		}
	}else{
		anexoJS.deleteAnexoFromTemp(ID_ANEXO,{
			callback:function(ok) { 
				if (!ok)
					errorAlert('O arquivo não pode ser deletado da pasta temporária.');
			}
		});
	}
}

function checkSignature(){

	var ele = document.getElementById('fileToCheck');
	var file = ele.files[0];
	  

	  if (file == null){
		  errorAlert('Você não selecionou nenhum arquivo!');
		  return;  
	  }

	var xhr = new XMLHttpRequest();
	xhr.upload.addEventListener("progress", uploadProgress, false);
	xhr.addEventListener("load", checkComplete, false);
	xhr.addEventListener("error", uploadFailed, false);
	xhr.addEventListener("abort", uploadCanceled, false);
	xhr.open(
	            "POST",
	            "upload/check.html"
	        );

	xhr.setRequestHeader("Cache-Control", "no-cache");
	xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	xhr.setRequestHeader("X-File-Name", ID_ANEXO);
	      
	xhr.send(file);
	
}

function fileSelectedToCheck() {
    var file = document.getElementById('fileToCheck').files[0];
    var output = document.getElementById("file_information");

    if (file) {
      var fileSize = 0;
      if (file.size > 1024 * 1024)
        fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
      else
        fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

      output.innerHTML = 'Arquivo: <i>' + file.name + '</i>';
      output.innerHTML += ' (' + fileSize + ')';
      document.getElementById('check_output').innerHTML = '';

      anexoJS.getAssinaturaHash(ID_ANEXO,{
			callback:function(hashAssinado) {

			var tam = hashAssinado.length;
			var block = tam/45;
			var newHash = '';

			var start = 0;
			for(var i = 1; i<block+1; i++){
				newHash += hashAssinado.slice(start, eval((i*45)-1))+' ';
				start = eval((i*45)-1);
			}

			newHash += hashAssinado.slice(start, eval((((block+1)*45)-tam)-1));

			output.innerHTML += '<br /><b>Code:</b> ';
    	  	output.innerHTML += newHash;

      		}
      });
    }
  }

function checkComplete(evt) {
	var output = document.getElementById("check_output");
	var response = eval("(" + evt.target.responseText + ")");
	var result = 'ASSINATURA NÃO É VÁLIDA!';

    if(response.success)
		result = 'ASSINATURA VÁLIDA!';
    
   output.innerHTML = '<font color="'+(response.success ? 'green' : 'red')+'">'+result+'</font>';
    
}

function salvarAcao(acao,id,ele){
	IS_UPDATES_ACTIONS = true;

	$j(ele).unbind('click');
	
	var texto = $j('#texto_acao').val();
	acao = acao.toLowerCase();

	if (texto == ""){
		alert('Para '+acao+' digite um texto.');
		return;
	}

	if(id == 1){

		var textoParaNotificacao = "Despacho - ${usuario.pstGrad.pstgradNome} ${usuario.usuNGuerra}";
		notificacaoJS.save(${idDocumento},textoParaNotificacao);

		if(CRIPTOGRAFAR){
			CRIPTOGRAFAR = false;
			segurancaJS.encryptMessage(texto, USER_LOGIN_CRIPTO_DEST,{
				callback:function(encHexa) {
				var array = encHexa.split(',');
				//alert(encHexa);
					despachoJS.save(${idDocumento},array[1],array[0],{
						callback:function() {
							setTimeout(function(){
								window.location.reload();
								},300);							
					    }
					});
				}
			});

		}else{
			despachoJS.save(${idDocumento},texto,0,{
				callback:function() {
				if ( $j('#div_despachos').length == 0 ){
					setTimeout(function(){
						window.location.reload();
						},300);
				}else{
					js.direto.close_mask();
					js.direto.show_updates(${idDocumento},'despachos');
				}	
			  }
			});
		}

		
				
	}else if(id == 2){

		var textoParaNotificacao = "Anotacao - ${usuario.pstGrad.pstgradNome} ${usuario.usuNGuerra}";
		notificacaoJS.save(${idDocumento},textoParaNotificacao);
		
		
		anotacaoJS.save(${idDocumento},texto,{
			callback:function() {
				if ( $j('#div_anotacoes').length == 0 ){
					setTimeout(function(){
						window.location.reload();
						},300);
				}else{
					js.direto.show_updates(${idDocumento},'anotacoes');
					js.direto.close_mask();
				}	
  			}
  		});
	}
	
}

function padronizado(valor){

	var textArea = $('texto_acao');
	
	var textAcao = textArea.value + " " + valor;
	
	//var bcharCount = charCount(textAcao);

	//if (!bcharCount)
		//return;

	textArea.value += " " + valor;
	$("texto_acao").focus();
}

function uploadFile() {

	$j('#close_attach').unbind('click');
	
	var ele = document.getElementById('fileToUpload');
	var file = ele.files[0];
	  

	  if (file == null){
		  alert('Você não selecionou nenhum arquivo!');
		  return;  
	  }

	    var xhr = new XMLHttpRequest();
	    xhr.upload.addEventListener("progress", uploadProgress, false);
	    xhr.addEventListener("load", uploadComplete, false);
	    xhr.addEventListener("error", uploadFailed, false);
	    xhr.addEventListener("abort", uploadCanceled, false);
	    xhr.open(
	            "POST",
	            "upload/upload.html"
	        );

	    var path = ele.value.split("\\"); // Retira as barras
        NAME = (path[path.length-1]);

		var arExtensaoArquivo = NAME.split(".");
	    var extensao = '.'; 
	    extensao += arExtensaoArquivo[arExtensaoArquivo.length-1];
			
	    if (arExtensaoArquivo.length == 1)
        	extensao = '';
        

	    CAMINHO_NOME = PROXIMO_ANEXO+'_'+ID_DOCUMENTO+extensao;

	    xhr.setRequestHeader("Cache-Control", "no-cache");
	    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    xhr.setRequestHeader("X-File-Name", CAMINHO_NOME);
	      
	    xhr.send(file);

      

/*var reader = new FileReader();

reader.onload = function(e) {

var bin = e.target.result;

xhr.sendAsBinary(bin);
alert('enviado');
};
reader.readAsBinaryString(file); 
*/      

      
	 /*if (xhr.status == 200) {
	   // $j('#rodape_anexo').html('<p style="text-align: center">Enviado!</p>');  //.innerHTML += "Uploaded!<br />";
	   anexoJS.saveAnexo(name,caminhoNome,id,false);
	  } else {
	    output.innerHTML += "Error " + xhr.status + " occurred uploading your file.<br />";
	  }*/
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {
      var percentComplete = Math.round(evt.loaded * 100 / evt.total);
      document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';

      $j( "#progressbar" ).progressbar({
			value: percentComplete
	  });

      $j( "#progressbar span" ).text(percentComplete.toString() + '%');
      
    }
    else {
      document.getElementById('output').innerHTML = 'Tamanho desconhecido.';
    }
}

function uploadComplete(evt) {
    
	/* This event is raised when the server send back a response */
    // alert(evt.target.responseText);
	document.getElementById('progressNumber').innerHTML = 'Completado!';  
	$j('#progressNumber').css('color','#3DD13F');
	anexoJS.saveAnexo(NAME,CAMINHO_NOME,ID_DOCUMENTO,false,true);
	PROXIMO_ANEXO++;
	
	var textoParaNotificacao = "Anexo("+NAME+") - ${usuario.pstGrad.pstgradNome} ${usuario.usuNGuerra}";
	notificacaoJS.save(${idDocumento},textoParaNotificacao);


	$j( "#progressbar" ).progressbar({
			value: 100
	  });

	$j('#close_attach').click(function(e) {
		setTimeout(function(){
			window.location.reload();
			},300);
	});
}

function uploadFailed(evt) {
    errorAlert("Ocorreu um erro ao tentar enviar o arquivo.");
}

function uploadCanceled(evt) {
    errorAlert("A transferência foi cancelada pelo usuário ou devido uma falha de conexão.");
}


function fileSelected() {
    var file = document.getElementById('fileToUpload').files[0];
    var output = document.getElementById("output");
    var progressNumber = document.getElementById("progressNumber");

    if (file) {
      var fileSize = 0;
      if (file.size > 1024 * 1024)
        fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
      else
        fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

      progressNumber.innerHTML = '';
      output.innerHTML = 'Arquivo: <i>' + file.name + '</i>';
      output.innerHTML += ' (' + fileSize + ')';
      //document.getElementById('fileType').innerHTML = 'Type: ' + file.type;

      $j( "#progressbar" ).progressbar({
			value: 0
	  });

	  //$j('#information').css('background-color','#B8C9DD');
    }
  }

</script>

<style type="text/css">

#progressNumber {
    /*position: relative;
    bottom: 10px;
    right: 0;
    color: orange;*/
}

.loadingIndicator {
    width: 0%;
    height: 2px;
    background-color: orange;
    position: absolute;
    bottom: 0;
    left: 0;
}

#progressbar {

height: 15px;

}

.upload-button {
    display:block; /* or inline-block */
    width: 352px; 
    padding: 2px 0; 
    text-align:center;    
    background:red; 
    border-bottom:1px solid #ddd;
    color:#fff;
}

</style>

<div style="width: 822px; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
				
			<a href="#wgrupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar</a> |
			<!-- <a href="" style="margin-left: 5px;" class="menu2">Responder</a> | -->
			<a href="" style="margin-left: 5px;" class="menu2">Despachar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anotar</a> |
			<a href="#wanexar" name="modal" style="margin-left: 5px;" class="menu2">Anexar</a> |
			
		
</div>



<div id="corpo_documento" style="float: left; position: static; width: 822px; vertical-align: middle; display: none;">
	
	<div style="float: left; text-align: left; margin-top: 10px; margin-left: 7px">
	
		<font size="+2">[${documento.tipoDocumento.tipoDocumentoNome}] ${documento.assunto}</font>		
		
		<br><br>
		
		<font color="#666666">Id Documento: </font><b>${idDocumento}</b><br>
		<c:if test="${encaminhadoPor != ''}">
			<font color="#666666">Carteira que encaminhou: </font><b>${encaminhadoPor}</b><br>
		</c:if>
		<font color="#666666">Nr. Prototocolo: </font><b>${documento.nrProtocolo}</b><br>
		<font color="#666666">Nr. Documento: </font><b>${documento.nrDocumento}</b><br>	
		<font color="#666666">Documento elaborado por: </font><b>${usuarioElaborador.pstGrad.pstgradNome}
		${usuarioElaborador.usuNGuerra}</b><br>
		
		
		<font color="#666666">Documento enviado para... (negrito) e acessado por...(azul): </font>
		
		 <c:forEach var="doc_cart" items="${allDocuments}">
			   <c:forEach var="conta" items="${doc_cart.carteira.contas}">
			    					    			
			    	<c:choose> 
	  					<c:when test="${doc_cart.status == '0'}" > 
	  						<span style="background-color: #fff;"><font color="black" style="font-size: 9px;" title="${conta.carteira.cartAbr}"><b>${conta.usuario.pstGrad.pstgradNome} ${conta.usuario.usuNGuerra};</b></font></span> 
	  					</c:when> 
	  					<c:otherwise> 
	  						<span style="background-color: #fff;"><font title="${conta.carteira.cartAbr}" color="blue" style="font-size: 9px;">${conta.usuario.pstGrad.pstgradNome} ${conta.usuario.usuNGuerra};</font></span> 
	  					</c:otherwise> 
					</c:choose>
			    			
			    				
			    </c:forEach>
		 </c:forEach>
		<br>
		
		<font color="#666666">Remetente: </font><b>${documento.remetente}</b><br>	
		<font color="#666666">Destinatario: </font><b>${documento.destinatario}</b><br>
		<font color="#666666">Data de entrada no sistema: </font><b><fmt:formatDate pattern="EEEE, d MMMM yyyy HH:mm" value="${documento.dataEntSistema}" /></b><br>
		<font color="#666666">Referência: </font><b>${documento.referencia}</b><br>
	
		<font color="#666666">Documento: </font><b>${documento_principal}</b> 
		
   <c:if test="${documento_principal != 'Sem documento'}">			
		
		>>
		
		<c:choose>
			<c:when test="${documento.assinatura == 0}">
				<c:if test="${documento_principal.assinado == 0}">
				 <span id="s_editar"><a href="#weditar" id="${documento_principal.anexoCaminho}" nomeAnexo="${documento_principal.anexoNome}" anexo="${documento_principal.idAnexo}" name="modal" class="l_edicao_vis">Editar</a></span> |
				</c:if>
			</c:when>
			<c:otherwise>
				Documento assinado.
			</c:otherwise>
		</c:choose>
		
		<span id="s_visualizar"><a href="fileview.html?id=${documento_principal.idAnexo}" target="_blank" class="l_edicao_vis">Visualizar</a></span>
		
		<c:if test="${documento_principal.assinado == 1 && documento_principal.idAssinadoPor == usuario.idUsuario}">
			| <span id="s_checar"><a href="#" name="liberar_edicao" anexo="${documento_principal.idAnexo}" class="l_edicao_vis">Liberar</a></span> 
		</c:if>
		
		<c:if test="${documento_principal.assinado == 1 || documento.assinatura == 1}">
			| <span id="s_checar"><a href="#wchecar" name="modal" id="checar_assinatura" anexo="${documento_principal.idAnexo}" class="l_edicao_vis">Checar</a></span>
		</c:if>
		
		<!-- | <span id="s_checar"><a href="#cryptofile" name="cryptofile" id="${documento_principal.idAnexo}" class="l_edicao_vis">Cripto</a></span> -->
		
		
		<br>
		<font color="#666666">SHA-1: </font><b>${documento_principal.hash}</b><br>
		
	</c:if>
		
		<div id="line" style="margin-top: 10px; background-color: #B8C9DD; position: relative; width: 822px; height: 30px; text-align: center; line-height:30px;">
			<a href="#wanexar" name="modal" id="link_titulo">Anexos</a>
		</div>
		
		
		
		<c:forEach var="anexo" items="${anexos}">
			<div id="div_anexos" class="anexos">
				<span id="${anexo.idAnexo}" name="ui_anexo">${anexo.anexoNome}</span>
				(
				<c:choose>
					<c:when test="${documento.assinatura == 0}">
						<c:if test="${anexo.assinado == 0}">
							<a href="#weditar" name="modal" id="${anexo.anexoCaminho}" nomeanexo="${documento_principal.anexoNome}" anexo="${anexo.idAnexo}" class="l_edicao_vis">Editar</a> |
						</c:if>
					</c:when>
				</c:choose>
				<a href="fileview.html?id=${anexo.idAnexo}" target="_blank" class="l_edicao_vis">Visualizar</a>
				<c:if test="${anexo.assinado == 1 || documento.assinatura == 1}">
				| <span id="s_checar"><a href="#wchecar" name="modal" id="checar_assinatura" anexo="${anexo.idAnexo}" class="l_edicao_vis">Checar</a></span>
				</c:if>
				<c:if test="${anexo.assinado == 1 && anexo.idAssinadoPor == usuario.idUsuario}">
				| <span id="s_checar"><a href="#" name="liberar_edicao" anexo="${anexo.idAnexo}" class="l_edicao_vis">Liberar</a></span> 
				</c:if>
				)
				<span id="hash_${anexo.idAnexo}" style="background-color: red; width: 100%; display: none;">SHA-1: ${anexo.hash}</span>
			</div>
		</c:forEach>
	
		<div id="line" style="margin-top: 10px; background-color: #B8C9DD; position: relative; width: 822px; height: 30px; text-align: center; line-height:30px;">
			<a href="javascript:show_updates(${idDocumento},'despachos')" id="link_titulo" name="link_despachos">Despachos</a> [<a href="#wacao" name="modal" id="1_Despachar">Despachar</a>]
		</div>
		<div style="position: relative" id="despachos">
			<c:forEach var="d" items="${despachos}">
				<div id="div_despachos" class="celula despacho">
					<p><strong>[${d.carteira.cartAbr }] [${d.usuario.pstGrad.pstgradNome} ${d.usuario.usuNGuerra}]</strong> - ${d.despacho} - 
					<span id="data_despacho"><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${d.dataHoraDespacho}" /></span> 
					<c:if test="${d.idUsuarioDestinatario != 0}"> (<a href="#decrypto" name="decrypto" id="${d.idDespacho}" style="color: red;">Descripto</a>)</c:if>
					</p>
				</div>
			 </c:forEach>
		 </div>
		
		<div id="line" class="div_title_anotacoes" style="">
			<a href="javascript:show_updates(${idDocumento},'anotacoes')" id="link_titulo" name="link_anotacoes">Anotações</a> [<a href="#wacao" name="modal" id="2_Anotar">Anotar</a>]
		</div>
		<div style="position: relative" id="anotacoes">
			<c:forEach var="a" items="${anotacoes}">
				<div id="div_anotacoes" class="celula anotacao">
					<strong>[${a.carteira.cartAbr }] [${a.usuario.pstGrad.pstgradNome} ${a.usuario.usuNGuerra}]</strong> - ${a.anotacao} - 
					<span id="data_anotacao"><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${a.dataHoraAnotacao}" /></span> 
				</div>
			 </c:forEach>
		 </div>	
		 
		<div id="line" style="margin-top: 10px; background-color: #B8C9DD; position: relative; width: 822px; height: 30px; text-align: center; line-height:30px;">
			<a href="#" id="link_titulo" name="link_historico">Histórico</a>
		</div>
		<div style="position: relative" id="historico">
			<c:forEach var="h" items="${historico}">
				<div id="div_historico" class="celula historico">
					<strong>[${h.carteira.cartAbr }]</strong> - ${h.historico} - 
					<span id="data_historico"><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${h.dataHoraHistorico}" /></span> 
				</div>
			 </c:forEach>
		</div>
	
	</div>	
		
		
		
		    
	
		
</div>	
	
<%@ include file="include_foot.jsp" %>
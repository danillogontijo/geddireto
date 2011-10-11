<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/formulariosJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/anexoJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/historicoJS.js"></script>



<!-- INICIO SUBMENU 2 DO CORPO PRINCIPAL -->
<div style="background-color: #B8C9DD;" class="ui_main_body ui_subMenu _width_main_body _font_normal">
								
	<a href="#grupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar</a> |
	<a href="" style="margin-left: 5px;" class="menu2">Responder</a> |
	<a href="" style="margin-left: 5px;" class="menu2">Despachar</a> |
	<a href="" style="margin-left: 5px;" class="menu2">Anotar</a> |
	<a href="" style="margin-left: 5px;" class="menu2">Anexar</a> |
				
</div>
			
<style type="text/css">
.upload-button {
    display:block; /* or inline-block */
    width: 822px; 
    padding: 2px 0; 
    text-align:center;    
    background:#1e90ff; 
    border-bottom:1px solid #ddd;
    color:#fff;
}


#files {
   /* margin: 0 0 0 400px;*/
    width: 778px;
    padding: 20px 20px 40px 20px;
    border: solid 2px #ccc;
    background: #fefefe;
    min-height: 40px;
    position: relative;
}

#fileDrop,
#files {
    -moz-box-shadow: 0 0 20px #ccc;
}

#fileDrop p {
	padding-top: 7px; 
	text-align: center; 
	font-size: medium;
	height: 100px;
	line-height: 50px;
}

#fileList {
    list-style: none;
    padding: 0;
    margin: 0;
}

    #fileList li {
        margin: 0;
        padding: 10px 0;
        margin: 0;
        overflow: auto;
        border-bottom: solid 1px #ccc;
        position: relative;
    }

        #fileList li img {
            width: 120px;
            border: solid 1px #999;
            padding: 6px;
            margin: 0 10px 0 0;
            background-color: #eee;
            display: block;
            float: left;
        }
        
        #fileList li p {
		    margin: 0;
		    padding: 0;
		    text-align: right;
		    float: left;
		}

#reset {
    top: 10px;
    right: 10px;
    color: #ccc;
    text-decoration: none;
    position: absolute;
}

#reset:hover {
    color: #333;
}

#files #upload {
    /*color: #fff;*/
    position: absolute;
    display: block;
    bottom: 10px;
    right: 10px;
    width: auto;
    /*background-color: #777;
    padding: 4px 6px;
    text-decoration: none;*/
    font-weight: bold;
    -moz-border-radius: 6px;
    font-size: 90%;
    
}

#files #upload:hover {
    background-color: #333;
}

.loader {
    position: absolute;
    bottom: 10px;
    right: 0;
    color: orange;
}

.loadingIndicator {
    width: 0%;
    height: 2px;
    background-color: orange;
    position: absolute;
    bottom: 0;
    left: 0;
}

.imagePreview {
    width: 300px;
    padding: 10px;
    border: solid 1px #ccc;
    position: absolute;
    background-color: white;
}

    .imagePreview img {
        max-width: 100%;
        display: block;
        margin: 0 auto;
    }
    
    
h1 {
    font-size: 1.6em;
    margin: 30px 0;
    padding: 0;
}

h2 {
    font-size: 1.4em;
    padding: 0 0 6px 0;
    margin: 0;
    border-bottom: solid 1px #ccc;
}

h3 {
    font-size: 1.2em;
    margin: 0 0 10px 0;
    padding: 0;
    text-align: left;
    width: 770px; 
    float: left
}



.file_list_font {
 font: 12px "trebuchet MS", arial, sans-serif;
 color: #777;
}

.remove {
	
	/*margin-left: 690px;
	position: relative;*/
	float: right; 
	width: 10px
	
	}

</style>

<style type="text/css">

.error {
	height:15px;
	background-color:#FFFE36;
	font-size:11px;
	border:1px solid #E1E16D;
	padding:4px 10px;
	color:#000;
	display:none;	
	
	-moz-border-radius:4px;
	-webkit-border-radius:4px; 
	-moz-border-radius-bottomleft:0;
	-moz-border-radius-topleft:0;	
	-webkit-border-bottom-left-radius:0; 
	-webkit-border-top-left-radius:0;
	
	-moz-box-shadow:0 0 6px #ddd;
	-webkit-box-shadow:0 0 6px #ddd;	
}

.error p {
	margin:0;		
}

/* pure CSS arrow */
.error em {
	display:block;
	width:0;
	height:0;
	border:10px solid;
	border-color:#FFFE36 transparent transparent;

	/* positioning */
	position:absolute;
	bottom:-17px;
	left:40%;
	
}

.arrow {
  /*
   * In Internet Explorer, The"border-style: dashed" will never be
   * rendered unless "(width * 5) >= border-width" is true.
   * Since "width" is set to "0", the "dashed-border" remains
   * invisible to the user, which renders the border just like how
   * "border-color: transparent" renders.
   */
  border-style: dashed;
  border-color: transparent;
  border-width: 0.53em;
  display: -moz-inline-box;
  display: inline-block;
  /* Use font-size to control the size of the arrow. */
  height: 0;
  line-height: 0;
  vertical-align: middle;
  width: 0;
}

.arrow-e {
  position: absolute;
  right: -20px;
  border-left-width: 1em;
  border-left-style: solid;
  border-left-color: #FFFE36;
  font-size: 14px;
}


</style>


<script type="text/javascript">

var TAMANHO_MAX_UPLOAD = <spring:message code="TAMANHO_MAX_UPLOAD"/>; //Em Mb
var ID_DOCUMENTO = 0;
var IS_ASSIGN = false;

$j(function(){

	//$j('span').click(function(e) {

		$j('#documentoForm').attr('novalidate',''); //bug validator
		
		
	//});

	$j('.tela_apresentacao').hide();
	$j( "button", ".file_list_font" ).button();

	$j( "a", "#formulario" ).button();


		
	//$( "a", ".demo" ).click(function() { return false; });

	
	/*$j.tools.validator.fn("[type=text]", function(element) {

		var txt = "";
		//var 
		
		documentoValidatorJS.getMessageValidator('DocumentoForm',element.id, element.value, {
			callback:function(dataFromServer) {

				txt = dataFromServer;			

	  		}
	  	});
		
		return txt == "" ? true :{     
			en: txt
		};

	});*/

$j("#documentoForm").validator({ 
	lang: 'pt-BR',
	position: 'center left', 
	offset: [-23, 121],
	/*message: '<div><span class="arrow arrow-e"></span></div>',*/
	message: '<div><em/></div>', // em element is the arrow
	errorInputEvent: 'change',
	singleError: false,
	//inputEvent: 'blur'
	/*onBeforeValidate: function(e, els)  {
		FileAPI.uploadQueue;
		alert('');
	}*/
		
}).submit(function(e) {

	var form = document.getElementById("documentoForm");

	
	if (!e.isDefaultPrevented()) {
		e.preventDefault();

		fEnviar(e);

	}
	
		
	});

$j.tools.validator.localize("pt-BR", {
	':email'  		: '',
	':number' 		: '',
	'[max]'	 		: '',
	'[min]'	 		: '',
	'[required]' 	: 'Este campo não pode ser vazio'
});

$j("#documentoForm").bind("onFail", function(e, errors)  {

	// we are only doing stuff when the form is submitted
	if (e.originalEvent.type == 'submit') {

		// loop through Error objects and add the border color
		$j.each(errors, function()  {
			var input = this.input;
			input.css({borderColor: 'red'}).focus(function()  {
				input.css({borderColor: '#444'});
			});
		});

		//alert('verifique os erros');
		
		errorAlert('Existem campos no formulário que não foram preenchidos corretamente.');		
		
	}
});

/*$j('#upload').click(function(e) {
	//alert("");
	$j('#documentoForm').submit(e);	
});	*/



});


function fEnviar(e){

	var documentoFormJS = null;

	getDocumentoFormJS();

	function getDocumentoFormJS()
	{

		dialogMessage('Enviando documento...','<p style="text-align: center"><img src="imagens/ajax-loader.gif" /></p>',true);
		
		formulariosJS.getDocumentoForm({
			callback:function(dataFromServer) {
				setDocumentoFormJS(dataFromServer);
			}
	  	});
	 	  	
	}

	function setDocumentoFormJS(documentoForm){
		this.documentoFormJS = documentoForm;
		var sDestinatarios = "";
		for (var i = 0; i < DESTINATARIOS.length ; i++) {
			var item = DESTINATARIOS[i];
			sDestinatarios += item.id+",";
		}

		this.documentoFormJS.destinatarios = sDestinatarios;
		this.documentoFormJS.tipoDocumento = $j('#tipoDocumento').val();
		this.documentoFormJS.nrDocumento = $j('#nrDocumento').val();
		this.documentoFormJS.dataDocumento = $j('#data').val();
		this.documentoFormJS.remetente = $j('#remetente').val();
		this.documentoFormJS.prioridade = $j('#prioridade').val();
		this.documentoFormJS.assunto = $j('#assunto').val();
		this.documentoFormJS.destinatario = $j('#destinatario').val();
		this.documentoFormJS.referencia = $j('#referencia').val();
		this.documentoFormJS.origem = $j('#origem').val();
		this.documentoFormJS.idCarteiraRemetente = ${contaAtual};
		if ( $j('input[name=assinatura]').is(':checked') ){
			this.documentoFormJS.assinatura = 1;
			IS_ASSIGN = true;
		}
		
		sendAndSaveFormToNewDocumentoJS(this.documentoFormJS);
	}	

	function sendAndSaveFormToNewDocumentoJS(documentoForm){

		documentosJS.sendAndSaveFormToNewDocumento(documentoForm,{
			callback:function(data) {
				
				if(data.id == "" || data == null){
					alertMessage("Error","O documento não pode ser enviado",false);
					return;
				}

				var list = "";
				for (var i = 0; i < DESTINATARIOS.length-1 ; i++) {
					var item = DESTINATARIOS[i];
					list += item.user+", ";
				}
				
				list += (DESTINATARIOS[DESTINATARIOS.length-1]).user;
				
				var txtHistorico = "(Enviado) - Do: ";
				txtHistorico += '${usuario.pstGrad.pstgradNome}	${usuario.usuNGuerra}';
				txtHistorico += " - Para: "+list;
				
				ID_DOCUMENTO = data.id;
				//alert(documentoRetorno.idDocumento);
				
				historicoJS.save(ID_DOCUMENTO,txtHistorico);

				FileAPI.uploadQueue(e);

				var stayInLoop = true;
				while (stayInLoop){
					if (FileAPI.fileQueueSize() == 0){
						stayInLoop = false;

						var url = 'view.html?id='+ID_DOCUMENTO;
						
						var message = 'Foi gerado um protocolo sob o número '+
							'<b>'+data.texto+
							'</b><br /><a href="'+url+
							'">Abrir documento</a>';
	
						dialogMessage('Novo documento enviado',message,false);

						setTimeout(function(){
							window.location = url;
						},3000);
							
					}
				}
	  		}
	  	});

	}	
	
	//alert("Digite aki a funcao de finalizacao");

	//setTimeout("document.getElementById('documentoForm').submit()",1000);
	
}
</script>

<!-- INICIO CORPO PRINCIPAL -->
<div class="ui_main_body _width_main_body" style="background-color: #fff; min-height: 640px;">

<style type="text/css">

#documentoForm {
    -moz-background-clip: border;
    -moz-background-origin: padding;
    -moz-background-size: auto auto;
    -moz-border-radius-bottomleft: 5px;
    -moz-border-radius-bottomright: 5px;
    -moz-border-radius-topleft: 5px;
    -moz-border-radius-topright: 5px;
    background-color: #B8C9DD;
    color: #000;
    margin-bottom: 0;
    margin-left: auto;
    margin-right: auto;
    margin-top: 0;
    padding-bottom: 15px;
    padding-left: 20px;
    padding-right: 20px;
    padding-top: 15px;
    position: relative;
    height: 450px;
    
    
}


#documentoForm .field_input_select {
	border:1px solid #444;
	background-color:#79b7e7;
	padding:2px;
	color:#000;
	font-size:12px;
	font-weight: bold;
		
	margin-bottom: 10px;
	
	/* CSS3 spicing */
	text-shadow:1px 1px 1px #ddd;
	-moz-border-radius:4px;
	-webkit-border-radius:4px;	
	
}

#documentoForm input:focus 		{ color:#000; background-color:#DFEFFC; }
#documentoForm input:active 	{ background-color:red; }

#formulario table tr td {
	text-align: left;
	vertical-align: text-top;	
}

#formulario table tr td textarea{
	margin-top: 3px;
}

#formulario table tr td input{
		
}

#formulario table tr td select{
	
}

#formulario table tr td p{
	margin-bottom: 0px;
	margin-top: 0px;
}


#formulario .align_right {
	padding-left: 10px;
}

#formulario {

	width: 100%;
	padding-top: 10px;
	padding-bottom: 10px;
	vertical-align: top;

}

#formulario .field_width {
	width: 100%;	
	
}


.fileDropEnter {

	min-height: 880px;
	background-color: #FF7256;
	color: #000;
	position: absolute;
	width: 822px;
	top: 0;
	display: none;
	

}

.fileDropOver{
	background-color: #B8C9DD;
	font-weight: bold;
}

#formulario .link_sel_to {
	font-size: 80%;
	padding-top: 0px;
	padding-bottom: 0px;
	width: 100%;
}

#formulario .link_sel_to span{
	padding-top: 0px;
	padding-bottom: 0px;
	width: 100%;
}



</style>


<div id="formulario">

<form:form modelAttribute="documentoForm" method="get" enctype="multipart/form-data" style="padding-top: 10px;" action="teste.html">

 <h3>Formulário para novo documento</h3>
 
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
 
 <tr>
 	<td colspan="2">
 		<p>
 		<a href="#wgrupos" class="link_sel_to" name="modal">Para *</a>
 		<form:textarea path="destinatarios" readonly="true" required="required" data-message="Selecione o(s) destinatário(s)" cssClass="field_width field_input_select" /> 
		</p>
	</td> 		
 </tr>
 
 <tr>
 	<td>
 		<p>
 		Tipo *
	 	<form:select path="tipoDocumento" cssClass="field_width field_input_select" required="required" data-message="Selecione o tipo do documento">
			<option value="">&lt;Selecione o tipo de documento&gt;</option>
		    <form:options items="${tiposDocumentos}" itemValue="idTipoDocumento" itemLabel="tipoDocumentoNome" />
		</form:select>
		</p>
	</td>
 	
 	<td>
 		<p class="align_right">
 		Prioridade *
 		<form:select path="prioridade" id="prioridade" cssClass="field_width field_input_select" required="required" data-message="Selecione a prioridade do documento">
 			<option selected value="0">Normal</option>
			<option value="1">Urgente</option>
			<option value="2">Urgentíssimo</option>
 		</form:select>
 		</p>
 	</td>
 </tr>

<tr>
	<td>
		<p>
		Data. Documento *
	   <form:input path="dataDocumento" id="data" 
			cssClass="field_width field_input_select" required="required" readonly="true" autocomplete="off" data-message="Escolha uma data "/>
		</p>		
	</td>
	<td>
		<p class="align_right">
		Nr. Documento *
		<form:input path="nrDocumento" id="nrDocumento" 
			cssClass="field_width field_input_select" required="required" type="text" />
		</p>
	</td>
</tr>

<tr>
	<td colspan="2">
		<p>
		Assunto *
		<form:input path="assunto" id="assunto" 
			required="required" type="text" cssClass="field_width field_input_select" value="-" onfocus="javascript:this.value =''" />
		</p>		
	</td>
</tr>

<tr>
	<td colspan="2">
		<p>
		Remetente *
		<form:input path="remetente" id="remetente" 
			required="required" type="text" cssClass="field_width field_input_select" value="-" onfocus="javascript:this.value =''" />
		</p>		
	</td>
</tr>  

<tr>
	<td colspan="2">
		<p>
		Destinatario *
		<form:input path="destinatario" id="destinatario" 
			required="required" type="text" cssClass="field_width field_input_select" value="-" onfocus="javascript:this.value =''" />
		</p>		
	</td>
</tr>  

<tr>
	<td colspan="2">
		<p>
		Referência *
		<form:input path="referencia" id="referencia" 
			required="required" type="text" cssClass="field_width field_input_select" value="-" onfocus="javascript:this.value =''" />
		</p>		
	</td>
</tr>  


<tr>
	<td>
		<p style="display: none;">
		Assinar o documento? 
	   <form:checkbox path="assinatura" value="1" />
		</p>		
	</td>
	<td>
		<p class="align_right">
		Origem *
		<form:select path="origem" id="origem" cssClass="field_width field_input_select" required="required" data-message="Selecione a prioridade do documento">
			<option selected="" value="I">Interno</option>
			<option value="E">Externo</option>
		</form:select>
		</p>
	</td>
</tr>



</table>


 
</div>




<div id="wrap" style="width: 100%;">

<div class="upload-button" style="position: relative; overflow: hidden; direction: ltr;">
     	Clique aqui para adicionar os documentos
        <input type="file" id="fileField" name="fileField" multiple style="position: absolute; right: 0pt; top: 0pt; font-family: Arial; font-size: 118px; margin: 0pt; padding: 0pt; cursor: pointer; opacity: 0;" />
    </div>

    <div id="files" class="file_list_font">
       <h2>Arquivos</h2>
        <a id="reset" href="#" title="Remove todos os arquivos da lista">Limpar lista</a>
       <ul id="fileList"></ul>
       <!-- <a id="upload" href="#" title="Upload all files in list" type="submit">Enviar</a> -->
       
       <button type="submit" id="upload" >Enviar</button>
       
     </div>
     
     <div id="fileDrop" class="fileDropEnter">
    	<p style="padding-top: 7px; text-align: center; font-size: medium;">Solte os arquivos aqui</p>
    </div>
</div>

</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/FileAPI.js" charset="UTF-8"></script>

					
		</div>
	</div>
</div>
	
<%@ include file="include_foot.jsp" %>
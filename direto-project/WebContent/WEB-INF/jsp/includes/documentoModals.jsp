<script type="text/javascript">

/*function charCount(val){
	var max = 50;
	var length = val.length+1;

	if (length > max){
		//alert('excedeu');
		if (event.keyCode != 8)
     		$j('#texto_acao').attr("disabled","disabled");
		return false;
	}

	$j('#textCount').html("("+length+" / "+max+")");
	return true;
}*/

var users = [<c:forEach var="doc_cart" items="${allDocuments}"><c:forEach var="conta" items="${doc_cart.carteira.contas}">"[${conta.usuario.idUsuario}_${conta.carteira.idCarteira}@${conta.usuario.pstGrad.pstgradNome}-${conta.usuario.usuNGuerra}-(${conta.carteira.cartAbr})]",</c:forEach></c:forEach>"[0@GED]"];

    		function initURLTextarea(){
    			$j("#texto_acao").autocomplete({
    						wordCount:1,
    						mode: "outter",
    						on: {
    							query: function(text,cb){
    				//alert(text.toLowerCase().indexOf("sd"))
									//for( var k=0; k<pst_grad.length; k++ ){
										//alert(text.indexOf(pst_grad[k]));
										//if( text.indexOf( pst_grad[k] ) != -1){
										
										var auto = true;
										
										//for( var k=0; k<text.length; k++ )
											if(text.charCodeAt(0) < 64 || text.charCodeAt(0) > 96)
												auto = false;
										
										if(auto){
											var words = [];
		    								for( var i=0; i<users.length-1; i++ ){
		    									if( users[i].toLowerCase().indexOf(text.toLowerCase()) != -1 ) words.push(users[i].replace(/\s/g, "-")+" ");
		    									
		    								}
		    								cb(words);
										}		
    							}
							}
				});
			}

			function initDespachoRapido(){
				$j("#despacho_rapido").autocomplete({
    						wordCount:1,
    						mode: "outter",
    						on: {
    							query: function(text,cb){
    									var auto = true;
										if(text.charCodeAt(0) < 64 || text.charCodeAt(0) > 96)
											auto = false;
										if(auto){
											var words = [];
		    								for( var i=0; i<users.length; i++ ){
		    									if( users[i].toLowerCase().indexOf(text.toLowerCase()) != -1 ) words.push(users[i].replace(/\s/g, "-")+" ");
		    								}
		    								cb(words);
										}		
    							}
							}
				});
			}
    		

</script>

<style type="text/css">
ul.auto-list{
	display: none;
	position: absolute;
	top: 0px;
	left: 0px;
	border: 1px solid #778899;
	background-color: #E2E4FF;
	padding: 0;
	margin:0;
	list-style:none;
	z-index: 9999;
}
ul.auto-list > li:hover,
ul.auto-list > li[data-selected=true]{
	background-color: #FFFFCC;
}

ul.auto-list > li{
	border: 1px solid #fff;
	cursor: default;
	padding: 2px;

}

mark{
	font-weight: bold;
}


.actionArea{
	width: 560px;
	display: block;
	margin-left: 35px;
}

#highlighter{
	position:absolute;
	
	top: 0px;
	right: 2px;
	bottom: 2px;
	left: 2px;
	
	padding: 2px 4px 20px 1px;
	direction: ltr;
	
	vertical-align: baseline;
	background-color: transparent;
	border: 1px solid #000;
}

#texto_acao{

	vertical-align: text-bottom;
	background-color: transparent;
	position: static;
	display: block;
	direction: ltr;
	
	font-family: "Trebuchet MS",sans-serif;
	font-size: 13px;
	font-weight: 400;
	
	overflow: hidden;
	border: 0px;
	
	/*max-height: 100px;*/
	margin-left: 2px;
	width: 550px;
	height: auto;
}

.lineH{
/*height: 36px;*/ 
}

#highlighter b{background-color: #D8DFEA; font-weight: 200;}
#highlighter span{
display: inline; 
background-color: transparent; 
color: transparent;
word-spacing: 0;
white-space: pre-wrap;
}


</style>

<!-- Confirmação da edição documento -->
<div id="weditar" class="window">
	<table width="100%">
		<tr>
			<td colspan="3" align="center" bgcolor="#1E90FF" class="titulo_confirmacao" height="20" valign="middle">Confirmar edição do documento?</td>
		</tr>
		<tr>
			<td height="35" valign="bottom"><input type="button" id="bt_conf_edicao" value="Sim" name="bt_editar_sim"></td>
			<td height="35" valign="bottom"><input type="button" id="bt_conf_edicao" value="Sim e assinar" name="bt_editar_sim_assinar"></td>
			<td valign="bottom"><input type="button" id="bt_conf_edicao" value="Não" name="bt_editar_nao"></td>
		</tr>
		
	</table>
</div>


<!-- Despacho e Anotaçao -->
<div id="wacao" class="window">
	<div style="display: block; position: static;">
	<table width="100%">
		<tr>
			<td align="center" bgcolor="#1E90FF" width="690" class="titulo_confirmacao" height="20" valign="middle" id="titulo"></td>
			<td width="10"><a href="#" class="close" style="font-weight: bold">X</a></td>
		</tr>
		
		<tr>
			<td colspan="2">Padronizados: 
				<select onchange="padronizado(this.value)" style="width: 327pt;" name="slPreDespacho">
				<option value="0"></option>
				<option value="arquivar.">Arquivar</option>
				<option value="ciente.">Ciente</option>
				<option value="para conhecimento.">Para conhecimento</option>
				<option value="providenciar.">Providenciar</option>
				<option value="para conhecimento e providências.">Para conhecimento e providências</option>
				<option value="providências em andamento.">Providências em andamento</option>
				<option value="encaminhar.">Encaminhar</option></select>
				<br>Digite aqui seu texto: <br>
				
				<div id="actionArea" class="actionArea">
					<div id="actionBox" class="actionArea lineH" sytle="position:relative;">
						<div class="lineH" style="position: relative;">
							<div id="highlighter" class="lineH">
								<div style="text-align: left; width: 550px; display: block; position: static;">
									<span id="hiText"></span>
								</div>
							</div>
							
							<div id="textArea" style="position:relative;">
								<div style="width: 352px; vertical-align: baseline; display: block;">
									<textarea name="texto_acao" onkeypress="return js.direto.charProibido(event)" id="texto_acao"></textarea>
								</div>
							</div>
						</div>
					</div>
					<span id="t"></span>
				</div>
				
				<div id="textCount">Resta(m) <span id=charsLeft></span> caracter(es) a ser(em) digitado(s)</div>
				<div id="div_criptografar"><input type="checkbox" id="chk_criptografar" value="1">Criptografar</input></div>
			</td>
		</tr>
		
		<tr>
			<td height="35" valign="bottom" colspan="2"><input type="button" id="bt_acao_salvar" value="Salvar" name="bt_acao_salvar"></td>
		</tr>
	</table>
	</div>
</div>

<!-- Adicionar anexo -->
<div id="wanexar" class="window">
	<table width="100%">
		<tr>
			<td align="center" bgcolor="#1E90FF" width="690" class="titulo_confirmacao" height="20" valign="middle" id="titulo">Anexar novo documento</td>
			<td width="10"><a href="#" style="font-weight: bold" id="close_attach">X</a></td>
		</tr>
		
		<tr>
			<td colspan="2">
			
			<form enctype="multipart/form-data" method="post" name="fileinfo" id="fileinfo">
  			<!--<label>Selecione o arquivo:</label>  -->
  			
  			<div class="upload-button" style="position: relative; overflow: hidden; direction: ltr;">
     	Clique aqui para adicionar o anexo
        <input type="file" id="fileToUpload" name="fileToUpload" onchange="fileSelected();" style="position: absolute; right: 0pt; top: 0pt; font-family: Arial; font-size: 118px; margin: 0pt; padding: 0pt; cursor: pointer; opacity: 0;" />
    </div>
  			
  			
			</form>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" id="information">
		
			<div id="output"></div>
			<div id="progressbar"></div>
			<div id="progressNumber"></div>
			
			</td>
		</tr>
		
		
		<tr>
			<td height="35" valign="bottom" colspan="2" id="rodape_anexo"><input type="button" onclick="javascript:uploadFile()" id="bt_acao_anexar" value="Anexar" name="bt_acao_salvar"></td>
		</tr>
	</table>
	
</div>


<!-- check assinatura anexo -->
<div id="wchecar" class="window">
	<table width="370">
		<tr>
			<td align="center" bgcolor="#1E90FF" width="690" class="titulo_confirmacao" height="20" valign="middle" id="titulo">Checar assinatura</td>
			<td width="10"><a href="#" style="font-weight: bold" class="close">X</a></td>
		</tr>
		
		<tr>
			<td colspan="2">
			
			<form enctype="multipart/form-data" method="post" name="checkfile" id="checkfile">
  			<!--<label>Selecione o arquivo:</label>  -->
  			
  			<div class="upload-button" style="position: relative; overflow: hidden; direction: ltr;">
     			Clique aqui para selecionar o arquivo
        	<input type="file" id="fileToCheck" name="fileToCheck" onchange="fileSelectedToCheck();" style="position: absolute; right: 0pt; top: 0pt; font-family: Arial; font-size: 118px; margin: 0pt; padding: 0pt; cursor: pointer; opacity: 0;" />
    		</div>
  			
  			</form>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" id="check_information">
		
			<div id="file_information" style="max-width: 370px;"></div>
			<div id="check_output" style="max-width: 370px;"></div>
			
			</td>
		</tr>
		
		<tr>
			<td height="35" valign="bottom" colspan="2" id="rodape_anexo"><input type="button" onclick="javascript:checkSignature()" id="bt_acao_assinatura" value="Checar" name="bt_acao_assinatura"></td>
		</tr>
	</table>
	
</div>



<div id="form-sign" title="Assinar documento" style="display: none;">
	<p class="validateTips"></p>
	<fieldset>
		<label for="password">Digite sua senha do certificado</label>
		<input type="password" name="password" id="password" onfocus="javascript:this.value=''" value="" class="text ui-widget-content ui-corner-all" >
	</fieldset>
</div>

<div id="user-cripto" title="Destinatário"  style="display: none;">
	<p class="validateTips"></p>
	<form>
	<fieldset>
		<label for="password">Digite o login do destinatário</label>
		<input type="text" name="usulogin" id="usulogin" onfocus="javascript:this.value=''" value="" class="text ui-widget-content ui-corner-all" onkeypress="return teclaEnter(event);" />
	</fieldset>
	</form>
</div>

<style>
		label { display:block; }
		fieldset { padding:0; border:0; margin-top:5px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
    
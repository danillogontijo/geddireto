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

</script>

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
	<table width="100%">
		<tr>
			<td align="center" bgcolor="#1E90FF" width="690" class="titulo_confirmacao" height="20" valign="middle" id="titulo"></td>
			<td width="10"><a href="#" class="close" style="font-weight: bold">X</a></td>
		</tr>
		
		<tr>
			<td colspan="2">Padronizados: 
				<select onchange="padronizado(this.value)" style="width: 327pt;" name="slPreDespacho">
				<option value="0"></option>
				<option value="Arquivar.">Arquivar</option>
				<option value="Ciente.">Ciente</option>
				<option value="Para conhecimento.">Para conhecimento</option>
				<option value="Providenciar.">Providenciar</option>
				<option value="Para conhecimento e providências.">Para conhecimento e providências</option>
				<option value="Providências em andamento.">Providências em andamento</option>
				<option value="Encaminhar.">Encaminhar</option></select>
				<br>Digite aqui seu texto: <br>
				<textarea onkeypress="return js.direto.charProibido(event)" cols="76" rows="2" id="texto_acao"></textarea>
				<div id="textCount">Resta(m) <span id=charsLeft></span> caracter(es) a ser(em) digitado(s)</div>
				<div><input type="checkbox" id="chk_criptografar" value="1">Criptografar</input></div>
			</td>
		</tr>
		
		<tr>
			<td height="35" valign="bottom" colspan="2"><input type="button" id="bt_acao_salvar" value="Salvar" name="bt_acao_salvar"></td>
		</tr>
	</table>
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



<div id="form-sign" title="Assinar documento">
	<p class="validateTips"></p>
	<form>
	<fieldset>
		<label for="password">Digite sua senha do certificado</label>
		<input type="password" name="password" id="password" onfocus="javascript:this.value=''" value="" class="text ui-widget-content ui-corner-all" />
	</fieldset>
	</form>
</div>

<div id="user-cripto" title="Destinatário">
	<p class="validateTips"></p>
	<form>
	<fieldset>
		<label for="password">Digite o login do destinatário</label>
		<input type="text" name="usulogin" id="usulogin" onfocus="javascript:this.value=''" value="" class="text ui-widget-content ui-corner-all" />
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
    


<!-- Confirma��o da edi��o documento -->
<div id="weditar" class="window">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="#1E90FF" class="titulo_confirmacao" height="20" valign="middle">Confirmar edi��o do documento?</td>
		</tr>
		<tr>
			<td height="35" valign="bottom"><input type="button" id="bt_conf_edicao" value="Sim" name="bt_editar_sim"></td>
			<td valign="bottom"><input type="button" id="bt_conf_edicao" value="N�o" name="bt_editar_nao"></td>
		</tr>
		
	</table>
	<input type="hidden" id="hn_nome_anexo">
</div>


<!-- Despacho e Anota�ao -->
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
				<option value="Para conhecimento e provid�ncias.">Para conhecimento e provid�ncias</option>
				<option value="Provid�ncias em andamento.">Provid�ncias em andamento</option>
				<option value="Encaminhar.">Encaminhar</option></select>
				<br>Digite aqui seu texto: <br>
				<textarea onkeypress="return js.direto.charProibido(event)" cols="76" rows="2" onkeyup="" id="texto_acao"></textarea>
				<div id="progdespachar">(0 / 500)</div>
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
    
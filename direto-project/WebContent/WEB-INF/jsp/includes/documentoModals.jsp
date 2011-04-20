

<!-- Confirmação da edição documento -->
<div id="weditar" class="window">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="#1E90FF" class="titulo_confirmacao" height="20" valign="middle">Confirmar edição do documento?</td>
		</tr>
		<tr>
			<td height="35" valign="bottom"><input type="button" id="bt_conf_edicao" value="Sim" name="bt_editar_sim"></td>
			<td valign="bottom"><input type="button" id="bt_conf_edicao" value="Não" name="bt_editar_nao"></td>
		</tr>
		
	</table>
	<input type="hidden" id="hn_nome_anexo">
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
				<select onchange="TrocaTexto(this.value,&quot;Tdespachar&quot;);" style="width: 327pt;" name="slPreDespacho">
				<option value="0"></option>
				<option value="Arquivar.">Arquivar</option>
				<option value="Ciente.">Ciente</option>
				<option value="Para conhecimento.">Para conhecimento</option>
				<option value="Providenciar.">Providenciar</option>
				<option value="Para conhecimento e providências.">Para conhecimento e providências</option>
				<option value="Providências em andamento.">Providências em andamento</option>
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
    
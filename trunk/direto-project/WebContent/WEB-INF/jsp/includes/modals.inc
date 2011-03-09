<div id="notificacoes" class="tooltip">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Notificações</td>
		</tr>
	</table>
	<p id="p_notificacoes"><span id="s_sem_notificacoes" style="display: none;">Sem notificacoes.</span></p>
</div>


<!-- MODALS -->

<div id="boxes">

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
			<td align="center" bgcolor="#1E90FF" width="96%" class="titulo_confirmacao" height="20" valign="middle" id="titulo"></td>
			<td width="4%"><a href="#" class="close" style="font-weight: bold">X</a></td>
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
<textarea onkeypress="return js.direto.charProibido(event)" cols="76" rows="2" onkeyup="" id="Tdespachar"></textarea>
<div id="progdespachar">(0 / 500)</div>
</td>
		
		</tr>
		
		<tr>
			<td height="35" valign="bottom" colspan="2"><input type="button" id="bt_conf_edicao" value="Salvar" name="bt_editar_sim"></td>
		</tr>
		
	</table>


</div>
  
<!-- Start of Login Dialog -->  
<div id="wgrupos" class="window">
    <table align=center cellPadding=10 cellSpacing=10 width=80%>
		<tr>
			<td>
			<TABLE border=0 cellpadding=3 cellspacing=0 width=90% class=table>
				<TBODY>
					<TR>
						<TD colspan="3" align="center">
						<b>Grupo:</b> <SELECT
							name=slGrupo id="slGrupo" style="width: 200pt" LANGUAGE=javascript
							onchange="carregaGrupo()" onfocus="getGrupos()">
							<option id="0" value="0" selected>Selecione o grupo</option>
							
						</SELECT></TD>
					</TR>
					<TR>
						<TD style="width: 40%">
						<FIELDSET><LEGEND>&nbsp;<b>Usuários</b>&nbsp;</LEGEND> <SELECT
							name=ListaDE id=ListaDE multiple size=11 style="width: 310px;"
							ondblClick="mover($('ListaDE'), $('ListaPARA')); return false;">
	
	
						</SELECT></FIELDSET>
						</TD>
						<TD style="text-align: center" style="width:20%"><INPUT
							type="button" value="Incluir >>" style="width: 90pt"
							id=btParaDireita
							onClick="mover($('ListaDE'), $('ListaPARA')); return false;">
						<INPUT type="button" value="<< Remover" style="width: 90pt"
							name=btExclui id=btParaEsquerda
							onClick="remover($('ListaPARA'), $('ListaDE')); return false;">
						</TD>
	
						<TD style="text-align: center" style="width:40%">
						<FIELDSET><LEGEND>&nbsp;<b>Destinatário</b>&nbsp;</LEGEND>
						<SELECT name=ListaPARA id=ListaPARA size=11 style="width: 310px;"
							multiple
							ondblClick="remover($('ListaPARA'), $('ListaDE')); return false;">
	
						</SELECT></FIELDSET>
						</TD>
					</TR>
					
				</TBODY>
			</TABLE>
			</td>
		</tr>
	</table>   
	<a href="#" class="send close"/>Fechar [Enviar]</a>
	<input type="hidden" id="idCarteira" value="${contaAtual}" />
</div>
<!-- End of Login Dialog -->  


<!-- Mask to cover the whole screen -->
  <div id="mask"></div>
</div>

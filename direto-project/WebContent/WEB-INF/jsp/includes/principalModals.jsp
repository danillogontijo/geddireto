<div id="notificacoes">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Notifica��es</td>
		</tr>
	</table>
	<p id="p_notificacoes"><span id="s_sem_notificacoes" style="display: none;">Sem notificacoes.</span></p>
</div>

<!-- Comentario -->
<div id="wcomentar" class="window">
	<table width="100%">
		<tr>
			<td align="center" bgcolor="#1E90FF" class="titulo_confirmacao" height="20" valign="middle">Enviar sugest�o/cr�tica</td>
			<td width="10"><a href="#" class="close" style="font-weight: bold">X</a></td>
		</tr>
		<tr>
			<td>
			<textarea id="comentario" cols="76" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td valign="bottom"><input type="button" id="bt_conf_comentario" value="Enviar"></td>
		</tr>
		
	</table>
</div>

<!-- Pesquisa Rapida -->
<div id="wpesquisar" class="window">
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center" bgcolor="#1E90FF" class="titulo_confirmacao" height="20" valign="middle" colspan="4">Pesquisa r�pida</td>
			<td width="10"><a href="#" class="close" style="font-weight: bold">X</a></td>
		</tr>
		<tr class="_size_pesquisa">
			<td>
				Escolha a caixa de pesquisa:
			</td>
			<td colspan="3">
				<select id="fast_sl_caixas" style="width: 70%;">
					<option value="0">Todas as caixas</option>
					<option value="1">Caixa de Entrada</option>
					<option value="2">Arquivados</option>
					<option value="3">Enviados</option>
					<option value="4">Pendentes</option>
				</select>
			</td>
		</tr>
		<tr class="_size_pesquisa">
			<td>
				Protocolo:
			</td>
			<td colspan="3">
				<input type="text" style="width: 70%;" id="fast_protocolo"></input>
			</td>
		</tr>
		<tr class="_size_pesquisa">
			<td>
				Assunto:
			</td>
			<td colspan="3"">
				<input type="text" style="width: 70%;" id="fast_assunto"></input>
			</td>
		</tr>
		<tr class="_size_pesquisa">
			<td>
				Data de:
			</td>
			<td>
				<input type="text" id="dataDe" style="margin: 0 10px 0 10px;"></input>
			</td>
			<td width="100px;">
				Data at�:
			</td>
			<td>
				<input type="text" id="dataAte" style="margin: 0 10px 0 10px;"></input>
			</td>
		</tr>
		<tr class="_size_pesquisa">
			<td valign="bottom" colspan="5"><input type="button" id="bt_fast_search" value="Pesquisar"></td>
		</tr>
		
	</table>
</div>

<div id="wgrupos" class="window">
	<div style="position: absolute; right: 2px; background-color: #000; padding: 2px 2px 2px 2px;"><a href="#" class="close" style="font-weight: bold; color: #fff;">X</a></div>
    <table align=center cellPadding=10 cellSpacing=10 width=80% id="wgrupos_corpo">
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
						<FIELDSET><LEGEND>&nbsp;<b>Usu�rios</b>&nbsp;</LEGEND> <SELECT
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
						<FIELDSET><LEGEND>&nbsp;<b>Destinat�rio</b>&nbsp;</LEGEND>
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
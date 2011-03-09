<div id="notificacoes">
	<table width="100%">
		<tr>
			<td colspan="2" align="center" bgcolor="red" class="titulo_notificacoes" height="20" valign="middle">Notificações</td>
		</tr>
	</table>
	<p id="p_notificacoes"><span id="s_sem_notificacoes" style="display: none;">Sem notificacoes.</span></p>
</div>

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
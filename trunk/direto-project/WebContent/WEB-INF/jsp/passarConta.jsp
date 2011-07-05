<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<%@ include file="include_head.jsp" %>


		<div style="width:100%; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
			<input name="checkbox" type="checkbox" id="sel_tudo"
				onClick="js.direto.sel_chkbox_doc('16')" value="16" style="position: relative; top: 2px;" /> 
			
			Mostrar: 
			
			<a href="<c:out value="${mostrarURL}todas" />" style="margin-left: 5px;" class="menu2">Todas</a> |
			
			<a href="<c:out value="${mostrarURL}naolidas" />" style="margin-left: 5px;" class="menu2">Não lidas</a> |
			
			<a href="<c:out value="${mostrarURL}urgentes" />" style="margin-left: 5px;" class="menu2">Urgentes</a> |
				
			<a href="#wgrupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar selecionadas</a>  |	
			
			<a href="#acompanhar" style="margin-left: 5px;" class="menu2" name="acompanhar">Acompanhar</a>
			
			<c:if test="${box == 2}">
				<a href="javascript:Arquivar(4);" class="menu1">Apagar</a> |
			</c:if>
			
			<div style="position: relative; float: right">
				Ordernação:
				<select	name=slOrdenacao id="slOrdenacao" style="width: 100pt; font-style: normal; font-size: 12px; color: black; margin-right: 3px;" onchange="ordenacao()">
					<option value=0 <c:if test="${ordenacao == 0}">selected</c:if>>Prioridade</option>
					<option value=1 <c:if test="${ordenacao == 1}">selected</c:if>>Data decrescente</option>
					<option value=2 <c:if test="${ordenacao == 2}">selected</c:if>>Data crescente</option>
				</select>
			</div>
		
		</div>
		
		
		<!-- INICIO CORPO -->
		<div style="width:100%; text-align:center; float: left; position: static; width: 822px; vertical-align: middle;">
		
		
			<table width="100%">
			
				<tr>
					<td align="center">
					
					<fieldset>
						<label>Digite o login do usuário para o qual deseja trasferir as contas abaixo:</label>
						<input type="text" id="usuLogin" name="usuLogin">
					
					
					<p>
					
						<label style="">Selecione as contas:</label>
						<c:forEach var="conta" items="${usuario.contas}">
			    		     <c:choose>
						      <c:when test="${conta.carteira.idCarteira == contaAtual}">
						     	<input type="checkbox" name="contas" value="${conta.carteira.idCarteira}"" id="conta_principal" /><span style="font-weight: bold;">${conta.carteira.cartAbr}</span>
						      </c:when>
						
						      <c:otherwise>
						      	<input type="checkbox" name="contas" value="${conta.carteira.idCarteira}"" />${conta.carteira.cartAbr}
						      </c:otherwise>
						    </c:choose>
						    <c:if test="${conta.contaPrincipal == 1}"><br></c:if>
						</c:forEach>
					</p>	
			
					</fieldset>
					
					 </td>
				</tr>
			
			</table>
			
			
		
		
		</div>
		<!-- FIM CORPO -->
		
<%@ include file="include_foot.jsp" %>

</body>
</html>
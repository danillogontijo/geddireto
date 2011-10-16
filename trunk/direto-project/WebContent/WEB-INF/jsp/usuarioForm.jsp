<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<%@ include file="include_head.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioValidatorJS.js"></script>

<script language="JavaScript">

<c:if test="${primeiroAcesso}">

$j(function(){
	
	var alertaInicial = "Devido a falta ou informa��es incompletas "+
		"de alguns dados que nos foram enviados pelas Se�, n�o foi poss�vel "+ 
		"cadastrar todas as carteiras e vincul�-las a seus usu�rios. Portanto, pe�o que, caso haja "+
		"alguma inconsist�ncia ou todas as PASTAS (caixa entrada, arquivados, etc) "+
		"estejam VAZIAS, contate o ramal 4440 ou 4470, tendo em m�os a descri��o completa e "+
		"abreviatura da carteira de como deseja que apare�a para os outros usu�rios, al�m de "+ 
		"todos os grupos a qual fazia parte na vers�o anterior do GED. "+
		"Confira seus dados cadastrais completando o campo IDENTIDADE. "+
		"D�vidas, cr�ticas e/ou sugest�es utilize o link localizado "+
		"na parte superior ao lado de SAIR, n�o se esquecendo de "+
		"nos informar o nr ramal para que possamos, posteriormente, entrarmos em contato.<br>Att. Eqp Des Se� Infor.";
		
	alertMessage('ATEN��O',alertaInicial,false);
	
});

</c:if>

function validateField(element)
{
	var valor = element.value;
	if(element.id == "repeatedPassword")
		valor = document.getElementById("pass").value+"_"+element.value;
		
	
	usuarioValidatorJS.getMessageValidator('UsuarioForm',element.id, valor, {
  		callback:function(dataFromServer) {
  			setInputFieldStatus(element.id, dataFromServer);
  		}
  	});	  	
}

function setInputFieldStatus(elementId, message)
{
	var name = $j('#'+elementId).attr('field');
	if(message != "" || elementId == "repeatedPassword"){
		if (message == "")
			message = "Senhas conferem";
		updateTips( r('{0}',name,message) );
	}
	//document.getElementById("" + elementId + "Error").innerHTML = message;
}

function r(name, replacement, message){ return message.replace(name, replacement); }

function pstGradAll(){
	
	pstgradJS.getAll({
		callback:function(pstgradList) {
			dwr.util.addOptions(field_name, pstgradList, "idPstGrad", "pstgradNome");
 		}
  	});
}


function updateTips( t ) {
	var tips = $j( ".validateTips" );
	tips
		.text( t )
		.addClass( "ui-state-highlight" );
	setTimeout(function() {
		tips.removeClass( "ui-state-highlight", 1500 );
	}, 500 );
}

</script>

<style  type="text/css">

.validateTips { border: 1px solid transparent; padding: 0.3em; color: red;}
input[type=text] {width: 330px;}

</style>

<!-- INICIO SUBMENU 2 DO CORPO PRINCIPAL -->
			<div style="background-color: #B8C9DD;" class="ui_main_body ui_subMenu _width_main_body _font_normal">
				<span style="font-weight: bold;">Dados Cadastrais</span>
			</div>
			
			<!-- INICIO CORPO PRINCIPAL -->
			<div class="ui_main_body _width_main_body" style="background-color: #fff; min-height: 640px;">
				
			<form:form method="POST" commandName="usuarioForm">
				<table style="background-color: #fff; margin: 20px auto;">
					<tr>
						<td colspan="2">
						
						<p class="validateTips">
							<!-- Support for Spring errors object -->
							<spring:bind path="usuarioForm.*">
							  <c:forEach var="error" items="${status.errorMessages}">
							      <br><c:out value="${status.expression} ${error}"/>
							  </c:forEach>
							</spring:bind>
						</p>	
						</td>
					</tr>
				
					<tr>
						<td>Nome Completo :</td>
						<td>
						
						<spring:bind path="usuarioForm.usuNome">  
					  		<input type="text"
					  		 field="Nome Completo" 
					  		 name="<c:out value="${status.expression}"/>"
					  		 id="<c:out value="${status.expression}"/>" 
					  		 value="<c:out value="${status.value}"/>" 
					  		 onChange="validateField(this);"
					  		/>
					  	</spring:bind>
						
						</td>
					</tr>
					<tr>
						<td>Nome de Guerra :</td>
						<td>
						<spring:bind path="usuarioForm.usuNGuerra">  
					  		<input type="text"
					  		 field="Nome de Guerra"  
					  		 name="<c:out value="${status.expression}"/>"
					  		 id="<c:out value="${status.expression}"/>" 
					  		 value="<c:out value="${status.value}"/>" 
					  		 onChange="validateField(this);"
					  		/>
				  		</spring:bind>
						</td>
					</tr>
					<tr>
						<td>Identidade :</td>
						<td>
						<spring:bind path="usuarioForm.usuIdt">  
					  		<input type="text"
					  		 field="Identidade"
					  		 name="<c:out value="${status.expression}"/>"  
					  		 id="<c:out value="${status.expression}"/>" 
					  		 value="<c:out value="${status.value}"/>" 
					  		 onChange="validateField(this);"
					  		/>
				  		</spring:bind>
						</td>
					</tr>
					
					<tr>
						<td>Pst/Grad :</td>
						<td>
						
								
						<form:select path="usu_pstGrad" >
						
						<c:forEach var="pstGrad" items="${pstGradAll}">
							<form:option value="${pstGrad.idPstGrad}">${pstGrad.pstgradNome}</form:option>
						</c:forEach>
			                      
			            </form:select>
					
						
						</td>
					</tr>
					
					<tr bgcolor="white"><td>
						<font color="red">* </font>Nova senha:</td><td>
			  			<form:password path="usuSenha" id="pass"/>
				  	</td></tr>
					
					<tr bgcolor="white"><td>
						<font color="red">* </font>Repita a senha:</td><td>
			  			<spring:bind path="usuarioForm.repeatedPassword">
				  			<input type="password"
				  				name="<c:out value="${status.expression}"/>" 
					  		 	id="<c:out value="${status.expression}"/>" 
					  			value="<c:out value="${status.value}"/>"
								onkeyup="validateField(this);"	  			
				  			/>
				  			&nbsp;
					  		<span id="<c:out value="${status.expression}"/>Error" class="error">
					  			<c:out value="${status.errorMessage}"/>
					  		</span>
			  			</spring:bind>
					</td></tr>
					
					<tr>
						<td colspan="2" align="center"><br><input type="submit" value="Alterar"></td>
					</tr>
				</table>
			</form:form>

		
			</div>
		</div>
	</div>
	
	<%@ include file="include_foot.jsp" %>

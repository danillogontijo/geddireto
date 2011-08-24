<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<%@ include file="include_head.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioValidatorJS.js"></script>

<script language="JavaScript">

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


<div style="width:100%; text-align:center; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
			<span style="font-weight: bold;">Dados Cadastrais</span>
		
		</div>
		
		
		<!-- INICIO CORPO -->
		<div style="width:100%; text-align:left; float: left; position: static; width: 822px; vertical-align: middle;">
		
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
		<!-- FIM CORPO -->
		
<%@ include file="include_foot.jsp" %>

</body>
</html>






</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>
</head>
<body>

<script language="JavaScript">

DWREngine.setErrorHandler(null); // Swallow errors and warnings from DWR; handle appropriately in your app
DWREngine.setWarningHandler(null);

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
	document.getElementById("" + elementId + "Error").innerHTML = message;
}


function pstGradAll(){
	
	pstgradJS.getAll({
		callback:function(pstgradList) {
			dwr.util.addOptions(field_name, pstgradList, "idPstGrad", "pstgradNome");
 		}
  	});
}
</script>

<!-- Support for Spring errors object -->
<spring:bind path="usuarioForm.*">
  <c:forEach var="error" items="${status.errorMessages}">
    <B><FONT color=RED>
      <BR><c:out value="${status.expression}: ${error}"/>
    </FONT></B>
  </c:forEach>
</spring:bind>

<form:form method="POST" commandName="usuarioForm">
	<table>
		<tr>
			<td>Nome Completo :</td>
			<td>
			
			<spring:bind path="usuarioForm.usuNome">  
		  		<input type="text"
		  		 name="<c:out value="${status.expression}"/>" 
		  		 id="<c:out value="${status.expression}"/>" 
		  		 value="<c:out value="${status.value}"/>" 
		  		 onChange="validateField(this);"
		  		/>
		  		&nbsp;
		  		<span id="<c:out value="${status.expression}"/>Error" class="error">
		  			<c:out value="${status.errorMessage}"/>
		  		</span>
	  		</spring:bind>
			
			</td>
		</tr>
		<tr>
			<td>Nome de Guerra :</td>
			<td>
			<spring:bind path="usuarioForm.usuNGuerra">  
		  		<input type="text"
		  		 name="<c:out value="${status.expression}"/>" 
		  		 id="<c:out value="${status.expression}"/>" 
		  		 value="<c:out value="${status.value}"/>" 
		  		 onChange="validateField(this);"
		  		/>
		  		&nbsp;
		  		<span id="<c:out value="${status.expression}"/>Error" class="error">
		  			<c:out value="${status.errorMessage}"/>
		  		</span>
	  		</spring:bind>
			</td>
		</tr>
		<tr>
			<td>Identidade :</td>
			<td><form:input path="usuIdt" /></td>
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
			<td colspan="2"><input type="submit"></td>
		</tr>
	</table>
</form:form>




</body>
</html>
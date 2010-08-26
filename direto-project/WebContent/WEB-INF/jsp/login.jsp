<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="title"/></title>
	
<!-- Inicio Folha de Estilos -->
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<!-- Fim Folha de Estilos -->
    
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/usuarioJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/loginValidatorJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/util.js"></script>

<script language="JavaScript">

//DWREngine.setErrorHandler(null);
//DWREngine.setWarningHandler(null);


function validateAccountInputField(element){
	//loginValidatorJS.getMessageValidator(element.id, element.value,setInputFieldStatus(element.id, dataFromServer));
	//usuarioDwr.validateUser(usuLogin,validateCallback);
}

function validateCallback(retorno){

	var message;
	
	if(retorno==true){
		//message = ${login.USER_LOGIN_INVALIDATE};
		alert("usuario ok");
		var usuLogin = $('j_username').value;
		usuarioDwr.listActivedContas(usuLogin,montaContas);
			
	}else{
		//message = <c:out value="${login.USER_LOGIN_INVALIDATE}" />;
		alert("usuario inexistente");
	}	
}



function validateAccountInputField(element)
{

	loginValidatorJS.getMessageValidator(element.id, element.value, {
		callback:function(dataFromServer) {
			if (dataFromServer == "")
				usuarioJS.listActivedContas(element.value,montaContas);
			setInputFieldStatus(element.id, dataFromServer);
  		}
  	});
 	  	
}

function setInputFieldStatus(elementId, message)
{
	document.getElementById("" + elementId + "Error").innerHTML = message;
}

function montaContas(listBeans){
	if (listBeans != null){
		dwr.util.removeAllOptions('j_usuario_conta');
		dwr.util.addOptions('j_usuario_conta', listBeans, "id", "texto");
	}
	
}
</script>    
    
</head>

<body onload="document.f.j_username.focus();">
  

<div id="table" style="position: absolute; width: 1002px; top: 0; left: 0;">

	<div id="line" style="width: 1002px;float: left; height: 43px;">
		<div style="float: left;"><img name="direto_r1_c1" src="imagens/direto_r1_c1.jpg" width="189" height="43" border="0" id="direto_r1_c1" usemap="#m_direto_r1_c1" alt="" /></div>
		<div style="float: left; line-height:43px; background-image: url('imagens/direto_r1_c2.jpg'); width: 813px; height:43px; text-align: center;" class="menu_titulo">
		 	
		 	<a href="alterar_senha.jsp" class="menu_titulo">Alterar Senha</a> | 
		 	<a href="comentario.jsp" class="menu_titulo">Sugestões</a> | 
		 	<a href="passar_conta.jsp" class="menu_titulo">Passar Conta</a> |
			<a href="dados_cadastro.jsp?modo=ver" class="menu_titulo">Dados Cadastrais</a> |
			<a href="configuracao.jsp" class="menu_titulo">Configurações</a> |  
			<a href="logout.jsp" class="menu_titulo">Sair</a>
			 
   		</div>
   		<div style="float: left;"><img src="imagens/spacer.gif" width="1" height="43" border="0" alt="" /></div>
	</div>


	<div id="line" style="width: 1002px; float: left; height: 29px;">
   		<div style="float: left;"><img name="direto_r2_c1" src="imagens/head_complemento.jpg" width="117" height="29" border="0" id="direto_r2_c1" usemap="#m_direto_r2_c1" alt="" /></div>
   		<div style="float: left;"><img name="direto_r2_c2" src="imagens/direto_r2_c2.jpg" width="813" height="29" border="0" id="direto_r2_c2" alt="" /></div>
   		<div style="float: left; background-image: url('imagens/head_complemento.jpg');width: 72px;height:29px;"></div>
	</div>

</div>

    
<div id="table" style="position: absolute; width: 330px; text-align: center; margin-left: -165px; left: 501px; margin-top: -160px;">
	<spring:bind path="loginForm.j_username">
	  	<span id="<c:out value="${status.expression}"/>Error" class="error">
		  	<c:if test="${not empty param.login_error}">
		        Sua tentativa de login não obteve sucesso, tente novamente.<br/>
		        Causa: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
		    </c:if>
		  	<c:out value="${status.errorMessage}"/>
		</span>
	</spring:bind>    
</div>
    


<div id="login">
	<div class="bordaBox">
		<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div> LOGIN </div>
		<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
	</div>
</div>

<form name="f" action="<c:url value='j_spring_security_check'/>" id="form_login" method="POST">
<div id="table" style="position: absolute; width: 330px; margin-left: -165px; left: 501px; margin-top: -83px;">
	<div class="bordaBox">
		<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="conteudo">
				<div id="line" style="height: 45px; line-height: 50px; padding-top: 5px;">
						<div id="column">
							Usuário 
							
							<spring:bind path="loginForm.j_username">  
		 						 <input type="text"	name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" 
		  		 					value="<c:out value="${status.value}"/>" 
		  		 					onBlur="validateAccountInputField(this);" />
	  						</spring:bind>
							
							
							<!-- <input type='text' name='j_username' id="j_username" onblur="validaLogin(this.value)" value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/> --> 
						</div>
				
				</div>
				
				<div id="line" style="height: 45px; line-height: 50px;">
					<div id="column">
						Senha <input type="password" name="j_password" id="j_password"> <br>		
					</div>
				</div>
				
				<div id="line" style="height: 45px; line-height: 50px;">
					<div id="column">
					Contas <select id="j_usuario_conta" name="j_usuario_conta"><option>Selecione sua conta</option></select></div>
				</div>
				
				<div id="line" style="height: 30px; text-align: center; margin-top: 10px;">
					<input type="submit" id="j_button" value="Entrar" />
				</div>
				
				<div id="line" style="height: 20px; text-align: center; margin-top: 0px;">
					<input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me">Lembrar-me
				</div>
				
			</div>
		<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
	</div>
</div>
</form>
	
<div id="table" style="position: absolute; width: 1002px; top: 50%; left: 0; margin-top: 160px; text-align: center;">
	<font class="rodape">
		© 2010 - Direto - Gerenciador Eletrônico de Documentos<br>
					Ver. 3.0
	</font>
</div>
	





  </body>
</html>

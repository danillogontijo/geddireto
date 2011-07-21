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
<link href="" rel="stylesheet" type="text/css" />
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

	loginValidatorJS.getMessageValidator('LoginForm',element.id, element.value, {
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

function teclaEnter(e){
	
	var evento = (window.event ? e.keyCode : e.which);

	if(evento == 13) {
		GerarCookie('usuLogin', $('j_username').value, 355);
		return document.f.submit();
	}

//alert(e);
}

function logar(){
	if($('j_password').value != ""){
		GerarCookie('usuLogin', $('j_username').value, 355);
		return document.f.submit();
	}else
		alert("Digite a senha.");
}

//Função para ler o cookie.
function LerCookie(strCookie)
{
    var strNomeIgual = strCookie + "=";
    var arrCookies = document.cookie.split(';');

    for(var i = 0; i < arrCookies.length; i++)
    {
        var strValorCookie = arrCookies[i];
        while(strValorCookie.charAt(0) == ' ')
        {
            strValorCookie = strValorCookie.substring(1, strValorCookie.length);
        }
        if(strValorCookie.indexOf(strNomeIgual) == 0)
        {
            return strValorCookie.substring(strNomeIgual.length, strValorCookie.length);
        }
    }
    return null;
}

// Função para excluir o cookie desejado.
function ExcluirCookie(strCookie)
{
    GerarCookie(strCookie, '', -1);
}


//Armazena o login do usuario no form
function armazenaLoginForm(){
	var usuLogin = LerCookie('usuLogin');

	if (usuLogin != null){
		$('j_username').value = usuLogin;
	}
}

//Função para criar o cookie.
//Para que o cookie seja destruído quando o browser for fechado, basta passar 0 no parametro lngDias.
function GerarCookie(strCookie, strValor, lngDias)
{
 var dtmData = new Date();

 if(lngDias)
 {
     dtmData.setTime(dtmData.getTime() + (lngDias * 24 * 60 * 60 * 1000));
     var strExpires = "; expires=" + dtmData.toGMTString();
 }
 else
 {
     var strExpires = "";
 }
 document.cookie = strCookie + "=" + strValor + strExpires + "; path=/";
}


</script>    
    
</head>

<body onload="document.f.j_username.focus(); armazenaLoginForm();">
  
<style type="text/css">
#tudo {
height: 500px;
width: 500px;
margin: 0 auto;
text-align: left;
}
</style>

<div id="tudo">
    
<div id="table" style="position: absolute; width: 330px; text-align: center; margin-left: -165px; left: 501px; margin-top: -160px; top: 50%;">
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

<form name="f" action="<c:url value='j_spring_security_filter'/>" id="form_login" method="POST">
<div id="table" style="position: absolute; width: 330px; margin-left: -165px; left: 501px; margin-top: -83px; top: 50%;">
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
						Senha <input type="password" name="j_password" id="j_password" onkeypress="teclaEnter(event)"> <br>		
					</div>
				</div>
				
				<div id="line" style="height: 45px; line-height: 50px;">
					<div id="column">
					Contas <select id="j_usuario_conta" name="j_usuario_conta"><option>Selecione sua conta</option></select></div>
				</div>
				
				<div id="line" style="height: 30px; text-align: center; margin-top: 10px;">
					<input type="button" id="j_button" value="Entrar" onclick="logar()" />
				</div>
				
				<div id="line" style="height: 20px; text-align: center; margin-top: 0px;">
					<input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me">Lembrar-me
				</div>
				
			</div>
		<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
	</div>
</div>
</form>
	
<div id="table" style="position: absolute; background-color: #fff; width: 330px; top: 50%; margin-top: 160px; margin-left: 15px; text-align: center;">
	<font class="rodape">
		© 2011 - Direto - Gerenciador Eletrônico de Documentos<br>
					Ver. 3.0
	</font>
</div>
	

</div>


  </body>
</html>
